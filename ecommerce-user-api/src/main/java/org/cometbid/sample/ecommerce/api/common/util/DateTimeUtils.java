/*
 * The MIT License
 *
 * Copyright 2024 samueladebowale.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cometbid.sample.ecommerce.api.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.Duration;

/**
 *
 * @author samueladebowale
 */
public class DateTimeUtils {

    public static final String DATETIME_FORMAT = "dd-MMM-yyyy HH:mm";
    public static final String TIME_OFDAY_FORMAT = "dd-MMM-yyyy HH:mm a";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_OFDAY_FORMAT);
    public static final LocalDateTime FIXED_DATE = LocalDateTime.now();
    public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(
            DateTimeFormatter.ofPattern(DATETIME_FORMAT));

    /**
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime getDateTimeFromString(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {

            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);

            return dateTime;
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param localDteTime
     * @return
     */
    public static String getAsString(LocalDateTime localDteTime) {

        if (localDteTime == null) {
            return null;
        }

        try {

            return localDteTime.format(DateTimeFormatter.ISO_DATE_TIME);

        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param dateStr
     * @return
     */
    public static LocalDate getDateFromString(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        try {

            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);

            return date;
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param localDte
     * @return
     */
    public static String getAsString(LocalDate localDte) {

        if (localDte == null) {
            return null;
        }

        try {

            return localDte.format(DateTimeFormatter.ISO_DATE);

        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Date asDate(LocalDate localDate) {

        return Date.from(localDate.atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {

        return Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {

        return date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {

        return java.time.Instant.ofEpochMilli(date.getTime()).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public static LocalDateTime convertStringToDate(String dateToConvert, ZoneId currentTimeZone,
            ZoneId destinationTimeZone) {

        ZonedDateTime destZonedDateTime = null;

        if (dateToConvert.length() <= 10) {
            LocalDate myDateTime = getDateFromString(dateToConvert);
            ZonedDateTime currentZonedDate = myDateTime.atStartOfDay(currentTimeZone);

            destZonedDateTime = currentZonedDate.withZoneSameInstant(destinationTimeZone);
        } else {

            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            destZonedDateTime = ZonedDateTime.parse(dateToConvert, formatter);
        }
        return destZonedDateTime.toLocalDateTime();
    }

    public static int yearsBetween(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getYears();
    }

    public static int monthsBetween(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getMonths();
    }

    public static int daysBetween(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }

    public static LocalDate getLocalDateFromLongMillisecs(Long epochMillis) {

        return LocalDate.ofEpochDay(Duration.ofMillis(epochMillis).toDays());
    }

    public static LocalDateTime getLocalDateTimeFromLongMillisecs(Long longValue) {
        Assert.notNull(longValue, "Parameter longValue is null");

        long secondsSinceEpoch = longValue / 1000;
        return LocalDateTime.ofEpochSecond(secondsSinceEpoch, 0, ZoneOffset.UTC);
    }

    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {

        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        return localDateTime;
    }
    
     public static LocalDate convertTimeZones(LocalDate dateToConvert, ZoneId currentTimeZone,
            ZoneId destinationTimeZone) {

        ZonedDateTime currentZonedDate = dateToConvert.atStartOfDay(currentTimeZone);

        ZonedDateTime destZonedDateTime = currentZonedDate.withZoneSameInstant(destinationTimeZone);
        return destZonedDateTime.toLocalDate();
    }

    public static LocalDateTime convertTimeZones(LocalDateTime dateTimeToConvert, ZoneId currentTimeZone,
            ZoneId destinationTimeZone) {

        ZonedDateTime currentZonedDateTime = ZonedDateTime.of(dateTimeToConvert, currentTimeZone);

        ZonedDateTime destZonedDateTime = currentZonedDateTime.withZoneSameInstant(destinationTimeZone);
        return destZonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS).withNano(0);
    }

    public static long currentTimestamp() {
        return now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public static LocalDate today() {

        return LocalDate.now(ZoneOffset.UTC);
    }
}
