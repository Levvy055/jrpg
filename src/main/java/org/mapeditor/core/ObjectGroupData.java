/*-
 * #%L
 * libtiled
 * %%
 * Copyright (C) 2004 - 2019 Thorbjørn Lindeijer <thorbjorn@lindeijer.nl>
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.13 at 11:56:22 PM GMT 
//

package org.mapeditor.core;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The object group is in fact a map layer, and is hence called<br>
 * "object layer" in Tiled Qt.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectGroup", propOrder = { "objects" })

public class ObjectGroupData extends MapLayer {

	/**
	 * 
	 */
	@XmlElement(name = "object", required = true, type = MapObject.class)
	protected List<MapObject> objects;
	/**
	 * The color used to display the objects in this group.
	 * 
	 */
	@XmlAttribute(name = "color")
	protected String color;
	/**
	 * Whether the objects are drawn according to the order<br>
	 * of appearance ("index") or sorted by their<br>
	 * y-coordinate ("topdown"). Defaults to "topdown".
	 * 
	 */
	@XmlAttribute(name = "draworder")
	protected String draworder;

	/**
	 * 
	 */
	public List<MapObject> getObjects() {
		if (objects == null) {
			objects = new ArrayList<MapObject>();
		}
		return this.objects;
	}

	/**
	 * The color used to display the objects in this group.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getColor() {
		return color;
	}

	/**
	 * The color used to display the objects in this group.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setColor(String value) {
		this.color = value;
	}

	/**
	 * Whether the objects are drawn according to the order<br>
	 * of appearance ("index") or sorted by their<br>
	 * y-coordinate ("topdown"). Defaults to "topdown".
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDraworder() {
		return draworder;
	}

	/**
	 * Whether the objects are drawn according to the order<br>
	 * of appearance ("index") or sorted by their<br>
	 * y-coordinate ("topdown"). Defaults to "topdown".
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDraworder(String value) {
		this.draworder = value;
	}
}
