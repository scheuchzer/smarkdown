package com.ja.smarkdown.plantuml.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

public class UmlEncoder {

	public String encode(String source) {
		String text = null;
		Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
		try {
			text = transcoder.encode(source);
		} catch (IOException ioe) {
			text = "' unable to encode string";
		}

		try {
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			text = "' invalid encoded string";
		}
		return text;
	}
}
