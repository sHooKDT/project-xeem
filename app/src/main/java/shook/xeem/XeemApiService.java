package shook.xeem;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import shook.xeem.activities.MainActivity;
import shook.xeem.list_adapters.BlankListAdapter;
import shook.xeem.objects.BlankObject;

public class XeemApiService {

    static final String API_URL = "http://46.101.8.217:500/";

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface ApiMethods {

        @GET("blanks")
        Call<blankListResponse> loadBlanks();

        @POST("blanks")
        Call<ResponseBody> postBlank(@Body BlankObject _blank);

        @DELETE("blanks/{id}")
        Call<ResponseBody> rmBlank(@Path("id") String id, @Header("If-Match") String etag);

        @PATCH("blanks/{id}")
        Call<errorResponse> editBlank(@Path("id") String id, @Header("If-Match") String etag, @Body String blank);

    }

    static ApiMethods API = retrofit.create(ApiMethods.class);

    public static void deleteBlank (BlankObject deletable) {
        Call<ResponseBody> rmBlankCall = API.rmBlank(deletable.getID(), deletable.getEtag());
        rmBlankCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("MYTAG", "[DELETE] Success: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MYTAG", "[DELETE] Fail" + t.getMessage());
            }
        });
    }

    public static void editBlank (BlankObject _blank) {
        String myetag = _blank.getEtag();
        _blank.rmEtag();
        Log.d("MYTAG", _blank.toJSON());
        Call<errorResponse> editBlankCall = API.editBlank(_blank.getID(), myetag, _blank.toJSON());
        editBlankCall.enqueue(new Callback<errorResponse>() {
            @Override
            public void onResponse(Call<errorResponse> call, Response<errorResponse> response) {
                Log.d("MYTAG", "[PATCH] Success: " + response.code());
//                Log.d("MYTAG", "[PATCH] Response: " + response.body()._status);
            }

            @Override
            public void onFailure(Call<errorResponse> call, Throwable t) {
                Log.d("MYTAG", "[PATCH] Fail" + t.getMessage());
            }
        });
    }

    class blankListResponse {
        List<BlankObject> _items;
        public blankListResponse(List<BlankObject> _items) {
            this._items = _items;
        }
    }
    class errorResponse {
        @SerializedName("_status") String _status;
        @SerializedName("_issues") JSONObject _issues;
        @SerializedName("_error") error _error;
        class error {
            String message;
            int code;
        }
        public errorResponse(String _status, error _error, JSONObject _issues) {
            this._error = _error;
            this._status = _status;
            this._issues = _issues;
        }
    }

    public static void postBlank (BlankObject _blank) {
        Call<ResponseBody> postBlankCall = API.postBlank(_blank);
        postBlankCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("MYTAG", "[POSTING] Success: " + response.code());
                updateBlanks();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MYTAG", "[POSTING] Fail: " + t.getMessage());
            }
        });
    }

    public static void updateBlanks()  {
        Call<blankListResponse> blankGetCall = API.loadBlanks();
        blankGetCall.enqueue(new Callback<blankListResponse>() {
            @Override
            public void onResponse(Call<blankListResponse> call, Response<blankListResponse> response) {
                Log.d("MYTAG", "[UPDATE] Success: " + response.code());
                MainActivity.setBlankList(response.body()._items);
            }

            @Override
            public void onFailure(Call<blankListResponse> call, Throwable t) {
                Log.d("MYTAG", "[UPDATE] Fail: " + t.getMessage());
            }
        });
    }

}
