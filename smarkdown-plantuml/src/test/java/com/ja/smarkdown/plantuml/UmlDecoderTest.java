package com.ja.smarkdown.plantuml;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.ja.smarkdown.plantuml.servlet.UmlDecoder;

public class UmlDecoderTest {

	@Test
	public void testDecode() {
		String actual = new UmlDecoder().decode("SyfFKj2rKt3CoKnELR1Io4ZDoSa70000");
		assertThat(actual, is("@startuml\nBob -> Alice : hello\n@enduml"));
	}
}
