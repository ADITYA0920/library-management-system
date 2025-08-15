package com.LMS.libraryMngSystem.web;

import com.LMS.libraryMngSystem.domain.book.dto.RegisterBookRequest;
import com.LMS.libraryMngSystem.domain.book.service.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api" + BookController.PATH)
@RequiredArgsConstructor
public class BookController {

    public static final String PATH = "/book";
    public static final String ADD = "/add";
    public static final String ALL = "/all";
    public static final String SEARCH = "/search";
    public static final String ISSUE = "/issue";
    public static final String RETURN = "/return";

    private @NonNull BookService bookService;

    @PostMapping(ADD)
    public ResponseEntity<?> addBook(@RequestBody RegisterBookRequest request) {
        String response = bookService.addBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(ALL)
    public ResponseEntity<?> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks(page, size));
    }

    @GetMapping(SEARCH + "/{title}")
    public ResponseEntity<?> searchBook(@PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.searchBook(title));
    }

    @PostMapping(ISSUE)
    public ResponseEntity<?> issueBook(@RequestParam String key, @RequestParam Long userId) {
         return ResponseEntity.status(HttpStatus.OK).body(bookService.issueBook(key, userId));
    }

    @PostMapping(RETURN)
    public ResponseEntity<?>  returnBook(@RequestParam String key, @RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.returnBook(key, userId));
    }

}
