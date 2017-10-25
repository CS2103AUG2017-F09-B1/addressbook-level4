package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.TagNotFoundException;

/**
 * Adds a person to the address book.
 */
public class HighlightCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "highlight";
    public static final String COMMAND_ALIAS = "hl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Highlights names with the specified tag. "
            + "Parameters: " + "tag."
            + "Example: " + COMMAND_WORD + " "
            + "Unregistered";

    public static final String MESSAGE_SUCCESS = "Highlighted persons with tag: ";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag not found: ";

    private final String highlightTag;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public HighlightCommand(String highlightTag) {
        this.highlightTag = highlightTag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updateHighlightStatus(highlightTag);
            return new CommandResult(MESSAGE_SUCCESS + highlightTag);
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND + highlightTag);
        }
    }

//    @Override
//    public CommandResult executeUndoableCommand() throws CommandException {
//        requireNonNull(model);
//        try {
//            model.highlight(highlightTag);
//            return new CommandResult(String.format(MESSAGE_SUCCESS, sortCriteria));
//        } catch (AlreadySortedException e) {
//            String failureMessage = String.format(MESSAGE_FAILURE, sortCriteria);
//            throw new CommandException(failureMessage);
//        }
//    }

//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof SortCommand // instanceof handles nulls
//                && sortCriteria.equals(((SortCommand) other).sortCriteria));
//    }

}
