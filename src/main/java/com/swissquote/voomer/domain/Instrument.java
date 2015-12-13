package com.swissquote.voomer.domain;

//basic pojo
public class Instrument {

	private static final String SEPARATOR = "/";

	private static int FUNNY_STATIC_INDEX = 0;

	private int id = (FUNNY_STATIC_INDEX++);

	private String base;

	private String term;

	private Double exposure;

	private Double margin;

	private String name;

	public static class InstrumentBuilder {

		private Instrument i = new Instrument();

		public InstrumentBuilder withBase(String base) {
			i.setBase(base);
			return this;
		}

		public InstrumentBuilder withTerm(String term) {
			i.setTerm(term);
			return this;
		}

		public InstrumentBuilder withExposure(Double exposure) {
			i.setExposure(exposure);
			return this;
		}

		public InstrumentBuilder withMargin(Double margin) {
			i.setMargin(margin);
			return this;
		}

		public Instrument build() {
			return i;
		}
	}

	public String getName() {
		return base + SEPARATOR + term;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Double getExposure() {
		return exposure;
	}

	public void setExposure(Double exposure) {
		this.exposure = exposure;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	@Override
	public String toString() {
		return "Instrument [id=" + id + ", base=" + base + ", term=" + term + ", exposure=" + exposure + ", margin="
				+ margin + ", name=" + name + "]";
	}

}
