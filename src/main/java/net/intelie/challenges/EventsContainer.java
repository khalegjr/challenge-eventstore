package net.intelie.challenges;

import java.util.concurrent.ConcurrentHashMap;

public class EventsContainer extends ConcurrentHashMap<String, Event> {

	private static final long serialVersionUID = -1445005892248158466L;

	private KeyGenerator keyGenerator = new KeyGenerator();

	public EventsContainer() {
		super();
	}


	/**
	 * Method responsible for inserting an event in the container, generating a key with the KeyGenerator class.
	 * @param event
	 */
	public void insertEvent(Event event) {

		if(event.type() == null || event.type().isBlank() || event.type().isEmpty()) {
			throw new IllegalArgumentException();
		}

		String hashKey = keyGenerator.MakeAKey(event.type());

		this.put(hashKey, event);
	}
}
