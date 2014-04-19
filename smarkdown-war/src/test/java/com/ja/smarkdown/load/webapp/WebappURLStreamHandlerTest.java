package com.ja.smarkdown.load.webapp;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WebappURLStreamHandlerTest {

	@Mock
	private ServletContext ctx;

	@InjectMocks
	private WebappURLStreamHandler handler;

	@Test
	public void testOpenConnection() throws Exception {
		final URL url = new URL(null, "webapp:foo.md",
				new WebappURLStreamHandler());
		doReturn(new URL("file:///")).when(ctx).getResource(eq("/foo.md"));
		final URLConnection con = handler.openConnection(url);
		assertNotNull(con);
	}
}
