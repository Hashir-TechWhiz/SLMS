package librarymanagementsystem.decorators;

import librarymanagementsystem.books.BookComponent;

public class FeaturedBook extends ConcreteBookDecorator {
    public FeaturedBook(BookComponent b) {
        super(b);
    }

    public String getTitle() {
        return "[FEATURED] " + super.getTitle();
    }
}
