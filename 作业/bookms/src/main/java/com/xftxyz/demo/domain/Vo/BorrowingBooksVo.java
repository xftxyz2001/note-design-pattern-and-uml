package com.xftxyz.demo.domain.Vo;

import com.xftxyz.demo.domain.Book;
import com.xftxyz.demo.domain.User;

/**
 * 
 * @date 2022/10/12
 *       添加视图层对象
 * @date 2022/11/3
 *       新增属性 user
 */
public class BorrowingBooksVo {
    private User user;
    private Book book; // 借阅书籍
    private String dateOfBorrowing; // 借书日期
    private String dateOfReturn; // 还书日期

    public void setBook(Book book) {
        this.book = book;
    }

    public void setDateOfBorrowing(String dateOfBorrowing) {
        this.dateOfBorrowing = dateOfBorrowing;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public Book getBook() {
        return book;
    }

    public String getDateOfBorrowing() {
        return dateOfBorrowing;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
