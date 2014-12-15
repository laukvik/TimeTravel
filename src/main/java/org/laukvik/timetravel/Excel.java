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

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class Excel {

    private final XSSFWorkbook wb;
    private XSSFSheet s;

    public Excel(InputStream excel) throws IOException {
        wb = new XSSFWorkbook(excel);
        s = wb.getSheetAt(0);
    }

    /**
     * Returns the number of rows
     *
     * @return
     */
    public int getRowCount() {
        return s.getLastRowNum();
    }

    /**
     * Returns the string value of the cell
     *
     * @param column
     * @param row
     * @return
     */
    public String getText(int column, int row) {
        XSSFRow r = s.getRow(row + 1);
        XSSFCell c = r.getCell(column);
        if (c.getCellType() == XSSFCell.CELL_TYPE_STRING) {
            String v = c.getStringCellValue();
            if (v == null) {
                return null;
            } else if (v.trim().isEmpty()) {
                return null;
            }
            return v;
        }
        return null;
    }

    /**
     * Returns the number in the cell
     *
     * @param column
     * @param row
     * @return
     */
    public Long getNumber(int column, int row) {
        XSSFRow r = s.getRow(row + 1);
        XSSFCell c = r.getCell(column);
        if (c.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            Number n = c.getNumericCellValue();
            return n.longValue();
        }
        return null;
    }

    /**
     * Returns the index for the column name
     *
     * @param name
     * @return
     */
    public int getColumnIndex(String name) {
        XSSFRow r = s.getRow(0);
        for (int x = 0; x < r.getLastCellNum(); x++) {
            String val = r.getCell(x).getStringCellValue();
            if (val.equalsIgnoreCase(name)) {
                return x;
            }
        }
        return -1;
    }

    public String getSheetName() {
        return s.getSheetName();
    }

    public Time getTime(int column, int row) {
        XSSFRow r = s.getRow(row + 1);
        XSSFCell c = r.getCell(column);
        if (c.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            Number n = c.getNumericCellValue();
            return Time.year(n.intValue());
        } else if (c.getCellType() == XSSFCell.CELL_TYPE_STRING) {
            Time t = Time.parse(c.getStringCellValue());
            return t;
        }

        try {
            java.util.Date date = c.getDateCellValue();
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);

            Time t = new Time();
            t.year = cal.get(Calendar.YEAR);
            t.month = cal.get(Calendar.MONTH);
            t.day = cal.get(Calendar.DAY_OF_MONTH);

            return t;
        } catch (Exception e) {
        }
        return null;
    }

}
