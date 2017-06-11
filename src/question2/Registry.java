package question2;

import java.util.List;

/**
 * Interface represents registry for items of generic type T.
 * @author Natalja Olefire
 *
 * @param <T> type of collected items
 */
public interface Registry<T> {
	/**
	 * Adds item to the registry
	 * @param item
	 */
	public void add(final T item);
	
	/**
	 * Searches item in the registry by registration number
	 * @param number registration number to look for
	 * @return found item, or null if not found
	 */
	public T searchByRegistrationNumber(final String number);
	
	/**
	 * Searches item in the registry by name
	 * @param name name to look for
	 * @return list of found items, or empty list if nothing is found 
	 */
	public List<T> searchByName(final String name);
}
