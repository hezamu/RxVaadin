package org.vaadin.hezamu.rx;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class RiskCalculatorView extends UI {

	OptionGroup ogTestCoverage;
	ListSelect lsAgility;
	TextField tfTeamSize;
	ComboBox cbPMTechnicality;
	CheckBox chkVaadin;

	TrafficLightIndicator indicator;
	Label lblScore;
	Label lblFault;

	@Override
	protected void init(VaadinRequest request) {
		initUI();

		RiskCalculatorPresenter.setupUILogic(this);
	}

	private void initUI() {
		VerticalLayout mainContent = new VerticalLayout();
		setContent(mainContent);

		Panel mainPanel = new Panel(
				"How likely is your team to deliver great software?",
				new HorizontalLayout());
		mainPanel.setSizeUndefined();
		HorizontalLayout panelContent = (HorizontalLayout) mainPanel
				.getContent();
		panelContent.setMargin(true);
		panelContent.setSpacing(true);
		mainContent.addComponent(mainPanel);
		mainContent.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);

		panelContent.addComponent(initLeftPanel());
		panelContent.addComponent(initRightPanel());
	}

	private Panel initLeftPanel() {
		Panel result = new Panel("Describe your project", new VerticalLayout());
		VerticalLayout content = (VerticalLayout) result.getContent();
		content.setMargin(true);
		content.setSpacing(true);

		ogTestCoverage = new OptionGroup("Test coverage",
				RiskCalculatorPresenter.coverages.keySet());
		ogTestCoverage.setImmediate(true);
		content.addComponent(ogTestCoverage);

		lsAgility = new ListSelect("Agility (ctrl-click to multiselect)",
				RiskCalculatorPresenter.agilities);
		lsAgility.setImmediate(true);
		lsAgility.setMultiSelect(true);
		content.addComponent(lsAgility);

		tfTeamSize = new TextField("Team size");
		tfTeamSize.setImmediate(true);
		content.addComponent(tfTeamSize);

		cbPMTechnicality = new ComboBox(
				"When has your Project Manager last written code?",
				RiskCalculatorPresenter.PMTechnicalities.keySet());
		cbPMTechnicality.setImmediate(true);
		content.addComponent(cbPMTechnicality);

		chkVaadin = new CheckBox("Does your project include Vaadin?");
		chkVaadin.setImmediate(true);
		content.addComponent(chkVaadin);

		lblFault = new Label();
		content.addComponent(lblFault);

		return result;
	}

	private Panel initRightPanel() {
		Panel result = new Panel("Analysis", new VerticalLayout());
		VerticalLayout content = (VerticalLayout) result.getContent();
		content.setMargin(true);
		content.setSpacing(true);

		indicator = new TrafficLightIndicator();
		content.addComponent(indicator);

		lblScore = new Label("Score");
		content.addComponent(lblScore);

		return result;
	}
}