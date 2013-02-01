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
 *  Created on : Sep 26, 2011, 9:53:52 PM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.mvc.controller.panel.BasePanel;
import net.techest.ug.mvc.entity.Colleague;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.Paperyear;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**选题控制
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/paper.do")
public class PaperController extends BasePanel {

    /**保存修改的选题
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=save")
    public String savePaperYear(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_add", request, modelMap)) {
            return "global/notify";
        }
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=selectlist");
        modelMap.put("time", "3");
        modelMap.put("msg", "保存失败");
        
        int id = Integer.parseInt(request.getParameter("id"));
        Paperyear py = getDBService().getPaperYearById(id);
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dtstart = sdf.parse(request.getParameter("stdate"));
            Date dtend = sdf.parse(request.getParameter("eddate"));
            int maxsel = Integer.parseInt(request.getParameter("maxsel"));
            py.setDtstart(dtstart);
            py.setDtend(dtend);
            py.setMaxsel(maxsel);
            getDBService().savePaperYear(py);
            modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=selectlist");
            modelMap.put("msg", "保存成功");
        } catch (Exception ex) {
            modelMap.put("msg", "保存失败" + ex.getMessage());
        }

        return "global/notify";
    }
    /**保存选题
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=savenew")
    public String saveNewPaperYear(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_add", request, modelMap)) {
            return "global/notify";
        }
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=selectlist");
        modelMap.put("time", "3");
        modelMap.put("msg", "保存失败");

        int mid = Integer.parseInt(request.getParameter("major"));

        if (!havePermission("global", request, modelMap) && havePermission("manage_major", request, modelMap)) {
            if (mid != getNowUser().getMid()) {
                modelMap.put("msg", "对不起 您只能操作您所在专业");
                return "global/notify";
            }
        }
        Major major = getDBService().getMajorById(mid);
        Paperyear py1 = getDBService().getPaperYearByMajor(major);
        if (py1 != null) {
            modelMap.put("msg", "保存失败  同一专业只允许添加一次");
            return "global/notify";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dtstart = sdf.parse(request.getParameter("stdate"));
            Date dtend = sdf.parse(request.getParameter("eddate"));
            int maxsel = Integer.parseInt(request.getParameter("maxsel"));
            //这个操作会吧该专业的所有题库里面的题加入到待选区  并生成对应的选择
            Paperyear py = getDBService().saveNewPaperYear(major, dtstart, dtend, maxsel);
            modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=selectdetail&id=" + py.getId());
            modelMap.put("msg", "保存成功");
        } catch (Exception ex) {
            modelMap.put("msg", "保存失败" + ex.getMessage());
        }

        return "global/notify";
    }

    /**单个选题删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=deletesel")
    public String deletesel(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_delete", request, modelMap)) {
            return "global/notify";
        }
        Papersel ps = null;
        //如果有id 则删除
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=selectdetail&id=" + request.getParameter("sid"));
        modelMap.put("time", "3");
        modelMap.put("msg", "删除失败");

        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            ps = getDBService().getPaperSelById(id);
            if (!(havePermission("manage_colleague", request, modelMap) || havePermission("manage_major", request, modelMap))) {
                return "global/notify";
            }
            ps.setIsdel(1);
            getDBService().savePaperSel(ps);
            modelMap.put("msg", "删除成功");
        }
        if (request.getParameterValues("id[]") != null && request.getParameterValues("checkall[]") != null && request.getParameterValues("checkall[]") != null && request.getParameterValues("id[]").length > 0) {
            for (int i = 0; i < request.getParameterValues("id[]").length; i++) {
                int id = Integer.parseInt(request.getParameterValues("id[]")[i]);

                //必须在checkall[]里面的才更新
                boolean checked = false;
                for (int rr = 0; rr < request.getParameterValues("checkall[]").length; rr++) {
                    if (request.getParameterValues("checkall[]")[rr].equals(request.getParameterValues("id[]")[i])) {
                        checked = true;
                    }
                }
                if (!checked) {
                    continue;
                }
                ps = getDBService().getPaperSelById(id);
                if (!(havePermission("manage_colleague", request, modelMap) || havePermission("manage_major", request, modelMap))) {
                    return "global/notify";
                }
                ps.setIsdel(1);
                getDBService().savePaperSel(ps);
            }
            modelMap.put("msg", "删除成功");
        }


        return "global/notify";

    }

    /**选题year删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=deleteyear")
    public String deleteyear(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_delete", request, modelMap)) {
            return "global/notify";
        }
        Paperyear py = null;
        //如果有id 则删除
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=selectlist");
        modelMap.put("time", "3");
        modelMap.put("msg", "删除失败");

        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            py = getDBService().getPaperYearById(id);
            if (!(havePermission("manage_colleague", request, modelMap) || havePermission("manage_major", request, modelMap))) {
                return "global/notify";
            }
            py.setStatu(0);
            //删掉papersel里面的
            getDBService().deleteAllPaperSelBydPaperYear(py);
            getDBService().savePaperYear(py);
            modelMap.put("msg", "删除成功");
        }
        if (request.getParameterValues("id[]") != null && request.getParameterValues("checkall[]") != null && request.getParameterValues("id[]").length > 0) {
            for (int i = 0; i < request.getParameterValues("id[]").length; i++) {
                int id = Integer.parseInt(request.getParameterValues("id[]")[i]);

                //必须在checkall[]里面的才更新
                boolean checked = false;
                for (int rr = 0; rr < request.getParameterValues("checkall[]").length; rr++) {
                    if (request.getParameterValues("checkall[]")[rr].equals(request.getParameterValues("id[]")[i])) {
                        checked = true;
                    }
                }
                if (!checked) {
                    continue;
                }
                py = getDBService().getPaperYearById(id);
                if (!(havePermission("manage_colleague", request, modelMap) || havePermission("manage_major", request, modelMap))) {
                    return "global/notify";
                }
                py.setStatu(0);
                //删掉papersel里面的
                getDBService().deleteAllPaperSelBydPaperYear(py);
                getDBService().savePaperYear(py);
            }
            modelMap.put("msg", "删除成功");
        }


        return "global/notify";

    }

    /**选题year删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=autofill")
    public String autofill(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_edit", request, modelMap)) {
            return "global/notify";
        }
        //如果有id 则删除
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("time", "3");
        modelMap.put("msg", "保存失败");

        if (request.getParameter("yid") != null && !request.getParameter("yid").equals("")) {
            int id = Integer.parseInt(request.getParameter("yid"));
            modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=selectdetail&id=" + id);

            if (!(havePermission("manage_colleague", request, modelMap) || havePermission("manage_major", request, modelMap))) {
                return "global/notify";
            }
            List<Papersel> papersel = getDBService().getPaperSelByYearId(id);
            Iterator<Papersel> it = papersel.iterator();
            while (it.hasNext()) {
                Papersel ps = it.next();
                try {
                    User u = getDBService().getUserById(ps.getOwneruid());
                    int max = Integer.parseInt(request.getParameterValues("utype[]")[u.getType()]);
                    int selected = ps.getMaxslots() - ps.getLeftsolts();
                    if (max - selected >= 0) {
                        ps.setMaxslots(max);
                        ps.setLeftsolts(max - selected);
                        getDBService().savePaperSel(ps);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            modelMap.put("msg", "保存成功");
        }


        return "global/notify";

    }
}
