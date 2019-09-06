package de.femtopedia.mensa.openmensa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.femtopedia.mensa.openmensa.json.Day;
import de.femtopedia.mensa.openmensa.json.Meal;
import de.femtopedia.studip.shib.CustomAccessClient;
import de.femtopedia.studip.shib.CustomAccessHttpResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import javafx.util.Pair;
import oauth.signpost.exception.OAuthException;
import okhttp3.Request;

public class OpenMensaApi extends CustomAccessClient {

	public static final String OPENMENSA_API_BASE_URL = "https://openmensa.org/api/v2/";
	private static Type dayType = new TypeToken<List<Day>>() {
	}.getType();
	private static Type mealType = new TypeToken<List<Meal>>() {
	}.getType();

	private Gson gson;

	public OpenMensaApi() {
		this.gson = new GsonBuilder()
				.enableComplexMapKeySerialization()
				.disableHtmlEscaping()
				.create();
	}

	public List<Meal> getMeals(int canteenId, String day)
			throws IllegalAccessException, IOException {
		return this.getData("canteens/" + canteenId + "/days/" + day + "/meals", mealType);
	}

	public Meal getMeal(int canteenId, String day, int mealId) throws
			IllegalAccessException, IOException {
		return this.getData("canteens/" + canteenId + "/days/" + day + "/meals/" + mealId, Meal.class);
	}

	public List<Day> getDays(int canteenId)
			throws IllegalAccessException, IOException {
		return this.getData("canteens/" + canteenId + "/days", dayType);
	}

	public Day getDay(int canteenId, String day)
			throws IllegalAccessException, IOException {
		return this.getData("canteens/" + canteenId + "/days/" + day, Day.class);
	}

	/**
	 * Performs a HTTP GET Request.
	 *
	 * @param url        The URL to get
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 */
	public CustomAccessHttpResponse get(String url, String[] headerKeys, String[] headerVals)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url)
				.get();
		Request request = requestBuilder.build();
		return executeRequest(request.newBuilder(), headerKeys, headerVals);
	}

	/**
	 * Performs a HTTP POST Request.
	 *
	 * @param url        The URL to post
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @param nvps       A list containing Pairs for Form Data
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 */
	public CustomAccessHttpResponse post(String url, String[] headerKeys, String[] headerVals, List<Pair<String, String>> nvps) throws IOException, IllegalArgumentException, IllegalAccessException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url);
		Request request = requestBuilder.post(getFormBody(nvps)).build();
		return executeRequest(
				request.newBuilder(),
				headerKeys, headerVals);
	}

	/**
	 * Performs a HTTP PUT Request.
	 *
	 * @param url        The URL to put
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @param nvps       A list containing Pairs for Form Data
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 */
	public CustomAccessHttpResponse put(String url, String[] headerKeys, String[] headerVals, List<Pair<String, String>> nvps) throws IOException, IllegalArgumentException, IllegalAccessException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url);
		Request request = requestBuilder.put(getFormBody(nvps)).build();
		return executeRequest(
				request.newBuilder(),
				headerKeys, headerVals);
	}

	/**
	 * Performs a HTTP DELETE Request.
	 *
	 * @param url        The URL to delete
	 * @param headerKeys An array containing keys for the headers to send with the request
	 * @param headerVals An array containing values for the headers to send with the request (size must be the same as headerKeys.length)
	 * @param nvps       A list containing Pairs for Form Data
	 * @return A {@link CustomAccessHttpResponse} representing the result
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if the session isn't valid
	 * @throws OAuthException           if any OAuth errors occur
	 */
	public CustomAccessHttpResponse delete(String url, String[] headerKeys, String[] headerVals, List<Pair<String, String>> nvps) throws IOException, IllegalArgumentException, IllegalAccessException, OAuthException {
		Request.Builder requestBuilder = new Request.Builder()
				.url(url);
		Request request = requestBuilder.delete(getFormBody(nvps)).build();
		return executeRequest(
				request.newBuilder(),
				headerKeys, headerVals);
	}

	/**
	 * Performs a HTTP GET Request and converts the site's content into the given Object type using Gson.
	 *
	 * @param apiUrl   The URL to get
	 * @param objClass The class to cast to
	 * @param <T>      The wanted Generic type
	 * @return the parsed and converted object
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if anything else went wrong
	 */
	public <T> T getData(String apiUrl, Class<T> objClass)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		CustomAccessHttpResponse response = null;
		try {
			response = this.get(OPENMENSA_API_BASE_URL + apiUrl);
			return gson.fromJson(response.readLine(), objClass);
		} catch (OAuthException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				response.close();
		}
		return null;
	}

	/**
	 * Performs a HTTP GET Request and converts the site's content into the given Object type using Gson.
	 *
	 * @param apiUrl The URL to get
	 * @param type   The object type to cast to
	 * @param <T>    The wanted Generic type
	 * @return the parsed and converted object
	 * @throws IOException              if reading errors occur
	 * @throws IllegalArgumentException if the header values are broken
	 * @throws IllegalAccessException   if anything else went wrong
	 */
	public <T> T getData(String apiUrl, Type type)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		CustomAccessHttpResponse response = null;
		try {
			response = this.get(OPENMENSA_API_BASE_URL + apiUrl);
			return gson.fromJson(response.readLine(), type);
		} catch (OAuthException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				response.close();
		}
		return null;
	}

	@Override
	public boolean isErrorCode(int statusCode) {
		return statusCode != 200;
	}

}
