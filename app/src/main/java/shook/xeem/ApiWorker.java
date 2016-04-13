package shook.xeem;

import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ApiWorker {

    private static final String BASE_URL = "http://46.101.8.217:500/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getBlanksList () {
        get("blanks", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response) {
                Log.d("MYTAG", String.format("Response: %s", response.toString()));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("MYTAG", String.format("Failed with code %d \n Response: %s", statusCode, errorResponse.toString()));
            }

        });
    }


    public static void postNewBlank (BlankObject _blank) {

        try {
            StringEntity entity = new StringEntity(_blank.getJSON().toString());
            client.post(null, BASE_URL + "blanks", entity, "application/json", new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("MYTAG", String.format("%d OK", statusCode));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("MYTAG", String.format("%d FAIL", statusCode));
                    Log.d("MYTAG", errorResponse.toString());
                }
            });
        } catch (Exception e) {e.printStackTrace();}

    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
