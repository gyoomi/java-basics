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


















}
