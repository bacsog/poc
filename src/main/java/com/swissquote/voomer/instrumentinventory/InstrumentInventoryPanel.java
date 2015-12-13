package com.swissquote.voomer.instrumentinventory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.vaadin.gridutil.cell.GridCellFilter;

import com.swissquote.App;
import com.swissquote.voomer.domain.Instrument;
import com.swissquote.voomer.uielements.HighlightRenderer;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterCell;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import com.vaadin.ui.renderers.TextRenderer;

import utils.DemoUtils;

public class InstrumentInventoryPanel extends Panel implements Component {

	private static final long serialVersionUID = 1L;

	private static final String NOSTRO = "CHF";

	private static final String MARGIN = "margin";

	private static final String EXPOSURE = "exposure";

	private static final String BASE = "base";

	private static final String NAME = "name";

	private static final String ID = "id";

	private Grid grid;
	private GridCellFilter filter;

	private BeanItemContainer<Instrument> container;

	private List<Instrument> currentData;

	private FooterRow grandTotalRow;

	private boolean receivingUpdates = false;

	public InstrumentInventoryPanel() {
		grid = new Grid();
		grid.setSizeFull();

		grid.addColumn(ID, Integer.class).setRenderer(new NumberRenderer("%02d")).setHeaderCaption("##")
				.setExpandRatio(0).setWidth(50);
		grid.addColumn(NAME, String.class).setRenderer(new HighlightRenderer(NOSTRO)).setExpandRatio(2);
		grid.addColumn(BASE, String.class).setRenderer(new HighlightRenderer(NOSTRO)).setExpandRatio(2);
		grid.addColumn(EXPOSURE, Double.class).setRenderer(new ProgressBarRenderer()).setExpandRatio(2);
		grid.addColumn(MARGIN, Double.class).setRenderer(new TextRenderer()).setExpandRatio(4);

		container = new BeanItemContainer<Instrument>(Instrument.class, getRandomInstruments());
		grid.setContainerDataSource(container);

		Layout layout = new VerticalLayout();
		layout.addComponent(prepareLiveButton());
		layout.addComponent(grid);
		setContent(layout);

		addFilters();
		initFooterRow();
	}

	private Component prepareLiveButton() {
		Button button = new Button("Activate Live feed");
		button.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (receivingUpdates) {
					receivingUpdates = false;
					button.setCaption("Activate Live feed");
				} else {
					receivingUpdates = true;
					button.setCaption("Stop Live feed");
				}
			}
		});
		return button;
	}

	private List<Instrument> getRandomInstruments() {
		List<Instrument> list = new LinkedList<Instrument>();
		for (int i = 0; i < App.EXAMPLE_DATA_SET_SIZE; ++i) {
			list.add(DemoUtils.randomInstrument());
		}
		currentData = list;
		return list;
	}

	private void addFilters() {
		filter = new GridCellFilter(grid);
		filter.setNumberFilter(MARGIN, "minimum", "maximum");
		filter.setTextFilter(NAME, true, false);
	}

	@SuppressWarnings("unchecked")
	public void updateInstrument(Object update) {
		if (!receivingUpdates) {
			return;
		}

		if (update instanceof Instrument) {
			updateByContainerItem((Instrument) update);
		} else if (update instanceof List) {
			updateByContainerItem((List<Instrument>) update);
		}
	}

	@SuppressWarnings("unchecked") // property checks disabled
	private void updateByContainerItem(Instrument updated) {
		System.out.println("Received: " + updated.toString());

		BeanItem<Instrument> item = container
				.getItem(container.getIdByIndex(new Random().nextInt(container.size() - 1)));
		item.getItemProperty(MARGIN).setValue(updated.getMargin());
		item.getItemProperty(EXPOSURE).setValue(updated.getExposure());

		grid.markAsDirty();
	}

	@SuppressWarnings("unchecked") // property checks disabled
	private void updateByContainerItem(List<Instrument> updates) {
		int i = 0;
		for (Instrument updated : updates) {
			System.out.println("Received: " + updated.toString());

			try {
				BeanItem<Instrument> item = container.getItem(container.getIdByIndex(i++));
				item.getItemProperty(MARGIN).setValue(updated.getMargin());
				item.getItemProperty(EXPOSURE).setValue(updated.getExposure());
			} catch (Exception e) {
				// element might not be visible. Skipping then.
			}
		}
		calculateTotal();
		grid.markAsDirty();
	}

	private void initFooterRow() {
		grandTotalRow = grid.appendFooterRow();
		FooterCell cell = grandTotalRow.join(ID, NAME);
		cell.setHtml("Total:");
		grandTotalRow.getCell(MARGIN)
				.setHtml("<b>" + currentData.stream().mapToDouble(Instrument::getMargin).sum() + "</b>");

		container.addItemSetChangeListener(new ItemSetChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void containerItemSetChange(final ItemSetChangeEvent event) {
				grandTotalRow.getCell(MARGIN)
						.setHtml("<b>" + currentData.stream().mapToDouble(Instrument::getMargin).sum() + "</b>");
			}
		});
	}

	private void calculateTotal() {
		grandTotalRow.getCell(MARGIN)
				.setHtml("<b>" + currentData.stream().mapToDouble(Instrument::getMargin).sum() + "</b>");
	}
}