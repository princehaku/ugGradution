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
 *  Created on : Sep 20, 2011, 9:02:38 AM
 *  Author     : princehaku
 */
package net.techest.ug.beans;

import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;

/**用户和用户详情代理
 *
 * @author princehaku
 */
public class UserAndFieldsProxy {

    public UserAndFieldsProxy(User user, Userfields uf) {
        this.user = user;
        this.uf = uf;
    }

    public Userfields getUf() {
        return uf;
    }

    public void setUf(Userfields uf) {
        this.uf = uf;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    User user;
    Userfields uf;
    
}
