package librarymanagementsystem.states;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.books.BorrowRecord;
import librarymanagementsystem.core.Library;
import librarymanagementsystem.users.User;

public class AvailableState implements BookState {
    private Book book;

    public AvailableState(Book b) {
        this.book = b;
    }

    public void borrow(User u) {
        // check user's limit
        Library lib = Library.getInstance();
        if (!lib.canBorrow(u)) {
            System.out.println("User has reached borrow limit.");
            return;
        }
        // create borrow record
        BorrowRecord r = new BorrowRecord(book, u);
        book.addBorrowRecord(r);
        u.addBorrowedRecord(r);
        book.setState(new BorrowedState(book, u, r));
        System.out.println("Book borrowed: " + book.getTitle() + " by " + u.getName());
        Library.getInstance().registerBorrowAction(u, book);
    }

    public void reserve(User u) {
        book.addReservation(u);
        System.out.println("Book reserved: " + book.getTitle() + " by " + u.getName());
    }

    public void returned() {
        System.out.println("Book is already available.");
    }

    public String getStatus() {
        return "Available";
    }
}
