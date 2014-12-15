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

import java.io.FileInputStream;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class ExcelTest {

    public ExcelTest() {
    }

    @Test
    public void findColumnIndexes() throws IOException {
        Excel excel = new Excel(new FileInputStream("Classical_antiquity.xlsx"));
        assertThat(excel.getColumnIndex("Title")).isEqualTo(0);
        assertThat(excel.getColumnIndex("From")).isEqualTo(1);
        assertThat(excel.getColumnIndex("To")).isEqualTo(2);
        assertThat(excel.getColumnIndex("Description")).isEqualTo(3);
        assertThat(excel.getColumnIndex("Wiki")).isEqualTo(4);
    }

    @Test
    public void findCellValues() throws IOException {
        Excel excel = new Excel(new FileInputStream("Classical_antiquity.xlsx"));
        assertThat(excel.getRowCount()).isEqualTo(7);
        assertThat(excel.getText(0, 0)).isEqualTo("Archaic Period");
        assertThat(excel.getNumber(1, 0)).isEqualTo(-700L);
        assertThat(excel.getNumber(2, 0)).isEqualTo(100L);
    }

    @Test
    public void readYear() throws IOException {
        Excel excel = new Excel(new FileInputStream("List_of_Presidents_of_the_United_States.xlsx"));
        Time time = excel.getTime(0, 0);
        assertThat(time.year).isEqualTo(1731);
        assertThat(time.month).isNull();
        assertThat(time.day).isNull();
    }


}
