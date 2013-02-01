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
 *  Created on : Sep 23, 2011, 11:55:45 PM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.data;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.mvc.controller.panel.TeacherPanelController;
import net.techest.ug.mvc.entity.News;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**新闻控制
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/news.do")
public class NewsController extends TeacherPanelController {

    /**显示新闻页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=view")
    public String viewUser(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("news_list", request, modelMap)) {
            return "global/notify";
        }
        
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            News news = getDBService().getNewsById(id);
            modelMap.put("news", news);
            User u=getDBService().getUserById(news.getAuthoruid());
            Userfields uf=getDBService().getUserFieldsByUser(u);
            modelMap.put("user", uf);
        }
        
        return "panel/news";
    }

    /**新闻删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=delete")
    public String delete(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("news_delete", request, modelMap)) {
            return "global/notify";
        }

        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=newslist");
        modelMap.put("time", "3");
        modelMap.put("msg", "删除失败");
        News news=null;
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            news = getDBService().getNewsById(id);
            news.setStatu(0);
            getDBService().saveNews(news);
            modelMap.put("msg", "删除成功");
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
    public String select(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("news_list", request, modelMap)) {
            return "global/notify";
        }
        News news=null;
        //如果有id 则更新
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            if (!havePermission("news_edit", request, modelMap)) {
                return "global/notify";
            }
            int id = Integer.parseInt(request.getParameter("id"));
            news = getDBService().getNewsById(id);
        } else {
            if (!havePermission("news_add", request, modelMap)) {
                return "global/notify";
            }
            //没有则添加
            news=new News();
            news.setAuthoruid(getNowUser().getId());
            news.setAddtime(new Date());
            news.setStatu(1);
        }
        
        if (request.getParameter("title") != null) {
            news.setTitle(request.getParameter("title"));
        }
        if (request.getParameter("desp") != null) {
            news.setText(request.getParameter("desp"));
        }
        
        try {
            getDBService().saveNews(news);
            Usergroup ug = getDBService().getGroupByUser(getNowUser());
            modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=newslist");
            modelMap.put("time", "3");
            modelMap.put("msg", "保存成功");
        } catch (Exception ex) {
            modelMap.put("msg", "保存失败" + ex.getMessage());
        }

        return "global/notify";
    }
}
