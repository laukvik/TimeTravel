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

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Era.findAll", query = "SELECT e FROM Era e ORDER BY e.title ASC"),
    @NamedQuery(name = "Era.removeAll", query = "DELETE FROM Era e")
})
public class Era implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private long fromYear;
    private long toYear;

    @XmlElement(required = true)
    @Lob
    @NotNull
    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String description;

    @Lob
    private String wiki;

    @XmlElement(required = true)
    @Lob
    private String photo;

    @NotNull
    @ManyToOne
    private EraCollection collection;

    public Era() {
    }

    public Era(String title, long fromYear, long toYear) {
        this.fromYear = fromYear;
        this.toYear = toYear;
        this.title = title;
    }


    @Override
    public int hashCode() {
        int hash = 3;
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
        final Era other = (Era) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getFromYear() {
        return fromYear;
    }

    public void setFromYear(long fromYear) {
        this.fromYear = fromYear;
    }

    public long getToYear() {
        return toYear;
    }

    public void setToYear(long toYear) {
        this.toYear = toYear;
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

    public EraCollection getCollection() {
        return collection;
    }

    public void setCollection(EraCollection collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "Era{" + "id=" + id + ", fromYear=" + fromYear + ", toYear=" + toYear + ", title=" + title + ", collection=" + collection + '}';
    }



}
