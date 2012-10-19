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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import jp.xet.uncommons.web.env.EnvironmentProfile;
import jp.xet.uncommons.web.env.RemoteEnvironmentProfile;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.StalePageException;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.ObjectUtils;

/**
 * アプリケーション内で処理されない例外が発生した場合に、メールを送信する {@link IRequestCycleListener} 実装クラス。
 * 
 * @since 1.0
 * @version $Id$
 * @author daisuke
 */
public class ErrorReportRequestCycleListener extends AbstractRequestCycleListener implements EnvironmentAware {
	
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
	
	private Environment environment;
	
	
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
		Args.notNull(mailSender, "mailSender");
		Args.notNull(from, "from");
		Args.notNull(to, "to");
		Args.notNull(subjectPattern, "subjectPattern");
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
			Throwable rootCause = getRootCause(ex);
			if (rootCause == null) {
				rootCause = ex;
			}
			
			String environment = loadEnvironment();
			if (enabledEnvironments == null || environment == null
					|| ObjectUtils.containsElement(enabledEnvironments, environment)) {
				String type = rootCause.getClass().getSimpleName();
				String message = rootCause.getMessage();
				String subject = MessageFormat.format(subjectPattern, environment, type, message);
				
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(from);
				mailMessage.setTo(to);
				mailMessage.setSubject(subject);
				mailMessage.setText(getStackTrace(ex));
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
					Arrays.toString(enabledEnvironments),
					environment
				});
			}
		}
		return null;
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	/**
	 * 環境名を読み出す。
	 * 
	 * @return 環境名
	 * @since 1.0
	 */
	protected String loadEnvironment() {
		if (environment != null) {
			EnvironmentProfile profile = RemoteEnvironmentProfile.toEnvironmentProfile(environment.getActiveProfiles());
			return profile.toString();
		}
		
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
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("IOException should not have been thrown.", e);
				}
			}
		}
	}
	
	Throwable getRootCause(Throwable throwable) {
		List<Throwable> list = getThrowableList(throwable);
		return list.size() < 2 ? null : list.get(list.size() - 1);
	}
	
	String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
	
	List<Throwable> getThrowableList(Throwable throwable) {
		List<Throwable> list = new ArrayList<Throwable>();
		while (throwable != null && list.contains(throwable) == false) {
			list.add(throwable);
			throwable = throwable.getCause();
		}
		return list;
	}
}
