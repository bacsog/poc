package com.swissquote.voomer.bucketinventory;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import com.vaadin.event.Action;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

//a bit of a copy paste code thrown in from a demo just to see how the code looks like
public class BucketInventoryPanel extends Panel implements Component {

	private static final long serialVersionUID = 1L;

	private static final Object NAME_PROPERTY = "name";
	private static final Object HOURS_PROPERTY = "hours";
	private static final Object MODIFIED_PROPERTY = "modified";
	protected static final Action ADD_ITEM_ACTION = new Action("add_item");
	protected static final Action ADD_CATEGORY_ACTION = new Action("add_category");
	protected static final Action REMOVE_ITEM_ACTION = new Action("remove_item");
	private TreeTable table;

	public BucketInventoryPanel() {
		table = new TreeTable();
		table.setSizeFull();
		table.setSelectable(true);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);

		table.addContainerProperty(NAME_PROPERTY, String.class, "");
		table.addContainerProperty(HOURS_PROPERTY, Integer.class, 0);
		table.addContainerProperty(MODIFIED_PROPERTY, Date.class, new Date());

		populateWithRandomHierarchicalData(table);

		table.addActionHandler(new Action.Handler() {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(final Action action, final Object sender, final Object target) {
				if (action == ADD_ITEM_ACTION) {
					// Create new item
					final Object item = table.addItem(new Object[] { "New Item", 0, new Date() }, null);
					table.setChildrenAllowed(item, false);
					table.setParent(item, target);
				} else if (action == ADD_CATEGORY_ACTION) {
					final Object item = table.addItem(new Object[] { "New Category", 0, new Date() }, null);
					table.setParent(item, target);
				} else if (action == REMOVE_ITEM_ACTION) {
					table.removeItem(target);
				}
			}

			@Override
			public Action[] getActions(final Object target, final Object sender) {

				if (target == null) {
					// Context menu in an empty space -> add a new main category
					return new Action[] { ADD_CATEGORY_ACTION };

				} else if (table.areChildrenAllowed(target)) {
					// Context menu for a category
					return new Action[] { ADD_CATEGORY_ACTION, ADD_ITEM_ACTION, REMOVE_ITEM_ACTION };

				} else {
					// Context menu for an item
					return new Action[] { REMOVE_ITEM_ACTION };
				}
			}
		});

		Layout layout = new VerticalLayout();
		setContent(layout);
		layout.addComponent(table);

	}

	@SuppressWarnings("unchecked")
	private void populateWithRandomHierarchicalData(final TreeTable sample) {
		final Random random = new Random();
		int hours = 0;
		final Object allProjects = sample.addItem(new Object[] { "All Projects", 0, new Date() }, null);
		for (final int year : Arrays.asList(2010, 2011, 2012, 2013)) {
			int yearHours = 0;
			final Object yearId = sample.addItem(new Object[] { "Year " + year, yearHours, new Date() }, null);
			sample.setParent(yearId, allProjects);
			for (int project = 1; project < random.nextInt(4) + 2; project++) {
				int projectHours = 0;
				final Object projectId = sample
						.addItem(new Object[] { "Customer Project " + project, projectHours, new Date() }, null);
				sample.setParent(projectId, yearId);
				for (final String phase : Arrays.asList("Implementation", "Planning", "Prototype")) {
					final int phaseHours = random.nextInt(50);
					final Object phaseId = sample.addItem(new Object[] { phase, phaseHours, new Date() }, null);
					sample.setParent(phaseId, projectId);
					sample.setChildrenAllowed(phaseId, false);
					sample.setCollapsed(phaseId, false);
					projectHours += phaseHours;
				}
				yearHours += projectHours;
				sample.getItem(projectId).getItemProperty(HOURS_PROPERTY).setValue(projectHours);
				sample.setCollapsed(projectId, false);
			}
			hours += yearHours;
			sample.getItem(yearId).getItemProperty(HOURS_PROPERTY).setValue(yearHours);
			sample.setCollapsed(yearId, false);
		}
		sample.getItem(allProjects).getItemProperty(HOURS_PROPERTY).setValue(hours);
		sample.setCollapsed(allProjects, false);
	}

}
