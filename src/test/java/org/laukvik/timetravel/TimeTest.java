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

import org.junit.Test;
import static org.laukvik.timetravel.Time.JANUARY;

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
        assert (time.day == 5);
        assert (time.month == Time.OCTOBER);
        assert (time.year == 2014);
    }

    @Test
    public void testYear() {
        Time time = Time.year(2014);
        assert (time.day == null);
        assert (time.month == null);
        assert (time.year == 2014);
    }

    @Test
    public void testDateMonth() {
        Time time = Time.at(5).october();
        assert (time.day == 5);
        assert (time.month == Time.OCTOBER);
        assert (time.year == null);
    }

    @Test
    public void testMonthYear() {
        Time time = Time.in(JANUARY, 2014);
        assert (time.day == null);
        assert (time.month == Time.JANUARY);
        assert (time.year == 2014);
    }


}
