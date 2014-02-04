package org.vaadin.hezamu.rx.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme(DevScoreTheme.THEME_NAME)
public class DevScoreUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		DevScoreView mainView = new DevScoreView();
		
		setContent(new VerticalLayout() {
			{
				setMargin(true);

				addComponent(mainView);
				setComponentAlignment(mainView, Alignment.MIDDLE_CENTER);
			}
		});


		DevScorePresenter.setupUILogic(mainView);
	}
}