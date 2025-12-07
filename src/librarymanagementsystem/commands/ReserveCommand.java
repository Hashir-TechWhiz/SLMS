package librarymanagementsystem.commands;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.users.User;

public class ReserveCommand implements Command {
    private Book book;
    private User user;

    public ReserveCommand(Book b, User u) {
        this.book = b;
        this.user = u;
    }

    public void execute() {
        book.reserve(user);
    }
}
