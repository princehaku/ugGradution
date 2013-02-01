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
 *  Created on : Sep 19, 2011, 10:21:23 AM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.beans.YearMajorProxy;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.Paperyear;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/colleague.do")
public class ColleagueManagerPanelController extends TeacherPanelController {
    
    protected String menuxml = "ColleagueMenu.xml";
    
    @Override
    public  String getMenuxml() {
        return menuxml;
    }
    
    public ColleagueManagerPanelController() {
    }

    
    /**显示选题列表
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=selectlist")
    public String slist(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_manager", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "select", "list", request, modelMap);


        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }

        List<Paperyear> lst = null;
        List<YearMajorProxy> list = new ArrayList<YearMajorProxy>();
        int total = 0;
        Major major = getDBService().getMajorByUser(getNowUser());
        if (havePermission("global", request, modelMap)) {
            lst = getDBService().getPaperYearList(st, s);
            total = getDBService().getAllPaperYearNumbers();
        } else {
            if(havePermission("manage_colleague", request, modelMap)){
                //非高级管理员只能看到本学院的
                lst = getDBService().getPaperYearByColleague(major.getCid(), st, s);
                total = getDBService().getPaperYearByColleagueNumbers(major.getCid());
            }else{
                //其他只能看到本专业的
                lst = getDBService().getPaperYearByMajor(major, st, s);
                total = getDBService().getPaperYearByMajorNumbers(major);
            }
        }
        //遍历获取专业名字
        Iterator it = lst.iterator();
        while (it.hasNext()) {
            Paperyear py = (Paperyear) it.next();
            Major m = getDBService().getMajorById(py.getMid());
            YearMajorProxy ymproxy = new YearMajorProxy(m, py);
            list.add(ymproxy);
        }

        this.buildPager(modelMap, "admin.do?c=selectlist", st, s, total);


        modelMap.put("paperyears", list);
        modelMap.put("module", "select_list");
        return "panel/panel";
    }
    
}
