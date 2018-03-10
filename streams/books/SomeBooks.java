package com.openmarket.streamsworkshop.books;

import java.util.ArrayList;
import java.util.List;

public class SomeBooks {

    public static List<Book> getSomeBooks() {
        List<Book> library = new ArrayList<>();
        library.add(Book.of("Foreign Soil", "Clarke", 1979));
        library.add(Book.of("Harry Potter and the Philosopher's Stone", "Rowling", 1965));
        library.add(Book.of("Harry Potter and the Goblet of Fire", "Rowling", 1965));
        library.add(Book.of("Conspiracy of Calaspia", "Guptara", 1988));
        library.add(Book.of("Red Rising", "Brown", 1991));
        library.add(Book.of("A little life", "Yanagihara", 1974));
        library.add(Book.of("Jane Eyre", "Brontë", 1816));
        library.add(Book.of("The Exaltation of Inanna", "Enheduanna", -2285));
        return library;
    }
}
