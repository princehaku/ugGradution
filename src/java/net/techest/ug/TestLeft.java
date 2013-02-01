/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : Sep 18, 2011, 8:07:34 PM
 *  Author     : princehaku
 */
package net.techest.ug;

import java.net.URL;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author princehaku
 */
public class TestLeft {

    public static void main(String[] ags) {

        try {
            String base="trunk";
            String type="list";
            StringBuilder content = new StringBuilder("<ul id=\"nav\">");
            SAXReader saxReader = new SAXReader();
            URL inputXml = ClassLoader.getSystemClassLoader().getResource("AdminMenu.xml");
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext();) {
                Element e = (Element) i.next();
                System.out.println(e.attribute("name"));
                if (e.attribute("name").getStringValue().equals(base)) {
                    content.append("<li><a class='heading'>");
                } else {
                    content.append("<li><a class='heading collapsed'>");
                }
                content.append(e.attribute("value").getStringValue());
                content.append("</a> <ul class='navigation'>");

                for (Iterator i2 = e.elementIterator(); i2.hasNext();) {
                    Element e2 = (Element) i2.next();
                    System.out.println(e2.attribute("name"));

                    if (e2.attribute("name").getStringValue().equals(type)) {
                        content.append("<li class='heading selected'>");
                        content.append(e2.getStringValue());
                    } else {
                        content.append("<li>");
                        String url = e.attribute("name").getStringValue() + ".do?c=" + e2.attribute("name").getStringValue();
                        content.append("<a href='" + url + "' title=''>" + e2.getStringValue() + "</a>");
                    }
                    content.append("</li>");

                }
                content.append("</ul></li>");
            }
            content.append("</ul>");
            System.out.print(content.toString());
        } catch (Exception ex) {
        }

    }
}
