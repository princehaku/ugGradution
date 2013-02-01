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
 *  Created on : 2011-8-13, 10:43:19
 *  Author     : princehaku
 */
package net.techest.ug.dao;

import java.util.Iterator;
import java.util.List;
import net.techest.ug.mvc.entity.Major;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**专业DAO
 *
 * @author princehaku
 */
@Repository
public class MajorDAO extends HibernateDaoSupport{
    
    @Autowired
    public MajorDAO(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
	}
    
    public Major getByid(int id){
        Iterator it= getSession().createQuery("from Major where statu='1' and id="+id).iterate();
        if(it.hasNext()){
            return (Major) it.next();
        }else{
            return null;
        }
    }

    public List<Major> getByCid(int id) {
        return getSession().createQuery("from Major where statu='1' AND cid='"+id+"'").list();
    }
}
