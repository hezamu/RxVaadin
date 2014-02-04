package org.vaadin.hezamu.rx.demo;

import org.vaadin.hezamu.rx.demo.TrafficLightIndicator.TrafficLight;

import com.vaadin.data.Property;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class TrafficLightIndicator extends CustomComponent implements
		Property<TrafficLight> {
	public enum TrafficLight {
		OFF, GREEN, YELLOW, RED;
	}

	private TrafficLight value;
	private boolean readOnly = false;
	private Label label;

	public TrafficLightIndicator() {
		this(null);
	}

	public TrafficLightIndicator(TrafficLight value) {
		label = new Label();
		label.setWidth("50px");
		label.setHeight("50px");
		label.addStyleName(DevScoreTheme.TRAFFIC_LIGHT);
		setValue(value);
		setCompositionRoot(label);
	}

	@Override
	public TrafficLight getValue() {
		return value;
	}

	@Override
	public void setValue(TrafficLight newValue)
			throws com.vaadin.data.Property.ReadOnlyException {
		if (readOnly)
			throw new ReadOnlyException();

		value = newValue;

		if (value == TrafficLight.GREEN)
			label.addStyleName(DevScoreTheme.GOOD);
		else if (value == TrafficLight.YELLOW)
			label.addStyleName(DevScoreTheme.UGLY);
		else if (value == TrafficLight.RED)
			label.addStyleName(DevScoreTheme.BAD);
		else
			label.addStyleName(DevScoreTheme.OFF);
	}

	@Override
	public Class<? extends TrafficLight> getType() {
		return TrafficLight.class;
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	@Override
	public void setReadOnly(boolean newStatus) {
		readOnly = newStatus;
	}
}
