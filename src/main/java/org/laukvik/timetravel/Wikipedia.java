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
import java.net.URL;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class Wikipedia {

    private final String url;
    private String title;
    private String description;
    private String photo;

    public Wikipedia(String url) throws Exception {
        this.url = url;
        parse();
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }


    private void parse() throws IOException {
        Source source = new Source(new URL(url));
        /* Find title tag */
        title = source.getElementById("firstHeading").getTextExtractor().toString();

//        List<Element> items = source.getAllElements(HTMLElementName.TITLE);
//        title = items.get(0).getTextExtractor().toString();

        /* Extract text */
        description = source.getFirstElement("p").getTextExtractor().toString();
//        description = source.getTextExtractor().toString();


        /* Extract photo */
//        source.clearCache();
        List<Element> images = source.getAllElements(HTMLElementName.IMG);
        for (Element el : images) {

            int width = Integer.parseInt(el.getAttributeValue("width"));
//            System.out.println("img: width=" + el.getAttributeValue("width") + " src=" + el.getAttributeValue("src"));
            if (width == 220) {
                photo = "http:" + el.getAttributeValue("src");
                break;
            }
        }
    }


}
