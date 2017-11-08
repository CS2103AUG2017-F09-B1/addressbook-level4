package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.room.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.Email;
import seedu.room.model.person.Name;
import seedu.room.model.person.Person;
import seedu.room.model.person.Phone;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.Room;
import seedu.room.model.person.Timestamp;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.tag.Tag;

public class AddImageCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addImage";
    public static final String COMMAND_ALIAS = "ai";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an image to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing Image will be replaced by the new image.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[ Image Url ]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "/Downloads/placeholder_image";

    public static final String MESSAGE_ADD_IMAGE_SUCCESS = "Successfully changed image for Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the resident book.";

    private final Index index;
    private final String newImageURL;

    /**
     *
     * @param index of the person in the list whose image is to be updated
     * @param newImageURL url to the new replacing image
     */
    public AddImageCommand(Index index, String newImageURL) {
        requireNonNull(index);

        this.index = index;
        this.newImageURL = newImageURL;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!(new File(newImageURL).exists())) {
            throw new CommandException(Messages.MESSAGE_INVALID_IMAGE_URL);
        }

        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        Person editedPerson = editPersonImage(person);
        createPersonImage(editedPerson);

        try {
            model.updatePerson(person, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_IMAGE_SUCCESS, editedPerson.getName()));
    }

    public Person editPersonImage(ReadOnlyPerson person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Room room = person.getRoom();
        Timestamp timestamp = person.getTimestamp();
        Set<Tag> tags = person.getTags();

        Person editedPerson =  new Person(name, phone, email, room, timestamp, tags);
        if (checkJarResourcePath(person)) editedPerson.getPicture().setJarResourcePath();
        editedPerson.getPicture().setPictureUrl(name.toString() + phone.toString() + ".jpg");
        return editedPerson;
    }

    public boolean checkJarResourcePath(ReadOnlyPerson person) {
        File picture = new File(person.getPicture().getPictureUrl());
        return (picture.exists()) ? false : true;
    }

    public void createPersonImage(ReadOnlyPerson person) {
        File picFile = new File(newImageURL);
        try {
            if (person.getPicture().checkJarResourcePath()) {
                ImageIO.write(ImageIO.read(picFile), "jpg", new File(person.getPicture().getJarPictureUrl()));
            } else {
                ImageIO.write(ImageIO.read(picFile), "jpg", new File(person.getPicture().getPictureUrl()));
            }
        } catch (Exception e) {
            System.out.println("Cannot create Person Image");
        }
    }
}
