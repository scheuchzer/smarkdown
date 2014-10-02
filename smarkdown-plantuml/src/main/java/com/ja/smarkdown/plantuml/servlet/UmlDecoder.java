/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * Project Info:  http://plantuml.sourceforge.net
 *
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */
package com.ja.smarkdown.plantuml.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

public class UmlDecoder {

    public String decode(String source) {
        String uml;
        try {
            uml = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uml = "' invalid encoded string";
        }
        Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
        try {
            uml = transcoder.decode(uml);
        } catch (IOException ioe) {
            uml = "' unable to decode string";
        }

        return uml;
    }

}