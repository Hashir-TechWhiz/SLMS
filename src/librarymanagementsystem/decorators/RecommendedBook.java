package librarymanagementsystem.decorators;

import librarymanagementsystem.books.BookComponent;

public class RecommendedBook extends ConcreteBookDecorator {
    public RecommendedBook(BookComponent b) {
        super(b);
    }

    public String getTitle() {
        return "[RECOMMENDED] " + super.getTitle();
    }
}
