package com.ja.smarkdown.plantuml;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ja.smarkdown.plantuml.servlet.UmlEncoder;

public class UmlEncoderTest {

	@Test
	public void testEncode() {
		String actual = new UmlEncoder().encode("Bob -> Alice : hello");
		assertThat(actual, is("SyfFKj2rKt3CoKnELR1Io4ZDoSa70000"));
	}
}
