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
import net.techest.ug.mvc.entity.Paperyear;
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
public class PaperYearDAO extends HibernateDaoSupport{
    
    @Autowired
    public PaperYearDAO(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
	}

    public void save(Paperyear py) {
        getSession().saveOrUpdate(py);
    }

    public List<Paperyear> getList(int st, int s) {
        Query query=getSession().createQuery("from Paperyear where statu>='1'");
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }

    public int getALLNumbers() {
        Long a= (Long) getSession().createQuery("select count(id) from Paperyear where statu>='1'").uniqueResult();
        return a.intValue();
    }

    public List<Paperyear> getPaperYearByMajor(Major major, int st, int s) {
        Query query=getSession().createQuery("from Paperyear where statu>='1' and mid='"+major.getId()+"'");
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }

    public int getPaperYearByMajorNumbers(Major major) {
        Long a=(Long)getSession().createQuery("select count(id) from Paperyear where statu>='1' and  mid='"+major.getId()+"'").uniqueResult();
        return a.intValue();
    }

    public int getPaperYearByColleagueNumbers(int cid) {
        BigInteger a=(BigInteger) getSession().createSQLQuery("select count(*) from paperyear left join major on major.id=paperyear.mid where paperyear.statu>='1' and major.cid='"+cid+"'").uniqueResult();
        return a.intValue();
    }

    public List<Paperyear> getPaperYearByColleague(int cid, int st, int s) {
        Query query=getSession().createSQLQuery("select * from paperyear left join major on major.id=paperyear.mid where paperyear.statu>='1' and major.cid='"+cid+"'").addEntity(Paperyear.class);
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }
}
