package com.swissquote;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.annotation.WebServlet;

import com.swissquote.voomer.MainPanel;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import utils.Broadcaster;
import utils.Broadcaster.BroadcastListener;
import utils.DemoUtils;

@Theme("valo")
@Widgetset("com.swissquote.MyAppWidgetset")
@Push
public class App extends UI implements BroadcastListener {

	private static final long serialVersionUID = 1L;
	private static final int UPDATE_FREQUENCY = 500;
	public static final int EXAMPLE_DATA_SET_SIZE = 1230;

	private MainPanel mainPanel;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		getPage().setTitle(appName());
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		Broadcaster.register(this);

		mainPanel = new MainPanel();
		layout.addComponent(mainPanel);

		startBroadcasting();
	}

	// Generating one update per number of records.
	// Since I am lazy, it is just a set of random updates,
	// not corresponding to the actual data-set.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void startBroadcasting() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			while (true) {
				try {
					Thread.sleep(UPDATE_FREQUENCY);
				} catch (Exception e) {
					e.printStackTrace();
				}
				LinkedList updates = new LinkedList();
				for (int i = 0; i < EXAMPLE_DATA_SET_SIZE; i++) {
					updates.add(DemoUtils.randomInstrument());
				}
				Broadcaster.broadcast(updates);
			}
		});
	}

	@Override
	public void detach() {
		Broadcaster.unregister(this);
		super.detach();
	}

	public static String appName() {
		return "Voomer";
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = App.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 1L;
	}

	@Override
	public void receiveBroadcast(final Object object) {

		// ... and the single most ugly code in the project. But again, it is
		// Sunday, so it stays as it is.
		access(new Runnable() {
			@Override
			public void run() {
				mainPanel.getInventoryPanel().updateInstrument(object);
			}
		});
	}

}
