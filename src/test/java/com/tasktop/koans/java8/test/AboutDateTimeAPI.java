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

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tasktop.koans.KoanRunner;

@RunWith(KoanRunner.class)
public class AboutDateTimeAPI {

	@Test
	@SuppressWarnings("deprecation")
	public void java7_datesAreNotImmutable() {
		Date date = new Date();
		date.setHours(11);
		date.setHours(13);

		int hours = date.getHours();

		assertThat(13).isSameAs(hours);
	}

	@Test
	public void java8_datesAreImmutable() {
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime date2 = date.plus(Duration.of(1, ChronoUnit.HOURS));

		assertThat(date).isNotSameAs(date2);
	}

	@Test
	public void java7_timeInMiliSeconds() {
		Date now = new Date();

		long timeInMilis = now.getTime();

		assertThat(timeInMilis).isGreaterThan(0);
	}

	@Test
	public void java8_timeInMiliSeconds() {
		Instant now = Instant.now();

		long timeInMilis = now.toEpochMilli();

		assertThat(timeInMilis).isGreaterThan(0);
	}

	@Test
	public void java7_getNow() {
		Date now = new Date();

		assertThat(now).isNotNull();
	}

	@Test
	public void java8_getNow() {
		LocalDate now = LocalDate.now();

		assertThat(now).isNotNull();
	}

	@Test
	public void java7_dateInFuture() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 24);
		Date date = calendar.getTime();

		assertThat(date).isNotNull();
	}

	@Test
	public void java8_dateInFuture() {
		LocalDate now = LocalDate.now();
		LocalDate future = now.plus(1, ChronoUnit.DAYS);

		assertThat(future).isNotNull();
	}

	@Test
	public void java8_createSpecificDate() {
		LocalDate now = LocalDate.of(2000, 11, 23);

		assertThat(now.getYear()).isEqualTo(2000);
		assertThat(now.getMonthValue()).isEqualTo(11); // notice it's not 10 as with Java 7 ;)
		assertThat(now.getDayOfMonth()).isEqualTo(23);
	}

	@Test
	public void java8_convertDateToDateTime() {
		LocalDate now = LocalDate.of(2000, 11, 23);

		LocalDateTime time = now.atTime(14, 14);

		assertThat(time.getHour()).isEqualTo(14);
		assertThat(time.getMinute()).isEqualTo(14);
		assertThat(time.getYear()).isEqualTo(2000);
		assertThat(time.getMonthValue()).isEqualTo(11);
		assertThat(time.getDayOfMonth()).isEqualTo(23);
	}

	@Test
	public void java8_getNowWithSpcificClock() {
		Clock clock = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.systemDefault());

		LocalDateTime now = LocalDateTime.now(clock);

		assertThat(now.getHour()).isEqualTo(1);
		assertThat(now.getMinute()).isEqualTo(0);
		assertThat(now.getYear()).isEqualTo(1970);
		assertThat(now.getMonthValue()).isEqualTo(1);
		assertThat(now.getDayOfMonth()).isEqualTo(1);
	}

	@Test
	public void java8_periodsAreUsedToDescribeDurationsInFullDays() {
		LocalDate now = LocalDate.now();
		LocalDate future = now.plusDays(1);

		Period period = Period.between(now, future);

		assertThat(period.getDays()).isEqualTo(1);
	}

	@Test
	public void java8_durationsAreUsedToDescribeDurationsMorePrecisely() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime future = now.plusMinutes(1);

		Duration duration = Duration.between(now, future);

		assertThat(duration.getSeconds()).isEqualTo(60);
	}

	@Test
	public void java8_durationsCanAlsoBeUsedToCreateDate() {
		Duration duration = Duration.ofDays(1);
		LocalDateTime now = LocalDateTime.now();

		LocalDateTime future = now.plus(duration);

		assertThat(future).isEqualTo(now.plusDays(1));
	}

	@Test
	public void java8_adjustDatesBySemantic() {
		LocalDate date = LocalDate.of(2015, 11, 19); // it's a Thursday

		LocalDate saturday = date.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

		assertThat(saturday.getYear()).isEqualTo(2015);
		assertThat(saturday.getMonthValue()).isEqualTo(11);
		assertThat(saturday.getDayOfMonth()).isEqualTo(21);
	}

}
