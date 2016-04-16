package shook.xeem;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import shook.xeem.activities.MainActivity;
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
        Call<BlankObject> postBlank(@Body BlankObject _blank);

        @DELETE("blanks/{blankid}")
        Call<BlankObject> rmBlank(@Path("blankid") String blankid);

    }

    static ApiMethods API = retrofit.create(ApiMethods.class);

    class blankListResponse {
        List<BlankObject> _items;
        public blankListResponse(List<BlankObject> _items) {
            this._items = _items;
        }
    }

    public static void postBlank (BlankObject _blank) {
        Log.d("MYTAG", "Trying to post this json: " + _blank.toJSON());
        Call<BlankObject> postBlankCall = API.postBlank(_blank);
        postBlankCall.enqueue(new Callback<BlankObject>() {
            @Override
            public void onResponse(Call<BlankObject> call, Response<BlankObject> response) {
                Log.d("MYTAG", "[POSTING] Success: " + response.code());
                updateBlanks();
            }

            @Override
            public void onFailure(Call<BlankObject> call, Throwable t) {
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
