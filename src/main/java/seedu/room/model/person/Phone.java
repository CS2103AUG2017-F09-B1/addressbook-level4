package seedu.room.model.person;

import seedu.room.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the resident book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    public static final String PHONE_NOT_SET_DEFAULT = "Not Set";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Phone(String phone) throws IllegalValueException {
        if (phone == null) {
            this.value = PHONE_NOT_SET_DEFAULT;
        } else {
            String trimmedPhone = phone.trim();
            if (!isValidPhone(trimmedPhone)) {
                throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
            }
            this.value = trimmedPhone;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX) || test.equals(PHONE_NOT_SET_DEFAULT);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
