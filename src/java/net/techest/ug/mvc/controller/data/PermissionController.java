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
 *  Created on : Sep 20, 2011, 23:31:45 PM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.beans.UserAndFieldsProxy;
import net.techest.ug.beans.UserPermissionProxy;
import net.techest.ug.helper.MD5;
import net.techest.ug.mvc.controller.panel.TeacherPanelController;
import net.techest.ug.mvc.entity.Permission;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**权限控制
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/permission.do")
public class PermissionController extends TeacherPanelController {

    /**显示权限页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=show")
    public String viewUser(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("user_change_permission", request, modelMap)) {
            return "global/notify";
        }
        ArrayList<UserPermissionProxy> list = new ArrayList<UserPermissionProxy>();

        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            List<Permission> permissions = getDBService().getPermissionList();
            Iterator<Permission> it = permissions.iterator();
            while (it.hasNext()) {
                Permission p = it.next();
                list.add(new UserPermissionProxy(p, getNowUser()));
            }
            int uid=Integer.parseInt(request.getParameter("id"));
            modelMap.put("pageid", request.getParameter("id"));
            modelMap.put("permissionset", request.getParameter("string"));
            modelMap.put("permission", list);
        }
        return "panel/permission";
    }

}
