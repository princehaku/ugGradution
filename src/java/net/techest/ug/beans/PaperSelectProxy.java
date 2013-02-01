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
 *  Created on : Sep 26, 2011, 10:04:47 PM
 *  Author     : princehaku
 */
package net.techest.ug.beans;

import java.util.ArrayList;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.User;

/**
 *
 * @author princehaku
 */
public class PaperSelectProxy {
    int selected=0;
    
    PaperAndOwnerProxy paperinfo;
    
    Papersel papersel;
    
    private ArrayList<User> selectedUsers;

    public ArrayList<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(ArrayList<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public PaperAndOwnerProxy getPaperinfo() {
        return paperinfo;
    }

    public void setPaperinfo(PaperAndOwnerProxy paperinfo) {
        this.paperinfo = paperinfo;
    }

    public Papersel getPapersel() {
        return papersel;
    }

    public void setPapersel(Papersel papersel) {
        this.papersel = papersel;
    }
    
    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
