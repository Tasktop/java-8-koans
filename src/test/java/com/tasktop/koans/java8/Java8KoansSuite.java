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
package com.tasktop.koans.java8;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.tasktop.koans.KoanSuiteRunner;
import com.tasktop.koans.java8.test.AboutBase64;
import com.tasktop.koans.java8.test.AboutDateTimeAPI;
import com.tasktop.koans.java8.test.AboutDefaultMethods;
import com.tasktop.koans.java8.test.AboutLambdas;
import com.tasktop.koans.java8.test.AboutNashorn;
import com.tasktop.koans.java8.test.AboutOptionals;
import com.tasktop.koans.java8.test.AboutParallelStreams;
import com.tasktop.koans.java8.test.AboutStreams;

@RunWith(KoanSuiteRunner.class)
@SuiteClasses({ //
		AboutLambdas.class, //
		AboutDefaultMethods.class, //
		AboutStreams.class, //
		AboutParallelStreams.class, //
		AboutDateTimeAPI.class, //
		AboutOptionals.class, //
		AboutNashorn.class, //
		AboutBase64.class, //
})
public class Java8KoansSuite {

}
