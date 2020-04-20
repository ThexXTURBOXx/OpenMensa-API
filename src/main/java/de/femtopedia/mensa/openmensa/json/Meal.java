package de.femtopedia.mensa.openmensa.json;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * A class representing a meal.
 */
@SuppressWarnings({"MemberName", "JavadocVariable"})
@RequiredArgsConstructor
@Getter
@ToString
public class Meal {

    private final int id;
    private final String name;
    private final String category;
    private final Prices prices;
    private final List<String> notes;

}
