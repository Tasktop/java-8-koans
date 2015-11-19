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

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tasktop.koans.KoanRunner;

@RunWith(KoanRunner.class)
public class AboutOptionals {

	public static class Person {

		String name;

		String interests;

		public Person(String name, Optional<String> interests) {
			this.name = name;
			this.interests = interests.orElse(null);
		}

		public String getName() {
			return name;
		}

		public Optional<String> getInterests() {
			return Optional.ofNullable(interests);
		}

	}

	@Test
	public void optionalsGiveYouSemantic() {
		Person bob = new Person("Bob", Optional.of("Longboarding"));

		boolean bobHasInteresets = bob.getInterests().isPresent();

		assertThat(bobHasInteresets).isTrue();
	}

	@Test
	public void optionalsAreOptional() {
		Optional<String> optional = Optional.of("b");

		String value = optional.orElse("b");

		assertThat(value).isEqualTo("b");
	}

}
