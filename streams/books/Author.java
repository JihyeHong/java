package com.openmarket.streamsworkshop.books;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Author {

    private String surname;
    private int birthYear;

    public Author(String surname, int birthYear) {
        this.surname = surname;
        this.birthYear = birthYear;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getAge() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).getYear() - birthYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Author)) {
            return false;
        }
        Author other = (Author)obj;
        if(!other.getSurname().equals(surname)) {
            return false;
        }
        if(other.getBirthYear() != birthYear) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return surname.hashCode() + birthYear;
    }
}
