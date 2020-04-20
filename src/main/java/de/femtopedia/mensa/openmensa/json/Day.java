package de.femtopedia.mensa.openmensa.json;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * A class representing a Pagination.
 */
@SuppressWarnings({"MemberName", "JavadocVariable"})
@RequiredArgsConstructor
@Getter
@ToString
public class Day {

    private final String date;
    private final boolean closed;

}
