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

		assertThat(bobHasInteresets).isFalse(); // FIXME: no optional present. So false can't be true ;)
	}

	@Test
	public void optionalsAreOptional() {
		Optional<String> optional = Optional.of(null); // FIXME: find out how to create an optional of null

		String value = optional.orElse("b");

		assertThat(value).isEqualTo("b");
	}

}
