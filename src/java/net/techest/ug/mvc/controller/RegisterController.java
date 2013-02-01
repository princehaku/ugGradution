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
 *  Created on : Sep 13, 2011, 1:34:00 PM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.techest.ug.helper.Validator;
import net.techest.ug.WebSystem;
import net.techest.ug.helper.MD5;
import net.techest.ug.mvc.entity.Colleague;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/register.do")
public class RegisterController extends BaseController{

    @RequestMapping
    public String showForm(HttpServletRequest request, ModelMap modelMap) throws Exception {
        List<Colleague> cs = getDBService().getAllColleague();
        System.out.println("学院共" + cs.get(0).toString() + "个");
        modelMap.addAttribute("colleague", cs);
        return "register";
    }

    /**提交注册
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "action=submit", method = RequestMethod.POST)
    public String submit(String sid, String password1, String password2, String email, int col, int major, HttpServletRequest request, ModelMap modelMap) throws Exception {

        modelMap.put("sid", sid);
        modelMap.put("password1", password1);
        modelMap.put("email", email);
        boolean canSaveToDB = true;
        if (!Validator.validate(sid + "", "\\d*") || sid.equals("")) {
            modelMap.put("msg", "请输入正确的学号");
            canSaveToDB = false;
        }
        if (password1.equals("")) {
            modelMap.put("msg", "请输入一个密码");
            canSaveToDB = false;
        }
        if (!password1.equals(password2)) {
            modelMap.put("msg", "两次输入的密码不一致");
            canSaveToDB = false;
        }
        if (!Validator.validate(email, ".*?@.*")) {
            modelMap.put("msg", "请输入正确的EMAIL");
            canSaveToDB = false;
        }
        if (col < 0) {
            modelMap.put("msg", "请选择正确的学院");
            canSaveToDB = false;
        }
        if (major < 0) {
            modelMap.put("msg", "请选择正确的专业");
            canSaveToDB = false;
        }

        if (canSaveToDB) {
            //加密密码
            password1 = MD5.getMD5(password1.getBytes());
            //新建学生
            User u = new User(1, sid, major, "", sid, password1, 0, 1);
            Userfields uf = new Userfields();
            uf.setEmail(email);
            uf.setLastlogintime(new Date());
            uf.setRegtime(new Date());
            uf.setType(0);
            uf.setLastloginip(request.getRemoteAddr());
            uf.setRegip(request.getRemoteAddr());
            try {
                getDBService().saveUser(u, uf);
                System.out.println("注册成功");
                modelMap.put("time", "3");
                modelMap.put("url", request.getContextPath() + "/");
                modelMap.put("msg", "注册成功  请使用您的学号登录系统");
                return "global/notify";
            } catch (Exception ex) {
                modelMap.put("msg", "注册失败 已经有相同用户了\n" + ex.getMessage());
            }
        }
        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);
        return "register";
    }
}
