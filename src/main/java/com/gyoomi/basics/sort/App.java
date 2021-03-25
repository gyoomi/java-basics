/**
 * Copyright © 2020-2021, Glodon Digital Supplier & Purchaser BU.
 * <p>
 * All Rights Reserved.
 */

package com.gyoomi.basics.sort;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.commons.collections4.comparators.ReverseComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Main
 *
 * @author Leon
 * @date 2021-03-24 15:47
 */
public class App
{

	public static void main(String[] args)
	{
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

	public static class Student
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
}
