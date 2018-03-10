package com.openmarket.streamsworkshop.books;

import java.util.*;
import java.util.stream.Collectors;

public class BooksExercise {

    public static List<String> getSurnamesOfFirstFifteenAuthorsOver50(List<Book> library) {
        List<String> surnames = new LinkedList<>();
        Set<Author> authors = new HashSet<>();
        for (Book book : library) {
            if (book.getAuthor().getAge() >= 50) {
                authors.add(book.getAuthor());
                if (authors.size() >= 15) {
                    break;
                }
            }
        }

        for (Author author : authors) {
            surnames.add(author.getSurname().toUpperCase());
        }

        return surnames;
    }

    public static List<String> getSurnamesOfFirstFifteenAuthorsOver50WithStreams(List<Book> library) {
        List<String> surnames = library.stream()
                .filter(b -> b.getAuthor().getAge() >= 50)
                .limit(15)
                .map(b -> b.getAuthor().getSurname().toUpperCase())
                .distinct()
                .collect(Collectors.toList());

        return surnames;
    }

    public static void main(String[] args) {
        List<String> authors = BooksExercise.getSurnamesOfFirstFifteenAuthorsOver50(SomeBooks.getSomeBooks());
        authors.stream().forEach(b -> System.out.println(b));
    }

}
