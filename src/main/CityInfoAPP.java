package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Response;
import utils.Response.Status;
import utils.SearchCriteria;
import utils.Utility;
import api.CityInfoAPIFactory;
import api.ICityInfoAPI;

public class CityInfoAPP {

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("ERROR : Please specify the city name ");
		} else {

			SearchCriteria criteria = new SearchCriteria();
			criteria.setCityName(args[0]);
			ICityInfoAPI api = CityInfoAPIFactory.getAPI(CityInfoAPIFactory.GOEURO);

			// calling the API and get info
			Response<String> response = api.getCityInfo(criteria);

			if (response.isSuccess()) {

				Response<String> processResp = processResponse(response.getData());

				System.out.println(processResp.getStatus().name() + " :" + processResp.getMessage());
			} else {
				System.out.println("ERROR :" + response.getMessage());
			}
		}

	}

	/**
	 * Process response and write to file
	 * 
	 * @param input
	 * @return
	 */
	public static Response<String> processResponse(String input) {

		Response<String> resp = new Response<String>();
		try {

			JSONArray jsonArray = new JSONArray(input);

			if (jsonArray.length() > 0) {
				BufferedWriter writer = new BufferedWriter(new FileWriter("cityInfo.csv"));
				for (int i = 0; i < jsonArray.length(); i++) {

					List<String> results = new ArrayList<>();
					JSONObject obj = jsonArray.getJSONObject(i);

					results.add(Integer.toString(obj.getInt("_id")));
					results.add(obj.getString("name"));
					results.add(obj.getString("type"));

					JSONObject geoPosition = obj.getJSONObject("geo_position");
					results.add(Double.toString(geoPosition.getDouble("latitude")));
					results.add(Double.toString(geoPosition.getDouble("longitude")));

					writer.write(Utility.joinList(results));
					writer.newLine();

				}

				writer.flush();
				writer.close();
				resp.setStatus(Status.SUCCESS);
				resp.setMessage("File generated successfully");
			} else {
				resp.setStatus(Status.WARNING);
				resp.setMessage("No results found for the criteria");
			}

		} catch (Exception e) {
			resp.setStatus(Status.ERROR);
			resp.setMessage(e.getMessage());
		}

		return resp;
	}

}
