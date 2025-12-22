package librarymanagementsystem.states;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.books.BorrowRecord;
import librarymanagementsystem.core.Library;
import librarymanagementsystem.users.User;

public class BorrowedState implements BookState {
    private Book book;
    private User borrower;
    private BorrowRecord record;

    public BorrowedState(Book b, User u, BorrowRecord r) {
        this.book = b;
        this.borrower = u;
        this.record = r;
    }

    public void borrow(User u) {
        System.out.println("Cannot borrow - book is already borrowed.");
    }

    public void reserve(User u) {
        book.addReservation(u);
        System.out.println("Book reserved (queued): " + book.getTitle() + " by " + u.getName());
    }

    public void returned() {
        record.markReturned();
        System.out.println("Book returned: " + book.getTitle() + " by " + borrower.getName());
        User next = book.popReservation();
        if (next != null) {
            Library.getInstance().notifyReservationAvailable(next, book);
            book.setState(new AvailableState(book));
        } else {
            book.setState(new AvailableState(book));
        }
    }

    public String getStatus() {
        return "Borrowed";
    }
}
