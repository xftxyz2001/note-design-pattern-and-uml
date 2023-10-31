package com.xftxyz.demo.service;

import com.xftxyz.demo.domain.BookCategory;
import com.xftxyz.demo.utils.page.Page;

public interface IBookCategoryService {

    // 图书类别分页查询
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum);

    int deleteBookCategoryById(int bookCategoryId);
}
