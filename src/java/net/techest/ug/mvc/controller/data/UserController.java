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
 *  Created on : Sep 18, 2011, 4:54:45 PM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.techest.ug.beans.UserAndFieldsProxy;
import net.techest.ug.helper.Log4j;
import net.techest.ug.helper.MD5;
import net.techest.ug.mvc.controller.panel.TeacherPanelController;
import net.techest.ug.mvc.entity.Colleague;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**用户控制
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/user.do")
public class UserController extends TeacherPanelController {

    /**显示一个用户详情
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=view")
    public String view(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("login", request, modelMap)) {
            return "global/notify";
        }
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            User u = (getDBService().getUserById(id));
            Userfields uf = getDBService().getUserFieldsByUser(u);
            modelMap.put("uf", new UserAndFieldsProxy(u, uf));
        }
        return "panel/user_view";
    }

    /**用户删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=delete")
    public String delete(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("user_delete", request, modelMap)) {
            return "global/notify";
        }

        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=userlist");
        modelMap.put("time", "3");
        modelMap.put("msg", "删除失败");
        User user = null;
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            //不能删除自己
            if (id == getNowUser().getId()) {
                return "global/notify";
            }
            user = getDBService().getUserById(id);
            //注意这里有漏洞可以利用
            if (!havePermission("global", request, modelMap) && user.getGid() >= getNowUser().getGid()) {
                return "global/notify";
            }
            try {
                getDBService().delUser(user);
                modelMap.put("msg", "删除成功");
            } catch (Exception e) {
                Log4j.getInstance().info("删除失败" + e.getMessage());
                modelMap.put("msg", "删除失败");
            }
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
                //不能删除自己
                if (id == getNowUser().getId()) {
                    continue;
                }
                user = getDBService().getUserById(id);
                //注意这里有漏洞可以利用
                if (!havePermission("global", request, modelMap) && user.getGid() >= getNowUser().getGid()) {
                    continue;
                }
                try {
                    getDBService().delUser(user);
                } catch (Exception e) {
                    Log4j.getInstance().info("删除失败" + e.getMessage());
                    modelMap.put("msg", "删除失败");
                }
            }
            modelMap.put("msg", "删除成功");
        }

        return "global/notify";

    }

    /**导出用户
     * 必须要求有浏览权限
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=userexport")
    public String exportalluser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        if (!havePermission("user_list", request, modelMap)) {
            return "global/notify";
        }
        response.setContentType("application/xls");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String dataString = sd.format(new Date());
        response.addHeader("Content-Disposition", "attachment;filename=export_" + dataString + ".xls");
        Usergroup ug = getDBService().getGroupByUser(getNowUser());

        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=userlist");
        modelMap.put("time", "3");
        modelMap.put("msg", "对不起 您没有权限");


        ArrayList<UserAndFieldsProxy> l = new ArrayList();
        List<User> list = null;
        Major major;
        Colleague c;
        int st = 0;
        int s = 9999;

        if (havePermission("global", request, modelMap)) {
            list = getDBService().getAllUserList(st, s);
        } else {
            major = getDBService().getMajorByUser(getNowUser());
            if (havePermission("manage_colleague", request, modelMap)) {
                c = getDBService().getColleagueByMajor(major);
                list = getDBService().getUserListByColleague(c, st, s);
            } else {
                list = getDBService().getUserListByMajor(major, st, s);
            }
        }
        Iterator<User> it = list.iterator();
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet sheet = workbook.createSheet("用户", 0);
        int i = 0;
        //表格头
        sheet.addCell(new Label(0, 0, "学号"));
        sheet.addCell(new Label(1, 0, "姓名"));
        sheet.addCell(new Label(2, 0, "电话"));
        sheet.addCell(new Label(3, 0, "EMAIL"));
        sheet.addCell(new Label(4, 0, "学院"));
        sheet.addCell(new Label(5, 0, "专业"));
        sheet.addCell(new Label(6, 0, "用户类型"));
        sheet.addCell(new Label(7, 0, "注册时间"));

        while (it.hasNext()) {
            i++;
            User u = it.next();
            Userfields uf = getDBService().getUserFieldsByUser(u);
            Usergroup ug2 = getDBService().getGroupByUser(u);
            major = getDBService().getMajorByUser(u);
            sheet.addCell(new Label(0, i, u.getSid()));
            sheet.addCell(new Label(1, i, uf.getName()));
            sheet.addCell(new Label(2, i, uf.getTel()));
            sheet.addCell(new Label(3, i, uf.getEmail()));
            sheet.addCell(new Label(4, i, getDBService().getColleagueByMajor(major).getName()));
            sheet.addCell(new Label(5, i, major.getName()));
            sheet.addCell(new Label(6, i, ug2.getDesp()));
            sheet.addCell(new Label(7, i, uf.getRegtime() + ""));
        }
        workbook.write();
        workbook.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();

        return null;
    }

    /**导入用户
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=import")
    public String importall(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("user_add", request, modelMap)) {
            return "global/notify";
        }
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=userlist");
        modelMap.put("time", "3");
        modelMap.put("msg", "保存失败");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //判断一下是否可以导入此gid
        int gid = Integer.parseInt(multipartRequest.getParameter("group"));
        if (!havePermission("global", request, modelMap)) {
            if (getNowUser().getGid() <= gid) {
                modelMap.put("msg", "保存失败,您没有权限");
                return "global/notify";
            }
        }
        int major = Integer.parseInt(multipartRequest.getParameter("major"));
        String password = MD5.getMD5(multipartRequest.getParameter("password").getBytes());
        String permissionset = multipartRequest.getParameter("permissionset");
        MultipartFile file = multipartRequest.getFile("file");
        int total = 0;
        int success = 0;
        int faild = 0;
        StringBuilder sb = new StringBuilder();
        if (file != null) {
            Log4j.getInstance().debug("文件" + file.toString() + "行");
            //开始解析excel
            Workbook workbook = Workbook.getWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheet(0);
            Log4j.getInstance().debug("共有" + sheet.getRows() + "行");
            total = sheet.getRows() - 1;
            for (int i = 1; i < sheet.getRows(); i++) {
                Cell sid = sheet.getCell(0, i);
                if (sid.getContents().equals("")) {
                    continue;
                }
                Cell name = sheet.getCell(1, i);
                Cell tel = sheet.getCell(2, i);
                Cell email = sheet.getCell(3, i);
                Cell maxslots = sheet.getCell(4, i);
                User u = new User(gid, sid.getContents(), major, permissionset, name.getContents(), password, 0, 1);
                Userfields uf = new Userfields();
                uf.setName(name.getContents());
                uf.setMaxpapers(0);
                try{
                    int max = Integer.parseInt(maxslots.getContents());
                    uf.setMaxpapers(max);
                } catch (Exception ex){
                    
                }
                uf.setTel(tel.getContents());
                uf.setEmail(email.getContents());
                uf.setLastlogintime(new Date());
                uf.setRegtime(new Date());
                uf.setType(0);
                uf.setLastloginip(request.getRemoteAddr());
                uf.setRegip(request.getRemoteAddr());
                try {
                    getDBService().saveUser(u, uf);
                    success += 1;
                } catch (Exception ex) {
                    faild += 1;
                    sb.append("学号");
                    sb.append(sid.getContents());
                    sb.append("保存失败<br />");
                    sb.append("原因 :");
                    ex.printStackTrace();
                    sb.append(ex.getMessage());
                    sb.append("<br />");
                }
            }
            modelMap.put("msg", "保存完成<br />总数: " + total + "条  成功:" + success + "条" + "失败:" + faild + "条<br />" + sb.toString());
            if (faild > 0) {
                modelMap.put("time", "600");
            }
        }

        return "global/notify";
    }

    /**保存和更新用户
     * 必须要求有浏览权限
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=save")
    public String save(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("user_list", request, modelMap)) {
            return "global/notify";
        }
        if (!havePermission("global", request, modelMap) && Integer.parseInt(request.getParameter("group")) > getNowUser().getGid()) {
            modelMap.put("msg", "对不起 您不能设置一个比您大的权限");
            return "global/notify";
        }
        User user = null;
        Userfields userfields = null;
        //如果有id 则更新
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            if (!havePermission("user_edit", request, modelMap)) {
                return "global/notify";
            }
            int id = Integer.parseInt(request.getParameter("id"));
            user = getDBService().getUserById(id);
            userfields = getDBService().getUserFieldsByUser(user);
            if (!havePermission("global", request, modelMap) && user.getGid() >= getNowUser().getGid()) {
                modelMap.put("msg", "对不起 您不能设置权限大于等于您的用户");
                return "global/notify";
            }
        } else {
            if (!havePermission("user_add", request, modelMap)) {
                return "global/notify";
            }
            //没有则添加
            user = new User();
            user.setStatu(1);
            userfields = new Userfields();
            userfields.setLastloginip(request.getRemoteAddr());
            userfields.setLastlogintime(new Date());
            userfields.setRegip(request.getRemoteAddr());
            userfields.setRegtime(new Date());
        }
        if (request.getParameter("group") != null) {
            user.setGid(Integer.parseInt(request.getParameter("group")));
        }
        if (request.getParameter("type") != null) {
            user.setType(Integer.parseInt(request.getParameter("type")));
        }
        if (request.getParameter("sid") != null) {
            user.setSid(request.getParameter("sid"));
        }
        if (request.getParameter("username") != null) {
            user.setUsername(request.getParameter("username"));
            userfields.setName(request.getParameter("username"));
        }
        if (request.getParameter("major") != null) {
            user.setMid(Integer.parseInt(request.getParameter("major")));
        }
        if (request.getParameter("phone") != null) {
            userfields.setTel(request.getParameter("phone"));
        }
        if (request.getParameter("email") != null) {
            userfields.setEmail(request.getParameter("email"));
        }
        if (request.getParameter("nickname") != null) {
            //userfields.setName(request.getParameter("username"));
        }
        if (request.getParameter("desp") != null) {
            userfields.setText(request.getParameter("desp"));
        }
        if (request.getParameter("maxpapers") != null) {
            Integer max = 0;
            try {
                max = Integer.parseInt(request.getParameter("maxpapers"));
            } catch (Exception ex){
                
            }
            userfields.setMaxpapers(max);
        }
        if (request.getParameter("password") != null && !request.getParameter("password").equals("")) {
            user.setPassword(MD5.getMD5(request.getParameter("password").getBytes()));
        }
        if (request.getParameter("permissionset") != null) {
            user.setPermissionset(request.getParameter("permissionset"));
        }

        try {
            getDBService().saveUser(user, userfields);
            Usergroup ug = getDBService().getGroupByUser(getNowUser());
            modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=userlist");
            modelMap.put("time", "3");
            modelMap.put("msg", "保存成功");
        } catch (Exception ex) {
            modelMap.put("msg", "保存失败  已经有相同的学号了<br />" + ex.getMessage());
        }

        return "global/notify";
    }

    /**更新用户自己的资料
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=saveprofile")
    public String saveprofile(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("my_profile", request, modelMap)) {
            return "global/notify";
        }
        User user = getNowUser();
        Userfields userfields = getDBService().getUserFieldsByUser(user);

        if (request.getParameter("type") != null) {
            user.setType(Integer.parseInt(request.getParameter("type")));
        }
        if (request.getParameter("sid") != null) {
            //user.setSid(request.getParameter("sid"));
        }
        if (request.getParameter("username") != null) {
            //user.setUsername(request.getParameter("username"));
            //userfields.setName(request.getParameter("username"));
        }
        if (request.getParameter("major") != null) {
            user.setMid(Integer.parseInt(request.getParameter("major")));
        }
        if (request.getParameter("phone") != null) {
            userfields.setTel(request.getParameter("phone"));
        }
        if (request.getParameter("email") != null) {
            userfields.setEmail(request.getParameter("email"));
        }
        if (request.getParameter("nickname") != null) {
            //userfields.setName(request.getParameter("nickname"));
        }
        if (request.getParameter("desp") != null) {
            userfields.setText(request.getParameter("desp"));
        }
        if (request.getParameter("maxpapers") != null) {
            userfields.setMaxpapers(Integer.parseInt(request.getParameter("maxpapers")));
        }
        if (request.getParameter("password") != null && !request.getParameter("password").equals("")) {
            user.setPassword(MD5.getMD5(request.getParameter("password").getBytes()));
        }

        try {
            getDBService().saveUser(user, userfields);
            Usergroup ug = getDBService().getGroupByUser(getNowUser());
            modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=myprofile");
            modelMap.put("time", "3");
            modelMap.put("msg", "保存成功");
        } catch (Exception ex) {
            modelMap.put("msg", "保存失败  已经有相同的学号了<br />" + ex.getMessage());
        }

        return "global/notify";
    }
}
