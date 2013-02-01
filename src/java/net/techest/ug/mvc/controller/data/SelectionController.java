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

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;
import net.techest.ug.beans.UserAndFieldsProxy;
import net.techest.ug.helper.Log4j;
import net.techest.ug.mvc.controller.panel.BasePanel;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.Papertrunk;
import net.techest.ug.mvc.entity.Paperyear;
import net.techest.ug.mvc.entity.Selection;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/selection.do")
public class SelectionController extends BasePanel {

    /**显示所有选入的同学
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=view")
    public String view(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_list", request, modelMap)) {
            return "global/notify";
        }

        ArrayList<UserAndFieldsProxy> l = new ArrayList();
        List<User> list = null;
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            list = getDBService().getSelectedUserByPaperSelId(id);
            Iterator<User> it = list.iterator();
            while (it.hasNext()) {
                User u = it.next();
                Userfields uf = getDBService().getUserFieldsByUser(u);
                l.add(new UserAndFieldsProxy(u, uf));
            }

        }

        modelMap.put("users", l);

        return "panel/select_view";
    }

    /**选题选入
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

        int id = Integer.parseInt(request.getParameter("id"));

        JSONObject json = new JSONObject();
        json.put("statu", 0);
        json.put("msg", "选入失败");
        Papersel ps = getDBService().getPaperSelById(id);
        Paperyear py = getDBService().getPaperYearById(ps.getYid());
        Date d = new Date();
        //如果不是选题时间
        if (!(d.getTime() >= py.getDtstart().getTime() && d.getTime() <= py.getDtend().getTime()) || py.getStatu() == 2) {
            json.put("msg", "选入失败  选题时间已结束");
            modelMap.put("json", json);
            return "json";
        }

        try {
            //查找是否已达选题上限
            if (getDBService().isPaperSelExcess(getNowUser(), id)) {
                json.put("msg", "选入失败  已到达您的选题上限 请在我的选题里面撤销部分选题方可继续选入");
            } else {
                if (getDBService().isPaperOwnerExcess(id)) {
                    json.put("msg", "选入失败  该老师已经达到带生上限");
                } else {
                    boolean s = getDBService().selectPaperSel(getNowUser(), id);
                    if (s) {
                        json.put("statu", 1);
                        json.put("msg", "选入成功");
                    }
                }
            }

        } catch (Exception ex) {
            json.put("msg", "选入失败" + ex.getMessage());
        }

        modelMap.put("json", json);
        return "json";
    }

    /**选题删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=deselect")
    public String deletesel(HttpServletRequest request, ModelMap modelMap) throws Exception {

        if (!havePermission("select", request, modelMap)) {
            return "global/notify";
        }
        int id = Integer.parseInt(request.getParameter("id"));

        JSONObject json = new JSONObject();
        json.put("statu", 0);
        json.put("msg", "撤销失败");
        Papersel ps = getDBService().getPaperSelById(id);
        Paperyear py = getDBService().getPaperYearById(ps.getYid());
        Date d = new Date();
        //如果不是选题时间
        if (!(d.getTime() >= py.getDtstart().getTime() && d.getTime() <= py.getDtend().getTime()) || py.getStatu() == 2) {
            json.put("msg", "撤销失败  选题时间已结束");
            modelMap.put("json", json);
            return "json";
        }

        try {
            getDBService().deselectPaperSel(getNowUser(), id);
            json.put("statu", 1);
            json.put("msg", "撤销成功");
        } catch (Exception ex) {
            json.put("msg", "撤销失败" + ex.getMessage());
        }

        modelMap.put("json", json);
        return "json";

    }

    /**强制选中选题
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=approve")
    public String approve(HttpServletRequest request, ModelMap modelMap) throws Exception {

        if (!havePermission("global", request, modelMap)) {
            return "global/notify";
        }

        int pid = Integer.parseInt(request.getParameter("pid"));
        String sid = request.getParameter("sid");

        JSONObject json = new JSONObject();
        json.put("statu", 0);
        json.put("msg", "选入失败");

        try {
            User u = getDBService().getUserBySid(sid);
            if (u == null) {
                json.put("msg", "对不起，该学号不存在");
                modelMap.put("json", json);
                return "json";
            }
            Papersel ps = getDBService().getPaperSelById(pid);
            if (ps.getLeftsolts() <= 0) {
                json.put("msg", "对不起，此题已经达到选题上限");
                modelMap.put("json", json);
                return "json";
            }
            getDBService().saveSelectionByUserIdAndPaperselId(u, pid);
            json.put("txt", "<div>学号:" + u.getSid() + " 姓名:" + u.getUsername() + " "
                    + "<a onclick=\"if(confirm('确定撤销该生此项选题?'))"
                    + "{unapprove(" + pid + "," + u.getId() + ",this);}\" href=\"javascript:void(null);\"><img alt=\"Unapprove\" src=\"img/icons/icon_unapprove.png\">[撤选]</a></div>");
            json.put("statu", 1);
            json.put("msg", "选入成功");
        } catch (Exception ex) {
            json.put("msg", "选入失败" + ex.getMessage());
        }

        modelMap.put("json", json);
        return "json";

    }

    /**强制撤销选题
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=unapprove")
    public String unapprove(HttpServletRequest request, ModelMap modelMap) throws Exception {

        if (!havePermission("global", request, modelMap)) {
            return "global/notify";
        }
        int sid = Integer.parseInt(request.getParameter("pid"));
        int uid = Integer.parseInt(request.getParameter("uid"));

        JSONObject json = new JSONObject();
        json.put("statu", 0);
        json.put("msg", "撤销失败");

        try {
            getDBService().deleteSelectionByUserIdAndPaperselId(uid, sid);
            json.put("statu", 1);
            json.put("msg", "撤销成功");
        } catch (Exception ex) {
            json.put("msg", "撤销失败" + ex.getMessage());
        }

        modelMap.put("json", json);
        return "json";

    }

    @RequestMapping(params = "c=savesel")
    public String saveDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        if (!havePermission("select_edit", request, modelMap)) {
            return "global/notify";
        }

        int id = Integer.parseInt(request.getParameter("id"));

        JSONObject json = new JSONObject();
        json.put("statu", 0);
        json.put("msg", "保存失败");


        Papersel ps = getDBService().getPaperSelById(id);
        if (request.getParameter("max") != null) {
            int max = Integer.parseInt(request.getParameter("max"));
            int slected = ps.getMaxslots() - ps.getLeftsolts();
            if (max < slected) {
                json.put("msg", "保存失败  上限不能小于已选用户总数");
                modelMap.put("json", json);
                return "json";
            }
            try {
                ps.setMaxslots(max);
                ps.setLeftsolts(max - slected);
                json.put("left", max - slected);
                getDBService().savePaperSel(ps);
            } catch (Exception ex) {
                json.put("msg", "保存失败" + ex.getMessage());
            }
            json.put("statu", 1);
            json.put("msg", "保存成功");
        }



        modelMap.put("json", json);
        return "json";
    }

    @RequestMapping(params = "c=updatestatus")
    public String updateStatus(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        if (!havePermission("select_edit", request, modelMap)) {
            return "global/notify";
        }
        int pid = Integer.parseInt(request.getParameter("pid"));
        int s = Integer.parseInt(request.getParameter("s"));
        JSONObject json = new JSONObject();
        json.put("statu", 0);
        json.put("msg", "更新失败");
        try {
            Paperyear py = getDBService().getPaperYearById(pid);
            py.setStatu(s);
            getDBService().savePaperYear(py);
            json.put("msg", "更新状态成功");
        } catch (Exception ex) {
            json.put("msg", "更新失败" + ex.getMessage());
        }
        modelMap.put("json", json);
        return "json";
    }

    /**导出选入用户
     * 必须要求有浏览权限
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=selectexport")
    public String exportall(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        if (!havePermission("select_list", request, modelMap)) {
            return "global/notify";
        }
        response.setContentType("application/xls");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String dataString = sd.format(new Date());
        response.addHeader("Content-Disposition", "attachment;filename=export_" + dataString + ".xls");
        int yid= Integer.parseInt(request.getParameter("mid"));
        //创建工作区
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet sheet = workbook.createSheet("选题", 0);
        List<Papersel> secs = null;
        if (havePermission("global", request, modelMap)) {
            secs = getDBService().getPaperSelByYearId(yid);
//        } 
//        else if (havePermission("manage_colleague", request, modelMap)) {
//            secs = getDBService().getAllSelectionListByColleague(getNowUser());
//        } else if (havePermission("manage_major", request, modelMap)) {
//            secs = getDBService().getAllSelectionListByMajorId(getNowUser().getMid());
        } else {
            secs = getDBService().getPaperSelByOwner(getNowUser(), 0, 9999);
        }
        int i = 0;
        //表格头
        sheet.addCell(new Label(0, 0, "题目编号"));
        sheet.addCell(new Label(1, 0, "题目"));
        sheet.addCell(new Label(2, 0, "指导教师"));
        sheet.addCell(new Label(3, 0, "学号"));
        sheet.addCell(new Label(4, 0, "姓名"));
        sheet.addCell(new Label(5, 0, "电话"));
        sheet.addCell(new Label(6, 0, "EMAIL"));
        sheet.addCell(new Label(7, 0, "学院"));
        sheet.addCell(new Label(8, 0, "专业"));
        Log4j.getInstance().debug("导出选题" + secs.size() + "个");
        Iterator<Papersel> sit = secs.iterator();
        //遍历选题
        while (sit.hasNext()) {
            Papersel ps = sit.next();
            //Papersel ps = getDBService().getPaperSelById(se.getSid());
            if (ps == null) {
                continue;
            }
            Papertrunk pt = getDBService().getPaperTrunkById(ps.getPid());

            ArrayList<UserAndFieldsProxy> l = new ArrayList();

            int id = ps.getId();
            List<User> list = getDBService().getSelectedUserByPaperSelId(id);
            Log4j.getInstance().debug("有" + list.size() + "选择了" + id);
            if (list.isEmpty()) {
                i++;
                sheet.addCell(new Label(0, i, "" + id));
                sheet.addCell(new Label(1, i, pt.getTitle()));
                sheet.addCell(new Label(2, i, getDBService().getUserById(pt.getUid()).getUsername()));
                sheet.addCell(new Label(3, i, ""));
                sheet.addCell(new Label(4, i, ""));
                sheet.addCell(new Label(5, i, ""));
                sheet.addCell(new Label(6, i, ""));
                sheet.addCell(new Label(7, i, ""));
                sheet.addCell(new Label(8, i, ""));
                continue;
            }
            Iterator<User> us = list.iterator();
            while (us.hasNext()) {
                User u = us.next();
                Userfields uf = getDBService().getUserFieldsByUser(u);
                l.add(new UserAndFieldsProxy(u, uf));
            }

            Iterator it = l.iterator();
            while (it.hasNext()) {
                i++;
                UserAndFieldsProxy u = (UserAndFieldsProxy) it.next();
                Major major = getDBService().getMajorByUser(u.getUser());
                sheet.addCell(new Label(0, i, "" + id));
                sheet.addCell(new Label(1, i, pt.getTitle()));
                sheet.addCell(new Label(2, i, getDBService().getUserById(pt.getUid()).getUsername()));
                sheet.addCell(new Label(3, i, u.getUser().getSid()));
                sheet.addCell(new Label(4, i, u.getUf().getName()));
                sheet.addCell(new Label(5, i, u.getUf().getTel()));
                sheet.addCell(new Label(6, i, u.getUf().getEmail()));
                sheet.addCell(new Label(7, i, getDBService().getColleagueByMajor(major).getName()));
                sheet.addCell(new Label(8, i, major.getName()));
            }

        }

        workbook.write();
        workbook.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();

        return null;
    }
}
