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

    // Borrow history
    private List<BorrowRecord> borrowHistory = new ArrayList<>();

    // Reservation queue
    private List<User> reservationQueue = new ArrayList<>();

    public Book(String bookId, String title, String author, String category, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.state = new AvailableState(this);
    }

    // Getters
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

    public List<String> getTags() {
        return tags;
    }

    public BookState getState() {
        return state;
    }

    public List<BorrowRecord> getBorrowHistory() {
        return borrowHistory;
    }

    public List<User> getReservationQueue() {
        return reservationQueue;
    }

    // Setters (REQUIRED for Update Book)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    // Other helpers
    public void addTag(String tag) {
        tags.add(tag);
    }

    public void addBorrowRecord(BorrowRecord record) {
        borrowHistory.add(record);
    }

    public void addReservation(User user) {
        reservationQueue.add(user);
    }

    public User popReservation() {
        if (reservationQueue.isEmpty()) {
            return null;
        }
        return reservationQueue.remove(0);
    }

    // State delegation
    public void borrow(User user) {
        state.borrow(user);
    }

    public void reserve(User user) {
        state.reserve(user);
    }

    public void returned() {
        state.returned();
    }

    // Reporting helpers
    public int getTotalBorrows() {
        return borrowHistory.size();
    }

    // Display
    @Override
    public String toString() {
        return bookId + " - " + title + " (" + author + ") [" + state.getStatus() + "]";
    }
}
