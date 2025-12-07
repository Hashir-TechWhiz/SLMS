package librarymanagementsystem.states;

import librarymanagementsystem.users.User;

public interface BookState {
    void borrow(User u);

    void reserve(User u);

    void returned();

    String getStatus();
}
