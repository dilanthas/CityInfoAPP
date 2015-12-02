package api.goeuro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import utils.Response;
import utils.Response.Status;
import utils.SearchCriteria;
import api.ICityInfoAPI;

public class GoEuroAPI implements ICityInfoAPI {

	public static final String END_POINT = "http://api.goeuro.com/api/v2/position/suggest/en/";
	@Override
	public String getEndpoint() {
		return END_POINT;
	}


	@Override
	public Response<String> getCityInfo(SearchCriteria criteria) {

		Response<String> response = new Response<>();
		if (validateCriteria(criteria)) {

			String query = buildRequest(criteria);

			response = queryAPI(query);

		} else {

			response.setStatus(Status.ERROR);
			response.setMessage("Invalid Criteria");
		}
		return response;
	}

	/**
	 * Query API
	 * 
	 * @param query
	 * @return
	 */
	private Response<String> queryAPI(String query) {

		Response<String> response = new Response<String>();
		System.out.println("Connecting to API =====>");

		try {
			URL url = new URL(query);

			URLConnection urlc = url.openConnection();
			urlc.setRequestProperty("Content-Type", "application/xml");


			// get result
			BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			StringBuffer output = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				output.append(line);
			}
			br.close();
			response.setStatus(Status.SUCCESS);
			response.setData(output.toString());
			response.setMessage("Successfully data reterived");
			System.out.println("===== Data Received from API =====");
		} catch (Exception e) {
			System.out.println("===== Error Connecting to API =====");
			e.printStackTrace();
			response.setStatus(Status.ERROR);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	private String buildRequest(SearchCriteria criteria) {

		StringBuilder sb = new StringBuilder(END_POINT);
		sb.append(criteria.getCityName());
		return sb.toString();
	}

	/**
	 * Validation search criteria
	 */
	@Override
	public boolean validateCriteria(SearchCriteria criteria) {

		String cityName = criteria.getCityName();
		boolean valid = true;
		if (cityName == null || cityName.length() == 0) {
			valid = false;
		}
		return valid;
	}


}
