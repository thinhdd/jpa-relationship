package com.hellokoding.jpa.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Book{
    private int id;
    private String name;
    private Set<Publisher> publishers;
    private BookCategory bookCategory;

    public Book() {

    }

    public Book(String name) {
        this.name = name;
    }

    public Book(String name, Set<Publisher> publishers){
        this.name = name;
        this.publishers = publishers;
    }

    public Book(String name, Set<Publisher> publishers, BookCategory bookCategory) {
        this.name = name;
        this.publishers = publishers;
        this.bookCategory = bookCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_publisher", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "publisher_id", referencedColumnName = "id"))
    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }

    @ManyToOne
    @JoinColumn(name = "book_category_id")
    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    @Override
    public String toString() {
        String result = String.format(
                "Book [id=%d, name='%s']%n",
                id, name);
        if (publishers != null) {
            for(Publisher publisher : publishers) {
                result += String.format(
                        "Publisher[id=%d, name='%s']%n",
                        publisher.getId(), publisher.getName());
            }
        }

        return result;
    }
}
