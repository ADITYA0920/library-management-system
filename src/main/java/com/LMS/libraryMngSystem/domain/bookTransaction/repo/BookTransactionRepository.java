package com.LMS.libraryMngSystem.domain.bookTransaction.repo;

import com.LMS.libraryMngSystem.domain.book.entity.Book;
import com.LMS.libraryMngSystem.domain.bookTransaction.entity.BookTransaction;
import com.LMS.libraryMngSystem.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction ,Long> {

    Optional<BookTransaction> findByUserAndBookAndStatus(User user, Book book, BookTransaction.BookTxnStatus status);

}
