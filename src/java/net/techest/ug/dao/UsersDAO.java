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
public class UsersDAO extends HibernateDaoSupport {

    @Autowired
    public UsersDAO(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**根据id得到一个用户
     * 
     * @param id
     * @return 
     */
    public User getByid(int id) {
        Iterator it = getSession().createQuery("from User where id=" + id).iterate();
        if (it.hasNext()) {
            return (User) it.next();
        } else {
            return null;
        }
    }

    /**保存用户
     * 
     * @param user 
     */
    public void save(User user) {
        getSession().saveOrUpdate(user);
    }

    /**保存用户的其他属性
     * 
     * @param userfields 
     */
    public void saveFileds(Userfields userfields) {
        getSession().saveOrUpdate(userfields);
    }

    /**得到用户的详细信息
     * 
     * @param user
     * @return 
     */
    public Userfields getFields(User user) {
        Iterator it = getSession().createQuery("from Userfields where uid=" + user.getId()).iterate();
        if (it.hasNext()) {
            return (Userfields) it.next();
        } else {
            return null;
        }
    }

    /**登录验证
     * 
     * @param username
     * @param password
     * @return 
     */
    public User login(String username, String password) {

        password = MD5.getMD5(password.getBytes());

        Iterator it = getSession().createQuery("from User where (username='" + username + "' OR sid='" + username + "') AND password='" + password + "'").iterate();

        if (it.hasNext()) {
            return (User) it.next();
        } else {
            return null;
        }
    }

    /**得到用户组
     * 
     * @param u
     * @return 
     */
    public Usergroup getGroupByUser(User u) {

        Iterator it = getSession().createQuery("from Usergroup where id=" + u.getGid()).iterate();
        if (it.hasNext()) {
            return (Usergroup) it.next();
        } else {
            return null;
        }
    }

    /**是否拥有某权限
     * 
     * @return 
     */
    public boolean hasPermission(User nowUser, String type) {
        Query q = getSession().createSQLQuery("select * from user as u left join usergroup as ug on u.gid=ug.id where `u`.id='" + nowUser.getId() + "' and `u`.statu='1' and (`u`.`permissionset`='*' OR `ug`.`permissionset`='*' OR find_in_set((select id from permission where `key`='" + type + "' limit 1),`ug`.`permissionset`) OR find_in_set((select id from permission where `key`='" + type + "' limit 1),`u`.`permissionset`)) limit 1");
        return q.list().size() > 0;
    }
    /**搜索所有用户
     * 
     * @param st
     * @param s
     * @return 
     */
    public List<User> findList(int st, int s) {
        Query query = getSession().createQuery("from User where statu=1 order by gid,sid DESC");
        query.setFirstResult(st*s);
        query.setMaxResults(s);
        List list = query.list();
        return list;
    }

    /**搜索专业下面的用户
     * 
     * @param nowUser
     * @param st
     * @param s
     * @return 
     */
    public List<User> findListByMajor(Major major, int st, int s) {
        Query query = getSession().createQuery("from User where mid='" + major.getId() + "' and statu =1 order by gid,sid DESC");
        query.setFirstResult(st);
        query.setMaxResults(s);
        List list = query.list();
        return list;
        
    }

    public List<User> findListByColleague(Colleague c, int st, int s) {
        Query query = getSession().createSQLQuery("select * from user where mid in (select id from major where cid='"+c.getId()+"') and statu =1 order by gid,sid DESC").addEntity(User.class);
        query.setFirstResult(st);
        query.setMaxResults(s);
        List list = query.list();
        return list;
    }

    public int getNumbersByColleague(Colleague c) {
        BigInteger a= (BigInteger) getSession().createSQLQuery("select count(id) from user where mid in (select id from major where cid='"+c.getId()+"') and statu =1 order by gid,sid DESC").uniqueResult();
        return a.intValue();
    }

    public List<User> findByCid(Colleague c) {
        Query query = getSession().createSQLQuery("select * from user left join major on user.mid = major.id where major.cid='"+c.getId()+"' and user.statu =1 order by sid DESC").addEntity(User.class);
        return query.list();
    }

    public Userfields getFieldsById(int uid) {
        Iterator it = getSession().createQuery("from Userfields where uid=" + uid).iterate();
        if (it.hasNext()) {
            return (Userfields) it.next();
        } else {
            return null;
        }
    }

    public List<User> getSelectedUserByPaperSelId(int sid) {
        Query query = getSession().createSQLQuery("select * from user where id in (SELECT selection.uid from selection where selection.sid='"+sid+"' and selection.statu=1 )").addEntity(User.class);
        return query.list();
    }

    public void delUser(User user) {
        getSession().createSQLQuery("delete from user where id=" + user.getId()).executeUpdate();
        getSession().createSQLQuery("delete from userfields where uid=" + user.getId()).executeUpdate();
    }
}
