package seedu.room.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.commands.exceptions.TagNotFoundException;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.exceptions.DuplicateEventException;
import seedu.room.model.event.exceptions.EventNotFoundException;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.tag.Tag;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;


    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyResidentBook newData);

    /**
     * Returns the ResidentBook
     */
    ReadOnlyResidentBook getResidentBook();

    /**
     * Deletes the given person.
     */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Adds the given person
     */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Delete all persons with the given tag
     */
    void deleteByTag(Tag tag) throws IllegalValueException, CommandException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Remove tag inside the Resident Book
     */
    void removeTag(Tag t) throws TagNotFoundException;
    /**
     * Sorts the Resident Book by name, phone, room or phone depending on the sortCriteria
     */
    void sortBy(String sortCriteria) throws AlreadySortedException;

    /**
     * Updates the highlight status of persons with the specified tag
     *
     * @throws TagNotFoundException if no specified tag exists
     */
    void updateHighlightStatus(String highlightTag) throws TagNotFoundException;

    /**
     * Swaps two residents' rooms
     */
    void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2) throws PersonNotFoundException;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyEvent> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * Returns the ResidentBook
     */
    ReadOnlyEventBook getEventBook();

    /**
     * Deletes the given person.
     */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;

    /**
     * Adds the given person
     */
    void addEvent(ReadOnlyEvent person) throws DuplicateEventException;

    /**
     * Replaces the given person {@code target} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);

    /**
     * Sorts the Event Book by name, phone, room or phone depending on the sortCriteria
     */
    void sortEventsBy(String sortCriteria) throws AlreadySortedException;

}
