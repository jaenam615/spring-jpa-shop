package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @Test
    public void 상품_등록() throws Exception {
        //given
        Book book = new Book();

        //when
        Long bookId = itemService.saveItem(book);

        //then
        Assert.assertEquals(book, itemRepository.findOne(bookId));
    }

    @Test
    public void 단일_상품_조회() throws Exception {
        //given
        Book book = new Book();
        book.setAuthor("TestBook");
        book.setIsbn("123");

        //when
        Long bookId = itemService.saveItem(book);
        Item savedItem = itemService.findOne(bookId);

        Book savedBook = (Book) savedItem;

        //then
        Assert.assertEquals(savedBook, book);
        Assert.assertEquals(savedBook.getAuthor(), book.getAuthor());
        Assert.assertEquals(savedBook.getIsbn(), book.getIsbn());
    }
    
    @Test
    public void 전체_상품_조회() throws Exception {
        //given
        Book book1 = new Book();
        Book book2 = new Book();

        //when
        Long bookId1 = itemService.saveItem(book1);
        Long bookId2 = itemService.saveItem(book2);
        List<Item> items = itemService.findAllItems();

        List<Book> books = items.stream()
                .map(item -> (Book) item)
                .toList();
        //then

        Assert.assertEquals(2, books.size());
    }
}
