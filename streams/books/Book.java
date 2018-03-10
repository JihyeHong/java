package com.openmarket.streamsworkshop.books;

public class Book {

    private String title;
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public Book (String title, Author author) {
        this.title = title;
        this.author = author;
    }

    public static Book of(String title, String authorSurname, int authorBirthYear) {
        return new Book(title, new Author(authorSurname, authorBirthYear));
    }

}
