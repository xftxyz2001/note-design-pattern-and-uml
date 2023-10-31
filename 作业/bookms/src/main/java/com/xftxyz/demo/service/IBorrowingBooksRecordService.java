package com.xftxyz.demo.service;

import javax.servlet.http.HttpServletRequest;

import com.xftxyz.demo.domain.Vo.BorrowingBooksVo;
import com.xftxyz.demo.utils.page.Page;

public interface IBorrowingBooksRecordService {

    // 查询用户的借书记录
    public Page<BorrowingBooksVo> userSelectByPageNum(int pageNum, HttpServletRequest request);

    // 分页查询所有用户借书记录
    public Page<BorrowingBooksVo> selectAllByPage(int pageNum);
}
