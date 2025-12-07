package librarymanagementsystem.books;

import java.util.ArrayList;
import java.util.List;

import librarymanagementsystem.states.AvailableState;
import librarymanagementsystem.states.BookState;
import librarymanagementsystem.users.User;

public class Book implements BookComponent {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private BookState state;

    // Optional metadata
    private String edition;
    private List<String> tags = new ArrayList<>();

    // borrow history
    private List<BorrowRecord> borrowHistory = new ArrayList<>();

    // reservation queue
    private List<User> reservationQueue = new ArrayList<>();

    // decorator indicator (wrapped decorator will hold reference if decorated)

    public Book(String bookId, String title, String author, String category, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.state = new AvailableState(this);
    }

    // getters & setters
    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String e) {
        this.edition = e;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String t) {
        tags.add(t);
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState s) {
        this.state = s;
    }

    public List<BorrowRecord> getBorrowHistory() {
        return borrowHistory;
    }

    public void addBorrowRecord(BorrowRecord r) {
        borrowHistory.add(r);
    }

    public List<User> getReservationQueue() {
        return reservationQueue;
    }

    public void addReservation(User u) {
        reservationQueue.add(u);
    }

    public User popReservation() {
        if (reservationQueue.size() == 0)
            return null;
        return reservationQueue.remove(0);
    }

    // delegate to state
    public void borrow(User u) {
        state.borrow(u);
    }

    public void reserve(User u) {
        state.reserve(u);
    }

    public void returned() {
        state.returned();
    }

    public int getTotalBorrows() {
        return borrowHistory.size();
    }

    public String toString() {
        return bookId + " - " + title + " (" + author + ") [" + state.getStatus() + "]";
    }
}