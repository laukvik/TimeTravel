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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public void removeEverything() {
        LOG.info("Remove everything!");
//        em.createNamedQuery("Tag.removeAll").executeUpdate();
//        em.createNamedQuery("Era.removeAll").executeUpdate();
//        em.createNamedQuery("Event.removeAll").executeUpdate();
//        em.createNamedQuery("User.removeAll").executeUpdate();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Event> findEventsInEra(Era era) {
        return em.createNamedQuery("Event.findInEra").setParameter("era", era).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Event> findEventsByTag(Tag... tags) {
        List<Tag> list = new ArrayList<>();
        list.addAll(Arrays.asList(tags));
        return em.createNamedQuery("Event.findByTag").setParameter("tags", list).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Event> findEventsByTagsInEra(Era era, Tag... tags) {
        List<Tag> list = new ArrayList<>();
        list.addAll(Arrays.asList(tags));
        return em.createNamedQuery("Event.findByTagInEra").setParameter("era", era).setParameter("tags", list).getResultList();
    }

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
//        e.getTags().addAll(Arrays.asList(tags));
        em.persist(e);
        return e;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Tag> findTags() {
        return em.createNamedQuery("Tag.findAll").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Era> findEras() {
        return em.createNamedQuery("Era.findAll").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Event> findEvents() {
        return em.createNamedQuery("Event.findAll").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<org.laukvik.timetravel.Collection> findCollections() {
        return em.createNamedQuery("Collection.findAll").getResultList();
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Event updateWiki(Long eventId) {
        LOG.info("Updating wiki information for {0} ", eventId);
        Event evt = em.find(Event.class, eventId);
        updateWiki(evt);
        em.persist(evt);
        return evt;
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Event updateWiki(Event evt) {
        if (evt.getWiki() == null || evt.getWiki().isEmpty()) {
            LOG.info("Cant update wiki for event {0} {1}", evt, evt.getWiki());
            return evt;
        }
        LOG.info("Updating wiki for event {0} {1}", evt, evt.getWiki());
        try {
            Wikipedia wp = new Wikipedia(evt.getWiki());
            evt.setTitle(wp.getTitle());
            evt.setDescription(wp.getDescription());
            evt.setPhoto(wp.getPhoto());

        } catch (Exception e) {
            LOG.warn("Failed updating wiki for event {0} ", evt.getWiki());
            evt = null;
        }
        return evt;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Tag findTagByTitle(String title) {
        List<Tag> tags = em.createNamedQuery("Tag.findByTitle", Tag.class).setParameter("title", title).getResultList();
        if (tags.isEmpty()) {
            return null;
        }
        return tags.get(0);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Collection updateWikiForCollection(Collection evt) {
        if (evt.getWiki() == null || evt.getWiki().isEmpty()) {
            LOG.info("Cant update wiki for Collection {0} {1}", evt, evt.getWiki());
            return evt;
        }
        LOG.info("Updating wiki for Collection {0} {1}", evt, evt.getWiki());
        try {
            Wikipedia wp = new Wikipedia(evt.getWiki());
            evt.setTitle(wp.getTitle());
            evt.setDescription(wp.getDescription());
            evt.setPhoto(wp.getPhoto());

        } catch (Exception e) {
            LOG.warn("Failed updating wiki for Collection {0} ", evt.getWiki());
            evt = null;
        }
        return evt;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void updateEraByWiki(Era era) throws Exception {
        LOG.info("Getting era details from wiki {0} ", era);
        Wikipedia wp = new Wikipedia(era.getWiki());
        era.setPhoto(wp.getPhoto());
        era.setTitle(wp.getTitle());
        era.setDescription(wp.getDescription());
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void updateTagFromWiki(Tag tag) throws Exception {
        LOG.info("Getting tag details from wiki {0} ", tag);
        Wikipedia wp = new Wikipedia(tag.getWiki());
        tag.setPhoto(wp.getPhoto());
        tag.setTitle(wp.getTitle());
        tag.setDescription(wp.getDescription());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long importTags(InputStream excelInputStream, String collectionTitle, String collectionWiki) throws MissingColumnException, ImportFailedException {
        LOG.info("Importing tag collection {0} using wiki: {1}", collectionTitle, collectionWiki);
        if (collectionTitle == null || collectionTitle.isEmpty()) {
            throw new MissingTitleException();
        }

        int importCount = 0;
        try {
            Excel xl = new Excel(excelInputStream);

            final int TITLE_COLUMN = xl.getColumnIndex(IMPORT_TITLE);
            final int DESC_COLUMN = xl.getColumnIndex(IMPORT_DESC);
            final int WIKI_COLUMN = xl.getColumnIndex(IMPORT_WIKI);
            final int PHOTO_COLUMN = xl.getColumnIndex(IMPORT_PHOTO);

            /* Validate columns */

            /* Collect missing columns */
            List<String> missing = new ArrayList<>();
            if (TITLE_COLUMN == -1) {
                missing.add(IMPORT_TITLE);
            }
            if (DESC_COLUMN == -1) {
                missing.add(IMPORT_DESC);
            }
            if (WIKI_COLUMN == -1) {
                missing.add(IMPORT_WIKI);
            }
            if (PHOTO_COLUMN == -1) {
                missing.add(IMPORT_PHOTO);
            }
            if (!missing.isEmpty()) {
                throw new MissingColumnException(collectionTitle, missing);
            }

            int max = xl.getRowCount();

            TagCollection c = new TagCollection();
            c.setTitle(collectionTitle);
            c.setWiki(collectionWiki);
            em.persist(c);

            for (int y = 0; y < max; y++) {
                LOG.info("Reading tag {0}/{1} in {2}", (y + 1), max, collectionTitle);
                Tag tag = new Tag();
                tag.setCollection(c);
                try {
                    try {
                        tag.setWiki(xl.getText(WIKI_COLUMN, y));
                        updateTagFromWiki(tag);
                    } catch (NullPointerException e) {
                    }
                    try {
                        String title = xl.getText(TITLE_COLUMN, y);
                        if (title != null && !title.trim().isEmpty()) {
                            tag.setTitle(title);
                        }
                    } catch (NullPointerException e) {
                    }
                    try {
                        String desc = xl.getText(DESC_COLUMN, y);
                        if (desc != null && !desc.trim().isEmpty()) {
                            tag.setDescription(desc);
                        }
                    } catch (NullPointerException e) {
                    }
                    try {
                        String photo = xl.getText(PHOTO_COLUMN, y);
                        if (photo != null && !photo.trim().isEmpty()) {
                            tag.setPhoto(photo);
                        }
                    } catch (NullPointerException e) {
                    }
                } catch (Exception e) {
                    tag = null;
                    break;
                }
                if (tag != null) {
                    em.persist(tag);
                    importCount++;
                }
            }
        } catch (FileNotFoundException ex) {
            LOG.info("Importing tag collection {0}: File not found! ", collectionTitle);
            throw new ImportFailedException(collectionTitle);
        } catch (IOException ex) {
            LOG.info("Importing tag collection {0}: Could not read file! ", collectionTitle);
            throw new ImportFailedException(collectionTitle);
        }
        return importCount;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long importEras(InputStream excelInputStream, String collectionTitle, String collectionWiki) throws MissingColumnException, ImportFailedException {
        LOG.info("Importing eras collection {0} using wiki {1}", collectionTitle, collectionWiki);
        long importCount = 0;
        try {

            Excel xl = new Excel(excelInputStream);



            if (collectionTitle == null || collectionTitle.isEmpty()) {
                throw new MissingTitleException();
//                collectionTitle = xl.getSheetName();
            }

            final int TITLE_COLUMN = xl.getColumnIndex(IMPORT_TITLE);
            final int FROM_COLUMN = xl.getColumnIndex(IMPORT_FROM);
            final int TO_COLUMN = xl.getColumnIndex(IMPORT_TO);
            final int DESC_COLUMN = xl.getColumnIndex(IMPORT_DESC);
            final int WIKI_COLUMN = xl.getColumnIndex(IMPORT_WIKI);
            final int PHOTO_COLUMN = xl.getColumnIndex(IMPORT_PHOTO);

            /* Validate columns */

            /* Collect missing columns */
            List<String> missing = new ArrayList<>();
            if (TITLE_COLUMN == -1) {
                missing.add(IMPORT_TITLE);
            }
            if (FROM_COLUMN == -1) {
                missing.add(IMPORT_FROM);
            }
            if (TO_COLUMN == -1) {
                missing.add(IMPORT_TO);
            }
            if (DESC_COLUMN == -1) {
                missing.add(IMPORT_DESC);
            }
            if (WIKI_COLUMN == -1) {
                missing.add(IMPORT_WIKI);
            }
            if (PHOTO_COLUMN == -1) {
                missing.add(IMPORT_PHOTO);
            }
            if (!missing.isEmpty()) {
                throw new MissingColumnException(collectionTitle, missing);
            }

            EraCollection c = new EraCollection();
            c.setTitle(collectionTitle);
            c.setWiki(collectionWiki);
            em.persist(c);

            int max = xl.getRowCount();
            for (int y = 0; y < max; y++) {
                LOG.info("Reading tag {0}/{1} in collection {2}", (y + 1), max, collectionTitle);
                Era era = new Era();
                era.setCollection(c);
                try {
                    try {
                        era.setFromYear(xl.getNumber(FROM_COLUMN, y));
                    } catch (NullPointerException e) {
                    }
                    try {
                        era.setToYear(xl.getNumber(TO_COLUMN, y));
                    } catch (NullPointerException e) {
                    }
                    try {
                        era.setWiki(xl.getText(WIKI_COLUMN, y));
                        updateEraByWiki(era);
                    } catch (NullPointerException e) {
                    }

                    try {
                        String title = xl.getText(TITLE_COLUMN, y);
                        if (title != null && !title.trim().isEmpty()) {
                            era.setTitle(title);
                        }
                    } catch (NullPointerException e) {
                    }
                    try {
                        String desc = xl.getText(DESC_COLUMN, y);
                        if (desc != null && !desc.trim().isEmpty()) {
                            era.setDescription(desc);
                        }
                    } catch (NullPointerException e) {
                    }
                    try {
                        String photo = xl.getText(PHOTO_COLUMN, y);
                        if (photo != null && !photo.trim().isEmpty()) {
                            era.setPhoto(photo);
                        }
                    } catch (NullPointerException e) {
                    }

                } catch (Exception e) {
                    era = null;
                    break;
                }
                if (era != null) {
                    LOG.info("Importing eras collection {0} {1}", collectionTitle, era);
                    System.out.print(era);
                    em.persist(era);
                    importCount++;
                }
            }
        } catch (FileNotFoundException ex) {
            LOG.info("Importing eras collection {0}: File not found! ", collectionTitle);
            throw new ImportFailedException(collectionTitle);
        } catch (IOException ex) {
            LOG.info("Importing eras collection {0}: Could not read file! ", collectionTitle);
            throw new ImportFailedException(collectionTitle);
        }
        return importCount;
    }

    public final static String IMPORT_TITLE = "Title";
    public final static String IMPORT_DATE = "Date";
    public final static String IMPORT_DESC = "Description";
    public final static String IMPORT_WIKI = "Wiki";
    public final static String IMPORT_PHOTO = "Photo";
    public final static String IMPORT_FROM = "From";
    public final static String IMPORT_TO = "To";

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long importEvents(InputStream excelInputStream, String collectionTitle, String collectionWiki) throws MissingColumnException, ImportFailedException {
        LOG.info("Importing event collection {0} using wiki {1}", collectionTitle, collectionWiki);
        if (collectionTitle == null || collectionTitle.isEmpty()) {
            throw new MissingTitleException();
        }

        int importCount = 0;
        try {
            Excel xl = new Excel(excelInputStream);
            /* Validate columns */
            final int TITLE_COLUMN = xl.getColumnIndex(IMPORT_TITLE);
            final int DATE_COLUMN = xl.getColumnIndex(IMPORT_DATE);
            final int DESC_COLUMN = xl.getColumnIndex(IMPORT_DESC);
            final int WIKI_COLUMN = xl.getColumnIndex(IMPORT_WIKI);
            final int PHOTO_COLUMN = xl.getColumnIndex(IMPORT_PHOTO);
            /* Collect missing columns */
            List<String> missing = new ArrayList<>();
            if (TITLE_COLUMN == -1) {
                missing.add(IMPORT_TITLE);
            }
            if (DATE_COLUMN == -1) {
                missing.add(IMPORT_DATE);
            }
            if (DESC_COLUMN == -1) {
                missing.add(IMPORT_DESC);
            }
            if (WIKI_COLUMN == -1) {
                missing.add(IMPORT_WIKI);
            }
            if (PHOTO_COLUMN == -1) {
                missing.add(IMPORT_PHOTO);
            }
            if (!missing.isEmpty()) {
                throw new MissingColumnException(collectionTitle, missing);
            }

            EventCollection c = new EventCollection();
            c.setTitle(collectionTitle);
            c.setWiki(collectionWiki);
            em.persist(c);

            int max = xl.getRowCount();
            for (int y = 1; y < max; y++) {
                LOG.info("Importing event {0}/{1} in collection {2}", (y + 1), max, collectionTitle);
                Event evt = new Event();
                evt.setCollection(c);
                try {
                    /* Extract details */
                    evt.setTime(xl.getTime(DATE_COLUMN, y));
                    try {
                        evt.setWiki(xl.getText(WIKI_COLUMN, y));
                        /* Fetch wiki details */
                        updateWiki(evt);
                    } catch (Exception e) {
                    }

                    try {
                        String title = xl.getText(TITLE_COLUMN, y);
                        if (title != null && !title.trim().isEmpty()) {
                            evt.setTitle(title);
                        }
                    } catch (NullPointerException e) {
                    }
                    try {
                        String desc = xl.getText(DESC_COLUMN, y);
                        if (desc != null && !desc.trim().isEmpty()) {
                            evt.setDescription(desc);
                        }
                    } catch (NullPointerException e) {
                    }
                    try {
                        String photo = xl.getText(PHOTO_COLUMN, y);
                        if (photo != null && !photo.trim().isEmpty()) {
                            evt.setPhoto(photo);
                        }
                    } catch (NullPointerException e) {
                    }

                } catch (NullPointerException e) {
                    evt = null;
                    break;
                }
                if (evt != null) {
                    em.persist(evt);
                    importCount++;
                }
            }
        } catch (FileNotFoundException ex) {
            LOG.info("Importing event collection {0}: File not found!", collectionTitle);
            throw new ImportFailedException(collectionTitle);
        } catch (IOException ex) {
            LOG.info("Importing event collection {0}: IO error!", collectionTitle);
            throw new ImportFailedException(collectionTitle);
        }
        return importCount;
    }

}
