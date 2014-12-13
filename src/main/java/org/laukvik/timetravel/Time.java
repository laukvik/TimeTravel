/*
 * Copyright (C) 2014 morten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.laukvik.timetravel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *
 *
 * Time.year(2014);<br>
 * Time.at(25).january(2014);<br>
 * Time.at(22).january();<br>
 * Time.at(22).month(10);<br>
 * Time.in(JANUARY, 2014);<br>
 * Time.year(2014).january();<br>
 * Time.year(2014).month(22);<br>
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Embeddable
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Time implements Serializable {

    public static int JANUARY = java.util.Calendar.JANUARY;
    public static int FEBRUARY = java.util.Calendar.FEBRUARY;
    public static int MARCH = java.util.Calendar.MARCH;
    public static int APRIL = java.util.Calendar.APRIL;
    public static int MAY = java.util.Calendar.MAY;
    public static int JUNE = java.util.Calendar.JUNE;
    public static int JULY = java.util.Calendar.JULY;
    public static int AUGUST = java.util.Calendar.AUGUST;
    public static int SEPTEMBER = java.util.Calendar.SEPTEMBER;
    public static int OCTOBER = java.util.Calendar.OCTOBER;
    public static int NOVEMBER = java.util.Calendar.NOVEMBER;
    public static int DECEMBER = java.util.Calendar.DECEMBER;

    Integer year;
    Integer month;
    Integer day;

    public Time() {
    }

    public static Time in(int month, int year) {
        return new Time(year, month, null);
    }

    public static Time year(int year) {
        return new Time(year, null, null);
    }

    public static Time at(int day) {
        return new Time(null, null, day);
    }

    public static Time January(int year) {
        return new Time(year, null, null);
    }

    public Time january() {
        return this.month(JANUARY);
    }

    public Time february() {
        return this.month(FEBRUARY);
    }

    public Time march() {
        return this.month(MARCH);
    }

    public Time april() {
        return this.month(APRIL);
    }

    public Time may() {
        return this.month(MAY);
    }

    public Time june() {
        return this.month(JUNE);
    }

    public Time july() {
        return this.month(JULY);
    }

    public Time august() {
        return this.month(AUGUST);
    }

    public Time september() {
        return this.month(SEPTEMBER);
    }

    public Time october() {
        return this.month(OCTOBER);
    }

    public Time november() {
        return this.month(NOVEMBER);
    }

    public Time december() {
        return this.month(DECEMBER);
    }


    public Time month(int month) {
        this.month = month;
        return this;
    }

    public Time month(int month, int year) {
        this.month = month;
        this.year = year;
        return this;
    }

    public Time january(int year) {
        return this.month(JANUARY, year);
    }

    public Time february(int year) {
        return this.month(FEBRUARY, year);
    }

    public Time march(int year) {
        return this.month(MARCH, year);
    }

    public Time april(int year) {
        return this.month(APRIL, year);
    }

    public Time may(int year) {
        return this.month(MAY, year);
    }

    public Time june(int year) {
        return this.month(JUNE, year);
    }

    public Time july(int year) {
        return this.month(JULY, year);
    }

    public Time august(int year) {
        return this.month(AUGUST, year);
    }

    public Time september(int year) {
        return this.month(SEPTEMBER, year);
    }

    public Time october(int year) {
        return this.month(OCTOBER, year);
    }

    public Time november(int year) {
        return this.month(NOVEMBER, year);
    }

    public Time december(int year) {
        return this.month(DECEMBER, year);
    }

    public Time(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        if (year != null && month != null && day != null) {
            /* Date */
            DateFormat format = new SimpleDateFormat("d.MMMMM.YYYY");
            Calendar c = new GregorianCalendar();
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.YEAR, year);
            return format.format(c.getTime());
//            return day + "." + month + "." + year;

        } else if (year == null && month != null && day != null) {
            /* Date, yearly */
//            return day + "." + month;
            DateFormat format = new SimpleDateFormat("d.MMMMM");
            Calendar c = new GregorianCalendar();
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.MONTH, month);
            return format.format(c.getTime());

        } else if (year != null && month == null && day == null) {
            /* Year */
            return year + "";
        } else if (day == null && year != null && month != null) {
//            return month + " " + year;
            DateFormat format = new SimpleDateFormat("MMMMM yyyy");
            Calendar c = new GregorianCalendar();
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.YEAR, year);
            return format.format(c.getTime());
        } else {
            return null;
        }
    }

}
