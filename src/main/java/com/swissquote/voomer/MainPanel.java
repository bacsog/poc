package com.swissquote.voomer;

import com.swissquote.voomer.bucketinventory.BucketInventoryPanel;
import com.swissquote.voomer.instrumentinventory.InstrumentInventoryPanel;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private InstrumentInventoryPanel inventoryPanel;
	private TabSheet tabSheet;

	public MainPanel() {
		tabSheet = new TabSheet();
		tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		buildTabs();

		Layout layout = new VerticalLayout();
		setContent(layout);
		layout.addComponent(tabSheet);
	}

	private void buildTabs() {
		VerticalLayout bucketLayout = new VerticalLayout(new BucketInventoryPanel());
		bucketLayout.setMargin(true);
		tabSheet.addTab(bucketLayout, "Bucket Inventory");

		inventoryPanel = new InstrumentInventoryPanel();
		VerticalLayout inventoryLayout = new VerticalLayout(getInventoryPanel());
		inventoryLayout.setMargin(true);
		tabSheet.addTab(inventoryLayout, "Instrument Inventory");
		tabSheet.setSelectedTab(inventoryLayout);
	}

	public InstrumentInventoryPanel getInventoryPanel() {
		return inventoryPanel;
	}

}