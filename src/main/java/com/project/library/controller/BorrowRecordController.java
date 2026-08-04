package com.project.library.controller;

import com.project.library.dto.BorrowRecordDTO;
import com.project.library.entity.BorrowRecord;
import com.project.library.service.BorrowRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // FULL BORROW HISTORY FOR A MEMBER
    @GetMapping("/history/member/{memberId}")
    public List<BorrowRecordDTO> getBorrowHistoryByMember(@PathVariable Long memberId) {
        return borrowRecordService.getBorrowHistoryByMember(memberId);
    }

    // FULL BORROW HISTORY FOR A BOOK
    @GetMapping("/history/book/{bookId}")
    public List<BorrowRecordDTO> getBorrowHistoryByBook(@PathVariable Long bookId) {
        return borrowRecordService.getBorrowHistoryByBook(bookId);
    }

    // ALL CURRENTLY BORROWED BOOKS
    @GetMapping("/active")
    public List<BorrowRecordDTO> getActiveBorrows() {
        return borrowRecordService.getActiveBorrows();
    }

    // ALL OVERDUE BOOKS
    @GetMapping("/overdue")
    public List<BorrowRecordDTO> getOverdueBorrows() {
        return borrowRecordService.getOverdueBorrows();
    }

    // COUNT OF CURRENTLY BORROWED BOOKS FOR A MEMBER
    @GetMapping("/count/member/{memberId}")
    public long getActiveBorrowCountByMember(@PathVariable Long memberId) {
        return borrowRecordService.getActiveBorrowCountByMember(memberId);
    }
}