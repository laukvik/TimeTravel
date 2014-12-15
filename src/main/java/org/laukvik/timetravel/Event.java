/*
 * Copyright (C) 2014 morten
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author morten
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e ORDER BY e.time.year ASC, e.time.month ASC, e.time.day ASC"),
    @NamedQuery(name = "Event.removeAll", query = "DELETE FROM Event e"),
    @NamedQuery(name = "Event.findByTag", query = "SELECT e FROM Event e JOIN e.metas m WHERE m.tag IN :tags"),
    @NamedQuery(name = "Event.findInEra", query = "SELECT e FROM Event e WHERE e.time.year BETWEEN :from AND :to ORDER BY e.time.year ASC, e.time.month ASC, e.time.day ASC"),
    @NamedQuery(name = "Event.findByTagInEra", query = "SELECT e FROM Event e JOIN e.metas m WHERE m.tag IN :tags AND e.time.year BETWEEN :from AND :to  ORDER BY e.time.year ASC, e.time.month ASC, e.time.day ASC")
})
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlTransient
    @Version
    private int version;

    @Embedded
    @XmlElement(required = true)
    private Time time;

    @XmlElement(required = true)
    @Lob
    @NotNull
    private String title;

    @XmlElement(required = true)
//    @XmlTransient
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @XmlElement(required = true)
    @Lob
    private String photo;

    @XmlElement(required = true)
    @Lob
    private String wiki;

    @XmlTransient
    @ManyToOne
    private User author;

    @XmlTransient
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @NotNull
    @ManyToOne
    private EventCollection collection;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "event")
    private List<Meta> metas;

    public Event() {
        metas = new ArrayList<>();
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void addMeta(Tag tag) {
        Meta m = new Meta();
        m.setEvent(this);
        m.setTag(tag);
        metas.add(m);
    }

    @PrePersist
    private void beforeSave() {
        this.created = new Date();
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public EventCollection getCollection() {
        return collection;
    }

    public void setCollection(EventCollection collection) {
        this.collection = collection;
    }


    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", time=" + time + ", title=" + title + ", wiki=" + wiki + '}';
    }



}
