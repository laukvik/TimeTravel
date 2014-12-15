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

    String collectionTitle;
    String collectionWiki;

    private Part file;

    /**
     * Creates a new instance of AdministrationBean
     */
    public AdministrationBean() {
    }


    public String uploadTags() {
        return "tags?faces-redirect=true";
    }

    public void validateTagsFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<>();
        file = (Part) value;
        if (file.getSize() > 1024000000) {
            msgs.add(new FacesMessage("file too big"));
        }
        try {
            if (collectionTitle == null || collectionTitle.isEmpty()) {
                collectionTitle = getFileName();
            }
            srv.importTags(file.getInputStream(), collectionTitle, collectionWiki);
        } catch (ImportFailedException ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage(ex.getMessage()));
        } catch (IOException ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage("Could not read tag file!"));
        } catch (Exception ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage("Could not read tag file!"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public String uploadEvents() {
        return "events?faces-redirect=true";
    }

    public void validateEventsFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<>();
        file = (Part) value;
        if (file.getSize() > 1024000000) {
            msgs.add(new FacesMessage("file too big"));
        }
        try {
            if (collectionTitle == null || collectionTitle.isEmpty()) {
                collectionTitle = getFileName();
            }
            srv.importEvents(file.getInputStream(), collectionTitle, collectionWiki);
        } catch (ImportFailedException ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage(ex.getMessage()));
        } catch (IOException ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage("Could not read eras file!"));
        } catch (Exception ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage(ex.getMessage()));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public String uploadEras() {
        return "eras?faces-redirect=true";
    }

    public void validateEraFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<>();
        file = (Part) value;
        if (file.getSize() > 1024000000) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (collectionTitle == null || collectionTitle.isEmpty()) {
            collectionTitle = getFileName();
        }
        try {
            srv.importEras(file.getInputStream(), collectionTitle, collectionWiki);
        } catch (MissingColumnException ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage(ex.getMessage()));
        } catch (ImportFailedException ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage(ex.getMessage()));
        } catch (IOException ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage("Could not read eras file!"));
        } catch (Exception ex) {
            ex.printStackTrace();
            msgs.add(new FacesMessage(ex.getMessage()));
        }
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
//        User u = srv.createUser(username, password, UserType.MASTER);
//        Tag t1 = srv.createTag("Bhuddism");
//        Tag t2 = srv.createTag("Christianity");
//        Tag t3 = srv.createTag("Hinduism");
//        Tag t4 = srv.createTag("Islam");
//
//        Era e1 = srv.createEra("Classical antiquity", -700, 600);
//        Era e2 = srv.createEra("Middle Ages", 500, 1000);
//        Era e3 = srv.createEra("Early modern period", 1450, 1750);
//
//        Event ev1 = srv.createEvent("Robert Dinwiddie", Time.year(1693), "Robert Dinwiddie (1693 – 27 July 1770) was a British colonial administrator who served as lieutenant governor of colonial Virginia from 1751 to 1758, first under Governor Willem Anne van Keppel, 2nd Earl of Albemarle, and then, from July 1756 to January 1758, as deputy for John Campbell, 4th Earl of Loudoun. Since the governors at that time were largely absentee, he was the de facto.", t1);

        return "./administration/?faces-redirect=true";
    }

// Extract file name from content-disposition header of file part
    private String getFileName() {
        final String partHeader = file.getHeader("content-disposition");
//        System.out.println("***** partHeader: " + partHeader);
        for (String content : file.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public String getCollectionWiki() {
        return collectionWiki;
    }

    public void setCollectionWiki(String collectionWiki) {
        this.collectionWiki = collectionWiki;
    }

}
