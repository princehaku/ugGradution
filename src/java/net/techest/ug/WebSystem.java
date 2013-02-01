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
 *  Created on : 2011-8-16, 8:06:20
 *  Author     : princehaku
 */
package net.techest.ug;

import javax.servlet.http.HttpSession;
import net.techest.ug.service.AuthorizeService;
import net.techest.ug.service.DBService;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author princehaku
 */
public class WebSystem {

    /**单例模式
     * 
     */
    private static WebSystem instance = new WebSystem();
    /**权限服务
     */
    private AuthorizeService authService;

    public static WebSystem getInstance() {
        return instance;
    }
    /**bean工厂
     * 
     */
    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    public Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public AuthorizeService getAuthService() {
        if (authService == null) {
            this.authService = (AuthorizeService) this.getContext().getBean("AuthorizeService");
        }
        return authService;
    }

    public void setAuthService(AuthorizeService authService) {
        this.authService = authService;
    }
}
