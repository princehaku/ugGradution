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
 *  Created on : Sep 30, 2011, 9:03:53 AM
 *  Author     : princehaku
 */
package net.techest.ug.dao;

import java.util.List;
import net.techest.ug.mvc.entity.Selection;
import net.techest.ug.mvc.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author princehaku
 */
@Repository
public class SelectionDAO extends HibernateDaoSupport{
    
    @Autowired
    public SelectionDAO(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
	}

    public Selection getByUserAndSelId(User u, int id) {
        List a=getSession().createQuery("from Selection where uid='"+u.getId()+"' and sid='"+id+"' and statu=1").list();
        if(a.size()>0){
            return (Selection) a.get(0);
        }else{
            return null;
        }
    }

    public Selection getByUserIdAndSelId(int uid, int sid) {
        List a=getSession().createQuery("from Selection where uid='"+uid+"' and sid='"+sid+"' and statu=1").list();
        if(a.size()>0){
            return (Selection) a.get(0);
        }else{
            return null;
        }
    }
    
    public List<Selection> getAllSelectionListByOwner(User u) {
        return getSession().createSQLQuery("select * from selection left join papersel on papersel.id=selection.sid"+
                " where papersel.owneruid='"+u.getId()+"' and papersel.isdel=0 and selection.statu=1").addEntity(Selection.class).list();
    }

    public List<Selection> getAllSelectionListByColleague(User u) {
        return getSession().createSQLQuery("select * from selection left join papersel on papersel.id=selection.sid where papersel.owneruid in (select user.id from user left join major on major.id=user.mid where major.cid=(select  major.cid from user left join major on major.id=user.mid where user.id='"+u.getId()+"')) and papersel.isdel=0 and selection.statu=1").addEntity(Selection.class).list();
    }

    public List<Selection> getAllSelectionListByMajorId(int mid) {
        return getSession().createSQLQuery("select * from selection left join papersel on papersel.id=selection.sid where papersel.owneruid in (select user.id from user left join major on major.id=user.mid where major.id='"+mid+"') and papersel.isdel=0 and selection.statu=1").addEntity(Selection.class).list();
    }
    public List<Selection> getAllSelectionListByPaperYear(int yid) {
        return getSession().createSQLQuery("select * from selection left join papersel on papersel.id=selection.sid where papersel.yid="+yid +" and papersel.isdel=0 and selection.statu=1").addEntity(Selection.class).list();
    }
    /**
     * @deprecated 
     */
    public List<Selection> getAllSelectionList() {
        return getSession().createSQLQuery("select * from selection left join papersel on papersel.id=selection.sid where selection.statu=1 and papersel.isdel=0").addEntity(Selection.class).list();
    }

    public void delByUser(User user) {
        getSession().createSQLQuery("delete from selection where sid in (select id from papersel where papersel.owneruid="+ user.getId() + ")").executeUpdate();
        getSession().createSQLQuery("delete from selection where uid=" + user.getId()).executeUpdate();
    }
    
}