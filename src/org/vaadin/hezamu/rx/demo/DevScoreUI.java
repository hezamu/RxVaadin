package org.vaadin.hezamu.rx.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("devscore")
public class DevScoreUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout mainContent = new VerticalLayout();
		setContent(mainContent);

		DevScoreView mainView = new DevScoreView();
		mainContent.addComponent(mainView);
		mainContent.setComponentAlignment(mainView, Alignment.MIDDLE_CENTER);

		DevScorePresenter.setupUILogic(mainView);
	}
}