package com.aquarius.wizard.jdkapi;

import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class DateDemoTest {


    @Test
    public void testZoneDateTime() {
        System.out.println(ZonedDateTime.now());
        System.out.println(ZonedDateTime.now(ZoneId.of("America/New_York")));
        System.out.println(ZonedDateTime.now(Clock.systemUTC()));
    }

    @Test
    public void testTransferBetweenZones() {
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        Instant fromDb = Instant.now();
        ZonedDateTime sydney = ZonedDateTime.ofInstant(fromDb, ZoneId.of("Australia/Sydney"));
        ZonedDateTime beijing = ZonedDateTime.ofInstant(fromDb, ZoneId.systemDefault());
        System.out.println(sydney.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(beijing.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        for (String availableZoneId : availableZoneIds) {
            System.out.println(availableZoneId);
        }
    }
}
