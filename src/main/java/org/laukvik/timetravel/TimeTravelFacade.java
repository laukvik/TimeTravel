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
import java.util.Arrays;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Stateless
public class TimeTravelFacade implements Serializable {

    private static final Logger LOG = LogManager.getLogger("TimeTravel");

    @PersistenceContext(unitName = "TimeTravelPU")
    private EntityManager em;


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User createUser(String email, String password, UserType type) {
        LOG.info("Creating new user with email {0} and password {1} {2}", email, password, type);
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);
        u.setType(type);
        em.persist(u);
        return u;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Tag createTag(String title) {
        LOG.info("Creating tag  {0} ", title);
        Tag t = new Tag();
        t.setTitle(title);
        em.persist(t);
        return t;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Era createEra(String title, long fromYear, long toYear) {
        LOG.info("Creating Era  {0} ", title);
        Era e = new Era();
        e.setTitle(title);
        e.setFromYear(fromYear);
        e.setToYear(toYear);
        em.persist(e);
        return e;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Event createEvent(String title, Time time, String description, Tag... tags) {
        LOG.info("Creating Era  {0} ", title);
        Event e = new Event();
        e.setTitle(title);
        e.setTime(time);
        e.setDescription(description);
        e.getTags().addAll(Arrays.asList(tags));
        em.persist(e);
        return e;
    }

}
