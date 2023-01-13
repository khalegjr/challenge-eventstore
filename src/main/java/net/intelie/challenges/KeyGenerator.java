package net.intelie.challenges;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyGenerator {

	private Map<String, Number> keyControl = new ConcurrentHashMap<>();


	/**
	 * create a key based on the type entered
	 *
	 * @param type
	 * @return String key
	 */
	public String MakeAKey(String type) {
		// If it contains the type entered in the map key, we look for the value and add one more.
		if(keyControl.containsKey(type)) {
			int last_value = keyControl.get(type).intValue();
			last_value++;

			keyControl.put(type, last_value);

		}
		// If the type is not in the map, we enter the type as a key and initialize the value with a
		else {
			keyControl.put(type, 1);
		}

		//we return the generated key with the prefix "key", at the beginning of the string,
		// plus the concatenation of the type and the value contained in the KeyControl with the key of the type
		return "key_"+ type + "_" + keyControl.get(type).intValue();
	}
}
