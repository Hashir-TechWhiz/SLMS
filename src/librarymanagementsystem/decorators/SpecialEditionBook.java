package librarymanagementsystem.decorators;

import librarymanagementsystem.books.BookComponent;

public class SpecialEditionBook extends ConcreteBookDecorator {
    public SpecialEditionBook(BookComponent b) {
        super(b);
    }

    public String getTitle() {
        return "[SPECIAL] " + super.getTitle();
    }
}
