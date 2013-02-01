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
package net.techest.ug.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import net.techest.ug.WebSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;  

/**
 *
 * @author princehaku
 */
@Controller  
@RequestMapping("/index.do")  
public class IndexController{
    /**首页
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping
    public String home(HttpServletRequest request, ModelMap modelMap) throws Exception{ 
        return "index";
    }
}
