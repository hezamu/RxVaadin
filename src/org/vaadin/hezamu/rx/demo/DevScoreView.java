package org.vaadin.hezamu.rx.demo;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class DevScoreView extends Panel {

	OptionGroup ogTestCoverage;
	ListSelect lsAgility;
	TextField tfTeamSize;
	ComboBox cbPMTechnicality;
	CheckBox chkVaadin;

	TrafficLightIndicator indicator;
	Label lblScore;
	Label lblFault;
	private HorizontalLayout panelContent;

	public DevScoreView() {
		super("How likely is your team to deliver great software?");
		addStyleName(DevScoreTheme.DEV_SCORE_VIEW);
		setWidth("100%");

		setContent(panelContent = new HorizontalLayout() {
			{
				Panel leftPanel;
				Panel rightPanel;

				setMargin(true);
				setSpacing(true);
				setWidth("100%");

				addComponent(leftPanel = initLeftPanel());
				addComponent(rightPanel = initRightPanel());

				leftPanel.setWidth("100%");
				setExpandRatio(leftPanel, 1);

				rightPanel.setSizeUndefined();
				setExpandRatio(rightPanel, 0);
			}
		});
	}

	private Panel initLeftPanel() {
		Panel left = new Panel("Describe your project", new VerticalLayout());
		VerticalLayout content = (VerticalLayout) left.getContent();
		content.setMargin(true);
		content.setSpacing(true);

		ogTestCoverage = new OptionGroup("Test coverage",
				DevScorePresenter.coverages.keySet());
		ogTestCoverage.setImmediate(true);
		content.addComponent(ogTestCoverage);

		lsAgility = new ListSelect("Agility (ctrl-click to multiselect)",
				DevScorePresenter.agilities);
		lsAgility.setImmediate(true);
		lsAgility.setMultiSelect(true);
		content.addComponent(lsAgility);

		tfTeamSize = new TextField("Team size");
		tfTeamSize.setImmediate(true);
		content.addComponent(tfTeamSize);

		cbPMTechnicality = new ComboBox(
				"When has your Project Manager last written code?",
				DevScorePresenter.PMTechnicalities.keySet());
		cbPMTechnicality.setImmediate(true);
		content.addComponent(cbPMTechnicality);

		chkVaadin = new CheckBox("Does your project include Vaadin?");
		chkVaadin.setImmediate(true);
		content.addComponent(chkVaadin);

		content.addComponent(lblFault = new Label() {
			{
				addStyleName(DevScoreTheme.FAULT);
			}
		});

		return left;
	}

	private Panel initRightPanel() {
		Panel right = new Panel("Analysis", new VerticalLayout());
		VerticalLayout content = (VerticalLayout) right.getContent();
		content.setMargin(true);
		content.setSpacing(true);

		indicator = new TrafficLightIndicator();
		content.addComponent(indicator);

		lblScore = new Label("Score");
		lblScore.addStyleName(DevScoreTheme.SCORE);
		content.addComponent(lblScore);

		return right;
	}
}