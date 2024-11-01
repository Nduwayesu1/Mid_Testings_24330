package model1;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrower")
public class Borrower {
    @EmbeddedId
    private BorrowerId id;

    @ManyToOne
    @MapsId("bookId")  // Hano ira mapping  bookId-> composite pk yo muri book
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    @ManyToOne
    @MapsId("readerId")
    @JoinColumn(name = "reader_id", referencedColumnName = "user_id")
    private User reader;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "fine", nullable = false)
    private Integer fine;


    //the resource:  https://docs.redhat.com/en/documentation/jboss_enterprise_application_platform_common_criteria_certification/5/html/hibernate_annotations_reference_guide/ch02s02s06#idm140546551831712


    public Borrower() {
    }

    public Borrower(BorrowerId id, Book book, User reader, LocalDate dueDate, Integer fine) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.dueDate = dueDate;
        this.fine = fine;
    }

    public BorrowerId getId() {
        return id;
    }

    public void setId(BorrowerId id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }
}
