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
 *  Created on : Sep 19, 2011, 11:51:14 AM
 *  Author     : princehaku
 */
package net.techest.ug.beans;

import java.util.ArrayList;
import net.techest.ug.mvc.entity.Papertrunk;
import net.techest.ug.mvc.entity.User;
import net.techest.ug.mvc.entity.Userfields;

/**代理类 连接user和papertrunk
 *
 * @author princehaku
 */
public class PaperAndOwnerProxy {

    private Userfields user;
    private Papertrunk paper;

    public PaperAndOwnerProxy(Userfields user, Papertrunk pt) {
        this.user = user;
        this.paper = pt;
    }

    public Papertrunk getPaper() {
        return paper;
    }

    public Userfields getUser() {
        return this.user;
    }
}
