package de.femtopedia.mensa.openmensa.json;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * A class representing prices for a {@link Meal}.
 */
@SuppressWarnings({"MemberName", "JavadocVariable"})
@RequiredArgsConstructor
@Getter
@ToString
public class Prices {

    private final float students;
    private final float employees;
    private final float pupils;
    private final float others;

}
