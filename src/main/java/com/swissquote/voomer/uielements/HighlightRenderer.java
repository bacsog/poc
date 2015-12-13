package com.swissquote.voomer.uielements;

import com.vaadin.ui.renderers.HtmlRenderer;

import elemental.json.JsonValue;

public class HighlightRenderer extends HtmlRenderer {

	private static final long serialVersionUID = 1L;

	private String toBeHighligted;

	public HighlightRenderer(String toBeHighligted) {
		this.toBeHighligted = toBeHighligted;
	}

	@Override
	public JsonValue encode(String value) {
		if (value.contains(toBeHighligted)) {
			int index = value.indexOf(toBeHighligted);

			if (index == 0) {
				value = String.format("<span style=\"pointer-events: none;\"><b>%s</b>%s</span>",
						value.substring(0, index), value.substring(index));
			} else {
				value = String.format("<span style=\"pointer-events: none;\">%s<b>%s</b></span>",
						value.substring(0, index), value.substring(index));
			}
		}
		return super.encode(value);
	}
}
