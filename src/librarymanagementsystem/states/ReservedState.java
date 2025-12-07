package librarymanagementsystem.states;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.users.User;

public class ReservedState implements BookState {
    private Book book;

    public ReservedState(Book b) {
        this.book = b;
    }

    public void borrow(User u) {
        System.out.println("Book is reserved. Try borrowing if you are the reserver when available.");
    }

    public void reserve(User u) {
        book.addReservation(u);
        System.out.println("Book reserved (additional): " + book.getTitle());
    }

    public void returned() {
        book.setState(new AvailableState(book));
    }

    public String getStatus() {
        return "Reserved";
    }
}
