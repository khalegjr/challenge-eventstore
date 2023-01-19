package net.intelie.challenges;

import java.util.Iterator;

public class EventIteratorImpl implements EventIterator {

	private EventsContainer events;
	private Iterator<String> eventsIterator;
	private String eventConatinerKey;

	public EventIteratorImpl(EventsContainer events) {
		this.events = events;
		eventsIterator = events.keySet().iterator();
	}

	/**
	 * Method to close this resource. Override from AutoCloseable.class
	 *
	 * The close() method of an AutoCloseable object is called automatically
	 * when exiting a try-with-resources block for which the object has been
	 * declared in the resource specification header.
	 */
	@Override
	public void close() throws Exception {

		// instantiating null on all attributes, to close the object.
		eventsIterator = null;
		eventConatinerKey = null;
		events = null;
	}

	/**
	 * responsible method to go to the next event
	 */
	@Override
	public boolean moveNext() {

		//Checking if there are still events.
		if(eventsIterator.hasNext()) {
			eventConatinerKey = eventsIterator.next();
			return true;
		}

		eventConatinerKey = null;
		return false;
	}

	/**
	 * fetching the current event
	 *
	 * @return the event itself.
	 * @throws IllegalStateException if {@link #moveNext} was never called
	 *                               or its last result was {@code false}.
	 */
	@Override
	public Event current() {

		//if eventConatinerKey is null, throw a new IllegalStateException.
		if(eventConatinerKey == null) {
			throw new IllegalStateException();
		}

		return events.get(eventConatinerKey);
	}

	/**
	 * removing the event, using the ConcurrentHashMap's remove method.
	 *
	 * @throws IllegalStateException if {@link #moveNext} was never called
	 *                               or its last result was {@code false}.
	 */
	@Override
	public void remove() {

		if(eventConatinerKey == null) {
			throw new IllegalStateException();
		}

		events.remove(eventConatinerKey);
	}

}
