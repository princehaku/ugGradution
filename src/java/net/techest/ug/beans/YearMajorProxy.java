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
 *  Created on : Sep 27, 2011, 9:04:47 PM
 *  Author     : princehaku
 */
package net.techest.ug.beans;

import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.Paperyear;

/**
 *
 * @author princehaku
 */
public class YearMajorProxy {
    
    Major major;

    Paperyear paperyear;

    public YearMajorProxy(Major major, Paperyear paperyear) {
        this.major = major;
        this.paperyear = paperyear;
    }

    public Paperyear getPaperyear() {
        return paperyear;
    }

    public void setPaperyear(Paperyear paperyear) {
        this.paperyear = paperyear;
    }

    
    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
    
}
