/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2012/02/09
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.xet.uncommons.wicket.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.google.common.io.Closeables;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.StalePageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * アプリケーション内で処理されない例外が発生した場合に、メールを送信する {@link IRequestCycleListener} 実装クラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public class ErrorReportRequestCycleListener extends AbstractRequestCycleListener {
	
	private static Logger logger = LoggerFactory.getLogger(ErrorReportRequestCycleListener.class);
	
	private static final String DEFAULT_SUBJECT_PATTERN = "Exception report {0} - {1}: {2}";
	
	private static final String[] DEFAULT_ENABLED_ENVIRONMENTS = {
		"production",
		"staging"
	};
	
	private static final String PROP_PATH = "env.properties";
	
	private final MailSender mailSender;
	
	private String from;
	
	private String to;
	
	private String subjectPattern;
	
	private String[] enabledEnvironments;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mailSender {@link MailSender}
	 * @param from from mail address
	 * @param to to mail address
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public ErrorReportRequestCycleListener(MailSender mailSender, String from, String to) {
		this(mailSender, from, to, DEFAULT_SUBJECT_PATTERN, DEFAULT_ENABLED_ENVIRONMENTS);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mailSender {@link MailSender}
	 * @param from from mail address
	 * @param to to mail address
	 * @param subjectPattern メールのタイトルパターン文字列
	 */
	public ErrorReportRequestCycleListener(MailSender mailSender, String from, String to, String subjectPattern) {
		this(mailSender, from, to, subjectPattern, DEFAULT_ENABLED_ENVIRONMENTS);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mailSender {@link MailSender}
	 * @param from from mail address
	 * @param to to mail address
	 * @param subjectPattern mail subjectPattern
	 * @param enabledEnvironments メールを有効にする環境名。全環境を対象にする場合は{@code null}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public ErrorReportRequestCycleListener(MailSender mailSender, String from, String to, String subjectPattern,
			String[] enabledEnvironments) {
		Validate.notNull(mailSender);
		Validate.notNull(from);
		Validate.notNull(to);
		Validate.notNull(subjectPattern);
		this.from = from;
		this.to = to;
		this.subjectPattern = subjectPattern;
		this.mailSender = mailSender;
		this.enabledEnvironments = enabledEnvironments;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param mailSender {@link MailSender}
	 * @param from from mail address
	 * @param to to mail address
	 * @param enabledEnvironments メールを有効にする環境名。全環境を対象にする場合は{@code null}
	 */
	public ErrorReportRequestCycleListener(MailSender mailSender, String from, String to, String[] enabledEnvironments) {
		this(mailSender, from, to, DEFAULT_SUBJECT_PATTERN, enabledEnvironments);
	}
	
	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		if (ex instanceof PageExpiredException || ex instanceof StalePageException) {
			return null;
		}
		
		if (ex instanceof WicketRuntimeException) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			if (rootCause == null) {
				rootCause = ex;
			}
			
			String environment = loadEnvironment();
			if (enabledEnvironments == null || environment == null
					|| ArrayUtils.contains(enabledEnvironments, environment)) {
				String type = rootCause.getClass().getSimpleName();
				String message = rootCause.getMessage();
				String subject = MessageFormat.format(subjectPattern, environment, type, message);
				
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(from);
				mailMessage.setTo(to);
				mailMessage.setSubject(subject);
				mailMessage.setText(ExceptionUtils.getStackTrace(ex));
				try {
					logger.debug("sending exception report mail...");
					mailSender.send(mailMessage);
					logger.debug("success to send exception report mail");
				} catch (MailException e) {
					logger.error("failed to send exception report mail", e);
				}
			} else {
				logger.debug("exception report mail was not sent, "
						+ "because enabledEnvironments{} does not contain environment[{}]", new Object[] {
					ArrayUtils.toString(enabledEnvironments),
					environment
				});
			}
		}
		return null;
	}
	
	/**
	 * 環境名を読み出す。
	 * 
	 * @return 環境名
	 * @since 1.0
	 */
	protected String loadEnvironment() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream in = null;
		try {
			in = cl.getResourceAsStream(PROP_PATH);
			if (in == null) {
				return null;
			}
			Properties properties = new Properties();
			properties.load(in);
			return properties.getProperty("environment");
		} catch (IOException ex) {
			return "";
		} finally {
			Closeables.closeQuietly(in);
		}
	}
}
