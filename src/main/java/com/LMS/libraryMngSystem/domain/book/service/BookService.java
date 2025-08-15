package com.LMS.libraryMngSystem.domain.book.service;

import com.LMS.libraryMngSystem.domain.book.dto.RegisterBookRequest;
import com.LMS.libraryMngSystem.domain.book.entity.Book;
import com.LMS.libraryMngSystem.domain.book.exception.BookAlreadyExistsException;
import com.LMS.libraryMngSystem.domain.book.exception.BookTransactionNotFoundException;
import com.LMS.libraryMngSystem.domain.book.exception.BookUnavailableException;
import com.LMS.libraryMngSystem.domain.book.repo.BookRepository;
import com.LMS.libraryMngSystem.domain.bookTransaction.entity.BookTransaction;
import com.LMS.libraryMngSystem.domain.bookTransaction.repo.BookTransactionRepository;
import com.LMS.libraryMngSystem.domain.user.entity.User;
import com.LMS.libraryMngSystem.domain.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    public record BookResponse(String title, String author, String key, String status){};

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookTransactionRepository bookTxnRepo;

    @Transactional
    public String addBook(RegisterBookRequest request) {

        String title = request.getTitle();
        String author = request.getAuthor();

        String key = (title + author).trim().toLowerCase();

        Optional<Book> exist = bookRepository.findByKey(key);

        if(exist.isPresent()) {
            throw new BookAlreadyExistsException("Book already present: " + title + " by " + author);
        }

        Date current = new Date();

        Book book = Book.builder()
                .bookName(title)
                .author(author)
                .key(key)
                .status(Book.BookStatus.AVAILABLE)
                .created(current)
                .createdBy("admin")
                .lastModified(current)
                .lastModifiedBy("admin")
                .build();

        bookRepository.save(book);

        return "Book added successfully with key: " + key;
    }

    //-----------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public Page<BookResponse> getAllBooks(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("bookName").ascending());

        Page<Book> bookList = bookRepository.findAll(pageable);

        if (bookList.isEmpty()) {
            throw new RuntimeException("No books are available");
        }

        return bookList.map(book -> new BookResponse(
                book.getBookName(),
                book.getAuthor(),
                book.getKey(),
                book.getStatus().name()
        ));
    }

    //-------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<BookResponse> searchBook(String title) {

        List<Book> bookList = bookRepository.findByBookName(title);

        if (bookList.isEmpty()) {
            throw new RuntimeException("No books are available");
        }

        return mapToBookResponses(bookList);
    }

    //-------------------------------------------------------------------------
    @Transactional
    public String issueBook(String key, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Book book = bookRepository.findByKey(key).orElseThrow(() ->  new RuntimeException("Book not found with key: " + key));

        Book.BookStatus currentStatus = book.getStatus();

        if (book.getStatus() != Book.BookStatus.AVAILABLE) {
            throw new BookUnavailableException("Book is not available for issue");
        }

        Date current = new Date();
        String userName = user.getName();

        BookTransaction bookTransaction = BookTransaction.builder()
                .status(BookTransaction.BookTxnStatus.ISSUED)
                .issuedOn(LocalDateTime.now())
                .user(user)
                .book(book)
                .created(current)
                .createdBy(userName)
                .lastModified(current)
                .lastModifiedBy(userName)
                .build();

        book.setStatus(Book.BookStatus.ISSUED);
        book.setLastModifiedBy(userName);
        book.setLastModified(current);

        bookTxnRepo.save(bookTransaction);

        return "Book " + book.getBookName() +" issued successfully to " + user.getName();
    }

    //-------------------------------------------------------------------------
    @Transactional
    public String returnBook(String key, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Book book = bookRepository.findByKey(key).orElseThrow(() ->  new RuntimeException("Book not found with key: " + key));

        BookTransaction transaction = bookTxnRepo
                .findByUserAndBookAndStatus(user, book, BookTransaction.BookTxnStatus.ISSUED)
                .orElseThrow(() -> new BookTransactionNotFoundException(
                        "No active issue record found for this book and user"));

        Date current = new Date();

        transaction.setStatus(BookTransaction.BookTxnStatus.RETURNED);
        transaction.setReturnedOn(LocalDateTime.now());

        book.setStatus(Book.BookStatus.AVAILABLE);
        book.setLastModifiedBy(user.getName());
        book.setLastModified(current);

        transaction.setLastModifiedBy(user.getName());
        transaction.setLastModified(current);

        bookRepository.save(book);
        bookTxnRepo.save(transaction);

        return "book " + book.getBookName() +" returned successfully by " + user.getName();
    }

    //-------------------------------------------------------------------------
    private List<BookResponse> mapToBookResponses(List<Book> books) {
        return books.stream()
                .map(b -> new BookResponse(
                        b.getBookName(),
                        b.getAuthor(),
                        b.getKey(),
                        b.getStatus().name()
                ))
                .toList();
    }

}
