/**
 * Copyright © 2019, Glodon Digital Supplier BU.
 * <p>
 * All Rights Reserved.
 */

package com.gyoomi.basics.slices;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * The description of class
 *
 * @author Leon
 * @date 2019-12-13 10:17
 */
public class Main {

	public static List<String> ids;

	static {
		// 构造测试数据
		ids = new ArrayList<>(1500000);
		for (int i = 0; i < 1335810; i++) {
			ids.add(UUID.randomUUID().toString());
		}
	}


	/**
	 * 1. 使用Guava工具包中Lists.partition()方法
	 *    下面的操作就是将ids按照每个集合10000个的大小进行分割。（注意，最后一个集合可能元素不足10000个）
	 *
	 */
	@Test
	public void testGuavaTools() {
		List<List<String>> partitionList = Lists.partition(ids, 10000);
		System.out.println(partitionList.size());
	}

	/**
	 * 2. 使用Apache Common Collections工具包中Lists.partition()方法
	 *    下面的操作就是将ids按照每个集合10000个的大小进行分割。（注意，最后一个集合可能元素不足10000个）
	 *
	 */
	@Test
	public void testCommonCollections() {
		List<List<String>> partitionList = ListUtils.partition(ids, 10000);
		System.out.println(partitionList.size());
	}


	/**
	 * 3. 使用Stream流来进行集合的数据分片 待完成
	 *    下面的操作就是将ids按照每个集合10000个的大小进行分割。（注意，最后一个集合可能元素不足10000个）
	 *    TODO
	 */
	@Test
	public void testStream() {
		long l = Stream.iterate(1L, i -> i + 1).limit(3).reduce(Long::sum).get();
		System.out.println(l);
	}

	/**
	 * 4. 手动实现集合的数据分片
	 *    下面的操作就是将ids按照每个集合10000个的大小进行分割。（注意，最后一个集合可能元素不足10000个）
	 *
	 */
	@Test
	public void testByMe() {
		List<List<String>> partitionList = partition(ids, 10000);
		System.out.println(partitionList.size());
		System.out.println(partitionList.get(partitionList.size() - 1).size());
	}

	public static <T> List<List<T>> partition(List<T> source, int size) {
		List<List<T>> result = new ArrayList<>();
		// 1. 先计算出余数
		int remainder = source.size() % size;
		// 2. 商
		int number = (int) Math.ceil((double) source.size() / (double) size);

		// 3. 偏移量
		int offset = 0;
		List<T> value;
		for (int i = 0; i < number; i++) {
			if (i == number - 1) {
				value = source.subList(i * number + offset, i * number + offset + remainder);
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}


}
