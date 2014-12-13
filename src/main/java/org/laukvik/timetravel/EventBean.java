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
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Named(value = "eventBean")
@SessionScoped
@Path("/events")
public class EventBean implements Serializable {

    @EJB
    TimeTravelService srv;

    /**
     * Creates a new instance of EventBean
     */
    public EventBean() {
    }

    @GET
    @Path("/get")
//    @Produces({"application/xml", "application/json"})
    public Event getEvent() {
        Event prod = new Event();
        prod.setTitle("Mattress");
        prod.setDescription("Queen size mattress");
        return prod;
    }

}
