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
 *  Created on : Sep 13, 2011, 9:51:32 PM
 *  Author     : princehaku
 */
package net.techest.ug.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.techest.ug.dao.*;
import net.techest.ug.mvc.controller.data.TrunkController;
import net.techest.ug.mvc.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author princehaku
 */
public class DBService {

    @Autowired
    public UsersDAO userDao;
    @Autowired
    public ColleagueDAO colleagueDao;
    @Autowired
    public MajorDAO majorDao;
    @Autowired
    private NoticeDAO noticeDao;
    @Autowired
    private NewsDAO newsDao;
    @Autowired
    private PaperTrunkDAO paperTrunkDao;
    @Autowired
    private PermissionDAO permissionDao;
    @Autowired
    private UserGroupDAO userGroupDao;
    @Autowired
    private PaperYearDAO paperYearDao;
    @Autowired
    private PaperselDAO paperselDao;
    @Autowired
    private SelectionDAO selectionDao;

    public DBService() {
    }

    /**添加一个新用户
     * 
     * @param u
     * @param uf 
     */
    public void saveUser(User u, Userfields uf) {
        userDao.save(u);
        uf.setUid(u.getId());
        userDao.saveFileds(uf);
    }

    public List<User> getUserListByColleague(Colleague c, int st, int s) {
        return userDao.findListByColleague(c, st, s);
    }

    public int getUserNumbersByColleague(Colleague c) {
        return userDao.getNumbersByColleague(c);
    }

    /**得到一个用户
     * 
     * @param id
     */
    public User getUserById(int id) {
        return userDao.getByid(id);
    }

    /**得到一个用户的详细信息
     * 
     * @param user 
     */
    public Userfields getUserFieldsByUser(User u) {
        return userDao.getFields(u);
    }

    public Userfields getUserFieldsByUserId(int uid) {
        return userDao.getFieldsById(uid);
    }

    /**得到所有的学院列表
     * 
     */
    public List<Colleague> getAllColleague() {
        return colleagueDao.getAll();
    }

    /**根据专业得到学院
     * 
     */
    public Colleague getColleagueByMajor(Major major) {
        List l = colleagueDao.getHibernateTemplate().find("from Colleague where id='" + major.getCid() + "'");

        if (l.size() < 1) {
            return null;
        } else {
            return (Colleague) l.get(0);
        }
    }

    public Major getMajorById(int id) {
        return majorDao.getByid(id);
    }

    /**根据一个学院编号得到专业列表
     * 
     * @param id
     * @return 
     */
    public List<Major> getMajorsByCid(int id) {
        return majorDao.getByCid(id);
    }

    public Major getMajorByUser(User u) {
        List l = majorDao.getHibernateTemplate().find("from Major where id='" + u.getMid() + "'");

        if (l.size() < 1) {
            return null;
        } else {
            return (Major) l.get(0);
        }
    }

    public List<Major> getMajorListById(int id) {
        Major m = majorDao.getByid(id);
        return majorDao.getHibernateTemplate().find("from Major where cid='" + m.getCid() + "'");
    }

    /**登录用户
     * 会返回用户bean或者是null
     * @param username
     * @param password
     * @return 
     */
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    public void saveUser(User u) {
        userDao.save(u);
    }

    /**查找一个用户的通知
     * 
     * @param u
     * @return 
     */
    public List<Notice> getRecentNoticeByUser(User u) {
        return noticeDao.getRecentByUser(u);
    }

    public List<News> getRecentNews(int n) {
        return newsDao.getRecentNews(n);
    }

    public Papertrunk getPaperTrunkById(int id) {
        return paperTrunkDao.getById(id);
    }

    public void savePaperTrunk(Papertrunk pt) {
        paperTrunkDao.save(pt);
    }

    /**得到所有的题库
     * 有分页
     * @param st
     * @param s
     * @return 
     */
    public List<Papertrunk> getTrunkPapers(int st, int s) {
        return paperTrunkDao.getList(st, s);
    }

    /**得到某用户的题库
     * 有分页
     * @param u
     * @param st
     * @param s
     * @return 
     */
    public List<Papertrunk> getTrunkPapersByUser(User u, int st, int s) {
        return paperTrunkDao.getListByUser(u, st, s);
    }

    /**得到某用户的组
     * 
     * @param nowUser
     * @return 
     */
    public Usergroup getGroupByUser(User nowUser) {
        return userDao.getGroupByUser(nowUser);
    }

    /**监测某用户是否有某权限
     * 
     * @param nowUser
     * @param permissiontype
     * @return 
     */
    public boolean havePermission(User nowUser, String permissiontype) {
        return userDao.hasPermission(nowUser, permissiontype);
    }

    /**得到题库总数
     * 
     * @return 
     */
    public int getTrunkPaperNumbers() {
        return paperTrunkDao.getTotal();
    }

    /**得到某个用户的题库的数量
     * 
     * @param u
     * @return 
     */
    public int getTrunkPaperNumbersByUser(User u) {
        return paperTrunkDao.getTotalByUser(u);
    }

    /**得到权限在某用户之下的所有人的题库
     * 
     * @param nowUser
     * @param st
     * @param s
     * @return 
     */
    public List<Papertrunk> getTrunkPapersBelowUser(User u, int st, int s) {
        return paperTrunkDao.getListBelowUser(u, st, s);
    }

    /**得到某个学院的题库的数量
     * 
     * @param u
     * @return 
     */
    public int getTrunkPaperNumbersByColleague(Colleague c) {
        return paperTrunkDao.getTotalByColleague(c);
    }

    /**得到某个学院的题库
     * 
     * @param nowUser
     * @param st
     * @param s
     * @return 
     */
    public List<Papertrunk> getTrunkPapersByColleague(Colleague c, int st, int s) {
        return paperTrunkDao.getListByColleague(c, st, s);
    }

    /**得到权限在某用户之下的所有人的题库的数量
     * 
     * @param nowUser
     * @return 
     */
    public int getTrunkPaperNumbersBelowUser(User u) {
        return paperTrunkDao.getTotalBelowUser(u);
    }

    /**得到所有用户列表
     * 
     * @param st
     * @param s
     * @return 
     */
    public List<User> getAllUserList(int st, int s) {
        return userDao.findList(st, s);
    }

    /**得到所有用户数量
     * 
     * @return 
     */
    public int getUsersNumbers() {
        return userDao.getHibernateTemplate().find("from User where statu =1 order by sid DESC").size();
    }

    public List<User> getUserListByMajor(Major major, int st, int s) {
        return userDao.findListByMajor(major, st, s);
    }

    public int getUserNumbersByMajor(Major m) {
        return userDao.getHibernateTemplate().find("from User where mid='" + m.getId() + "' and statu =1 order by sid DESC").size();
    }

    /**得到所有的用户组
     * 
     * @return 
     */
    public List<Usergroup> getAllUserGroups() {
        return userGroupDao.getHibernateTemplate().find("from Usergroup where statu='1'");
    }

    /**得到所有的用户组的数量
     * 
     * @return 
     */
    public int getAllUserGroupNumbers() {
        return userGroupDao.getAllNumbers();
    }

    /**得到权限在某用户下面的的用户组
     * 
     * @return 
     */
    public List<Usergroup> getUserGroupsBelowUser(User u) {
        return userGroupDao.getHibernateTemplate().find("from Usergroup where statu='1' and id<'" + u.getGid() + "'");
    }

    public List<Usergroup> getUserGroupsBelowUser(User u, int st, int s) {
        return userGroupDao.getUserGroupsBelowUser(u, st, s);
    }

    /**得到所有的权限列表
     * 
     * 
     * @return 
     */
    public List<Permission> getPermissionList() {
        return permissionDao.getHibernateTemplate().find("from Permission order by key desc where statu=1");
    }

    public Usergroup getGroupById(int id) {
        List l = userGroupDao.getHibernateTemplate().find("from Usergroup where id='" + id + "'");
        if (l.size() < 1) {
            return null;
        } else {
            return (Usergroup) l.get(0);
        }
    }

    public void saveUserGroup(Usergroup ug1) {
        userGroupDao.getHibernateTemplate().saveOrUpdate(ug1);
    }

    public News getNewsById(int id) {
        List l = newsDao.getHibernateTemplate().find("from News where id='" + id + "'");
        if (l.size() < 1) {
            return null;
        } else {
            return (News) l.get(0);
        }
    }

    public void saveNews(News news) {
        this.newsDao.getHibernateTemplate().saveOrUpdate(news);
    }

    public List<User> getColleagueTeachers(Colleague c) {
        return userDao.findByCid(c);
    }

    public Paperyear saveNewPaperYear(Major major, Date dtstart, Date dtend, int maxsel) {
        List<Papertrunk> trunks = paperTrunkDao.getAllTrunkPapersByMajor(major);
        Paperyear py = new Paperyear();
        py.setStatu(1);
        py.setMid(major.getId());
        py.setDtstart(dtstart);
        py.setDtend(dtend);
        py.setMaxsel(maxsel);
        savePaperYear(py);

        //循环trunks  加入到papersel
        Iterator it = trunks.iterator();
        while (it.hasNext()) {
            Papertrunk t = (Papertrunk) it.next();
            Papersel ps = new Papersel();
            ps.setIsdel(0);
            ps.setLeftsolts(1);
            ps.setMaxslots(1);
            ps.setPid(t.getId());
            ps.setOwneruid(t.getUid());
            ps.setYid(py.getId());
            paperselDao.getHibernateTemplate().save(ps);
        }

        return py;

    }

    public void savePaperYear(Paperyear py) {
        paperYearDao.save(py);
    }

    public List<Papersel> getPaperSelByYearId(int id) {
        return paperselDao.getHibernateTemplate().find("from Papersel where isdel=0 and yid='" + id + "' order by owneruid DESC");
    }

    public int getAllPaperYearNumbers() {
        return paperYearDao.getALLNumbers();
    }

    public List<Paperyear> getPaperYearList(int st, int s) {
        return paperYearDao.getList(st, s);
    }

    public Paperyear getPaperYearById(int id) {
        List list = paperYearDao.getHibernateTemplate().find("from Paperyear where statu>=1 and id='" + id + "'");
        if (list.isEmpty()) {
            return null;
        } else {
            return (Paperyear) list.get(0);
        }
    }

    public Papersel getPaperSelById(int id) {
        List list = paperselDao.getHibernateTemplate().find("from Papersel where isdel=0 and  id='" + id + "'");
        if (list.isEmpty()) {
            return null;
        } else {
            return (Papersel) list.get(0);
        }
    }

    public void savePaperSel(Papersel ps) {
        paperselDao.getHibernateTemplate().saveOrUpdate(ps);
    }

    public Paperyear getPaperYearByMajor(Major major) {
        List list = paperselDao.getHibernateTemplate().find("from Paperyear where statu>=1 and  mid='" + major.getId() + "'");
        if (list.isEmpty()) {
            return null;
        } else {
            return (Paperyear) list.get(0);
        }
    }

    public void deleteAllPaperSelBydPaperYear(Paperyear py) {
        paperselDao.deleteAllPaperSelBydPaperYear(py);
    }

    public List<Paperyear> getPaperYearByMajor(Major major, int st, int s) {
        return paperYearDao.getPaperYearByMajor(major, st, s);
    }

    /**得到某专业下的paperyear数量
     * 一般说来会保证只有一个
     * @param major
     * @return 
     */
    public int getPaperYearByMajorNumbers(Major major) {
        return paperYearDao.getPaperYearByMajorNumbers(major);
    }

    /**得到某专业下的所有题目数量
     * 
     * @param major
     * @return 
     */
    public int getPaperSelByMajorNumbers(Major major) {
        return paperselDao.getPaperYearByMajorNumbers(major);
    }

    /**得到某专业下的所有题目列表
     * 
     * @param major
     * @param st
     * @param s
     * @return 
     */
    public List<Papersel> getPaperSelByMajor(Major major, int st, int s) {
        return paperselDao.getPaperYearByMajor(major, st, s);
    }

    /**根据paperyearid得到所属的题目列表
     * 
     */
    public List<Papersel> getPaperSelByYearId(int id, int st, int s) {
        return paperselDao.getPaperSelByYearId(id, st, s);
    }

    /**根据paperyearid得到所属的题目总数
     * 
     */
    public int getPaperSelNumbersByYearId(int id) {
        return paperselDao.getPaperSelNumbersByYearId(id);
    }

    public boolean isSelected(User u, Integer pid) {
        List a = selectionDao.getHibernateTemplate().find("from Selection where uid='" + u.getId() + "' and sid='" + pid + "' and statu='1'");
        if (a.size() > 0) {
            return true;
        }
        return false;
    }

    public List<Papersel> getPaperSelByUser(User u, int st, int s) {
        return paperselDao.getPaperSelByUser(u, st, s);
    }

    public int getPaperSelByUserNumbers(User u) {
        return paperselDao.getPaperSelByUserNumbers(u);
    }

    /**选入某题
     * 
     * @param u
     * @param id
     * @return 
     */
    public boolean selectPaperSel(User u, int id) {
        // Sel数量减一
        Papersel ps = paperselDao.getPaperSelById(id);
        Selection s = selectionDao.getByUserAndSelId(u, id);

        if (ps.getLeftsolts() > 0 && s == null) {
            s = new Selection(u.getId(), id, 1);
            selectionDao.getHibernateTemplate().save(s);
            ps.setLeftsolts(ps.getLeftsolts() - 1);
            paperselDao.getHibernateTemplate().saveOrUpdate(ps);
            return true;
        } else {
            return false;
        }
    }

    public void deselectPaperSel(User u, int id) {
        Selection s = selectionDao.getByUserAndSelId(u, id);
        s.setStatu(0);
        selectionDao.getHibernateTemplate().saveOrUpdate(s);
        Papersel ps = paperselDao.getPaperSelById(id);
        ps.setLeftsolts(ps.getLeftsolts() + 1);
        paperselDao.getHibernateTemplate().saveOrUpdate(ps);
    }

    public List<Papersel> getPaperSelByOwner(User u, int st, int s) {
        return paperselDao.getPaperSelByOwner(u, st, s);
    }

    public int getPaperSelNumbersByOwner(User u) {
        return paperselDao.getPaperSelNumbersByOwner(u);
    }

    public List<User> getSelectedUserByPaperSelId(int sid) {
        return userDao.getSelectedUserByPaperSelId(sid);
    }

    public List<Selection> getAllSelectionList() {
        return selectionDao.getAllSelectionList();
    }

    public List<Selection> getAllSelectionListByOwner(User u) {
        return selectionDao.getAllSelectionListByOwner(u);
    }

    public List<Selection> getAllSelectionListByColleague(User u) {
        return selectionDao.getAllSelectionListByColleague(u);
    }

    public int getPaperYearByColleagueNumbers(int cid) {
        return paperYearDao.getPaperYearByColleagueNumbers(cid);
    }

    public List<Paperyear> getPaperYearByColleague(int cid, int st, int s) {
        return paperYearDao.getPaperYearByColleague(cid, st, s);
    }

    /**得到某专业下的题库
     * 
     * @param major
     * @param st
     * @param s
     * @return 
     */
    public List<Papertrunk> getTrunkPapersByMajor(Major major, int st, int s) {
        return paperTrunkDao.getTrunkPapersByMajor(major, st, s);
    }

    public int getTrunkPaperNumbersByMajor(Major major) {
        return paperTrunkDao.getTrunkPaperNumbersByMajor(major);
    }

    public List<Selection> getAllSelectionListByMajorId(int mid) {
        return selectionDao.getAllSelectionListByMajorId(mid);
    }

    public boolean isPaperSelExcess(User u, int id) {
        Papersel ps = paperselDao.getPaperSelById(id);
        Paperyear py = getPaperYearById(ps.getYid());
        int now = paperselDao.getPaperSelByUserNumbers(u);
        System.out.print("当前已经选入了" + now + "上限是" + py.getMaxsel());
        if (py.getMaxsel() > now) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isPaperOwnerExcess(int id) {
        Papersel ps = paperselDao.getPaperSelById(id);
        // 查找是否已经到达选题老师上限
        User teacher = getUserById(ps.getOwneruid());
        Userfields teacherfields = getUserFieldsByUserId(ps.getOwneruid());
        int maxPapers = teacherfields.getMaxpapers();

        if (getAllSelectionListByOwner(teacher).size() >= maxPapers) {
            return true;
        }

        return false;
    }

    public Selection getSelectionByUserIdAndPaperselId(int uid, int pid) {
        Selection s = selectionDao.getByUserIdAndSelId(uid, pid);
        return s;
    }

    public void saveSelection(Selection s) {
        selectionDao.getHibernateTemplate().saveOrUpdate(s);
    }

    public void deleteSelectionByUserIdAndPaperselId(int uid, int sid) {
        Selection s = selectionDao.getByUserIdAndSelId(uid, sid);
        s.setStatu(0);
        selectionDao.getHibernateTemplate().saveOrUpdate(s);
        Papersel ps = getPaperSelById(sid);
        ps.setLeftsolts(ps.getLeftsolts() + 1);
        paperselDao.getHibernateTemplate().saveOrUpdate(ps);
    }

    public User getUserBySid(String sid) {
        // 此处有sql注入
        List a = userDao.getHibernateTemplate().find("from User where sid="+sid);
        if (a.size() > 0) {
            return (User)a.get(0);
        }
        return null;
    }

    public void saveSelectionByUserIdAndPaperselId(User u, int pid) throws Exception {
        Selection s = getSelectionByUserIdAndPaperselId(u.getId(), pid);
        if (s!=null) {
            throw new Exception("该用户已经选入");
        }
        s = new Selection(u.getId(), pid, 1);
        selectionDao.getHibernateTemplate().saveOrUpdate(s);
        Papersel ps = getPaperSelById(pid);
        ps.setLeftsolts(ps.getLeftsolts() - 1);
        paperselDao.getHibernateTemplate().saveOrUpdate(ps);
    }

    public List<Selection> getAllSelectionListByPaperYear(int yid) {
        return selectionDao.getAllSelectionListByPaperYear(yid);
    }

    public void delUser(User user) {
        selectionDao.delByUser(user);
        paperTrunkDao.delByUser(user);
        List<Papersel> ps = getPaperSelByUser(user, 0, 999);
        Iterator<Papersel> ips=ps.iterator();
        while (ips.hasNext()) {
            Papersel p=ips.next();
            deleteSelectionByUserIdAndPaperselId(user.getId(), p.getId());
        }
        paperselDao.delByUser(user);
        userDao.delUser(user);
    }
}
