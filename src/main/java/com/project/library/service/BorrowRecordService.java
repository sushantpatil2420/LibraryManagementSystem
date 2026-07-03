package com.project.library.service;

import com.project.library.dto.BorrowRecordDTO;
import com.project.library.entity.Book;
import com.project.library.entity.BorrowRecord;
import com.project.library.entity.Member;
import com.project.library.repository.BookRepository;
import com.project.library.repository.BorrowRecordRepository;
import com.project.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    //CONSTRUCTOR INJECTION
    public BorrowRecordService(
            BorrowRecordRepository borrowRecordRepository,
            MemberRepository memberRepository,
            BookRepository bookRepository) {

        this.borrowRecordRepository = borrowRecordRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    // BORROW BOOK SERVICE
    public BorrowRecord borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->
                        new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        BorrowRecord borrowRecord = new BorrowRecord();

        borrowRecord.setMember(member);
        borrowRecord.setBook(book);

        borrowRecord.setBorrowDate(LocalDate.now());

        borrowRecord.setDueDate(
                LocalDate.now().plusDays(14)
        );

        // REDUCE AVAILABLE COPIES
        book.setAvailableCopies(
                book.getAvailableCopies() - 1
        );

        bookRepository.save(book);

        return borrowRecordRepository.save(borrowRecord);
    }

    // RETURN BOOK SERVICE
    public BorrowRecord returnBook(Long borrowRecordId) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(()->
                        new RuntimeException("Borrow Record Not Found"));

        if (borrowRecord.getReturnDate() != null) {
            throw new RuntimeException("Book Already Returned");
        }

        borrowRecord.setReturnDate(LocalDate.now());

        Book book = borrowRecord.getBook();
        book.setAvailableCopies(
                book.getAvailableCopies() + 1
        );

        // SAVE UPDATED BOOK
        bookRepository.save(book);

        // Save updated borrow record
        return borrowRecordRepository.save(borrowRecord);
    }

    // CONVERT ENTITY TO DTO
    private BorrowRecordDTO convertToDTO(BorrowRecord borrowRecord) {
        return new BorrowRecordDTO(
                borrowRecord.getBorrowRecordId(),
                borrowRecord.getMember().getMemberId(),
                borrowRecord.getMember().getMemberName(),
                borrowRecord.getBook().getBookId(),
                borrowRecord.getBook().getBookTitle(),
                borrowRecord.getBorrowDate(),
                borrowRecord.getDueDate(),
                borrowRecord.getReturnDate()
        );
    }

    // FULL BORROW HISTORY FOR A MEMBER
    public List<BorrowRecordDTO> getBorrowHistoryByMember(Long memberId) {
        return borrowRecordRepository.findByMember_MemberId(memberId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // FULL BORROW HISTORY FOR A BOOK
    public List<BorrowRecordDTO> getBorrowHistoryByBook(Long bookId) {
        return borrowRecordRepository.findByBook_BookId(bookId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ALL CURRENTLY BORROWED BOOKS (NOT RETURNED)
    public List<BorrowRecordDTO> getActiveBorrows() {
        return borrowRecordRepository.findByReturnDateIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ALL OVERDUE BOOKS
    public List<BorrowRecordDTO> getOverdueBorrows() {
        return borrowRecordRepository.findByReturnDateIsNullAndDueDateBefore(LocalDate.now())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}