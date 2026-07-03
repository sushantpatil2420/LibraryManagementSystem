package com.project.library.repository;

import com.project.library.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // ALL BORROW RECORDS FOR A MEMBER (full history)
    List<BorrowRecord> findByMember_MemberId(Long memberId);

    // ALL BORROW RECORDS FOR A BOOK (full history)
    List<BorrowRecord> findByBook_BookId(Long bookId);

    // CURRENTLY BORROWED (NOT YET RETURNED)
    List<BorrowRecord> findByReturnDateIsNull();

    // OVERDUE BOOKS (NOT RETURNED + PAST DUE DATE)
    List<BorrowRecord> findByReturnDateIsNullAndDueDateBefore(LocalDate date);
}
