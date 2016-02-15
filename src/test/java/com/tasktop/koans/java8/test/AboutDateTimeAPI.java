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

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
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
		Duration durationtoAdd = Duration.of(1, ChronoUnit.HOURS);
		LocalDateTime date2 = date; // FIXME: See if you can find a way to add the durationToAdd to the date

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

		long timeInMilis = -1; // FIXME: checkout if you can find the method on "now" that returns the milis

		assertThat(timeInMilis).isGreaterThan(0);
	}

	@Test
	public void java7_getNow() {
		Date now = new Date();

		assertThat(now).isNotNull();
	}

	@Test
	public void java8_getNow() {
		LocalDate now = null; // FIXME: maybe there is a factory method on LocalDate you can use to get now

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
		LocalDate future = now.plus(1, null); // FIXME: Find out what a ChronoUnit is and what it means.

		assertThat(future).isNotNull();
	}

	@Test
	public void java8_createSpecificDate() {
		LocalDate now = null; // FIXME: use the of method to create a date that represents 2000-11-23

		assertThat(now.getYear()).isEqualTo(2000);
		assertThat(now.getMonthValue()).isEqualTo(11); // notice it's not 10 as with Java 7 ;)
		assertThat(now.getDayOfMonth()).isEqualTo(23);
	}

	@Test
	public void java8_convertDateToDateTime() {
		LocalDate now = LocalDate.of(2000, 11, 23);

		// FIXME: A LocalDate does not store hours and minutes. See if you can enhance now with this information. 
		// One hint, take a look at the atTime method.
		LocalDateTime time = null;

		assertThat(time.getHour()).isEqualTo(14);
		assertThat(time.getMinute()).isEqualTo(14);
		assertThat(time.getYear()).isEqualTo(2000);
		assertThat(time.getMonthValue()).isEqualTo(11);
		assertThat(time.getDayOfMonth()).isEqualTo(23);
	}

	@Test
	public void java8_getNowWithSpecificClock() {
		Clock clock = null; // FIXME: create a clock that start at the epoch and uses the sytem timezone

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

		Period period = Period.between(now, null); // FIXME: calculate the period between now and future

		assertThat(period.getDays()).isEqualTo(1);
	}

	@Test
	public void java8_durationsAreUsedToDescribeDurationsMorePrecisely() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime future = now.plusMinutes(1);

		Duration duration = Duration.between(null, future); // FIXME: calculate the duration between now and future

		assertThat(duration.getSeconds()).isEqualTo(60);
	}

	@Test
	public void java8_durationsCanAlsoBeUsedToCreateDate() {
		Duration duration = Duration.ofDays(1);
		LocalDateTime now = LocalDateTime.now();

		LocalDateTime future = now.plus(null); // FIXME: add the duration to now 

		assertThat(future).isEqualTo(now.plusDays(1));
	}

	@Test
	public void java8_adjustDatesBySemantic() {
		LocalDate date = LocalDate.of(2015, 11, 19); // it's a Thursday

		LocalDate saturday = date.with(TemporalAdjusters.next(null)); // FIXME: find out how to get the next Saturday.

		assertThat(saturday.getYear()).isEqualTo(2015);
		assertThat(saturday.getMonthValue()).isEqualTo(11);
		assertThat(saturday.getDayOfMonth()).isEqualTo(21);
	}

	@Test
	public void java8_useFixedOffsetsForTimeZones() {
		// FIXME: I wonder how many hours we need to pass in to have 28800 seconds
		OffsetDateTime date = OffsetDateTime.of(2000, 11, 23, 14, 14, 14, 14, ZoneOffset.ofHours(-1));

		assertThat(date.getOffset().get(ChronoField.OFFSET_SECONDS)).isEqualTo(28800);
	}

	@Test
	public void java8_useZoneIdsForTimeZones() {
		ZonedDateTime berlin = ZonedDateTime.now(ZoneId.of("")); // FIXME: How do we find out the ZoneIf of Berlin?
		ZonedDateTime vancouver = ZonedDateTime.now(ZoneId.of("Canada/Pacific"));

		assertThat(berlin.isBefore(vancouver)).isTrue();
	}

}
