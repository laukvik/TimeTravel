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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;

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
    TimeTravelFacade srv;

    String username;
    String password;
    Long userid;

    private Part file;

    /**
     * Creates a new instance of AdministrationBean
     */
    public AdministrationBean() {
    }

    public String uploadTags() {
        try {
            srv.importTags(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "tags?faces-redirect=true";
    }

    public String uploadEvents() {
        try {
            srv.importEvents(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "events?faces-redirect=true";
    }

    public String uploadEras() {
        try {
            srv.importEras(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "eras?faces-redirect=true";
    }

    public void validateExcelFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 1024000000) {
            msgs.add(new FacesMessage("file too big"));
        }
//        if (!"application/ms-excel".equals(file.getContentType())) {
//            msgs.add(new FacesMessage("not a text file"));
//        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }


    public String open() {
        return "./administration/?faces-redirect=true";
    }

    public String createUser() {
        User u = srv.createUser(username, password, UserType.MASTER);
        Tag t1 = srv.createTag("Bhuddism");
        Tag t2 = srv.createTag("Christianity");
        Tag t3 = srv.createTag("Hinduism");
        Tag t4 = srv.createTag("Islam");

        Era e1 = srv.createEra("Classical antiquity", -700, 600);
        Era e2 = srv.createEra("Middle Ages", 500, 1000);
        Era e3 = srv.createEra("Early modern period", 1450, 1750);

        Event ev1 = srv.createEvent("Robert Dinwiddie", Time.year(1693), "Robert Dinwiddie (1693 – 27 July 1770) was a British colonial administrator who served as lieutenant governor of colonial Virginia from 1751 to 1758, first under Governor Willem Anne van Keppel, 2nd Earl of Albemarle, and then, from July 1756 to January 1758, as deputy for John Campbell, 4th Earl of Loudoun. Since the governors at that time were largely absentee, he was the de facto.", t1);

        return "./administration/?faces-redirect=true";
    }

}
