package net.intelie.challenges;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EventStoreImpl implements EventStore {

	private Map<String, EventsContainer> events = new ConcurrentHashMap<>();

	/**
	 * Method responsible for inserting events in Map events.
	 * @param event
	 */
	@Override
	public void insert(Event event) {
		//Checking if it contains the key with the event type.
		if(	events.containsKey(event.type())) {
			//If so, get the EventsContainer with the event type key and insert the event using the insertEvent method.
			events.get(event.type()).insertEvent(event);

		}
		//Otherwise, create a new EventsContainer and insert the event into it, then insert it into the events using the event type as the key in the Map.
		else {

			EventsContainer newEventToInsert = new EventsContainer();
			newEventToInsert.insertEvent(event);
			events.put(event.type(), newEventToInsert);
		}
	}

	/**
	 * Method responsible for erasing all events inserted in the map, based on the requested type.
	 * @param String type
	 */
	@Override
	public void removeAll(String type) {
		//use the abstract remove method, to delete all events inserted with the type of the parameter.
		events.remove(type);
	}

	/**
	 * Method responsible for fetching all events on the map, based on the parameters entered.
	 * @param String type
	 * @param long startTime
	 * @param long endTime
	 */
	@Override
	public EventIterator query(String type, long startTime, long endTime) {

		//Validation to check if the type is empty or null, and check if the type contains no events.
		if(type.isBlank() || type.isEmpty() || type == null || !events.containsKey(type)) {
			return null;
		}

		EventsContainer result_data = new EventsContainer();

		//searching for all events within the established parameters
		for (Map.Entry<String, Event> entry : events.get(type).entrySet()) {

			long entry_timestamp = entry.getValue().timestamp();

			if(	entry_timestamp >= startTime && entry_timestamp <= endTime) {
				result_data.insertEvent(entry.getValue());
			}
		}

		//if there is no event, null return.
		if(result_data.isEmpty()) {
			return null;
		}

		return new EventIteratorImpl(result_data);

	}

}
