package com.project.library.service;

import com.project.library.dto.BorrowRecordDTO;
import com.project.library.entity.Book;
import com.project.library.entity.BorrowRecord;
import com.project.library.entity.Member;
import com.project.library.exception.BadRequestException;
import com.project.library.exception.ResourceNotFoundException;
import com.project.library.repository.BookRepository;
import com.project.library.repository.BorrowRecordRepository;
import com.project.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    private static final double FINE_PER_DAY = 5.0;

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
                        new ResourceNotFoundException("Member not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("Book not available");
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
                        new ResourceNotFoundException("Borrow Record Not Found"));

        if (borrowRecord.getReturnDate() != null) {
            throw new BadRequestException("Book Already Returned");
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

    // CALCULATE FINE FOR A BORROW RECORD
    private Double calculateFine(BorrowRecord borrowRecord) {
        LocalDate dueDate = borrowRecord.getDueDate();
        LocalDate returnDate = borrowRecord.getReturnDate();

        // IF NOT YET RETURNED, COMPARE DUE DATE TO TODAY
        LocalDate compareDate = (returnDate != null) ? returnDate : LocalDate.now();

        if (compareDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, compareDate);
            return daysLate * FINE_PER_DAY;
        }

        return 0.0;
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
                borrowRecord.getReturnDate(),
                calculateFine(borrowRecord)
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