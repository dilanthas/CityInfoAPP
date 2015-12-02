/**
 * 
 */
package api;

import api.goeuro.GoEuroAPI;

/**
 * @author dilantha
 *
 */
public class CityInfoAPIFactory {

	public static final String GOEURO = "GOEURO";

	public static ICityInfoAPI getAPI(String type) {
		ICityInfoAPI api = null;

		if (type.equals(GOEURO)) {
			api = new GoEuroAPI();
		}

		return api;
	}
}
