package librarymanagementsystem.core;

import java.util.ArrayList;
import java.util.List;

import librarymanagementsystem.books.Book;
import librarymanagementsystem.books.BorrowRecord;
import librarymanagementsystem.commands.Command;
import librarymanagementsystem.commands.CommandInvoker;
import librarymanagementsystem.observers.LibraryNotifier;
import librarymanagementsystem.users.User;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private CommandInvoker invoker = new CommandInvoker();

    private static Library instance = new Library();

    private Library() {
    }

    public static Library getInstance() {
        return instance;
    }

    public void addBook(Book b) {
        books.add(b);
    }

    public void removeBook(Book b) {
        books.remove(b);
    }

    public void updateBook(
            String bookId,
            String newTitle,
            String newAuthor,
            String newCategory,
            String newIsbn) {
        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (newTitle != null && !newTitle.trim().isEmpty()) {
            book.setTitle(newTitle);
        }
        if (newAuthor != null && !newAuthor.trim().isEmpty()) {
            book.setAuthor(newAuthor);
        }
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            book.setCategory(newCategory);
        }
        if (newIsbn != null && !newIsbn.trim().isEmpty()) {
            book.setIsbn(newIsbn);
        }

        System.out.println("Book updated successfully.");
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addUser(User u) {
        users.add(u);
        LibraryNotifier.getInstance().registerObserver(u);
    }

    public void removeUser(User u) {
        users.remove(u);
        LibraryNotifier.getInstance().removeObserver(u);
    }

    public List<User> getUsers() {
        return users;
    }

    public Book findBookById(String id) {
        for (Book b : books)
            if (b.getBookId().equals(id))
                return b;
        return null;
    }

    public User findUserById(String id) {
        for (User u : users)
            if (u.getUserId().equals(id))
                return u;
        return null;
    }

    public boolean canBorrow(User u) {
        return u.getBorrowedRecords().size() < u.getBorrowLimit();
    }

    public void executeCommand(Command c) {
        invoker.execute(c);
    }

    // notifications and events
    public void registerBorrowAction(User u, Book b) {
        String msg = u.getName() + " borrowed " + b.getTitle();
        LibraryNotifier.getInstance().notifyObservers(msg);
    }

    public void notifyReservationAvailable(User u, Book b) {
        String msg = "Reserved book available: " + b.getTitle() + ". Please borrow soon.";
        u.update(msg);
    }

    // Reports
    public Book mostBorrowedBook() {
        Book best = null;
        int max = -1;
        for (Book b : books) {
            if (b.getTotalBorrows() > max) {
                max = b.getTotalBorrows();
                best = b;
            }
        }
        return best;
    }

    public List<User> activeBorrowers() {
        List<User> list = new ArrayList<>();
        for (User u : users)
            if (u.getBorrowedRecords().size() > 0)
                list.add(u);
        return list;
    }

    public List<BorrowRecord> overdueBooks() {
        List<BorrowRecord> res = new ArrayList<>();
        for (User u : users) {
            for (BorrowRecord r : u.getBorrowedRecords()) {
                if (r.getReturnedDate() == null && r.daysLate() > 0)
                    res.add(r);
            }
        }
        return res;
    }
}