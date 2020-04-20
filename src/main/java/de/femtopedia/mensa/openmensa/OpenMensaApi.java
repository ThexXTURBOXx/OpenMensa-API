package de.femtopedia.mensa.openmensa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.femtopedia.mensa.openmensa.json.Day;
import de.femtopedia.mensa.openmensa.json.Meal;
import de.femtopedia.studip.shib.CustomAccessClient;
import de.femtopedia.studip.shib.CustomAccessHttpResponse;
import de.femtopedia.studip.shib.Pair;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import oauth.signpost.exception.OAuthException;
import okhttp3.Request;

public class OpenMensaApi extends CustomAccessClient {

    /**
     * The URL base for the RestAPI.
     */
    public static final String OPENMENSA_API_BASE_URL
            = "https://openmensa.org/api/v2/";

    /**
     * The used type for a list of {@link Day}s.
     */
    private static final Type DAY_TYPE = new TypeToken<List<Day>>() {
    }.getType();

    /**
     * The used type for a list of {@link Meal}s.
     */
    private static final Type MEAL_TYPE = new TypeToken<List<Meal>>() {
    }.getType();

    /**
     * The instance of {@link Gson} that is used.
     */
    private final Gson gson;

    /**
     * Initializes a default {@link OpenMensaApi} instance.
     */
    public OpenMensaApi() {
        this.gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .disableHtmlEscaping()
                .create();
    }

    /**
     * Returns a list of {@link Meal}s in the given canteen at the given day.
     *
     * @param canteenId The canteen to search for.
     * @param day       The day to search for.
     * @return a list of the {@link Meal}s.
     * @throws IOException            if reading errors occur.
     * @throws IllegalAccessException if the session isn't valid.
     */
    public List<Meal> getMeals(int canteenId, String day)
            throws IllegalAccessException, IOException {
        return this.getData("canteens/" + canteenId + "/days/" + day
                + "/meals", MEAL_TYPE);
    }

    /**
     * Returns details about a given {@link Meal}.
     *
     * @param canteenId The canteen to search for.
     * @param day       The day to search for.
     * @param mealId    The meal to search for.
     * @return a list of the {@link Meal}s.
     * @throws IOException            if reading errors occur.
     * @throws IllegalAccessException if the session isn't valid.
     */
    public Meal getMeal(int canteenId, String day, int mealId) throws
            IllegalAccessException, IOException {
        return this.getData("canteens/" + canteenId + "/days/" + day
                + "/meals/" + mealId, Meal.class);
    }

    /**
     * Returns a list of {@link Day}s in the given canteen.
     *
     * @param canteenId The canteen to search for.
     * @return a list of the {@link Day}s.
     * @throws IOException            if reading errors occur.
     * @throws IllegalAccessException if the session isn't valid.
     */
    public List<Day> getDays(int canteenId)
            throws IllegalAccessException, IOException {
        return this.getData("canteens/" + canteenId + "/days", DAY_TYPE);
    }

    /**
     * Returns the searched {@link Day} in the given canteen.
     *
     * @param canteenId The canteen to search for
     * @param day       The day to search for
     * @return the searched {@link Day}.
     * @throws IOException            if reading errors occur
     * @throws IllegalAccessException if the session isn't valid
     */
    public Day getDay(int canteenId, String day)
            throws IllegalAccessException, IOException {
        return this.getData("canteens/" + canteenId + "/days/" + day,
                Day.class);
    }

    /**
     * Performs a HTTP GET Request.
     *
     * @param url        The URL to get.
     * @param headerKeys An array containing keys for the headers to send with
     *                   the request.
     * @param headerVals An array containing values for the headers to send with
     *                   the request (size must be the same as
     *                   headerKeys.length).
     * @return A {@link CustomAccessHttpResponse} representing the result.
     * @throws IOException              if reading errors occur.
     * @throws IllegalArgumentException if the header values are broken.
     * @throws IllegalAccessException   if the session isn't valid.
     */
    public CustomAccessHttpResponse get(String url, String[] headerKeys,
                                        String[] headerVals)
            throws IOException, IllegalArgumentException,
            IllegalAccessException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get();
        Request request = requestBuilder.build();
        return executeRequest(request.newBuilder(), headerKeys, headerVals);
    }

    /**
     * Performs a HTTP POST Request.
     *
     * @param url        The URL to post.
     * @param headerKeys An array containing keys for the headers to send with
     *                   the request.
     * @param headerVals An array containing values for the headers to send with
     *                   the request (size must be the same as
     *                   headerKeys.length).
     * @param nvps       A list containing Pairs for Form Data.
     * @return A {@link CustomAccessHttpResponse} representing the result.
     * @throws IOException              if reading errors occur.
     * @throws IllegalArgumentException if the header values are broken.
     * @throws IllegalAccessException   if the session isn't valid.
     */
    public CustomAccessHttpResponse post(String url, String[] headerKeys,
                                         String[] headerVals,
                                         List<Pair<String, String>> nvps)
            throws IOException, IllegalArgumentException,
            IllegalAccessException {
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
     * @param url        The URL to put.
     * @param headerKeys An array containing keys for the headers to send with
     *                   the request.
     * @param headerVals An array containing values for the headers to send with
     *                   the request (size must be the same as
     *                   headerKeys.length).
     * @param nvps       A list containing Pairs for Form Data.
     * @return A {@link CustomAccessHttpResponse} representing the result.
     * @throws IOException              if reading errors occur.
     * @throws IllegalArgumentException if the header values are broken.
     * @throws IllegalAccessException   if the session isn't valid.
     */
    public CustomAccessHttpResponse put(String url, String[] headerKeys,
                                        String[] headerVals,
                                        List<Pair<String, String>> nvps)
            throws IOException, IllegalArgumentException,
            IllegalAccessException {
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
     * @param url        The URL to delete.
     * @param headerKeys An array containing keys for the headers to send with
     *                   the request.
     * @param headerVals An array containing values for the headers to send with
     *                   the request (size must be the same as
     *                   headerKeys.length).
     * @param nvps       A list containing Pairs for Form Data.
     * @return A {@link CustomAccessHttpResponse} representing the result.
     * @throws IOException              if reading errors occur.
     * @throws IllegalArgumentException if the header values are broken.
     * @throws IllegalAccessException   if the session isn't valid.
     */
    public CustomAccessHttpResponse delete(String url, String[] headerKeys,
                                           String[] headerVals,
                                           List<Pair<String, String>> nvps)
            throws IOException, IllegalArgumentException,
            IllegalAccessException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        Request request = requestBuilder.delete(getFormBody(nvps)).build();
        return executeRequest(
                request.newBuilder(),
                headerKeys, headerVals);
    }

    /**
     * Performs a HTTP GET Request and converts the site's content into the
     * given Object type using Gson.
     *
     * @param apiUrl The URL to get.
     * @param type   The object type to cast to.
     * @param <T>    The wanted Generic type.
     * @return the parsed and converted object.
     * @throws IOException              if reading errors occur.
     * @throws IllegalArgumentException if the header values are broken.
     * @throws IllegalAccessException   if anything else went wrong.
     */
    public <T> T getData(String apiUrl, Type type) throws IOException,
            IllegalArgumentException, IllegalAccessException {
        CustomAccessHttpResponse response = null;
        try {
            response = this.get(OPENMENSA_API_BASE_URL + apiUrl);
            return gson.fromJson(response.readLine(), type);
        } catch (OAuthException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorCode(int statusCode) {
        return statusCode != 200;
    }

}
