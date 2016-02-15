/**
 * (C) Copyright (c) 2015 Tasktop Technologies and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Holger Staudacher - initial implementation
 */
package com.tasktop.koans.java8.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.tasktop.koans.KoanRunner;

@RunWith(KoanRunner.class)
public class AboutDefaultMethods {

	@Test
	public void java7_iterateOverCollection() {
		List<Integer> numbers = Lists.newArrayList(1, 1, 1, 1, 1);
		LongAdder adder = new LongAdder();

		for (Integer number : numbers) {
			adder.add(number);
		}

		assertThat(adder.sum()).isEqualTo(5);
	}

	@Test
	public void java8_iterateOverCollection() {
		List<Integer> numbers = Lists.newArrayList(1, 1, 1, 1, 1);
		LongAdder adder = new LongAdder();

		numbers.forEach(number -> {
		}); // FIXME: add the number to the adder

		assertThat(adder.sum()).isEqualTo(5);
	}

	public static class Tasktopian {

		private final String name;

		private final String role;

		public Tasktopian(String name, String role) {
			this.name = name;
			this.role = role;
		}

		public String getName() {
			return name;
		}

		public String getRole() {
			return role;
		}

		@Override
		public boolean equals(Object obj) {
			return Objects.equals(name, ((Tasktopian) obj).name) && Objects.equals(role, ((Tasktopian) obj).role);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, role);
		}

		@Override
		public String toString() {
			return name + " (" + role + ")";
		}
	}

	@Test
	public void java7_continuousListSorting() {
		List<Tasktopian> list = Lists.newArrayList(new Tasktopian("Bob", "Engineer"), //
				new Tasktopian("Oscar", "VP of VPs"), //
				new Tasktopian("Mary", "Senior Engineer"), //
				new Tasktopian("Bob", "Designer"));

		Collections.sort(list, new Comparator<Tasktopian>() {

			@Override
			public int compare(Tasktopian t1, Tasktopian t2) {
				int result = t1.name.compareTo(t2.name);
				if (result == 0) {
					result = t1.role.compareTo(t2.role);
				}
				return result;
			}
		});

		assertThat(list).isEqualTo(Lists.newArrayList(new Tasktopian("Bob", "Designer"), //
				new Tasktopian("Bob", "Engineer"), //
				new Tasktopian("Mary", "Senior Engineer"), //
				new Tasktopian("Oscar", "VP of VPs")));
	}

	@Test
	public void java8_continuousListSorting() {
		List<Tasktopian> list = Lists.newArrayList(new Tasktopian("Bob", "Engineer"), //
				new Tasktopian("Oscar", "VP of VPs"), //
				new Tasktopian("Mary", "Senior Engineer"), //
				new Tasktopian("Bob", "Designer"));

		// FIXME: take a look the the comparing static method on Comparator. Pass in the method reference for getName.
		// This creates one comparator. Append another one by using the default method thenComparing and use the getRole
		// method for comparing.
		Collections.sort(list, null);

		assertThat(list).isEqualTo(Lists.newArrayList(new Tasktopian("Bob", "Designer"), //
				new Tasktopian("Bob", "Engineer"), //
				new Tasktopian("Mary", "Senior Engineer"), //
				new Tasktopian("Oscar", "VP of VPs")));
	}

	@Test
	public void java7_removeListElement() {
		List<String> list = Lists.newArrayList("a", "b", "c", "d", "e");

		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String element = iterator.next();
			if ("d".equals(element)) {
				iterator.remove();
			}
		}

		assertThat(list).isEqualTo(Lists.newArrayList("a", "b", "c", "e"));
	}

	@Test
	public void java7_removeListElementWithCopy() {
		List<String> list = Lists.newArrayList("a", "b", "c", "d", "e");

		for (String element : new ArrayList<>(list)) {
			if ("d".equals(element)) {
				list.remove(element);
			}
		}

		assertThat(list).isEqualTo(Lists.newArrayList("a", "b", "c", "e"));
	}

	@Test
	public void java8_removeListElement() {
		List<String> list = Lists.newArrayList("a", "b", "c", "d", "e");

		// FIXME: take a look at the removeIf method and pass in a predicate the is only true if the element is "d"
		list.removeIf(null);

		assertThat(list).isEqualTo(Lists.newArrayList("a", "b", "c", "e"));
	}

	public interface Printable_A {

		String printA();

		public default String print() {
			return printA();
		}

	}

	public interface Printable_B {

		String printB();

		default String print() {
			return printB();
		}

	}

	private static class MyDoable implements Printable_A, Printable_B {

		@Override
		public String printB() {
			return "B";
		}

		@Override
		public String printA() {
			return "A";
		}

		@Override
		public String print() {
			// FIXME:  Concatenate both super.print() calls here. Start with Printable_A.super.print()
			return null;
		}

	}

	@Test
	public void beAwareOfMultipleInheritance() {
		MyDoable doable = new MyDoable();

		String actual = doable.print();

		assertEquals("AB", actual);
	}

}
