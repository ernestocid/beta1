package testgeneration.preamblecalculation;

import java.util.Map;

public class Event {

	private String eventName;
	private Map<String, String> eventParameters;



	public Event(String eventName, Map<String, String> eventParameters) {
		this.eventName = eventName;
		this.eventParameters = eventParameters;
	}



	public Object getEventName() {
		return this.eventName;
	}



	public Object getEventParameters() {
		return this.eventParameters;
	}



	@Override
	public String toString() {
		return "Event " + getEventName() + " " + getEventParameters();
	}

}
