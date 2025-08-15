package com.LMS.libraryMngSystem.domain.book.entity;


import com.LMS.libraryMngSystem.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity {

    public enum BookStatus {
        AVAILABLE,
        ISSUED,
        RESERVED,
        LOST,
        DAMAGED,
        REMOVED
    }

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "key", unique = true, nullable = false)
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;

    @Override
    public String toString() {
        return bookName + "-" + author + "-" + key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        return this.key.equals(other.getKey());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
