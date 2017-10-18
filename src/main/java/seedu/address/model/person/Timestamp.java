package seedu.address.model.person;

import java.time.LocalDateTime;

/**
 Create a timestamp in each person to record the time created and time that this temporary person will expire
 */
public class Timestamp {

    LocalDateTime creationTime = null;
    LocalDateTime expiryTime = null; //after construction, a null expiryTime means this person will not expire

    public final long value;
    public Timestamp(long day) {
        creationTime = LocalDateTime.now();
        if (day > 0) {
            expiryTime = creationTime.plusDays(day);
        }
        value = day;
    }

    public LocalDateTime getCreationTime(){
        return creationTime;
    }


    /**
     * following method returns null if this person does not expiry
     * @return time of expiry
     */
    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

}
