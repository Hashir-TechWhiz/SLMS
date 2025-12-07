package librarymanagementsystem.commands;

import librarymanagementsystem.books.Book;

public class ReturnCommand implements Command {
    private Book book;

    public ReturnCommand(Book b) {
        this.book = b;
    }

    public void execute() {
        book.returned();
    }
}