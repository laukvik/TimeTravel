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
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Named(value = "restBean")
@Stateless
@Path("/events")
public class RestBean implements Serializable {

    @EJB
    TimeTravelFacade srv;

    /**
     * Creates a new instance of EventBean
     */
    public RestBean() {
    }

    @GET
    @Path("/get")
    @Produces({"application/json"})
    public Event getEvent() {
        Event prod = new Event();
        prod.setTitle("Mattress");
        prod.setDescription("Queen size mattress");
        return prod;
    }

    @GET
    @Path("/findevents")
    @Produces({"application/json"})
    public List<Event> findEvents() {
        return srv.findEvents();
    }

    @GET
    @Path("/findtags")
    @Produces({"application/json"})
    public List<Tag> findTags() {
        return srv.findTags();
    }

    @GET
    @Path("/finderas")
    @Produces({"application/json"})
    public List<Era> findEras() {
        return srv.findEras();
    }

    @GET
    @Path("/findcollections")
    @Produces({"application/json"})
    public List<org.laukvik.timetravel.Collection> findCollections() {
        return srv.findCollections();
    }

}
