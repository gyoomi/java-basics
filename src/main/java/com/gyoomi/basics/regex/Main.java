/**
 * Copyright © 2019, Glodon Digital Supplier BU.
 * <p>
 * All Rights Reserved.
 */

package com.gyoomi.basics.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * The description of class
 *
 * @author Leon
 * @date 2019-12-26 15:55
 */
public class Main {

	@Test
	public void test01() {
		String regex = "(ab)+";
		String testString = "ababa abbb ababab";
		String testString2 = "ba";
		Matcher matcher = Pattern.compile(regex).matcher(testString);
		Matcher matcher2 = Pattern.compile(regex).matcher(testString2);
		System.out.println(matcher.find()); // true
		System.out.println(matcher2.find()); // false
	}

	@Test
	public void test02() {
		String regex = "I love (JavaScript|Java)";
		String testString = "I love JavaScript";
		String testString2 = "I love Java";
		Matcher matcher = Pattern.compile(regex).matcher(testString);
		Matcher matcher2 = Pattern.compile(regex).matcher(testString2);
		System.out.println(matcher.find()); // true
		System.out.println(matcher2.find()); // true
	}

	@Test
	public void test03() {
		String regex = "(\\d{4})-(\\d{2})-(\\d{2})";
		String date = "2019-12-30";
		Matcher matcher = Pattern.compile(regex).matcher(date);
		matcher.find();

		System.out.println(matcher.group(1)); // 2019
		System.out.println(matcher.group(2)); // 12
		System.out.println(matcher.group(3)); // 30
	}

	@Test
	public void test04() {
		String regex01 = "\\d+";
		String regex02 = "\\?|\\*";
		Pattern p1 = Pattern.compile(regex01);
		Pattern p2 = Pattern.compile(regex02);
		System.out.println(p1.pattern()); // \d+
		System.out.println(p2.pattern()); // \?|\*
	}

	@Test
	public void test05() {
		String str = "我是;最厉害;Java;哈哈";
		String[] split = Pattern.compile(";").split(str);
		Stream.of(split).forEach(System.out::println);
		// 我是
		// 最厉害
		// Java
		// 哈哈
		System.out.println("=================================");
		String[] split2 = Pattern.compile(";").split(str, 3);
		Stream.of(split2).forEach(System.out::println);
		// 我是
		// 最厉害
		// Java;哈哈
	}

	@Test
	public void test06() {
		System.out.println(Pattern.matches("\\d+", "22233")); // true
		System.out.println(Pattern.matches("\\d+", "22233aa")); // false。需要匹配到所有字符串才能返回true,这里aa不能匹配到
		System.out.println(Pattern.matches("\\d+", "222bb33")); // false。需要匹配到所有字符串才能返回true,这里bb不能匹配到
	}

	@Test
	public void test07() {
		Matcher matcher = Pattern.compile("\\d+").matcher("234214a123");
		System.out.println(matcher.matches());
	}

	@Test
	public void test08() {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher m1 = pattern.matcher("22aa33");
		boolean b1 = m1.matches();
		System.out.println(b1); // false。因为aa不能被\d+匹配,导致整个字符串匹配未成功
		Matcher m2 = pattern.matcher("22223");
		boolean b2 = m2.matches();
		System.out.println(b2); // true,因为\d+匹配到了整个字符串
	}

	@Test
	public void test09() {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher m1 = pattern.matcher("22aa33");
		boolean b1 = m1.lookingAt();
		System.out.println(b1); //true,因为\d+匹配到了前面的22
		Matcher m2 = pattern.matcher("bb22223");
		boolean b2 = m2.lookingAt();
		System.out.println(b2); // false,因为\d+不能匹配前面的bb
	}

	@Test
	public void test10() {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher m1 = pattern.matcher("22aa33");
		boolean b1 = m1.find();
		System.out.println(b1); // true
		Matcher m2 = pattern.matcher("bb22223");
		boolean b2 = m2.find();
		System.out.println(b2); // true
		Matcher m3 = pattern.matcher("222cc23");
		boolean b3 = m3.find();
		System.out.println(b3); // true
		Matcher m4 = pattern.matcher("aabbc");
		boolean b4 = m4.find();
		System.out.println(b4); // true
	}

	@Test
	public void test11() {
		Pattern p = Pattern.compile("([a-z]+)(\\d+)");
		Matcher m1 = p.matcher("aaa2223bb");
		boolean b1 = m1.find();
		System.out.println(b1); // true。匹配aaa2223
		int groupCount = m1.groupCount();
		System.out.println(groupCount); // 2。因为有2组
		int startOfGroup1 = m1.start(1);
		System.out.println(startOfGroup1); // 返回0。返回第一组匹配到的子字符串在字符串中的索引号
		int startOfGroup2 = m1.start(2);
		System.out.println(startOfGroup2); // 返回3。返回第二组匹配到的子字符串在字符串中的索引号
		int endOfGroup1 = m1.end(1);
		System.out.println(endOfGroup1); // 返回3。返回第一组匹配到的子字符串的最后一个字符在字符串中的索引位置
		int endOfGroup2 = m1.end(2);
		System.out.println(endOfGroup2); // 返回7。返回第二组匹配到的子字符串的最后一个字符在字符串中的索引位置
		String group1 = m1.group(1);
		System.out.println(group1); // 返回aaa,返回第一组匹配到的子字符串
		String group2 = m1.group(2);
		System.out.println(group2); // 返回2223,返回第二组匹配到的子字符串
	}

	@Test
	public void test12() {
		Pattern p = Pattern.compile("([a-z]+)(\\d+)");
		Matcher m = p.matcher("aaa2223bb44445ccc");
		String all = m.replaceAll("替换后");
		System.out.println(all); // 替换后替换后ccc
		Matcher m2 = p.matcher("aaa2223bb44445ccc");
		String all2 = m2.replaceFirst("替换后");
		System.out.println(all2); // 替换后bb44445ccc
	}

	@Test
	public void test13() {
		Pattern p = Pattern.compile("cat");
		Matcher m = p.matcher("one cat two cats in the yard");
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "dog");
		}
		m.appendTail(sb);
		System.out.println(sb.toString()); // one dog two dogs in the yard
	}

	@Test
	public void test14() {
		String[] idCards = {"130681198712092019", "13068119871209201x", "13068119871209201", "123456789012345", "12345678901234x", "1234567890123"};
		Pattern cardPattern = Pattern.compile("(\\d{17}[0-9a-zA-Z]|\\d{14}[0-9a-zA-Z])");
		// 用于提取出生日字符串的正则表达式
		Pattern birthdayStringPattern = Pattern.compile("\\d{6}(\\d{8}).*");
		// 将生日字符串分解为年月日的正则表达式
		Pattern birthdayPattern = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})");

		Matcher m = cardPattern.matcher("");
		for (int i = 0; i < idCards.length; i++) {
			m.reset(idCards[i]);
			System.out.println("idCard " + idCards[i] + " 是否是身份证： " + m.matches());

			// 如果它是一个合法的身份证号，提取出出生的年月日
			if (m.matches()) {
				Matcher matcher = birthdayStringPattern.matcher(idCards[i]);
				matcher.lookingAt();
				String birthdayString = matcher.group(1);
				System.out.println("birthdayString: " + birthdayString);

				Matcher matcher2 = birthdayPattern.matcher(birthdayString);
				if (matcher2.find()) {
					System.out.println("它对应的出生年月日为：" + matcher2.group(1) + "年" + matcher2.group(2) + "月" + matcher2.group(3) + "日");
				}
			}
			System.out.println("==========================================");
		}
	}
}
