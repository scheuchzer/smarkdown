package com.ja.smarkdown.load.webinf;

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
public class WebInfURLStreamHandlerTest {

	@Mock
	private ServletContext ctx;

	@InjectMocks
	private WebInfURLStreamHandler handler;

	@Test
	public void testOpenConnection() throws Exception {
		final URL url = new URL(null, "web-inf:foo.md",
				new WebInfURLStreamHandler());
		doReturn(new URL("file:///")).when(ctx).getResource(
				eq("/WEB-INF/foo.md"));
		final URLConnection con = handler.openConnection(url);
		assertNotNull(con);
	}
}
