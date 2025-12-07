package librarymanagementsystem.commands;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.users.User;

public class CancelReservationCommand implements Command {
    private Book book;
    private User user;

    public CancelReservationCommand(Book b, User u) {
        this.book = b;
        this.user = u;
    }

    public void execute() {
        // remove from reservation list
        book.getReservationQueue().remove(user);
        System.out.println("Reservation cancelled for " + user.getName());
    }
}
