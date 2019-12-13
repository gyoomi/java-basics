# 超大集合数据分片策略

## 一、实际场景

实际项目开发中常常为遇到超大数据量的集合，比如说一个size为100w的List。而往往实际应用中,我们不可能把这么大的数据量进行发送或传递，
除了传递耗时过长，还有可能造成服务器内存溢出。所以我们往往将大集合进行数据分片，多次发送来提高灵活性和性能。

## 二、解决思路

将大集合拆分成若干个集合，然后分别发送或处理这些子集合。

## 三、实现方法

### 3.1 Guavas

```java
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
}
```

### 3.2 Common-Collections

```java
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
	 * 2. 使用Apache Common Collections工具包中Lists.partition()方法
	 *    下面的操作就是将ids按照每个集合10000个的大小进行分割。（注意，最后一个集合可能元素不足10000个）
	 *
	 */
	@Test
	public void testCommonCollections() {
		List<List<String>> partitionList = ListUtils.partition(ids, 10000);
		System.out.println(partitionList.size());
	}

}
```

### 3.3 手动编码实现

```java
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
```


## 四、总结

注意： 拆分后的每个子集合的数据，必须是可以执行的独立业务单元数据。




































