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
 *  Created on : Sep 13, 2011, 10:13:44 AM
 *  Author     : princehaku
 */
package net.techest.ug.mvc.controller.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.techest.ug.beans.PaperAndOwnerProxy;
import net.techest.ug.beans.PaperSelectProxy;
import net.techest.ug.beans.UserAndFieldsProxy;
import net.techest.ug.beans.YearMajorProxy;
import net.techest.ug.helper.Log4j;
import net.techest.ug.mvc.entity.Colleague;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.News;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.Papertrunk;
import net.techest.ug.mvc.entity.Paperyear;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/admin.do")
public class AdminPanelController extends BasePanel {

    protected String menuxml = "AdminMenu.xml";

    public String getMenuxml() {
        return menuxml;
    }

    /**管理员首页
     * 新闻列表
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=newslist")
    public String home(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("news_list", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "news", "list", request, modelMap);
        //显示新闻
        List<News> news = getDBService().getRecentNews(10);
        Log4j.getInstance().debug("得到新闻" + news.size());

        modelMap.put("news", news);

        modelMap.put("module", "news_list_admin");
        return "panel/panel";
    }

    /**
     * 编辑和添加公告
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=newsedit")
    public String newsaddedit(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("news_list", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "news", "edit", request, modelMap);

        //如果没有id则是新加
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            if (!havePermission("news_edit", request, modelMap)) {
                return "global/notify";
            }
            int id = Integer.parseInt(request.getParameter("id"));
            //从数据库查找对应的
            News news = getDBService().getNewsById(id);
            if (news == null) {
                modelMap.put("url", "?c=newslist");
                modelMap.put("time", "3");
                modelMap.put("msg", "对不起，这条新闻");
                return "global/notify";
            }
            if (!(havePermission("global", request, modelMap))) {
                if (news.getAuthoruid() != getNowUser().getId()) {
                    modelMap.put("msg", "对不起，您不能修改非您发布的新闻");
                    return "global/notify";
                }
            }
            modelMap.put("id", id);
            modelMap.put("news", news);
        } else {
            if (!havePermission("news_add", request, modelMap)) {
                return "global/notify";
            }

        }

        modelMap.put("module", "news_edit");
        return "panel/panel";
    }

    /**用户组列表
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=usergroup")
    public String grouplist(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("group_list", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "user", "group", request, modelMap);

        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }
        List<Usergroup> list = null;

        int total = 1;

        if (havePermission("global", request, modelMap)) {
            list = getDBService().getAllUserGroups();
            total = getDBService().getAllUserGroupNumbers();
        } else {
            list = getDBService().getUserGroupsBelowUser(getNowUser(), st, s);
            total = getDBService().getUserGroupsBelowUser(getNowUser()).size();
        }
        this.buildPager(modelMap, "admin.do?c=userlist", st, s, total);


        modelMap.put("groups", list);

        modelMap.put("module", "user_groups");
        return "panel/panel";
    }

    /**用户列表
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=userlist")
    public String userlist(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("user_list", request, modelMap)) {
            return "global/notify";
        }

        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "user", "list", request, modelMap);

        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }
        ArrayList<UserAndFieldsProxy> l = new ArrayList();
        List<User> list = null;

        int total = 1;
        Major major;
        Colleague c;
        if (havePermission("global", request, modelMap)) {
            list = getDBService().getAllUserList(st, s);
            total = getDBService().getUsersNumbers();
        } else {
            major = getDBService().getMajorByUser(getNowUser());
            if (havePermission("manage_colleague", request, modelMap)) {
                c = getDBService().getColleagueByMajor(major);
                list = getDBService().getUserListByColleague(c, st, s);
                total = getDBService().getUserNumbersByColleague(c);
            } else {
                list = getDBService().getUserListByMajor(major, st, s);
                total = getDBService().getUserNumbersByMajor(major);
            }
        }
        this.buildPager(modelMap, "admin.do?c=userlist", st, s, total);

        Iterator<User> it = list.iterator();
        while (it.hasNext()) {
            User u = it.next();
            Userfields uf = getDBService().getUserFieldsByUser(u);
            l.add(new UserAndFieldsProxy(u, uf));
        }

        modelMap.put("users", l);

        modelMap.put("module", "user_list");
        return "panel/panel";
    }

    /**用户导入页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=userimport")
    public String userimport(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("user_add", request, modelMap)) {
            return "global/notify";
        }

        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "user", "import", request, modelMap);

        //显示学院和用户组
        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);
        List<Usergroup> ug = null;
        if (havePermission("global", request, modelMap)) {
            ug = getDBService().getAllUserGroups();
        } else {
            ug = getDBService().getUserGroupsBelowUser(getNowUser());
        }
        modelMap.addAttribute("usergroup", ug);
        modelMap.put("module", "user_import");
        return "panel/panel";
    }


    /**题库导入页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=trunkimport")
    public String trunkimport(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("trunk_add", request, modelMap)) {
            return "global/notify";
        }

        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "trunk", "import", request, modelMap);

        //显示学院和用户组
        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);
        
        modelMap.put("module", "trunk_import");
        return "panel/panel";
    }

    /**用户编辑和增加页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=useredit")
    public String useraddedit(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("user_list", request, modelMap)) {
            return "global/notify";
        }

        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "user", "edit", request, modelMap);
        //如果没有id则是新加
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            if (!havePermission("user_edit", request, modelMap)) {
                return "global/notify";
            }
            int id = Integer.parseInt(request.getParameter("id"));
            //从数据库查找对应的
            User u = getDBService().getUserById(id);
            if (u == null) {
                modelMap.put("url", "?c=userlist");
                modelMap.put("time", "3");
                modelMap.put("msg", "对不起，没有这个用户");
                return "global/notify";
            }
            if (!(havePermission("global", request, modelMap) || havePermission("manage_colleague", request, modelMap)|| havePermission("manage_major", request, modelMap))) {
                if (getNowUser().getGid() <= u.getGid()) {
                    modelMap.put("msg", "对不起，您不能修改同组的用户");
                    return "global/notify";
                }
            }
            Userfields uf = getDBService().getUserFieldsByUser(u);
            modelMap.put("id", id);
            modelMap.put("uf", new UserAndFieldsProxy(u, uf));
        } else {
            if (!havePermission("user_add", request, modelMap)) {
                return "global/notify";
            }

        }
        //显示学院和用户组
        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);
        List<Usergroup> ug = null;
        if (havePermission("global", request, modelMap)) {
            ug = getDBService().getAllUserGroups();
        } else {
            ug = getDBService().getUserGroupsBelowUser(getNowUser());
        }
        modelMap.addAttribute("usergroup", ug);
        //如果没有id则是新加
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            modelMap.put("module", "user_edit");
        } else {
            modelMap.put("module", "user_add");
        }
        return "panel/panel";
    }

    /**题库列表
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=trunklist")
    public String trunklist(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("trunk_list", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "trunk", "list", request, modelMap);

        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }
        ArrayList<PaperAndOwnerProxy> l = new ArrayList();
        List<Papertrunk> list = null;

        int total = 1;
        Major major;
        Colleague c;
        if (havePermission("global", request, modelMap)) {
            list = getDBService().getTrunkPapers(st, s);
            total = getDBService().getTrunkPaperNumbers();
        } else {
            if (havePermission("manage_colleague", request, modelMap)) {
                major = getDBService().getMajorByUser(getNowUser());
                c = getDBService().getColleagueByMajor(major);
                list = getDBService().getTrunkPapersByColleague(c, st, s);
                total = getDBService().getTrunkPaperNumbersByColleague(c);
            }
            if (havePermission("manage_major", request, modelMap)) {
                major = getDBService().getMajorByUser(getNowUser());
                list = getDBService().getTrunkPapersByMajor(major, st, s);
                total = getDBService().getTrunkPaperNumbersByMajor(major);
            }  
            else {
                list = getDBService().getTrunkPapersByUser(getNowUser(), st, s);
                total = getDBService().getTrunkPaperNumbersByUser(getNowUser());
            }
        }
        this.buildPager(modelMap, "admin.do?c=trunklist", st, s, total);
        Iterator<Papertrunk> it = list.iterator();
        while (it.hasNext()) {
            Papertrunk pt=null;
            try{
            pt = it.next();
            User u = (getDBService().getUserById(pt.getUid()));
            Userfields uf = getDBService().getUserFieldsByUser(u);
            l.add(new PaperAndOwnerProxy(uf, pt));
                
            }catch(Exception ex){
                Log4j.getInstance().warn("错误读取 "+pt.toString());
            }
        }

        modelMap.put("trunks", l);

        modelMap.put("module", "trunk_list");
        return "panel/panel";
    }

    /**题库编辑和增加页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=trunkedit")
    public String trunkaddedit(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("trunk_list", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "trunk", "edit", request, modelMap);
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            if (!havePermission("trunk_edit", request, modelMap)) {
                return "global/notify";
            }
            int id = Integer.parseInt(request.getParameter("id"));
            //从数据库查找对应的
            Papertrunk pt = getDBService().getPaperTrunkById(id);
            if (pt == null) {
                modelMap.put("url", "?c=trunklist");
                modelMap.put("time", "3");
                modelMap.put("msg", "对不起，没有这个题目");
                return "global/notify";
            }
            if (!(havePermission("global", request, modelMap) || havePermission("manage_colleague", request, modelMap) || havePermission("manage_major", request, modelMap))) {
                if (pt.getUid() != getNowUser().getId()) {
                    modelMap.put("msg", "对不起，您不能修改非自己的题目");
                    return "global/notify";
                }
            }
            modelMap.put("id", id);
            modelMap.put("paper", pt);
        } else {

            if (!havePermission("trunk_add", request, modelMap)) {
                return "global/notify";
            }
        }
        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);
        modelMap.put("user", getNowUser());
        modelMap.put("module", "trunk_edit");
        return "panel/panel";
    }

    /**添加选题界面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=selectadd")
    public String selectadd(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_add", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "select", "add", request, modelMap);

        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);
        modelMap.put("user", getNowUser());
        modelMap.put("module", "select_add");
        return "panel/panel";
    }
    /**修改选题YEAR界面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=selectedit")
    public String selectedit(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("select_add", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "select", "list", request, modelMap);
        int id = Integer.parseInt(request.getParameter("id"));
        Paperyear py = getDBService().getPaperYearById(id);
        
        modelMap.put("id", id);
        modelMap.put("py", py);
        modelMap.put("user", getNowUser());
        modelMap.put("module", "select_edit");
        return "panel/panel";
    }

    /**显示选题列表
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=selectlist")
    public String selectlist(HttpServletRequest request, ModelMap modelMap) throws Exception {
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

    /**显示选题详情 可以进行数量修改
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=selectdetail")
    public String selectdetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        if (!havePermission("select_manager", request, modelMap)) {
            return "global/notify";
        }
        this.global(request, modelMap);
        if (request.getParameter("id") == null || request.getParameter("id").equals("")) {
            modelMap.put("url", request.getContextPath() +"/admin.do?c=selectlist");
            modelMap.put("time", "0");
            modelMap.put("msg", "请稍后");
            return "global/notify";
        }
        fetchLeft(getMenuxml(), "select", "detail", request, modelMap);
        int id = Integer.parseInt(request.getParameter("id"));

        int st = 0;
        if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
            st = Integer.parseInt(request.getParameter("p"));
        }
        int s = 30;
        if (request.getParameter("s") != null && !request.getParameter("s").equals("")) {
            s = Integer.parseInt(request.getParameter("s"));
        }

        List<Papersel> papersel = getDBService().getPaperSelByYearId(id, st, s);
        int total=getDBService().getPaperSelNumbersByYearId(id);
        this.buildPager(modelMap, "admin.do?c=selectdetail&id=" + id, st, s, total);

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
            //已经选入的用户信息
            ArrayList<User> selectedUsers = (ArrayList<User>) getDBService().getSelectedUserByPaperSelId(ps.getId());
            paperselectproxy.setSelectedUsers(selectedUsers);
            psp.add(paperselectproxy);
        }

        modelMap.put("papers", psp);
        modelMap.put("module", "select_detail");
        return "panel/panel";
    }
    /**用户资料页面
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=myprofile")
    public String myprofile(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("my_profile", request, modelMap)) {
            return "global/notify";
        }

        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "", "", request, modelMap);

        Userfields uf = getDBService().getUserFieldsByUser(getNowUser());
        modelMap.put("uf", new UserAndFieldsProxy(getNowUser(), uf));

        //显示学院
        List<Colleague> cs = getDBService().getAllColleague();
        modelMap.addAttribute("colleague", cs);

        modelMap.put("module", "my_profile");
        return "panel/panel";
    }
    
    /**导出选题详情
     * 
     */
    @RequestMapping(params = "c=selectexport")
    public String exportall(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        if (!havePermission("select_list", request, modelMap)) {
                    return "global/notify";
        }
        
        this.global(request, modelMap);
        fetchLeft(getMenuxml(), "select", "export", request, modelMap);
        
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
        modelMap.put("module", "select_export");
        return "panel/panel";
       
    }
}
