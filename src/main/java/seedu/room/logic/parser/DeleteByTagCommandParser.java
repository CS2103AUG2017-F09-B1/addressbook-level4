package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.DeleteByTagCommand;
import seedu.room.logic.parser.exceptions.ParseException;

//@@author Haozhe321
/**
 * Parses input arguments and creates a new DeleteByTagCommand object
 */
public class DeleteByTagCommandParser implements Parser<DeleteByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByTagCommand
     * and returns an DeleteByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByTagCommand parse(String args) throws ParseException {
        try {
            return new DeleteByTagCommand(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByTagCommand.MESSAGE_USAGE));
        }
    }

}

