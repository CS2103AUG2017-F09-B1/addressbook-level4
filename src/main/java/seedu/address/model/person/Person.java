package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import javax.print.DocFlavor;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Picture> picture;
    private ObjectProperty<UniqueTagList> tags;
    private String sortCriteria = "name";


    /**
     * Sets the field the list should be sorted by
     */
    public void setComparator(String field) {
        Set<String> validFields = new HashSet<String>(Arrays.asList(
                new String[] {"name", "phone", "email", "address"}
        ));

        if (validFields.contains(field)) {
            this.sortCriteria = field;
            System.out.println("Changing the sorting criteria for " + name + " to " + sortCriteria);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.picture = new SimpleObjectProperty<>(new Picture());
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getTags());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    @Override
    public ObjectProperty<Picture> pictureProperty() {
        return picture;
    }

    @Override
    public Picture getPicture() {
        return picture.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compareTo(Object T) {

        ReadOnlyPerson person = (ReadOnlyPerson) T;
        String firstField = this.getName().toString();
        String secondField = person.getName().toString();

        if (sortCriteria.equals("email")) {
            firstField = this.getEmail().toString();
            secondField = person.getEmail().toString();

        } else if (sortCriteria.equals("phone")) {
            firstField = this.getPhone().toString();
            secondField = person.getPhone().toString();

        } else if (sortCriteria.equals("address")) {
            firstField = this.getAddress().toString();
            secondField = person.getAddress().toString();
        } else {
            return firstField.compareTo(secondField);
        }

        // If a field is "Not Set" put the corresponding person at the end of the list.
        if (firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return 0;
        } else if (!firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return -1;
        } else if (firstField.equals("Not Set") && !secondField.equals("Not Set")) {
            return 1;
        } else {
            return firstField.compareTo(secondField);
        }

    }

}
