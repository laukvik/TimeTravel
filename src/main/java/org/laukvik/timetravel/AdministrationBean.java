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

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Enables administration of users and events
 *
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Named(value = "administrationBean")
@SessionScoped
public class AdministrationBean implements Serializable {

    @EJB
    TimeTravelService srv;

    String username;
    String password;
    Long userid;

    /**
     * Creates a new instance of AdministrationBean
     */
    public AdministrationBean() {
    }

    public String open() {
        return "./administration/?faces-redirect=true";
    }

    public String createUser() {
        srv.createUser(username, password);
        return "./administration/?faces-redirect=true";
    }

}
