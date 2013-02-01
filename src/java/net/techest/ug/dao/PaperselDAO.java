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
 *  Created on : 2011-8-26, 21:30:19
 *  Author     : princehaku
 */
package net.techest.ug.dao;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import net.techest.ug.mvc.entity.Major;
import net.techest.ug.mvc.entity.Papersel;
import net.techest.ug.mvc.entity.Paperyear;
import net.techest.ug.mvc.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**选题组DAO
 *
 * @author princehaku
 */
@Repository
public class PaperselDAO extends HibernateDaoSupport{
    
    @Autowired
    public PaperselDAO(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
	}

    public void deleteAllPaperSelBydPaperYear(Paperyear py) {
        getSession().createSQLQuery("update `papersel` set isdel='1' where yid='"+py.getId()+"'").executeUpdate();
    }

    public List<Papersel> getPaperYearByMajor(Major major, int st, int s) {
        Query query=getSession().createSQLQuery("select * from papersel left join paperyear on papersel.yid=paperyear.id where (NOW()>=paperyear.dtstart and NOW()<=paperyear.dtend) and paperyear.statu=1 and papersel.isdel=0 and paperyear.mid='"+major.getId()+"' order by papersel.owneruid DESC").addEntity(Papersel.class);
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }

    public int getPaperYearByMajorNumbers(Major major) {
        BigInteger a=(BigInteger) getSession().createSQLQuery("select count(papersel.id) from papersel left join paperyear on papersel.yid=paperyear.id where paperyear.statu>=1 and papersel.isdel=0 and paperyear.mid='"+major.getId()+"'").uniqueResult();
        return a.intValue();
    }

    public List<Papersel> getPaperSelByYearId(int id, int st, int s) {
        Query query=getSession().createSQLQuery("select * from papersel left join paperyear on papersel.yid=paperyear.id where paperyear.statu>=1 and papersel.isdel=0 and paperyear.id='"+id+"' order by papersel.owneruid DESC").addEntity(Papersel.class);
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }

    public int getPaperSelNumbersByYearId(int id) {
        BigInteger a=(BigInteger)getSession().createSQLQuery("select count(*) from papersel left join paperyear on papersel.yid=paperyear.id where paperyear.statu>=1 and papersel.isdel=0 and paperyear.id='"+id+"'").uniqueResult();
        return a.intValue();
    }

    public List<Papersel> getPaperSelByUser(User u, int st, int s) {
        Query query=getSession().createSQLQuery("select * from papersel left join selection on papersel.id=selection.sid where papersel.isdel=0 and selection.statu=1 and selection.uid='"+u.getId()+"'").addEntity(Papersel.class);
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }

    public int getPaperSelByUserNumbers(User u) {
        BigInteger a=(BigInteger)getSession().createSQLQuery("select count(*) from papersel left join selection on papersel.id=selection.sid where papersel.isdel=0 and selection.statu=1 and selection.uid='"+u.getId()+"'").uniqueResult();
        return a.intValue();
    }

    public Papersel getPaperSelById(int id) {
        List a=getSession().createQuery("from Papersel where id='"+id+"'").list();
        if(a.size()>0){
            return (Papersel) a.get(0);
        }else{
            return null;
        }
    }

    public List<Papersel> getPaperSelByOwner(User u, int st, int s) {
        Query query=getSession().createSQLQuery("select * from papersel where papersel.isdel=0 and papersel.owneruid='"+u.getId()+"'").addEntity(Papersel.class);
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }
    /**得到用户已经选入的题目总量
     * 
     * @param u
     * @return 
     */
    public int getPaperSelNumbersByOwner(User u) {
        BigInteger a=(BigInteger)getSession().createSQLQuery("select count(*) from papersel where papersel.isdel=0 and papersel.owneruid='"+u.getId()+"'").uniqueResult();
        return a.intValue();
    }

    public void delByUser(User user) {
        getSession().createSQLQuery("delete from papersel where owneruid=" + user.getId()).executeUpdate();
    }
}
