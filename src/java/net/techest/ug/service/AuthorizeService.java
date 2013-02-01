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
 *  Created on : 2011-8-16, 8:03:18
 *  Author     : princehaku
 */
package net.techest.ug.service;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.mvc.entity.User;
import org.springframework.ui.ModelMap;

/**
 *
 * @author princehaku
 */
public class AuthorizeService {
    
    
    public boolean checkPermission(String ptype,HttpServletRequest request,ModelMap modelMap) {
//        IPermissionC c=controllers.get(ptype);
//        if(c==null||c.check(request)){
//            modelMap.put("msg", "对不起,您没有权限执行这项操作");
//            return false;
//        }
        return true;
    }
}
