package librarymanagementsystem.commands;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.users.User;

public class BorrowCommand implements Command {
    private Book book;
    private User user;

    public BorrowCommand(Book b, User u) {
        this.book = b;
        this.user = u;
    }

    public void execute() {
        book.borrow(user);
    }
}
