# Haozhe321
###### /java/seedu/room/logic/parser/DeleteByTagCommandParserTest.java
``` java
public class DeleteByTagCommandParserTest {
    private DeleteByTagCommandParser parser = new DeleteByTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByTagCommand() throws IllegalValueException {
        assertParseSuccess(parser, "friends", new DeleteByTagCommand("friends"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "friends forever", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteByTagCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/room/logic/commands/DeleteByTagCommandTest.java
``` java
public class DeleteByTagCommandTest {
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());


    @Test
    public void execute_validTag_success() throws IllegalValueException, CommandException {
        String tagToDelete = "friends";
        Tag tag = new Tag(tagToDelete);
        DeleteByTagCommand deleteByTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(DeleteByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS, tag);
        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());

        expectedModel.deleteByTag(tag);

        assertCommandSuccess(deleteByTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() throws IllegalValueException, CommandException {
        String tagToDelete = "thisTagDoesNotExist";
        Tag tag = new Tag(tagToDelete);
        DeleteByTagCommand deleteByTagCommand = prepareCommand(tagToDelete);

        assertCommandFailure(deleteByTagCommand, model, Messages.MESSAGE_INVALID_TAG_FOUND);
    }

    @Test
    public void equals() throws IllegalValueException {
        DeleteByTagCommand deleteByTagFirstCommand = new DeleteByTagCommand("friends");
        DeleteByTagCommand deleteByTagSecondCommand = new DeleteByTagCommand("friends");

        // same object -> returns true
        assertTrue(deleteByTagFirstCommand.equals(deleteByTagSecondCommand));

        // different types -> returns false
        assertFalse(deleteByTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteByTagFirstCommand.equals(null));

    }

    /**
     * Returns a {@code DeleteByTagCommand} with the parameter {@code index}.
     */
    private DeleteByTagCommand prepareCommand(String tagName) throws IllegalValueException {
        DeleteByTagCommand deleteByTagCommand = new DeleteByTagCommand(tagName);
        deleteByTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByTagCommand;
    }
}
```
###### /java/seedu/room/logic/commands/AddCommandTest.java
``` java
        @Override
        public void deleteByTag(Tag tag) throws IllegalValueException, CommandException {
            fail("this method should not be called.");
        }
```
###### /java/seedu/room/model/person/TimestampTest.java
``` java
public class TimestampTest {

    @Test
    public void isValidTimestamp() {
        // invalid Timestamp
        assertFalse(Timestamp.isValidTimestamp(-1));
        assertFalse(Timestamp.isValidTimestamp(-2));
        assertFalse(Timestamp.isValidTimestamp(-10000));

        //valid Timestamp
        assertTrue(Timestamp.isValidTimestamp(1));
        assertTrue(Timestamp.isValidTimestamp(1000));
        assertTrue(Timestamp.isValidTimestamp(5));

    }
}
```
###### /java/seedu/room/model/ModelManagerTest.java
``` java
    @Test
    public void deleteTemporaryTest() throws IllegalValueException, PersonNotFoundException {
        ResidentBook residentBook = new ResidentBookBuilder().withPerson(TEMPORARY_JOE).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(residentBook, userPrefs);

        //modelManager has one temporary person inside -> returns false
        assertFalse(modelManager.equals(null));

        modelManager.deleteTemporary(residentBook);
        //added temporary has argument of 0, so it stays permanently -> returns false
        assertFalse(modelManager.getResidentBook().getPersonList().size() == 0);

    }
```
