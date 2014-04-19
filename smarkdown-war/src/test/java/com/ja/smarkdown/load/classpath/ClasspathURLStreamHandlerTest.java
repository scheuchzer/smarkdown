package com.ja.smarkdown.load.classpath;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.load.webapp.WebappURLStreamHandler;

@RunWith(MockitoJUnitRunner.class)
public class ClasspathURLStreamHandlerTest {

	@Mock
	private ClassLoader cl;

	@InjectMocks
	private ClasspathURLStreamHandler handler;

	@Test
	public void testOpenConnection() throws Exception {
		final URL url = new URL(null, "classpath:foo.md",
				new WebappURLStreamHandler());
		doReturn(new URL("file:///")).when(cl).getResource(eq("foo.md"));
		final URLConnection con = handler.openConnection(url);
		assertNotNull(con);
	}
}
