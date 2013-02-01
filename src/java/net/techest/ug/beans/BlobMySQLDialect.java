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
 *  Created on : Sep 19, 2011, 5:33:14 PM
 *  Author     : princehaku
 */
package net.techest.ug.beans;

import java.sql.Types;
import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

/**
 *
 * @author princehaku
 */
public class BlobMySQLDialect extends MySQLDialect {
    public BlobMySQLDialect(){
            super();
            registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
          }
}
