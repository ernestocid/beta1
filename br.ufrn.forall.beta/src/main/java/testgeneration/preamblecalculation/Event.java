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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((eventParameters == null) ? 0 : eventParameters.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (eventParameters == null) {
			if (other.eventParameters != null)
				return false;
		} else if (!eventParameters.equals(other.eventParameters))
			return false;
		return true;
	}

}
