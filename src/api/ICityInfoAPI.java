package api;

import utils.Response;
import utils.SearchCriteria;

public interface ICityInfoAPI {

	public String getEndpoint();

	public Response<String> getCityInfo(SearchCriteria criteria);

	public boolean validateCriteria(SearchCriteria criteria);

}
