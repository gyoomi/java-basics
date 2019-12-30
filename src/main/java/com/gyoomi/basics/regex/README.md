# java基础之正则表达式Pattern、Matcher

## 1. 正则表达式

正则表达式(Regular Expression)是一种文本模式，包括普通字符（例如，a 到 z 之间的字母）和特殊字符（称为"元字符"）。

正则表达式使用单个字符串来描述、匹配一系列匹配某个句法规则的字符串。

正则表达式是繁琐的，但它是强大的，学会之后的应用会让你除了提高效率外，会给你带来绝对的成就感。=加上应用的时候进行一定的参考，掌握正则表达式不是问题。

许多程序设计语言都支持利用正则表达式进行字符串操作。

当然，正则表达式要是详解介绍起来是相当复杂的，在这里我们就对正则表达式本身不做过多过多，要是没有接触过正则表达式或遗忘的同学，可以点解这里 [正则表达式教程](https://www.runoob.com/regexp/regexp-syntax.html) 进行回顾和学习。


## 2. 正则表达式中的组

为啥要把这一点单独拿出来说，是因为我们后面会频繁用到，当然实际开发中也经常使用。

正则表达式中组使用小括号。`()`。

分组和分支结构是我们直接感觉到括号最直接的作用。

### 2.1 分组

我们知道`/a+/`匹配连续出现的`a`，而要匹配连续出现的`ab`时，需要使用`/(ab)+/`。

其中括号是提供分组功能，使量词`+`作用于`ab`这个整体，测试如下：

```java
public class Main {

	@Test
	public void test01() {
		String regex = "(ab)+";
		String result = "ababa abbb ababab";
		String result2 = "ba";
		Matcher matcher = Pattern.compile(regex).matcher(result);
		Matcher matcher2 = Pattern.compile(regex).matcher(result2);
		System.out.println(matcher.find()); // true
		System.out.println(matcher2.find()); // false
	}

}
```

### 2.2 分支结构

而在多选分支结构(p1|p2)中，此处括号的作用也是不言而喻的，提供了子表达式的所有可能。

比如，要匹配如下的字符串：

```text
I love JavaScript

I love Java
```

可以使用正则:

```java
public class Main {

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

}
```

如果去掉正则中的括号，即I love JavaScript Java，匹配字符串是"I love JavaScript Java"，当然这不是我们想要的.

### 2.3 分组引用

这是括号一个重要的作用，有了它，我们就可以进行数据提取，以及更强大的替换操作。而要使用它带来的好处，必须配合使用实现环境的API。

以日期为例。假设格式是`yyyy-mm-dd`的，我们可以先写一个简单的正则：

`String regex = "\d{4}-\d{2}-\d{2}";`

然后再修改成括号版的：

`String regex = "(\d{4})-(\d{2})-(\d{2})";`

为什么要使用这个正则呢？

- 提取数据

比如提取出年、月、日，可以这么做。

```java
public class Main {

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
}
```

关于其中的语法说明及使用见下方。

- 动态替换

使用正则表达结合String#replace方法。可以实现参数的动态替换。


## 3. Pattern

java.util.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包。
它包括两个类：Pattern和Matcher。一个Pattern是一个正则表达式经编译后的表现模式。
一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
首先一个Pattern实例订制了一个所用语法与PERL的类似的正则表达式经编译后的模式，然后一个Matcher实例在这个给定的Pattern实例的模式控制下进行字符串的匹配工作。

### 3.1 捕获组的概念

捕获组可以通过从左到右计算其开括号来编号，编号是从1 开始的。例如，在表达式
((A)(B(C)))中，存在四个这样的组。

```text
1        ((A)(B(C)))
2        (A)
3        (B(C))
4        (C)
```

组零始终代表整个表达式。 以 (?) 开头的组是纯的非捕获 组，它不捕获文本，也不针对组合计进行计数。

与组关联的捕获输入始终是与组最近匹配的子序列。如果由于量化的缘故再次计算了组，则在第二次计算失败时将保留其以前捕获的值（如果有的话）例如，将字符串"aba"
与表达式(a(b)?)+ 相匹配，会将第二组设置为"b"。在每个匹配的开头，所有捕获的输入都会被丢弃。

### 3.2 Pattern的初始化

1. Pattern complie(String regex)

编译正则表达式，并创建Pattern类实例。由于Pattern的构造函数是私有的，不可以直接创建，所以通过静态的简单工厂方法compile(String regex)方法来创建，将给定的正则表达式编译并赋予给Pattern类。

```java
public class Main {
	
    Pattern p = Pattern.compile("\\d+");
    
}

```

### 3.3 Pattern常用方法

1. String pattern()

返回正则表达式的字符串形式。其实就是返回Pattern.complile(Stringregex)的regex参数。示例如下：

```java
public class Main {
	@Test
	public void test04() {
		String regex01 = "\\d+";
		String regex02 = "\\?|\\*";
		Pattern p1 = Pattern.compile(regex01);
		Pattern p2 = Pattern.compile(regex02);
		System.out.println(p1.pattern()); // \d+
		System.out.println(p2.pattern()); // \?|\*
	}
}
```

2. Pattern compile(String regex, int flags)

方法功能和compile(Stringregex)相同，不过增加了flag参数，flag参数用来控制正则表达式的匹配行为，可取值范围如下

| 参数                     | 作用                                                                     | 说明                                                                                                                                                               |
|:-------------------------|:-------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Pattern.CANON_EQ         | 启用规范等价                                                              | 当且仅当两个字符的“正规分解(canonicaldecomposition)”都完全相同的情况下，才认定匹配。默认情况下，不考虑“规范相等性(canonical equivalence)”                                  |
| Pattern.CASE_INSENSITIVE | 启用不区分大小写的匹配                                                     | 默认情况下，大小写不敏感的匹配只适用于US-ASCII字符集。这个标志能让表达式忽略大小写进行匹配，要想对Unicode字符进行大小不敏感的匹配，只要将UNICODE_CASE与这个标志合起来就行了。    |
| Pattern.COMMENTS         | 模式中允许空白和注释                                                       | 在这种模式下，匹配时会忽略(正则表达式里的)空格字符(不是指表达式里的“\s”，而是指表达式里的空格，tab，回车之类)。注释从#开始，一直到这行结束。可以通过嵌入式的标志来启用Unix行模式。 |
| Pattern.DOTALL           | 启用dotall模式。在这种模式下，表达式‘.’可以匹配任意字符，包括表示一行的结束符。 | 默认情况下，表达式‘.’不匹配行的结束符。                                                                                                                               |
| Pattern.LITERAL          | 启用模式的字面值解析                                                       |                                                                                                                                                                   |
| Pattern.MULTILINE        | 启用多行模式                                                              | 在这种模式下，‘\^’和‘$’分别匹配一行的开始和结束。此外，‘^’仍然匹配字符串的开始，‘$’也匹配字符串的结束。默认情况下，这两个表达式仅仅匹配字符串的开始和结束。                      |
| Pattern.UNICODE_CASE     | 启用Unicode感知的大小写折叠                                                | 在这个模式下，如果你还启用了CASE_INSENSITIVE标志，那么它会对Unicode字符进行大小写不敏感的匹配。默认情况下，大小写不敏感的匹配只适用于US-ASCII字符集。                         |
| Pattern.UNIX_LINES       | 启用Unix行模式                                                            | 在这个模式下，只有‘\n’才被认作一行的中止，并且与‘.’、‘^’、以及‘$’进行匹配。                                                                                              |

4. int flags()

返回当前Pattern的匹配flag参数。

5. String[] split(CharSequence input)

Pattern有一个split(CharSequenceinput)方法，用于分隔字符串，并返回一个`String[]`。此外`String[]` split(CharSequence input, int limit)功能和`String[]`split(CharSequence input)相同，  
增加参数limit目的在于要指定分割的段数。

示例如下：

```java
public class Main {
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
}
```

6. boolean matches(String regex, CharSequenceinput)

是一个静态方法，用于快速匹配字符串，该方法适合用于只匹配一次，且匹配全部字符串。方法编译给定的正则表达式并且对输入的字串以该正则表达式为模式开展匹配，该方法只进行一次匹配工作，并不需要生成一个Matcher实例。

```java
public class Main {
	@Test
	public void test06() {
		System.out.println(Pattern.matches("\\d+", "22233")); // true
		System.out.println(Pattern.matches("\\d+", "22233aa")); // false。需要匹配到所有字符串才能返回true,这里aa不能匹配到
		System.out.println(Pattern.matches("\\d+", "222bb33")); // false。需要匹配到所有字符串才能返回true,这里bb不能匹配到
	}
}
```

7. Pattern.matcher(CharSequence input)

返回一个Matcher对象。Matcher类的构造方法也是私有的，不能随意创建，只能通过Pattern.matcher(CharSequence input)方法得到该类的实例。

Pattern类只能做一些简单的匹配操作，要想得到更强更便捷的正则匹配操作，那就需要将Pattern与Matcher一起合作。Matcher类提供了对正则表达式的分组支持，以及对正则表达式的多次匹配支持。


## 4. Matcher



## 5. 示例应用

## 6. 总结




































