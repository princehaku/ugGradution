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
import java.math.BigInteger;
import java.util.ArrayList;
import net.techest.ug.mvc.controller.panel.TeacherPanelController;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.techest.ug.helper.Log4j;
import net.techest.ug.helper.MD5;
import net.techest.ug.mvc.entity.*;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

/**题库DAO
 *
 * @author princehaku
 */
@Repository
public class PaperTrunkDAO extends HibernateDaoSupport{
    
    @Autowired
    public PaperTrunkDAO(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
	}
    
    /**根据id得到一个题库条目
     * @param id
     * @return 
     */
    public  Papertrunk  getById(int id){
        Query query=getSession().createQuery("from Papertrunk where id='"+id+"' and statu=1");
        Iterator it=query.iterate();
        if(it.hasNext()){
            return (Papertrunk) it.next();
        }else{
            return null;
        }
    }
    
    public void save(Papertrunk pt){
        getSession().saveOrUpdate(pt);
    }

    public List<Papertrunk> getList(int st, int s) {
        Query query=getSession().createQuery("from Papertrunk where statu=1 order by uid DESC");
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        List<Papertrunk>  it= query.list();
        return it;
    }
    
    public int getTotal() {
       Long a=(Long) getSession().createQuery("select count(id) from Papertrunk where statu=1").uniqueResult();
       return a.intValue();
    }
    
    public List<Papertrunk> getListByUser(User u,int st, int s) {
        Query query=getSession().createQuery("from Papertrunk where statu=1 and uid='"+u.getId()+"' order by addtime DESC");
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        List<Papertrunk>  it= query.list();
        return it;
        }
    
    public int getTotalByUser(User u) {
        Long a=(Long)getSession().createQuery("select count(id) from Papertrunk where statu=1 and uid='"+u.getId()+"' order by addtime DESC").uniqueResult();
        return a.intValue();
    }
    
    public  List<Papertrunk> getListBelowUser(User u, int st, int s) {
        Query query=getSession().createQuery("from Papertrunk where statu=1 and gid<'"+u.getGid()+"' order by addtime DESC");
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        List<Papertrunk>  it= query.list();
        return it;
        
    }

    public int getTotalBelowUser(User u) {
        Long a=(Long)getSession().createQuery("select count(id) from Papertrunk where statu=1 and gid<'"+u.getGid()+"' order by addtime DESC").uniqueResult();
        return a.intValue();
    }

    public int getTotalByColleague(Colleague c) {
        BigInteger a=(BigInteger) getSession().createSQLQuery("select count(papertrunk.id) from papertrunk left join major on major.id=papertrunk.mid  where papertrunk.statu=1 and major.cid='"+c.getId()+"' order by papertrunk.addtime DESC").uniqueResult();
        return a.intValue();
    }

    public List<Papertrunk> getListByColleague(Colleague c, int st, int s) {
        Query query=getSession().createSQLQuery("select * from papertrunk inner join major on major.id=papertrunk.mid  where papertrunk.statu=1 and major.cid='"+c.getId()+"' order by papertrunk.addtime DESC").addEntity(Papertrunk.class);
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }

    public List<Papertrunk> getAllTrunkPapersByMajor(Major major) {
        Query query=getSession().createQuery("from Papertrunk where statu=1 and mid='"+major.getId()+"' order by uid DESC");
        return query.list();
    }

    public List<Papertrunk> getTrunkPapersByMajor(Major major, int st, int s) {
        Query query=getSession().createSQLQuery("select * from papertrunk where papertrunk.statu=1 and papertrunk.mid='"+major.getId()+"' order by papertrunk.uid DESC").addEntity(Papertrunk.class);
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        return query.list();
    }

    public int getTrunkPaperNumbersByMajor(Major major) {
         BigInteger a=(BigInteger)getSession().createSQLQuery("select count(*) from papertrunk where papertrunk.statu=1 and papertrunk.mid='"+major.getId()+"'").uniqueResult();
         return a.intValue();
    }

    public void delByUser(User user) {
        getSession().createSQLQuery("delete from papertrunk where uid=" + user.getId()).executeUpdate();
    }
}
