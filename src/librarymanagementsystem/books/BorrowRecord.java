package librarymanagementsystem.books;

import java.util.Date;
import librarymanagementsystem.users.User;
import java.util.Calendar;

public class BorrowRecord {
    private Book book;
    private User user;
    private Date borrowedDate;
    private Date dueDate;
    private Date returnedDate;

    public BorrowRecord(Book book, User user) {
        this.book = book;
        this.user = user;
        this.borrowedDate = new Date();
        int days = user.getMembershipType().equals("Faculty") ? 30
                : user.getMembershipType().equals("Guest") ? 7
                        : 14;
        Calendar c = Calendar.getInstance();
        c.setTime(borrowedDate);
        c.add(Calendar.DATE, days);
        this.dueDate = c.getTime();
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void markReturned() {
        this.returnedDate = new Date();
    }

    public long daysLate() {
        if (returnedDate == null) {
            Date now = new Date();
            long diff = now.getTime() - dueDate.getTime();
            return diff > 0 ? diff / (1000 * 60 * 60 * 24) : 0;
        } else {
            long diff = returnedDate.getTime() - dueDate.getTime();
            return diff > 0 ? diff / (1000 * 60 * 60 * 24) : 0;
        }
    }
}