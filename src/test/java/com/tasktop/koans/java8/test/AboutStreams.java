/*
 * Copyright (c) 2015 Tasktop Technologies.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.tasktop.koans.java8.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.tasktop.koans.KoanRunner;

@RunWith(KoanRunner.class)
public class AboutStreams {

	@Test
	public void java7_filterList() {
		List<String> list = Lists.newArrayList("a", "b", "a", "b");

		List<String> actualList = new ArrayList<>();
		for (String element : list) {
			if ("a".equals(element)) {
				actualList.add(element);
			}
		}

		assertThat(actualList).isEqualTo(Lists.newArrayList("a", "a"));
	}

	@Test
	public void java8_filterList() {
		List<String> list = Lists.newArrayList("a", "b", "a", "b");

		// FIXME: fill in the predicate that filders for "a"
		List<String> actualList = list.stream().filter(element -> false).collect(Collectors.toList());

		assertThat(actualList).isEqualTo(Lists.newArrayList("a", "a"));
	}

	@Test
	public void java7_filterAndSortList() {
		List<String> list = Lists.newArrayList("a.2", "b.1", "a.1", "b.2");

		List<String> actualList = new ArrayList<>();
		for (String element : list) {
			if (element != null && element.startsWith("a.")) {
				actualList.add(element);
			}
		}
		Collections.sort(actualList, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		assertThat(actualList).isEqualTo(Lists.newArrayList("a.1", "a.2"));
	}

	@Test
	public void java8_filterAndSortList() {
		List<String> list = Lists.newArrayList("a.2", "b.1", "a.1", "b.2");

		List<String> actualList = list.stream()
				.filter(element -> element != null)
				.filter(element -> element.startsWith("a."))
				.sorted(null) // FIXME: use string compare to. Think about Method references.
				.collect(Collectors.toList());

		assertThat(actualList).isEqualTo(Lists.newArrayList("a.1", "a.2"));
	}

	@Test
	public void java7_mapListToOtherType() {
		List<String> list = Lists.newArrayList("1", "2", "3");

		List<Integer> actualList = new ArrayList<>();
		for (String element : list) {
			actualList.add(Integer.parseInt(element));
		}

		assertThat(actualList).isEqualTo(Lists.newArrayList(1, 2, 3));
	}

	@Test
	public void java8_mapListToOtherType() {
		List<String> list = Lists.newArrayList("1", "2", "3");

		// FIXME: we need a function that converts Strings to integers. I wonder if a method on Integer could be used
		// parsed... I mean used for that.
		Function<? super String, ? extends Integer> mapper = null;
		List<Integer> actualList = list.stream().map(mapper).collect(Collectors.toList());

		assertThat(actualList).isEqualTo(Lists.newArrayList(1, 2, 3));
	}

	@Test
	public void java7_findListElement() {
		List<String> list = Lists.newArrayList("a.2", "b.1", "a.1", "b.2");

		String result = search(list, "a.1");

		assertThat(result).isEqualTo("a.1");
	}

	private String search(List<String> list, String searchFor) {
		for (String element : list) {
			if (searchFor.equals(element)) {
				return element;
			}
		}
		return null;
	}

	@Test
	public void java8_findListElement() {
		List<String> list = Lists.newArrayList("a.2", "b.1", "a.1", "b.2");

		// FIXME: fill in a predicate that filters for "a.1"
		String result = list.stream().filter(element -> false).findFirst().get();

		assertThat(result).isEqualTo("a.1");
	}

	@Test
	public void java7_reduceValues() {
		List<Integer> list = Lists.newArrayList(1, 1, 1, 1, 1);

		int sum = 0;
		for (Integer value : list) {
			sum += value;
		}

		assertThat(sum).isEqualTo(5);
	}

	@Test
	public void java8_reduceValues() {
		List<Integer> list = Lists.newArrayList(1, 1, 1, 1, 1);

		// FIXME: write an accumulator that creates a sum. Remember if a lambda consumes more than one parameter you
		// have to use parentheses.
		Integer sum = list.stream().reduce(null).get();

		assertThat(sum).isEqualTo(5);
	}

	@Test
	public void java8_streamsAreNotLimitedToCollections() {
		String string = null; // FIXME: create a string that can be splitted by "," and has a,b,c as elements.

		List<String> elements = Pattern.compile(",").splitAsStream(string).collect(Collectors.toList());

		assertThat(elements).isEqualTo(Lists.newArrayList("a", "b", "c"));
	}

}
