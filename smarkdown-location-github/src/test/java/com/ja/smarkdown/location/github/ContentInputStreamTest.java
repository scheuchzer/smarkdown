package com.ja.smarkdown.location.github;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ContentInputStreamTest {

	private String content = "{"
			+ "\"sha\":\"12e5401dedbafac40f093eab862a496856752ba8\","
			+ "\"size\":1621471,"
			+ "\"url\":\"https://blabla/adf301dedbafac40f093eab862a496856752ba8\","
			+ "\"content\":\"test\\ntest\"," + "\"encoding\":\"base64\"" + "}";

	@Test
	public void testRead() throws IOException {
		ContentInputStream is = new ContentInputStream(
				new ByteArrayInputStream(content.getBytes()));
		is.init();
		String actual = IOUtils.toString(is);
		assertThat(actual, is("test\ntest"));
	}

}
