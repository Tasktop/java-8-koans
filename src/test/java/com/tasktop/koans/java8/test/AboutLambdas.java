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
			return echo;
		});

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas_WithReturn_WithCurlyBrackets() {
		Yeller yeller = new Yeller();

		String result = yeller.yell("echo", echo -> {
			return echo;
		});

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas_WithReturn_WithCurlyBrackets_AndMultiline() {
		Yeller yeller = new Yeller();
		final String echoSufix = ".echo";

		String result = yeller.yell("echo", echo -> {
			String myEcho = echo + echoSufix;
			return myEcho;
		});

		assertThat(result).isEqualTo("echo.echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas() {
		Yeller yeller = new Yeller();

		String result = yeller.yell("echo", echo -> echo);

		assertThat(result).isEqualTo("echo");
	}

	@Test
	public void java8_passingFunctionsWithLambdas_WithMethodReference() {
		Yeller yeller = new Yeller();

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

		String result = yeller.yell(() -> "echo");

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

		yeller.yell("echo", echo -> yeller.setEcho(echo));

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

		boolean hasEcho = yeller.hasEcho(echo -> "echo".equals(echo));

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

		String echo = yeller.yell("hello", "echo", (echo1, echo2) -> echo1 + " " + echo2);

		assertThat(echo).isEqualTo("hello echo");
	}

}
