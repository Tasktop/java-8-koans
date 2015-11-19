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

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.tasktop.koans.KoanRunner;

/*
 * Read this to find out why parallel stream in Java 8 are the real BIG DEAL:
 * http://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html
 */
@RunWith(KoanRunner.class)
public class AboutParallelStreams {

	private static class FindMaxTask extends RecursiveTask<Integer> {

		private final List<Integer> data;

		private final int start;

		private final int end;

		public FindMaxTask(List<Integer> data, int start, int end) {
			this.data = data;
			this.start = start;
			this.end = end;
		}

		@Override
		protected Integer compute() {
			if (end - start <= 2) {
				return computeDirectly();
			}
			int mid = start + (end - start) / 2;
			FindMaxTask left = new FindMaxTask(data, start, mid);
			FindMaxTask right = new FindMaxTask(data, mid, end);
			invokeAll(left, right);
			return Math.max(left.join(), right.join());
		}

		private Integer computeDirectly() {
			int max = Integer.MIN_VALUE;
			for (int i = start; i < end; i++) {
				if (data.get(i) > max) {
					max = data.get(i);
				}
			}
			return max;
		}

	}

	@Test
	public void java7_parallelFindMax() {
		List<Integer> data = Lists.newArrayList(0, 1, 3, 34, 56, 99, 123, 21, 34, 54, 22, 1024);
		ForkJoinPool pool = new ForkJoinPool();

		int max = pool.invoke(new FindMaxTask(data, 0, data.size()));

		assertThat(max).isEqualTo(1024);
	}

	@Test
	public void java8_parallelFindMax() {
		List<Integer> data = Lists.newArrayList(0, 1, 3, 34, 56, 99, 123, 21, 34, 54, 22, 1024);

		int max = data.parallelStream().max(Ints::compare).get();

		assertThat(max).isEqualTo(1024);
	}

}
