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

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class TimeTravelSingleton {

    private final static class LOCK {
        static final TimeTravelSingleton INSTANCE = new TimeTravelSingleton();
    }

    public final static TimeTravelSingleton getInstance() {
        return LOCK.INSTANCE;
    }

    private final EntityManager em;

    private TimeTravelSingleton() {
        em = Persistence.createEntityManagerFactory("TimeTravelPU").createEntityManager();
    }

    public void createUser(String email, String password, UserType type) {
        em.getTransaction().begin();
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);
        u.setType(type);
        em.getTransaction().commit();
    }


}
