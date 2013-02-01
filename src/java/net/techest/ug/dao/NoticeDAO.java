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
 *  Created on : 2011-8-12, 14:19:46
 *  Author     : princehaku
 */
package net.techest.ug.dao;

/**
 *
 * @author princehaku
 */
import java.util.Iterator;
import java.util.List;
import net.techest.ug.helper.MD5;
import net.techest.ug.mvc.entity.*;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**用户DAO
 *
 * @author princehaku
 */
@Repository
public class NoticeDAO extends HibernateDaoSupport{
    
    @Autowired
    public NoticeDAO(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
	}
    
    /**根据一个用户得到他所得到的通知
     * 注意只能得到最近的10条
     * @param id
     * @return 
     */
    public  List<Notice>  getRecentByUser(User u){
        Query query=getSession().createQuery("from Notice where uid="+u.getId()+" and statu=1 order by time DESC");
        query.setFirstResult(0);
        query.setMaxResults(10);
        List<Notice>  it= query.list();
        return it;
    }
}
