package com.hellokoding.jpa;

import com.hellokoding.jpa.model.Book;
import com.hellokoding.jpa.model.BookCategory;
import com.hellokoding.jpa.model.Publisher;
import com.hellokoding.jpa.repository.BookCategoryRepository;
import com.hellokoding.jpa.repository.BookRepository;
import com.hellokoding.jpa.repository.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        // save a couple of books

        // many to many case
        Publisher publisherA = new Publisher("Publisher A");
        Publisher publisherB = new Publisher("Publisher B");
        Publisher publisherC = new Publisher("Publisher C");

        bookRepository.save(new HashSet<Book>() {{
            add(new Book("Book A", new HashSet<Publisher>() {{
                add(publisherA);
                add(publisherB);
            }}));

            add(new Book("Book B", new HashSet<Publisher>() {{
                add(publisherA);
                add(publisherC);
            }}));
        }});

        // fetch all books
        for (Book book : bookRepository.findAll()) {
            logger.info(book.toString());
        }

        // save a couple of publishers
        Book bookA = new Book("Book A");
        Book bookB = new Book("Book B");

        publisherRepository.save(new HashSet<Publisher>() {{
            add(new Publisher("Publisher A", new HashSet<Book>() {{
                add(bookA);
                add(bookB);
            }}));

            add(new Publisher("Publisher B", new HashSet<Book>() {{
                add(bookA);
                add(bookB);
            }}));
        }});

        // fetch all publishers
        for (Publisher publisher : publisherRepository.findAll()) {
            logger.info(publisher.toString());
        }

        // many to one case
        // only support save father before child
        Book book1 = new Book("nguoi cung kho");
        Book book2 = new Book("nguoi rat kho");
        BookCategory bookCategory = new BookCategory("kho", new HashSet<Book>() {{
            add(book1);
            add(book2);
        }});
        bookCategory = bookCategoryRepository.save(bookCategory);
        for(Book book : bookCategory.getBooks()){
            logger.info(book.getId() + " - " + book.getName() + "\n");
        }

    }
}
