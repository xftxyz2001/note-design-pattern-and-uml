package com.xftxyz.demo.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xftxyz.demo.domain.Book;
import com.xftxyz.demo.domain.BookExample;
import com.xftxyz.demo.domain.BorrowingBooks;
import com.xftxyz.demo.domain.BorrowingBooksExample;
import com.xftxyz.demo.domain.Vo.BookVo;
import com.xftxyz.demo.mapper.BookMapper;
import com.xftxyz.demo.mapper.BorrowingBooksMapper;
import com.xftxyz.demo.service.IBookService;
import com.xftxyz.demo.utils.page.Page;

@Service
public class BookServiceImpl implements IBookService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;

    @Override
    public List<BookVo> findBooksByBookName(String bookName) {
        BookExample bookExample = new BookExample();
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andBookNameEqualTo(bookName);
        List<Book> books = bookMapper.selectByExample(bookExample);
        List<BookVo> bookVos = new LinkedList<>();
        if (null == books) {
            return bookVos;
        }
        for (Book b : books) {
            BookVo bookVo = new BookVo();
            bookVo.setBookId(b.getBookId());
            bookVo.setBookName(b.getBookName());
            bookVo.setBookAuthor(b.getBookAuthor());
            bookVo.setBookPublish(b.getBookPublish());
            BorrowingBooksExample borrowingBooksExample = new BorrowingBooksExample();
            BorrowingBooksExample.Criteria criteria1 = borrowingBooksExample.createCriteria();
            criteria1.andBookIdEqualTo(b.getBookId());
            List<BorrowingBooks> borrowingBooks = borrowingBooksMapper.selectByExample(borrowingBooksExample);
            if (borrowingBooks == null || borrowingBooks.size() < 1) {
                bookVo.setIsExist("可借");
            } else {
                bookVo.setIsExist("不可借");
            }
            bookVos.add(bookVo);
        }
        return bookVos;
    }

    @Override
    public Page<BookVo> findBooksByCategoryId(int categoryId, int pageNum) {
        List<Book> books = bookMapper.selectByCategoryId(categoryId, (pageNum - 1) * 10, 10);
        List<BookVo> bookVos = new LinkedList<>();
        Page<BookVo> page = new Page<>();
        if (null == books) {
            page.setPageNum(1);
            page.setPageCount(1);
            return page;
        }
        for (Book b : books) {
            BookVo bookVo = new BookVo();
            bookVo.setBookId(b.getBookId());
            bookVo.setBookName(b.getBookName());
            bookVo.setBookAuthor(b.getBookAuthor());
            bookVo.setBookPublish(b.getBookPublish());
            BorrowingBooksExample borrowingBooksExample = new BorrowingBooksExample();
            BorrowingBooksExample.Criteria criteria1 = borrowingBooksExample.createCriteria();
            criteria1.andBookIdEqualTo(b.getBookId());
            List<BorrowingBooks> borrowingBooks = borrowingBooksMapper.selectByExample(borrowingBooksExample);
            if (borrowingBooks == null || borrowingBooks.size() < 1) {
                bookVo.setIsExist("可借");
            } else {
                bookVo.setIsExist("不可借");
            }
            bookVos.add(bookVo);
        }
        page.setList(bookVos);
        page.setPageNum(pageNum);
        page.setPageSize(10);
        int bookCount = bookMapper.selectBookCountByCategoryId(categoryId);
        int pageCount = 0;
        pageCount = bookCount / 10;
        if (bookCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        if (bookCount == 0) {
            page.setPageCount(1);
        }
        return page;

    }

    // @Override
    // public List<BookVo> findBooksByCategoryId(int categoryId) {
    // BookExample bookExample=new BookExample();
    // BookExample.Criteria criteria=bookExample.createCriteria();
    // criteria.andBookCategoryEqualTo(categoryId);
    // List<Book> books=bookMapper.selectByExample(bookExample);
    // List<BookVo> bookVos=new LinkedList<>();
    // if(null==books){
    // return bookVos;
    // }
    // for(Book b:books){
    // BookVo bookVo=new BookVo();
    // bookVo.setBookId(b.getBookId());
    // bookVo.setBookName(b.getBookName());
    // bookVo.setBookAuthor(b.getBookAuthor());
    // bookVo.setBookPublish(b.getBookPublish());
    // BorrowingBooksExample borrowingBooksExample=new BorrowingBooksExample();
    // BorrowingBooksExample.Criteria
    // criteria1=borrowingBooksExample.createCriteria();
    // criteria1.andBookIdEqualTo(b.getBookId());
    // List<BorrowingBooks>
    // borrowingBooks=borrowingBooksMapper.selectByExample(borrowingBooksExample);
    // if(borrowingBooks==null||borrowingBooks.size()<1){
    // bookVo.setIsExist("可借");
    // }else{
    // bookVo.setIsExist("不可借");
    // }
    // bookVos.add(bookVo);
    // }
    // return bookVos;
    // }
}
