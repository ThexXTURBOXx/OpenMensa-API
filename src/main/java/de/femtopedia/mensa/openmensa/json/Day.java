package de.femtopedia.mensa.openmensa.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Day {

	private String date;
	private boolean closed;

}
