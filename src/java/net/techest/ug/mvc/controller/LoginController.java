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
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.helper.Validator;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.techest.ug.helper.Log4j;
import net.techest.ug.mvc.entity.Usergroup;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/login.do")
public class LoginController extends BaseController{

    @RequestMapping
    public String showForm(HttpServletRequest request, ModelMap modelMap) throws Exception {
        return "login";
    }

    /**提交登录
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "action=submit", method = RequestMethod.POST)
    public String submit(String username, String password, HttpServletRequest request, ModelMap modelMap) throws Exception {
        
        Log4j.getInstance().debug("用户"+username+"开始使用"+password+"登录");
        
        if (!Validator.validate(username + "", "[a-zA-Z0-9]*") || username.equals("")) {
            //modelMap.put("msg", "请输入正确的用户名");
            //return "global/notify";
        }
        if (password.equals("")) {
            modelMap.put("msg", "请输入您的密码");
            return "global/notify";
        }
        
        modelMap.put("time", "3");

        User u=getDBService().login(username,password);
        
        if (u!=null) {
            if(u.getStatu()==0){
                modelMap.put("url", request.getContextPath() + "/login.do");
                modelMap.put("msg", "登录失败 您的帐号已被冻结");
                return "global/notify";
            }
            //更新上次登录时间和ip
            Userfields uf = getDBService().getUserFieldsByUser(u);
            uf.setLastloginip(request.getRemoteAddr());
            uf.setLastlogintime(new Date());
            getDBService().saveUser(u, uf);
            
            //写入session
            request.getSession().setAttribute("user", u);
            Usergroup ug=getDBService().getGroupByUser(u);
            modelMap.put("url", request.getContextPath() + ug.getHome()+"?c=newslist");
            modelMap.put("msg", "登录成功 请稍后");
        } else {
            modelMap.put("url", request.getContextPath() + "/login.do");
            modelMap.put("msg", "登录失败  请检查您的用户名和密码");
        }

        return "global/notify";
    }
}
