package com.xftxyz.demo.service;

import java.util.List;

import com.xftxyz.demo.domain.Book;
import com.xftxyz.demo.domain.BookCategory;

public interface IAdminService {

    // 验证用户是否存在
    public boolean adminIsExist(String name);

    // 管理员登陆
    public boolean adminLogin(String name, String password);

    // 录入新书
    public boolean addBook(Book book);

    // 获取所有图书类别
    public List<BookCategory> getBookCategorys();

    // 增加图书类别
    public boolean addBookCategory(BookCategory bookCategory);
}
