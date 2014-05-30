package com.ja.smarkdown.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SitemapTest {

    @Test
    public void testAddDocuemnt() {
        final ListingDocument doc = new ListingDocument("foo", "foo.md", "foo");
        final Sitemap sitemap = new Sitemap();
        sitemap.add(doc);
        System.out.println(sitemap);
        assertThat(sitemap.getLevel(), is(0));
        assertThat(sitemap.getPages(), is(notNullValue()));
        assertThat(sitemap.getPages().size(), is(1));
        assertThat(sitemap.getPages().contains(doc), is(true));
        assertThat(sitemap.toString(), is("- [foo](foo.html)\n"));
    }

    @Test
    public void testAddDocuemnt1Level() {
        final ListingDocument doc = new ListingDocument("foo/bar",
                "foo/bar.md", "foo/bar");
        final Sitemap sitemap = new Sitemap();
        sitemap.add(doc);
        System.out.println(sitemap);
        assertThat(sitemap.getPages(), is(notNullValue()));
        assertThat(sitemap.getPages().size(), is(0));
        assertThat(sitemap.getSections().get("Foo").getLevel(), is(1));
        assertThat(sitemap.getSections().get("Foo"), is(notNullValue()));
        assertThat(sitemap.getSections().get("Foo").getPages().contains(doc),
                is(true));
        assertThat(sitemap.toString(),
                is("Foo\n\n  - [foo/bar](foo/bar.html)\n"));
    }

    @Test
    public void testAddDocuemnt1LevelMultipleSections() {
        final Sitemap sitemap = new Sitemap();
        sitemap.add(new ListingDocument("foo/foo", "foo/foo.md", "foo/foo"));
        sitemap.add(new ListingDocument("bar/bar", "bar/bar.md", "bar/bar"));
        sitemap.add(new ListingDocument("bar/bar2", "bar/bar2.md", "bar/bar2"));
        sitemap.add(new ListingDocument("tar", "tar.md", "tar"));
        System.out.println(sitemap);
        assertThat(sitemap.getPages(), is(notNullValue()));
        assertThat(sitemap.toString(), is("- [tar](tar.html)\n\n" + "Bar\n\n"
                + "  - [bar/bar](bar/bar.html)\n"
                + "  - [bar/bar2](bar/bar2.html)\n\n" + "Foo\n\n"
                + "  - [foo/foo](foo/foo.html)\n"));
    }

    @Test
    public void testAddDocuemnt2Levels() {
        final ListingDocument doc = new ListingDocument("foo/bar/tar",
                "foo/bar/tar.md", "foo/bar/tar");
        final Sitemap sitemap = new Sitemap();
        sitemap.add(doc);
        final ListingDocument doc2 = new ListingDocument("foo/bla",
                "foo/bla.md", "foo/bla");
        sitemap.add(doc2);
        System.out.println(sitemap);
        assertThat(sitemap.getPages(), is(notNullValue()));
        assertThat(sitemap.getPages().size(), is(0));
        final Sitemap foo = sitemap.getSections().get("Foo");
        assertThat(foo, is(notNullValue()));
        assertThat(foo.getPages().size(), is(1));
        final Sitemap bar = foo.getSections().get("Bar");
        assertThat(bar, is(notNullValue()));
        assertThat(bar.getLevel(), is(2));
        assertThat(bar.getPages().contains(doc), is(true));
    }
}
