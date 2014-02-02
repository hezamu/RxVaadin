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
	private Label label; // TODO

	public TrafficLightIndicator() {
		this(null);
	}

	public TrafficLightIndicator(TrafficLight value) {
		label = new Label();
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
		label.setValue(value != null ? value.toString() : "");
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
