package com.jdk.optional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author cheny.huang
 * @date 2019-03-04 11:37.
 */
public class Student {
    private String name;
    private Set<String> book;

    public void addBook(String book) {
        if (this.book == null) {
            this.book = new HashSet<>();
        }
        this.book.add(book);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<String> getBook() {
        return book;
    }
}
