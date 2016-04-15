package shook.xeem;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class XeemApiService {

    static final String API_URL = "http://46.101.8.217:500/";

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface ApiMethods {

//        class blankListResponse {
//            List<BlankObject> blanks;
//            public blankListResponse(List<BlankObject> _items) {
//                this.blanks = _items;
//            }
//        }

        @GET("blanks")
        Call<blankListResponse> loadBlanks();

        @POST("blanks")
        Call<BlankObject> postBlank(@Body BlankObject _blank);
    }

    static ApiMethods API = retrofit.create(ApiMethods.class);

    class blankListResponse {
        List<BlankObject> _items;
        public blankListResponse(List<BlankObject> _items) {
            Log.d("MYTAG", "Constructor executed");
            this._items = _items;
        }
    }

    public static void postBlank (BlankObject _blank) {
        Call<BlankObject> postBlankCall = API.postBlank(_blank);
        postBlankCall.enqueue(new Callback<BlankObject>() {
            @Override
            public void onResponse(Call<BlankObject> call, Response<BlankObject> response) {
                Log.d("MYTAG", "Request status: " + response.code());
            }

            @Override
            public void onFailure(Call<BlankObject> call, Throwable t) {
                Log.d("MYTAG", "Request status: " + t.getMessage());
            }
        });
    }

    public static void updateBlanks()  {
        Call<blankListResponse> blankGetCall = API.loadBlanks();
        blankGetCall.enqueue(new Callback<blankListResponse>() {
            @Override
            public void onResponse(Call<blankListResponse> call, Response<blankListResponse> response) {
                Log.d("MYTAG", "Update success:" + response.code());
                MainActivity.setBlankList(response.body()._items);
            }

            @Override
            public void onFailure(Call<blankListResponse> call, Throwable t) {
                Log.d("MYTAG", "Request failed.");
            }
        });
    }

}
