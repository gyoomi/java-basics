# Java对集合中的元素进行排序的几种方法

## 一、前言

实际开发中，对业务数据按照指定规则进行排序是很常见的。比如说，“按照记录的创建日期降序排序”、“按照App的应用率从大到小排序”...... 在编码过程中，我们
一般在两个层面排序入手：其一、SQL脚本；其二、服务应用层面（通俗来讲就是Java代码层面）。当然，本人探讨的核心是其二。

## 二、需求设定

假如有一组学生列表，学生对象具有id、姓名、所在年级、出生日期、身高字段。需对这一组学生列表进行以下规则进行排序。

- 先按照年级从大到小排列，如果年级为空，则排在最后面
- 然后再按照出生日期从小到大排列，如果出生日期为空，排在最前面
- 最后，按照身高进行降序排序，如果身高为空，则排在最后面

## 三、代码实现

### 3.1 基础业务代码准备

**学生模型**

```java
	static class Student
{
	private String id;
	private String name;
	private Integer grade;
	private LocalDate birthday;
	private Integer height;

	public Student() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "Student{" +
			"id='" + id + '\'' +
			", name='" + name + '\'' +
			", grade=" + grade +
			", birthday=" + birthday +
			", height=" + height +
			'}';
	}
}
```

**模拟真实的测试数据**

```java
	private static List<Student> mockData()
	{
		List<Student> list = new ArrayList<Student>();
		Student s1 = new Student();
		s1.setId("s0001");
		s1.setName("张三");
		s1.setGrade(6);
		s1.setBirthday(LocalDate.of(2009, 10, 1));
		s1.setHeight(150);
		list.add(s1);

		Student s2 = new Student();
		s2.setId("s0002");
		s2.setName("李四");
		s2.setGrade(4);
		s2.setBirthday(LocalDate.of(2008, 6, 28));
		s2.setHeight(143);
		list.add(s2);

		Student s3 = new Student();
		s3.setId("s0003");
		s3.setName("王文武");
		s3.setGrade(null);
		s3.setBirthday(LocalDate.of(2012, 1, 9));
		s3.setHeight(112);
		list.add(s3);

		Student s4 = new Student();
		s4.setId("s0004");
		s4.setName("赵佳佳");
		s4.setGrade(6);
		s4.setBirthday(null);
		s4.setHeight(109);
		list.add(s4);

		Student s5 = new Student();
		s5.setId("s0005");
		s5.setName("甲家");
		s5.setGrade(6);
		s5.setBirthday(LocalDate.of(2009, 10, 1));
		s5.setHeight(169);
		list.add(s5);

		Student s6 = new Student();
		s6.setId("s0006");
		s6.setName("李四");
		s6.setGrade(4);
		s6.setBirthday(LocalDate.of(2008, 7, 6));
		s6.setHeight(170);
		list.add(s6);

		Student s7 = new Student();
		s7.setId("s0007");
		s7.setName("小华");
		s7.setGrade(6);
		s7.setBirthday(null);
		s7.setHeight(null);
		list.add(s7);

		return list;
	}
```

### 3.2 Java8前的写法

**代码**

```java
public class App {

	public static void main(String[] args) {
		List<Student> students = mockData();

		// 排序前
		System.out.println(students);

		Collections.sort(students, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				int gradeCompare = o1.getGrade().compareTo(o2.getGrade());
				if (0 != gradeCompare) {
					return gradeCompare;
				}
				int birthdayCompare = o1.getBirthday().compareTo(o2.getBirthday());
				if (0 != birthdayCompare) {
					return birthdayCompare;
				}
				return o1.getHeight().compareTo(o2.getHeight());
			}
		});

		// 排序后
		System.out.println(students);
	}
}
```

**运行结果**

```text
[Student{id='s0001', name='张三', grade=6, birthday=2009-10-01, height=150}, Student{id='s0002', name='李四', grade=4, birthday=2008-06-28, height=143}, Student{id='s0003', name='王文武', grade=null, birthday=2012-01-09, height=112}, Student{id='s0004', name='赵佳佳', grade=6, birthday=null, height=109}, Student{id='s0005', name='甲家', grade=6, birthday=2009-10-01, height=169}, Student{id='s0006', name='李四', grade=4, birthday=2008-07-06, height=170}, Student{id='s0007', name='小华', grade=6, birthday=null, height=null}]
Exception in thread "main" java.lang.NullPointerException
	at com.gyoomi.basics.sort.App$1.compare(App.java:36)
	at com.gyoomi.basics.sort.App$1.compare(App.java:32)
	at java.util.TimSort.countRunAndMakeAscending(TimSort.java:356)
	at java.util.TimSort.sort(TimSort.java:220)
	at java.util.Arrays.sort(Arrays.java:1512)
	at java.util.ArrayList.sort(ArrayList.java:1462)
	at java.util.Collections.sort(Collections.java:177)
	at com.gyoomi.basics.sort.App.main(App.java:31)
```

`我们发现：按照现有的排序规则进行排列时，控制台抛出异常。究其原因，因为我们某些学生排序字段是null，所以导致我们拿到null值进行compareTo，所以报错。`

_解决方法_

1. 过滤掉为空的记录，可以解决上述错误（但是这显然和我们实际开发情况差异较大，实际的数据可能很多数据就是null，我们一般会统一默认null的排在最前面或最后面。所以这种做法排除）
2. 在原有的排序判断逻辑中，添加对每个可能为null的字段判断。（这种做法的弊端，代码繁琐且容易出现细节错误的。而且后期排序规则改动，代码不好维护）

### 3.3 Java8的写法

**Lambda表达式**

```java

public class App 
{

	public static void main(String[] args) 
    {
		List<Student> students = mockData();

		// 排序前
		System.out.println(students);

		students.sort((o1, o2) ->
		{
			int gradeCompare = o1.getGrade().compareTo(o2.getGrade());
			if (0 != gradeCompare) 
			{
				return gradeCompare;
			}
			int birthdayCompare = o1.getBirthday().compareTo(o2.getBirthday());
			if (0 != birthdayCompare) 
			{
				return birthdayCompare;
			}
			return o1.getHeight().compareTo(o2.getHeight());
		});

		// 排序后
		System.out.println(students);
	}
}
```

运行结果同上。

**流收集器**

```java
public class App {

	public static void main(String[] args) {
		List<Student> students = mockData();

		// 排序前
		System.out.println(students);

		Collections.sort(students, Comparator.comparing(Student::getGrade)
			.thenComparing(Student::getBirthday)
			.thenComparing(Student::getHeight));

		// 排序后
		System.out.println(students);
	}
}
```

运行结果同上。我们发现Java8中并没有解决null值字段比较。这是比较蛋疼的。其中我还尝试了`Comparator#nullsFirst()`一样存在类似的问题。感觉很鸡肋。所以当我们要排序的字段可能存在null值，我们也不推荐这种方法，

### 3.4 第三方组件的写法

#### 3.4.1 Guava

**引入依赖**

```xml
        <!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>28.1-jre</version>
        </dependency>
```

**代码**

```java

public class App {

	public static void main(String[] args) {
		List<Student> students = mockData();

		// 排序前
		System.out.println(students);

		Collections.sort(students, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				return ComparisonChain.start()
					.compare(o2.getGrade(), o1.getGrade(), Ordering.natural().nullsLast())
					.compare(o1.getBirthday(), o2.getBirthday(), Ordering.natural().nullsFirst())
					.compare(o2.getHeight(), o1.getHeight(), Ordering.natural().nullsLast())
					.result();
			}
		});

		// 排序后
		System.out.println(students);
	}
}
```

**运行结果**

```text
[Student{id='s0001', name='张三', grade=6, birthday=2009-10-01, height=150}, Student{id='s0002', name='李四', grade=4, birthday=2008-06-28, height=143}, Student{id='s0003', name='王文武', grade=null, birthday=2012-01-09, height=112}, Student{id='s0004', name='赵佳佳', grade=6, birthday=null, height=109}, Student{id='s0005', name='甲家', grade=6, birthday=2009-10-01, height=169}, Student{id='s0006', name='李四', grade=4, birthday=2008-07-06, height=170}, Student{id='s0007', name='小华', grade=6, birthday=null, height=null}]
[Student{id='s0002', name='李四', grade=4, birthday=2008-06-28, height=143}, Student{id='s0006', name='李四', grade=4, birthday=2008-07-06, height=170}, Student{id='s0007', name='小华', grade=6, birthday=null, height=null}, Student{id='s0004', name='赵佳佳', grade=6, birthday=null, height=109}, Student{id='s0005', name='甲家', grade=6, birthday=2009-10-01, height=169}, Student{id='s0001', name='张三', grade=6, birthday=2009-10-01, height=150}, Student{id='s0003', name='王文武', grade=null, birthday=2012-01-09, height=112}]
```

**小结**

Guava的运行机制：默认情况下，空值的值应较小。对于空字段，您必须为Guava提供一个额外的指令，在这种情况下该怎么做。如果您想做特定的事情，但是通常您想要默认的大小写（即1，a，b，z，null），这是一种灵活的机制。

#### 3.4.2 Apache Commons Collections

**引入依赖**

```xml
        <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-collections4 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-collections4</artifactId>
            <version>4.4</version>
        </dependency>
```

**代码**

```java
public class App {

	public static void main(String[] args) {
		List<Student> students = mockData();

		// 排序前
		System.out.println(students);

		ComparatorChain<Object> cc = new ComparatorChain<>();
		cc.addComparator(new BeanComparator<>("grade"));
		cc.addComparator(new ReverseComparator<>(new BeanComparator<>("birthday")));
		cc.addComparator(new BeanComparator<>("height"));

		students.sort(cc);
		// 排序后
		System.out.println(students);
	}
}
```

**结果**

```text
[Student{id='s0001', name='张三', grade=6, birthday=2009-10-01, height=150}, Student{id='s0002', name='李四', grade=4, birthday=2008-06-28, height=143}, Student{id='s0003', name='王文武', grade=null, birthday=2012-01-09, height=112}, Student{id='s0004', name='赵佳佳', grade=6, birthday=null, height=109}, Student{id='s0005', name='甲家', grade=6, birthday=2009-10-01, height=169}, Student{id='s0006', name='李四', grade=4, birthday=2008-07-06, height=170}, Student{id='s0007', name='小华', grade=6, birthday=null, height=null}]
Exception in thread "main" java.lang.NullPointerException
	at org.apache.commons.collections.comparators.ComparableComparator.compare(ComparableComparator.java:92)
	at org.apache.commons.beanutils.BeanComparator.internalCompare(BeanComparator.java:238)
	at org.apache.commons.beanutils.BeanComparator.compare(BeanComparator.java:165)
	at org.apache.commons.collections4.comparators.ComparatorChain.compare(ComparatorChain.java:281)
	at java.util.TimSort.countRunAndMakeAscending(TimSort.java:356)
	at java.util.TimSort.sort(TimSort.java:220)
	at java.util.Arrays.sort(Arrays.java:1512)
	at java.util.ArrayList.sort(ArrayList.java:1462)
	at com.gyoomi.basics.sort.App.main(App.java:38)
```

**小结**

我们发现上述代码运行后，同样会抛出空指针异常的错误。这还是由于某些的排序字段为null导致的结果。解决方法同上。

此外，我们发现这种写法简洁，但是如果通过使用字符串（没有类型安全性，自动重构）而失去对字段的直接引用，则更容易出错。现在，如果重命名了字段，则编译器甚至不会报告问题。此外，由于此解决方案使用反射，因此排序速度要慢得多。

## 四、小节

|            | 优点    |  缺点  |
| --------   | :-----   | :---- |
| Java8前写法        | 自带，无需引入额外组件      |   不能处理null的情况   |
| Java8写法        | 自带，无需引入额外组件。功能稍微强大一点      |   不能处理null的情况    |
| Apache Commons Collections        | 相比这java自带的简洁      |   不能处理null的情况    |
| Guava        | 灵活，可以处理各种复杂情况的排序，可以处理null的情况      |   需要额外的学习和引入成本    |






















































