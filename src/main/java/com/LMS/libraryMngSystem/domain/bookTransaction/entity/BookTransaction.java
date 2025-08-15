package com.LMS.libraryMngSystem.domain.bookTransaction.entity;

import com.LMS.libraryMngSystem.domain.BaseEntity;
import com.LMS.libraryMngSystem.domain.book.entity.Book;
import com.LMS.libraryMngSystem.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "book_transaction")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookTransaction extends BaseEntity {

    public enum BookTxnStatus {
        ISSUED,
        RETURNED,
        OVERDUE,
        LOST,
        RESERVED,
        CANCELLED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookTxnStatus status;

    @Column(name = "issued_on", nullable = false)
    private LocalDateTime issuedOn;

    @Column(name = "return_on")
    private LocalDateTime returnedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book", nullable = false)
    private Book book;

    @Override
    public String toString() {
        return user + "-" + book + "-" + status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Book)) return false;
        BookTransaction other = (BookTransaction) o;
        return user.equals(other.getUser()) && book.equals(other.getBook()) && status.equals(other.getStatus());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
