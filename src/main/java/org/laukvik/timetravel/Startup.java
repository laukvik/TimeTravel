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

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.Singleton;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Singleton
@javax.ejb.Startup
public class Startup {

    @EJB
    private TimeTravelFacade facade;

    @PostActivate
    public void createDefaults() {
        facade.createUser("morten@laukvik.no", "123", UserType.MASTER);
    }

}