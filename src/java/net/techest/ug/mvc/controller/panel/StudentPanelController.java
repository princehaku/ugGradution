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
 *  Created on : Sep 13, 2011, 10:13:44 AM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.panel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.beans.PaperAndOwnerProxy;
import net.techest.ug.beans.PaperSelectProxy;
import net.techest.ug.beans.UserAndFieldsProxy;
import net.techest.ug.mvc.entity.Colleague;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.News;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.Paperyear;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/student.do")
public class StudentPanelController extends BasePanel {

    protected String menuxml = "UserMenu.xml";

    public String getMenuxml() {
        return menuxml;
    }

    /**学生首页
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=newslist")
    public String home(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("news_list", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "", "newslist", request, modelMap);
        //显示新闻
        List<News> news = getDBService().getRecentNews(5);
        modelMap.put("news", news);

        modelMap.put("module", "news_list");
        return "panel/panel";
    }

    /**我自己的选题
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=myselect")
    public String myselect(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("my_select", request, modelMap)) {
            return "global/notify";
        }

        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "", "myselect", request, modelMap);

        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }

//        Colleague c=getDBService().getColleagueByMajor(major);
//        List<User> teachers=getDBService().getColleagueTeachers(c);

        List<Papersel> papersel = getDBService().getPaperSelByUser(getNowUser(), st, s);
        int total = getDBService().getPaperSelByUserNumbers(getNowUser());
//        modelMap.put("teachers",teachers);
        List<PaperSelectProxy> psp = new ArrayList<PaperSelectProxy>();

        Iterator it = papersel.iterator();

        while (it.hasNext()) {
            Papersel ps = (Papersel) it.next();
            //
            PaperSelectProxy paperselectproxy = new PaperSelectProxy();
            paperselectproxy.setPapersel(ps);
            //用户详情和题库
            PaperAndOwnerProxy pnuproxy = new PaperAndOwnerProxy(getDBService().getUserFieldsByUserId(ps.getOwneruid()), getDBService().getPaperTrunkById(ps.getPid()));
            paperselectproxy.setPaperinfo(pnuproxy);

            psp.add(paperselectproxy);
        }

        modelMap.put("papers", psp);
        // 是否选题已经结束
        modelMap.put("isEnd", 0);
        Paperyear py = getDBService().getPaperYearByMajor(getDBService().getMajorByUser(getNowUser()));
        Date d= new Date();
        if (d.getTime() >= py.getDtstart().getTime() || py.getStatu() == 2) {
            modelMap.put("isEnd", 1);
        }
        
        this.buildPager(modelMap, "student.do?c=select", st, s, total);

        modelMap.put("module", "myselect");
        return "panel/panel";
    }

    /**题库查看
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=select")
    public String select(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "", "select", request, modelMap);

        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }

        Major major = getDBService().getMajorByUser(getNowUser());
//        Colleague c=getDBService().getColleagueByMajor(major);
//        List<User> teachers=getDBService().getColleagueTeachers(c);

        List<Papersel> papersel = getDBService().getPaperSelByMajor(major, st, s);
        int total = getDBService().getPaperSelByMajorNumbers(major);
//        modelMap.put("teachers",teachers);
        List<PaperSelectProxy> psp = new ArrayList<PaperSelectProxy>();

        Iterator it = papersel.iterator();

        while (it.hasNext()) {
            Papersel ps = (Papersel) it.next();
            //
            PaperSelectProxy paperselectproxy = new PaperSelectProxy();
            paperselectproxy.setPapersel(ps);
            //用户详情和题库
            PaperAndOwnerProxy pnuproxy = new PaperAndOwnerProxy(getDBService().getUserFieldsByUserId(ps.getOwneruid()), getDBService().getPaperTrunkById(ps.getPid()));
            paperselectproxy.setPaperinfo(pnuproxy);
            //查找是否已经选入
            boolean isselected = getDBService().isSelected(getNowUser(), ps.getId());
            if (isselected) {
                paperselectproxy.setSelected(1);
            }

            psp.add(paperselectproxy);
        }

        modelMap.put("papers", psp);

        this.buildPager(modelMap, "student.do?c=select", st, s, total);

        modelMap.put("module", "selecting");
        return "panel/panel";
    }
    /**用户资料页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=myprofile")
    public String myprofile(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("my_profile", request, modelMap)) {
            return "global/notify";
        }

        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "", "", request, modelMap);

        Userfields uf = getDBService().getUserFieldsByUser(getNowUser());
        modelMap.put("uf", new UserAndFieldsProxy(getNowUser(), uf));

        //显示学院
        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);

        modelMap.put("module", "my_profile");
        return "panel/panel";
    }
}
