package ar.edu.utn.sigmaproject.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Gian Franco Zabarino
 * Date: 19/08/12
 */
@Embeddable
public class Name implements Serializable {

    private String first = "";

    private String last = "";

    public Name() {}

    public Name(String arg) {
        if (arg == null) {
            return;
        }

        // change the name from "Meadows, Rob" to "Rob Meadows"
        Pattern commaPattern = Pattern.compile("^(\\w+),\\s?");
        Matcher matcher = commaPattern.matcher(arg);
        String toParse;
        if (matcher.find()) {
            String lastName = matcher.group(1);
            String firstName = arg.replace(lastName+",", "");
            toParse = firstName + " " + lastName;
        } else {
            toParse = arg;
        }

        // clean up garbage characters from the string
        toParse = toParse.replaceAll("[^a-zA-Z0-9 ]", "");


        String[] names = toParse.trim().split(" ");

        if (names.length == 0)
            return;

        switch (names.length) {
            case 1:
                setFirst(names[0]);
                break;
            case 2:
                setFirst(names[0]);
                setLast(names[1]);
                break;
            case 3:
                setFirst(names[0] + " " + names[1]);
                setLast(names[2]);
                break;
            default:
                setFirst(StringUtils.arrayToDelimitedString(Arrays.asList(names).subList(0, names.length - 2).toArray(), " "));
                setLast(names[names.length-1]);
        }
    }

    /**
     * @return true if all name fields are blank
     */
    @Transient
    public boolean isBlank() {
        return !StringUtils.hasText(first) &&
                !StringUtils.hasText(last);
    }

    /**
     * @return true if any name field is not blank.
     */
    @Transient
    public boolean isNotBlank() {
        return !isBlank();
    }

    @Transient
    public String getFullName() {
        return (first + " " + last).trim();
    }

    @Transient
    public String getSortName() {
        return (last + ", " + first).trim();
    }

    @NotNull
    @Column(name="firstname", nullable=false)
    @NotEmpty
    public String getFirst() {
        return first;
    }
    public void setFirst(String first) {
        this.first = first.trim();
    }

    /**
     * @return true if first name is blank.
     */
    public boolean hasFirst() {
        return StringUtils.hasText(first);
    }

    @NotNull
    @Column(name="lastname", nullable=false)
    @NotEmpty
    public String getLast() {
        return last;
    }
    public void setLast(String last) {
        this.last = last.trim();
    }

    /**
     * @return true if last name is blank.
     */
    public boolean hasLast() {
        return StringUtils.hasText(last);
    }

    @Override
    public String toString() {
        return getFirst() + " " + getLast();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;

        Name name = (Name) o;

        if (first != null ? !first.equals(name.first) : name.first != null) return false;
        if (last != null ? !last.equals(name.last) : name.last != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (last != null ? last.hashCode() : 0);
        return result;
    }
}
