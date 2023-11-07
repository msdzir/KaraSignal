package com.finitx.karasignal.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateTimeUtil {

    public Date getNow() {
        return new Date(getDefaultCalendar().getTime().getTime());
    }

    public Date expiresAt(Integer expiration) {
        Calendar calendar = getDefaultCalendar();
        calendar.add(Calendar.MINUTE, expiration);

        return new Date(calendar.getTime().getTime());
    }

    public Date expiresAt(Integer expiration, Integer metric) {
        Calendar calendar = getDefaultCalendar();
        calendar.add(metric, expiration);

        return new Date(calendar.getTime().getTime());
    }

    public boolean isExpired(Date date) {
        return date.before(getNow());
    }

    private Calendar getDefaultCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));

        return calendar;
    }
}
