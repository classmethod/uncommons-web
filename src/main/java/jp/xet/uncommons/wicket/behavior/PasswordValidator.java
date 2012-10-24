/*
 * Copyright 2011 Daisuke Miyamoto.
 * Created on 2011/11/04
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
package jp.xet.uncommons.wicket.behavior;

import java.util.Arrays;

import org.apache.wicket.util.lang.Args;
import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * 基準を満たすパスワードかどうかを判定するバリデータ。
 * 
 * @since 1.0.0
 * @version $Id: PasswordValidator.java 2297 2012-01-19 06:41:23Z miyamoto $
 * @author daisuke
 */
@SuppressWarnings("serial")
public final class PasswordValidator extends CompoundValidator<String> {
	
	private static final int DEFAULT_MIN_LENGTH = 6;
	
	private static final int DEFAULT_MAX_LENGTH = 32;
	
	// FORMAT-OFF
	// http://www.whatsmypass.com/the-top-500-worst-passwords-of-all-time
	private static final String[] WEAKS = {
		"123456", "porsche", "firebird", "prince", "rosebud", 
		"password", "guitar", "butter", "beach", "jaguar", 
		"12345678", "chelsea", "united", "amateur", "great", 
		"1234", "black", "turtle", "7777777", "cool", 
		"pussy", "diamond", "steelers", "muffin", "cooper", 
		"12345", "nascar", "tiffany", "redsox", "1313", 
		"dragon", "jackson", "zxcvbn", "star", "scorpio", 
		"qwerty", "cameron", "tomcat", "testing", "mountain", 
		"696969", "654321", "golf", "shannon", "madison", 
		"mustang", "computer", "bond007", "murphy", "987654", 
		"letmein", "amanda", "bear", "frank", "brazil", 
		"baseball", "wizard", "tiger", "hannah", "lauren", 
		"master", "xxxxxxxx", "doctor", "dave", "japan", 
		"michael", "money", "gateway", "eagle1", "naked", 
		"football", "phoenix", "gators", "11111", "squirt", 
		"shadow", "mickey", "angel", "mother", "stars", 
		"monkey", "bailey", "junior", "nathan", "apple", 
		"abc123", "knight", "thx1138", "raiders", "alexis", 
		"pass", "iceman", "porno", "steve", "aaaa", 
		"fuckme", "tigers", "badboy", "forever", "bonnie", 
		"6969", "purple", "debbie", "angela", "peaches", 
		"jordan", "andrea", "spider", "viper", "jasmine", 
		"harley", "horny", "melissa", "ou812", "kevin", 
		"ranger", "dakota", "booger", "jake", "matt", 
		"iwantu", "aaaaaa", "1212", "lovers", "qwertyui", 
		"jennifer", "player", "flyers", "suckit", "danielle", 
		"hunter", "sunshine", "fish", "gregory", "beaver", 
		"fuck", "morgan", "porn", "buddy", "4321", 
		"2000", "starwars", "matrix", "whatever", "4128", 
		"test", "boomer", "teens", "young", "runner", 
		"batman", "cowboys", "scooby", "nicholas", "swimming", 
		"trustno1", "edward", "jason", "lucky", "dolphin", 
		"thomas", "charles", "walter", "helpme", "gordon", 
		"tigger", "girls", "cumshot", "jackie", "casper", 
		"robert", "booboo", "boston", "monica", "stupid", 
		"access", "coffee", "braves", "midnight", "shit", 
		"love", "xxxxxx", "yankee", "college", "saturn", 
		"buster", "bulldog", "lover", "baby", "gemini", 
		"1234567", "ncc1701", "barney", "cunt", "apples", 
		"soccer", "rabbit", "victor", "brian", "august", 
		"hockey", "peanut", "tucker", "mark", "3333", 
		"killer", "john", "princess", "startrek", "canada", 
		"george", "johnny", "mercedes", "sierra", "blazer", 
		"sexy", "gandalf", "5150", "leather", "cumming", 
		"andrew", "spanky", "doggie", "232323", "hunting", 
		"charlie", "winter", "zzzzzz", "4444", "kitty", 
		"superman", "brandy", "gunner", "beavis", "rainbow", 
		"asshole", "compaq", "horney", "bigcock", "112233", 
		"fuckyou", "carlos", "bubba", "happy", "arthur", 
		"dallas", "tennis", "2112", "sophie", "cream", 
		"jessica", "james", "fred", "ladies", "calvin", 
		"panties", "mike", "johnson", "naughty", "shaved", 
		"pepper", "brandon", "xxxxx", "giants", "surfer", 
		"1111", "fender", "tits", "booty", "samson", 
		"austin", "anthony", "member", "blonde", "kelly", 
		"william", "blowme", "boobs", "fucked", "paul", 
		"daniel", "ferrari", "donald", "golden", "mine", 
		"golfer", "cookie", "bigdaddy", "0", "king", 
		"summer", "chicken", "bronco", "fire", "racing", 
		"heather", "maverick", "penis", "sandra", "5555", 
		"hammer", "chicago", "voyager", "pookie", "eagle", 
		"yankees", "joseph", "rangers", "packers", "hentai", 
		"joshua", "diablo", "birdie", "einstein", "newyork", 
		"maggie", "sexsex", "trouble", "dolphins", "little", 
		"biteme", "hardcore", "white", "0", "redwings", 
		"enter", "666666", "topgun", "chevy", "smith", 
		"ashley", "willie", "bigtits", "winston", "sticky", 
		"thunder", "welcome", "bitches", "warrior", "cocacola", 
		"cowboy", "chris", "green", "sammy", "animal", 
		"silver", "panther", "super", "slut", "broncos", 
		"richard", "yamaha", "qazwsx", "8675309", "private", 
		"fucker", "justin", "magic", "zxcvbnm", "skippy", 
		"orange", "banana", "lakers", "nipples", "marvin", 
		"merlin", "driver", "rachel", "power", "blondes", 
		"michelle", "marine", "slayer", "victoria", "enjoy", 
		"corvette", "angels", "scott", "asdfgh", "girl", 
		"bigdog", "fishing", "2222", "vagina", "apollo", 
		"cheese", "david", "asdf", "toyota", "parker", 
		"matthew", "maddog", "video", "travis", "qwert", 
		"121212", "hooters", "london", "hotdog", "time", 
		"patrick", "wilson", "7777", "paris", "sydney", 
		"martin", "butthead", "marlboro", "rock", "women", 
		"freedom", "dennis", "srinivas", "xxxx", "voodoo", 
		"ginger", "fucking", "internet", "extreme", "magnum", 
		"blowjob", "captain", "action", "redskins", "juice", 
		"nicole", "bigdick", "carter", "erotic", "abgrtyu", 
		"sparky", "chester", "jasper", "dirty", "777777", 
		"yellow", "smokey", "monster", "ford", "dreams", 
		"camaro", "xavier", "teresa", "freddy", "maxwell", 
		"secret", "steven", "jeremy", "arsenal", "music", 
		"dick", "viking", "11111111", "access14", "rush2112", 
		"falcon", "snoopy", "bill", "wolf", "russia", 
		"taylor", "blue", "crystal", "nipple", "scorpion", 
		"111111", "eagles", "peter", "iloveyou", "rebecca", 
		"131313", "winner", "pussies", "alex", "tester", 
		"123123", "samantha", "cock", "florida", "mistress", 
		"bitch", "house", "beer", "eric", "phantom", 
		"hello", "miller", "rocket", "legend", "billy", 
		"scooter", "flower", "theman", "movie", "6666", 
		"please", "jack", "oliver", "success", "albert"
	};
	// FORMAT-ON
	
	/**
	 * インスタンスを生成する。
	 */
	public PasswordValidator() {
		this(null, DEFAULT_MIN_LENGTH, DEFAULT_MAX_LENGTH);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param minLength 最短文字数
	 * @param maxLength 最長文字数
	 * @throws IllegalArgumentException 引数{@code minLength}が{@code maxLength}よりも大きい場合
	 */
	public PasswordValidator(int minLength, int maxLength) {
		this(null, minLength, maxLength);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param username ユーザ名
	 */
	public PasswordValidator(String username) {
		this(username, DEFAULT_MIN_LENGTH, DEFAULT_MAX_LENGTH);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param username ユーザ名
	 * @param minLength 最短文字数
	 * @param maxLength 最長文字数
	 * @throws IllegalArgumentException 引数{@code minLength}が{@code maxLength}よりも大きい場合
	 */
	public PasswordValidator(String username, int minLength, int maxLength) {
		Args.isTrue(minLength <= maxLength, "minLength must to be smaller than or equal to maxLength");
		
		if (username != null) {
			add(new PatternValidator(username) {
				
				@Override
				protected String resourceKey() {
					return "PasswordValidator.username";
				}
			}.setReverse(true));
		}
		add(StringValidator.lengthBetween(minLength, maxLength));
		
		add(new ContainsValidator<String>(Arrays.asList(WEAKS)) {
			
			@Override
			protected String resourceKey() {
				return "PasswordValidator.weak";
			}
			
		}.setReverse(true));
	}
	
}
