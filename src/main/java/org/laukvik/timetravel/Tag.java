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
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag.findByTitle", query = "SELECT t FROM Tag t WHERE t.title = :title"),
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t ORDER BY t.title ASC"),
    @NamedQuery(name = "Tag.removeAll", query = "DELETE FROM Tag t")
})
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlTransient
    @Version
    private int version;

    @NotNull
    @ManyToOne
    private TagCollection collection;

    @XmlTransient
    @ManyToOne
    private User author;

    @NotNull
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @Lob
    private String wiki;

    @XmlElement(required = true)
    @Lob
    private String photo;

//    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
//    List<Event> events;


    public Tag() {
    }

    public Tag(String title) {
        this.title = title;
    }


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Tag other = (Tag) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public TagCollection getCollection() {
        return collection;
    }

    public void setCollection(TagCollection collection) {
        this.collection = collection;
    }


    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", title=" + title + ", wiki=" + wiki + '}';
    }


}
