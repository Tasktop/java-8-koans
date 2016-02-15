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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Supplier;
import com.tasktop.koans.KoanRunner;

@RunWith(KoanRunner.class)
public class AboutLambdas {

	/*
	 * This is the Yeller. It yells inputs back to you by consuming Java 8 functional interfaces. 
	 * 
	 * You will learn how to use it with Java 7 Syntax and afterwards using Java 8 lambdas.
	 */
	private static class Yeller {

		private String echo;

		public String yell(String toEcho, Function<String, String> echoFunction) {
			return echoFunction.apply(toEcho);
		}

		public String yell(Supplier<String> echoSupplier) {
			return echoSupplier.get();
		}

		public void yell(String echo, Consumer<String> echoConsumer) {
			echoConsumer.accept(echo);
		}

		public String yell(String echo1, String echo2, EchoConcatentor concatenator) {
			return concatenator.concatenate(echo1, echo2);
		}

		public boolean hasEcho(Predicate<String> echoPredicate) {
			return echoPredicate.test(getEcho());
		}

		public String getEcho() {
			return echo;
		}

		public void setEcho(String echo) {
			this.echo = echo;
		}

	}

	@Test
	public void java7_passingFunctions() {
		Yeller yeller = new Yeller();

		String result = yeller.yell("echo", new Function<String, String>() {

			@Override
			public String apply(String toEcho) {
				return toEcho;
			}

		});

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas_WithReturn_WithParentheses_WithCurlyBrackets() {
		Yeller yeller = new Yeller();

		String result = yeller.yell("echo", (echo) -> {
			return null; // FIXME: Return the echo value
		});

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas_WithReturn_WithCurlyBrackets() {
		Yeller yeller = new Yeller();

		String result = yeller.yell("echo", echo -> {
			return null; // FIXME: Return the echo value
		});

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas_WithReturn_WithCurlyBrackets_AndMultiline() {
		Yeller yeller = new Yeller();
		final String echoSufix = ".echo";

		String result = yeller.yell("echo", echo -> {
			String myEcho = null; // FIXME: Modify the echo and concatenate the echoSufix
			return myEcho;
		});

		assertThat(result).isEqualTo("echo.echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas() {
		Yeller yeller = new Yeller();

		// FIXME: Pass in a lambda like above but without any parentheses, brackets, return statement and semi colons
		String result = yeller.yell("echo", (Function<String, String>) null);

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas_WithMethodReference() {
		Yeller yeller = new Yeller();

		// FIXME: Method references are just another way to express a lambda. What we want here is is to call the 
		// toString() method. So, try to pass in String::toString
		// This is just a method reference an the jdk knows how to compile that toString will be called on the passed 
		// in parameters.
		String result = yeller.yell("echo", String::toString);

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java7_passingSuppliers() {
		Yeller yeller = new Yeller();

		String result = yeller.yell(new Supplier<String>() {

			@Override
			public String get() {
				return "echo";
			}
		});

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingSuppliers() {
		Yeller yeller = new Yeller();

		String result = yeller.yell(() -> null); // FIXME: Let the supplier return the echo

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java7_passingConsumers() {
		Yeller yeller = new Yeller();

		yeller.yell("echo", new Consumer<String>() {

			@Override
			public void accept(String echo) {
				yeller.setEcho(echo);
			}
		});

		assertThat(yeller.getEcho()).isEqualTo("echo");
	}

	@Test
	public void java8_passingConsumers() {
		Yeller yeller = new Yeller();

		yeller.yell("echo", echo -> {
		}); // FIXME: consume the provided value and pass it to yeller.setEcho

		assertThat(yeller.getEcho()).isEqualTo("echo");
	}

	@Test
	public void java7_passingPredicates() {
		Yeller yeller = new Yeller();
		yeller.setEcho("echo");

		boolean hasEcho = yeller.hasEcho(new Predicate<String>() {

			@Override
			public boolean test(String echo) {
				return "echo".equals(echo);
			}
		});

		assertThat(hasEcho).isTrue();
	}

	@Test
	public void java8_passingPredicates() {
		Yeller yeller = new Yeller();
		yeller.setEcho("echo");

		boolean hasEcho = yeller.hasEcho(echo -> "echo".equals(null)); // FIXME: Fix the predicate to do a real equal check

		assertThat(hasEcho).isTrue();
	}

	/*
	 * This is the EchoConcatenator. It's your very first custom function interface. Please note the 
	 * Java 8 @FunctionalInterface annotation. It marks interfaces with only one method that can be used as lambdas.
	 */
	@FunctionalInterface
	private interface EchoConcatentor {

		String concatenate(String echo1, String echo2);
	}

	@Test
	public void java7_passingOwnFunctionalInterfaces() {
		Yeller yeller = new Yeller();

		String echo = yeller.yell("hello", "echo", new EchoConcatentor() {

			@Override
			public String concatenate(String echo1, String echo2) {
				return echo1 + " " + echo2;
			}
		});

		assertThat(echo).isEqualTo("hello echo");
	}

	@Test
	public void java8_passingOwnFunctionalInterfaces() {
		Yeller yeller = new Yeller();

		String echo = yeller.yell("hello", "echo", (echo1, echo2) -> null); // FIXME: let your concatenator concatenate :)

		assertThat(echo).isEqualTo("hello echo");
	}

}
