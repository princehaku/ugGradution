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

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import net.techest.ug.helper.Log4j;
import net.techest.ug.mvc.controller.panel.TeacherPanelController;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**用户组控制
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/groups.do")
public class UserGroupController extends TeacherPanelController {

    /**显示一个组的内容
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
        return "panel/user_view";
    }

    /**用户组删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=delete")
    public String delete(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("group_delete", request, modelMap)) {
            return "global/notify";
        }

        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=usergroup");
        modelMap.put("time", "3");
        modelMap.put("msg", "删除失败");
        Usergroup ug1;

        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id <5 || id == 99) {
                modelMap.put("msg", "删除失败  不能删除预设的系统组");
                return "global/notify";
            }
            //不能删除自己的组
            if (id >= getNowUser().getGid()) {
                return "global/notify";
            }
            ug1 = getDBService().getGroupById(id);
            ug1.setStatu(0);
            getDBService().saveUserGroup(ug1);
            modelMap.put("msg", "删除成功");
        }
        if (request.getParameterValues("id[]") != null && request.getParameterValues("id[]").length > 0) {

            for (int i = 0; i < request.getParameterValues("id[]").length; i++) {
                int id = Integer.parseInt(request.getParameterValues("id[]")[i]);
                if (id == 1 || id == 99) {
                    continue;
                }
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
                ug1 = getDBService().getGroupById(id);
                if (!havePermission("global", request, modelMap) && id >= getNowUser().getGid()) {
                    continue;
                }
                ug1.setStatu(0);
                getDBService().saveUserGroup(ug1);
            }
            modelMap.put("msg", "删除成功");
        }

        return "global/notify";

    }

    /**保存和更新用户组
     * 必须要求有浏览权限
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=save")
    public String save(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("group_list", request, modelMap)) {
            return "global/notify";
        }
        Usergroup ug = null;
        JSONObject json = new JSONObject();
        modelMap.put("json", json);
        json.put("statu", 0);
        json.put("msg", "保存失败");
        //如果有id 则更新
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            if (!havePermission("group_edit", request, modelMap)) {
                json.put("msg", "保存失败   您没有权限");
                return "json";
            }
            int id = Integer.parseInt(request.getParameter("id"));
            //不能修改自己的组
            if (id >= getNowUser().getGid()) {
                Log4j.getInstance().debug("不能修改自己的");
                json.put("msg", "保存失败   不能修改大于或等于自己的组");
                return "json";
            }
            ug = getDBService().getGroupById(id);
        } else {
            if (!havePermission("group_add", request, modelMap)) {
                json.put("msg", "添加失败   您没有权限");
                return "json";
            }
            ug = new Usergroup();
            ug.setDesp(request.getParameter("desp"));
            ug.setHome(request.getParameter("home"));
        }

        ug.setPermissionset(request.getParameter("set"));

        try {
            getDBService().saveUserGroup(ug);
            json.put("statu", 1);
            json.put("msg", "保存成功");
        } catch (Exception ex) {
            json.put("statu", 0);
            json.put("msg", "保存失败  " + ex.getMessage());
        }

        return "json";
    }
}
