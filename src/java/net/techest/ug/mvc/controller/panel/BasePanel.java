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
 *  Created on : Sep 18, 2011, 8:44:51 AM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.panel;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.techest.ug.beans.UserAndFieldsProxy;
import net.techest.ug.helper.Log4j;
import net.techest.ug.mvc.controller.BaseController;
import net.techest.ug.mvc.entity.Colleague;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.Notice;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import net.techest.ug.mvc.entity.Usergroup;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/global.do")
public class BasePanel extends BaseController {

    public BasePanel() {
    }


    /**
     * 设置左边的栏位
     *
     * @param request
     * @param modelMap
     */
    public void fetchLeft(String path, String base, String type, HttpServletRequest request, ModelMap modelMap) {

        try {
            StringBuilder content = new StringBuilder("<ul id=\"nav\">");
            SAXReader saxReader = new SAXReader();
            URL inputXml = request.getServletContext().getResource("WEB-INF/" + path);
            Document document = saxReader.read(inputXml);
            Element e = document.getRootElement();
            for (Iterator i = e.elementIterator(); i.hasNext();) {
                Element e1 = (Element) i.next();
                if (e1.attribute("name").getStringValue().equals(base)) {
                    content.append("<li><a class='heading expanded'>");
                } else {
                    content.append("<li><a class='heading collapsed'>");
                }
                content.append(e1.attribute("value").getStringValue());
                content.append("</a> <ul class='navigation'>");

                for (Iterator i2 = e1.elementIterator(); i2.hasNext();) {
                    Element e2 = (Element) i2.next();
                    if (e1.attribute("name").getStringValue().equals(base) && e2.attribute("name").getStringValue().equals(type)) {
                        content.append("<li class='heading selected'>");
                        content.append(e2.getStringValue());
                    } else {
                        content.append("<li>");
                        String url = e.attribute("baseurl").getStringValue() + "?c=" + e1.attribute("name").getStringValue() + e2.attribute("name").getStringValue();
                        content.append("<a href='" + url + "' title=''>" + e2.getStringValue() + "</a>");
                    }
                    content.append("</li>");

                }
                content.append("</ul></li>");
            }
            content.append("</ul>");
            modelMap.put("leftbar", content.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(params = "fetchMajor", method = RequestMethod.POST)
    public String fetchMajor(int id, HttpServletRequest request, ModelMap modelMap) throws Exception {
        List<Major> cs = getDBService().getMajorsByCid(id);
        JSONArray json = JSONArray.fromObject(cs);
        modelMap.addAttribute("json", json);
        return "json";
    }

    @RequestMapping(params = "fetchMajorById", method = RequestMethod.POST)
    public String fetchMajorById(int id, HttpServletRequest request, ModelMap modelMap) throws Exception {
        List<Major> cs = getDBService().getMajorListById(id);
        JSONArray json = JSONArray.fromObject(cs);
        modelMap.addAttribute("json", json);
        return "json";
    }
    /**全局数据显示
     *
     * @param request
     * @param modelMap
     */
    public void global(HttpServletRequest request, ModelMap modelMap) {
        User u = (User) request.getSession().getAttribute("user");
        Usergroup ug = getDBService().getGroupByUser(u);
        modelMap.put("ug", ug);
        modelMap.put("user", u);
        //显示通知
        List<Notice> notices = getDBService().getRecentNoticeByUser(u);
        modelMap.put("noticesize", notices.size());
        modelMap.put("notice", notices);
    }

    /**分页创建
     * 
     */
    public void buildPager(ModelMap modelMap, String linkprefix, int nowpage, int persize, int totoal) {
        int totalpages = (int) totoal / persize;
        Log4j.getInstance().debug("共" + totalpages + "页");
        StringBuilder content = new StringBuilder();
        content.append("<ul class=\"pagination\"><li class=\"text\"><a href='" + linkprefix + "&p=0&s=" + persize + "'>首页</a></li>");

        if(totoal % persize==0)totalpages--;
        
        for (int i = 0; i <= totalpages; i++) {
            if (nowpage == i) {
                content.append("<li class='page'><a href='" + linkprefix + "&p=" + i + "&s=" + persize + "'>" + (i + 1) + "</a></li>");
            } else {
                content.append("<li><a href='" + linkprefix + "&p=" + i + "&s=" + persize + "'>" + (i + 1) + "</a></li>");
            }
        }

        content.append("<li class=\"text\"><a href='" + linkprefix + "&p=" + totalpages + "&s=" + persize + "'>末页</a></li></ul>");

        modelMap.put("pager", content.toString());
    }

    /**权限检查
     * 
     */
    public boolean havePermission(String permissiontype, HttpServletRequest request, ModelMap modelMap) {

        if (request.getSession().getAttribute("user") == null) {
            modelMap.put("time", "3");
            modelMap.put("url", request.getContextPath() + "/login.do");
            modelMap.put("msg", "对不起，请先登录");
            return false;
        }
        if(permissiontype.equals("login")){
            return true;
        }
        this.nowUser = (User) request.getSession().getAttribute("user");
        Log4j.getInstance().debug("检测权限" + permissiontype);
        if (getDBService().havePermission(getNowUser(), permissiontype)) {
            Log4j.getInstance().debug("允许");
            return true;
        } else {
            Log4j.getInstance().debug("拒绝");
            modelMap.put("msg", "对不起，您没有权限");
            return false;
        }
    }

}
