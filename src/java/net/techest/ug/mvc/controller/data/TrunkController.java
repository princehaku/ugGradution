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
import net.techest.ug.beans.PaperAndOwnerProxy;
import net.techest.ug.helper.Log4j;
import net.techest.ug.helper.StringTools;
import net.techest.ug.mvc.controller.panel.TeacherPanelController;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.Papertrunk;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;
import net.techest.ug.mvc.entity.Usergroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**题库控制
 *
 * @author princehaku
 */
@Controller
@RequestMapping("/trunk.do")
public class TrunkController extends TeacherPanelController {

    /**显示一个题库内容
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
        Papertrunk pt = null;
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            pt = getDBService().getPaperTrunkById(id);
            User u = (getDBService().getUserById(pt.getUid()));
            Userfields uf = getDBService().getUserFieldsByUser(u);
            modelMap.put("pt", new PaperAndOwnerProxy(uf, pt));
        }
        return "panel/trunk_view";
    }

    /**题库删除
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=delete")
    public String delete(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("trunk_delete", request, modelMap)) {
            return "global/notify";
        }
        Papertrunk pt = null;
        //如果有id 则删除
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() +ug.getHome()+"?c=trunklist");
        modelMap.put("time", "3");
        modelMap.put("msg", "删除失败");

        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            int id = Integer.parseInt(request.getParameter("id"));
            pt = getDBService().getPaperTrunkById(id);
            if (!havePermission("global", request, modelMap)) {
               if(havePermission("manage_colleague", request, modelMap)){
                   
               }
                else if(pt.getUid() != getNowUser().getId()) {
                    return "global/notify";
                }
            }
            pt.setStatu(0);
            getDBService().savePaperTrunk(pt);
            modelMap.put("msg", "删除成功");
        }
        if (request.getParameterValues("id[]") != null && request.getParameterValues("checkall[]") !=null && request.getParameterValues("id[]").length > 0) {
            for (int i = 0; i < request.getParameterValues("id[]").length; i++) {
                int id = Integer.parseInt(request.getParameterValues("id[]")[i]);
                
                //必须在checkall[]里面的才更新
                boolean checked=false;
                for(int rr=0;rr<request.getParameterValues("checkall[]").length;rr++){
                    if(request.getParameterValues("checkall[]")[rr].equals(request.getParameterValues("id[]")[i])){
                        checked=true;
                    }
                }
                if(!checked){
                    continue;
                }
                pt = getDBService().getPaperTrunkById(id);
                if (!havePermission("global", request, modelMap) && pt.getUid() != getNowUser().getId()) {
                    return "global/notify";
                }
                pt.setStatu(0);
                getDBService().savePaperTrunk(pt);
            }
            modelMap.put("msg", "删除成功");
        }


        return "global/notify";

    }

    /**保存和更新题库
     * 必须要求有浏览权限
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=save")
    public String select(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("trunk_list", request, modelMap)) {
            return "global/notify";
        }
        if (request.getParameter("major") == null || request.getParameter("major").equals("0")) {
            modelMap.put("msg", "请选择专业");
            return "global/notify";
        }

        Papertrunk pt = null;
        User u = (User) request.getSession().getAttribute("user");
        //如果有id 则更新
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            if (!havePermission("trunk_edit", request, modelMap)) {
                return "global/notify";
            }
            int id = Integer.parseInt(request.getParameter("id"));
            pt = getDBService().getPaperTrunkById(id);
        } else {
            if (!havePermission("trunk_add", request, modelMap)) {
                return "global/notify";
            }
            //没有则添加
            pt = new Papertrunk();
            pt.setUid(u.getId());
        }
        pt.setAddtime(new Date());
        pt.setTitle(request.getParameter("title"));
        pt.setDesp(request.getParameter("desp"));
        pt.setShortdesp(StringTools.subMinStr(request.getParameter("desp").replaceAll("<([^>]*)>", ""), 0, 100));
        pt.setTags(request.getParameter("tags"));
        pt.setMid(Integer.parseInt(request.getParameter("major")));
        pt.setStatu(1);
        try {
            getDBService().savePaperTrunk(pt);
            Usergroup ug = getDBService().getGroupByUser(getNowUser());
            modelMap.put("url", request.getContextPath() +ug.getHome()+"?c=trunklist");
            modelMap.put("time", "3");
            modelMap.put("msg", "保存成功");
        } catch (Exception ex) {
            modelMap.put("msg", "保存失败" + ex.getMessage());
        }
        return "global/notify";
    }
    
    
    /**导入用户
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=import")
    public String importall(HttpServletRequest request, ModelMap modelMap) throws Exception {
        if (!havePermission("trunk_add", request, modelMap)) {
            return "global/notify";
        }
        Usergroup ug = getDBService().getGroupByUser(getNowUser());
        modelMap.put("url", request.getContextPath() + ug.getHome() + "?c=trunklist");
        modelMap.put("time", "3");
        modelMap.put("msg", "保存失败");
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        //专业
        int major=Integer.parseInt(multipartRequest.getParameter("major"));
        Papertrunk pt=null;
        MultipartFile file = multipartRequest.getFile("file");
        int total=0;
        int success=0;
        int faild=0;
        StringBuilder sb=new StringBuilder();
        if(file!=null){
            Log4j.getInstance().debug("文件" + file.toString() + "行");
                //开始解析excel
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheet(0);
                Log4j.getInstance().debug("共有" + sheet.getRows() + "行");
                total=sheet.getRows()-1;
                for (int i = 1; i < sheet.getRows(); i++) {
                    Cell name = sheet.getCell(0,i);
                    Cell desp = sheet.getCell(1,i);
                    Cell tag = sheet.getCell(2,i); 
                    pt=new Papertrunk();
                    pt.setAddtime(new Date());
                    pt.setStatu(1);
                    pt.setDesp(desp.getContents());
                    pt.setShortdesp(desp.getContents());
                    pt.setMid(major);
                    pt.setUid(getNowUser().getId());
                    pt.setTitle(name.getContents());
                    pt.setTags(tag.getContents());
            try {
                getDBService().savePaperTrunk(pt);
                success+=1;
            }catch(Exception ex){
                faild+=1;
                sb.append("标题 ");
                sb.append(name.getContents());
                sb.append("保存失败<br />");
                sb.append("原因 :");
                sb.append(ex.getMessage());
                sb.append("<br />");
            }
                }
          modelMap.put("msg", "保存完成<br />总数: "+total+"条  成功:"+success+"条"+"失败:"+faild+"条<br />"+sb.toString());
          if(faild>0){
                modelMap.put("time", "600");
          }
        }
        
        return "global/notify";
    }

    /**题库导出
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "c=trunkexport")
    public String trunkexport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        if (!havePermission("trunk_list", request, modelMap)) {
            return "global/notify";
        }
        response.setContentType("application/xls");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String dataString = sd.format(new Date());
        response.addHeader("Content-Disposition", "attachment;filename=export_" + dataString + ".xls");
        //创建工作区
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet sheet = workbook.createSheet("题库", 0);
        List<Papertrunk> secs = null;
        if (havePermission("global", request, modelMap)) {
            secs = getDBService().getTrunkPapersByMajor(getDBService().getMajorByUser(nowUser), 0, 1000);
        } else {
            secs = getDBService().getTrunkPapersByUser(nowUser, 0, 1000);
        }
//        else if (havePermission("manage_colleague", request, modelMap)) {
//            secs = getDBService().getAllSelectionListByColleague(getNowUser());
//        } else if (havePermission("manage_major", request, modelMap)) {
//            secs = getDBService().getAllSelectionListByMajorId(getNowUser().getMid());
//        } else {
//            secs = getDBService().getAllSelectionListByOwner(getNowUser());
//        }
        int i = 0;
        //表格头
        sheet.addCell(new Label(0, 0, "题目编号"));
        sheet.addCell(new Label(1, 0, "题目"));
        sheet.addCell(new Label(2, 0, "描述"));
        sheet.addCell(new Label(3, 0, "标签"));
        sheet.addCell(new Label(4, 0, "教师"));
        Log4j.getInstance().debug("导出题库" + secs.size() + "个");
        Iterator<Papertrunk> sit = secs.iterator();
        //遍历选题
        while (sit.hasNext()) {
            i++;
            Papertrunk pt = sit.next();
            sheet.addCell(new Label(0, i, pt.getId()+""));
            sheet.addCell(new Label(1, i, pt.getTitle()));
            sheet.addCell(new Label(2, i, pt.getDesp()));
            sheet.addCell(new Label(3, i, pt.getTags()));
            User u = getDBService().getUserById(pt.getUid());
            sheet.addCell(new Label(4, i, u.getUsername()));
        }

        workbook.write();
        workbook.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();

        return null;
    }
}
