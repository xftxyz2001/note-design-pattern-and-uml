package com.xftxyz.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.xftxyz.demo.domain.Book;
import com.xftxyz.demo.domain.BorrowingBooks;
import com.xftxyz.demo.domain.BorrowingBooksExample;
import com.xftxyz.demo.domain.Department;
import com.xftxyz.demo.domain.DepartmentExample;
import com.xftxyz.demo.domain.User;
import com.xftxyz.demo.domain.UserExample;
import com.xftxyz.demo.domain.Vo.BorrowingBooksVo;
import com.xftxyz.demo.mapper.BookMapper;
import com.xftxyz.demo.mapper.BorrowingBooksMapper;
import com.xftxyz.demo.mapper.DepartmentMapper;
import com.xftxyz.demo.mapper.UserMapper;
import com.xftxyz.demo.service.IUserService;
import com.xftxyz.demo.utils.page.Page;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;
    @Resource
    private BookMapper bookMapper;

    @Override
    public List<User> findUserByUserName(String userName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public List<Department> findAllDepts() {

        return departmentMapper.selectByExample(new DepartmentExample());
    }

    @Override
    public User userLogin(String userName, String password) {
        List<User> users = findUserByUserName(userName);
        if (null == users) {
            return null;
        }
        for (User user : users) {
            if (user.getUserPwd().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateUser(User user, HttpServletRequest request) {
        // 获取session对象中user对象
        User sessionUser = (User) request.getSession().getAttribute("user");
        user.setUserId(sessionUser.getUserId());
        int n = userMapper.updateByPrimaryKey(user);
        if (n > 0) {
            // 修改成功，更新session对象
            User newUser = userMapper.selectByPrimaryKey(user.getUserId());
            request.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }

    @Override
    public List<BorrowingBooksVo> findAllBorrowingBooks(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        // 设置查询条件 userId
        BorrowingBooksExample borrowingBooksExample = new BorrowingBooksExample();
        BorrowingBooksExample.Criteria criteria = borrowingBooksExample.createCriteria();
        criteria.andUserIdEqualTo(user.getUserId());
        List<BorrowingBooks> borrowingBooksList = borrowingBooksMapper.selectByExample(borrowingBooksExample);
        if (null == borrowingBooksList) {
            return null;
        }
        // 将数据库表对应的对象(Do)转化成视图层对象（VO）
        List<BorrowingBooksVo> res = new LinkedList<BorrowingBooksVo>();
        for (BorrowingBooks borrowingBooks : borrowingBooksList) {
            Book book = bookMapper.selectByPrimaryKey(borrowingBooks.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();
            borrowingBooksVo.setBook(book);

            // 日期转换
            Date date1 = borrowingBooks.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBorrowing = sdf.format(date1);

            // 算出还书日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 2);// 增加两个月
            Date date2 = calendar.getTime();
            String dateOfReturn = sdf.format(date2);

            borrowingBooksVo.setDateOfBorrowing(dateOfBorrowing);
            borrowingBooksVo.setDateOfReturn(dateOfReturn);
            res.add(borrowingBooksVo);
        }
        return res;
    }

    @Override
    public boolean userReturnBook(int bookId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        BorrowingBooksExample borrowingBooksExample = new BorrowingBooksExample();
        BorrowingBooksExample.Criteria criteria = borrowingBooksExample.createCriteria();
        criteria.andUserIdEqualTo(user.getUserId());
        criteria.andBookIdEqualTo(bookId);

        // 删除数据库中user_d等于userId,book_id等于bookId的记录
        int n = borrowingBooksMapper.deleteByExample(borrowingBooksExample);
        if (n > 0)
            return true;
        return false;
    }

    @Override
    public boolean userBorrowingBook(int bookId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        // 检查该书是否可借,从借书记录表中查询该书是否已借出
        BorrowingBooksExample borrowingBooksExample = new BorrowingBooksExample();
        BorrowingBooksExample.Criteria criteria = borrowingBooksExample.createCriteria();
        criteria.andBookIdEqualTo(bookId);
        List<BorrowingBooks> list = borrowingBooksMapper.selectByExample(borrowingBooksExample);
        if (list.size() > 0) {
            return false;
        }

        BorrowingBooks borrowingBooks = new BorrowingBooks();
        borrowingBooks.setBookId(bookId);
        borrowingBooks.setUserId(user.getUserId());
        borrowingBooks.setDate(new Date());
        // 数据库中增加一条借书记录
        int n = borrowingBooksMapper.insert(borrowingBooks);
        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User findUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<User> findUserByPage(int pageNum) {
        List<User> users = userMapper.selectByPageNum((pageNum - 1) * 10, 10);
        Page<User> page = new Page<>();
        page.setList(users);
        page.setPageNum(pageNum);
        page.setPageSize(10);

        int userCount = userMapper.selectUserCount();
        int pageCount = userCount / 10;
        if (userCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteUserById(int userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }
}
