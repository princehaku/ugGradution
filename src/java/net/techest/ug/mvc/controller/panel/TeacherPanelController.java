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
import net.techest.ug.beans.PaperAndOwnerProxy;
import net.techest.ug.beans.PaperSelectProxy;
import net.techest.ug.mvc.entity.Papersel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/teacher.do")
public class TeacherPanelController extends AdminPanelController {
    
    protected String menuxml = "TeacherMenu.xml";
    
    @Override
    public  String getMenuxml() {
        return menuxml;
    }
    
    public TeacherPanelController() {
    }

    
    /**显示选题列表
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=selectmylist")
    public String selectlist(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_list", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "select", "mylist", request, modelMap);


        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }

        List<Papersel> papersel = getDBService().getPaperSelByOwner(getNowUser(), st, s);
        int total=getDBService().getPaperSelNumbersByOwner(getNowUser());
        this.buildPager(modelMap, "?c=selectmylist", st, s, total);

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
        modelMap.put("module", "select_mylist");
        return "panel/panel";
    }
    
}
