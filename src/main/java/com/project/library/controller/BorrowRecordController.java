package com.project.library.controller;

import com.project.library.entity.BorrowRecord;
import com.project.library.service.BorrowRecordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    // Constructor Injection
    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    // BORROW BOOK
    @PostMapping("/{memberId}/{bookId}")
    public BorrowRecord borrowBook(@PathVariable Long memberId,
                                   @PathVariable Long bookId) {
        return borrowRecordService.borrowBook(memberId, bookId);
    }

    // RETURN BOOK
    @PutMapping("/return/{borrowRecordId}")
    public BorrowRecord returnBook(@PathVariable Long borrowRecordId) {
        return borrowRecordService.returnBook(borrowRecordId);
    }
}
