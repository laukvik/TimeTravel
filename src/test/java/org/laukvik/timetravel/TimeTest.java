/*
 * Copyright (C) 2014 Morten Laukvik <morten@laukvik.no>
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

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class TimeTest {

    public TimeTest() {
    }

    @Test
    public void testFullTime() {
        Time time = Time.at(5).october(2014);
        assertThat(time.day).isEqualTo(5);
        assertThat(time.month).isEqualTo(Time.OCTOBER);
        assertThat(time.year).isEqualTo(2014);

    }

    @Test
    public void testYear() {
        Time time = Time.year(2014);
        assertThat(time.day).isNull();
        assertThat(time.month).isNull();
        assertThat(time.year).isEqualTo(2014);
    }

    @Test
    public void testDateMonth() {
        Time time = Time.at(5).october();
        assertThat(time.day).isEqualTo(5);
        assertThat(time.month).isEqualTo(Time.OCTOBER);
        assertThat(time.year).isNull();
    }

    @Test
    public void testMonthYear() {
        Time time = Time.in(Time.JANUARY, 2014);
        assertThat(time.day).isNull();
        assertThat(time.month).isEqualTo(Time.JANUARY);
        assertThat(time.year).isEqualTo(2014);
    }

    @Test
    public void parseFullDate() {
        Time time = Time.parse("1731-02-11");
        assertThat(time.year).isEqualTo(1731);
        assertThat(time.month).isEqualTo(2);
        assertThat(time.day).isEqualTo(11);
    }

    @Test
    public void parseYear() {
        Time time = Time.parse("1731");
        assertThat(time.year).isEqualTo(1731);
        assertThat(time.month).isNull();
        assertThat(time.day).isNull();
    }

}
