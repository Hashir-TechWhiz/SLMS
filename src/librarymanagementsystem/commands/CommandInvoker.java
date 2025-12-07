package librarymanagementsystem.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {
    private List<Command> history = new ArrayList<>();

    public void execute(Command c) {
        c.execute();
        history.add(c);
    }
}
