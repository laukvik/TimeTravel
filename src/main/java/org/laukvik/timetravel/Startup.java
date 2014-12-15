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
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Singleton
@javax.ejb.Startup
public class Startup implements Serializable {

    @EJB
    private TimeTravelFacade srv;


    @PostConstruct
    void open() {
//        System.out.println("STARTING SINGLETON!!!!!!!");
//        srv.removeEverything();
//        User u = srv.createUser("morten@laukvik.no", "123", UserType.MASTER);
//        Tag t1 = srv.createTag("Bhuddism");
//        Tag t2 = srv.createTag("Christianity");
//        Tag t3 = srv.createTag("Hinduism");
//        Tag t4 = srv.createTag("Islam");
//
//        Era e1 = srv.createEra("Classical antiquity", -700, 600);
//        Era e2 = srv.createEra("Middle Ages", 500, 1000);
//        Era e3 = srv.createEra("Early modern period", 1450, 1750);
////
//        Event ev1 = srv.createEvent("Robert Dinwiddie", Time.year(1693), "Robert Dinwiddie (1693 – 27 July 1770) was a British colonial administrator who served as lieutenant governor of colonial Virginia from 1751 to 1758, first under Governor Willem Anne van Keppel, 2nd Earl of Albemarle, and then, from July 1756 to January 1758, as deputy for John Campbell, 4th Earl of Loudoun. Since the governors at that time were largely absentee, he was the de facto.", t1);

//        srv.importEvents(new FileInputStream("/Users/morten/Projects/TimeTravel/Events.xlsx"));
//        srv.importTags(new FileInputStream("/Users/morten/Projects/TimeTravel/Tags.xlsx"));
//        srv.importEras(new FileInputStream("/Users/morten/Projects/TimeTravel/Eras.xlsx"));
    }

    @PreDestroy
    void close() {
    }

}
