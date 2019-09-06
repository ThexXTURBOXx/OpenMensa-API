package de.femtopedia.mensa.openmensa.json;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Meal {

	private int id;
	private String name;
	private String category;
	private Prices prices;
	private List<String> notes;

}
