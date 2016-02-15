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

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
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

		int max = -1; // FIXME: take a look at Collection.parallelStream and use it's max method.

		assertThat(max).isEqualTo(1024);
	}

}
