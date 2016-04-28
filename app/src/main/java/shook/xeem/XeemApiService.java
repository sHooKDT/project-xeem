package shook.xeem;

import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;

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
import retrofit2.http.Path;
import shook.xeem.objects.BlankObject;

public class XeemApiService {

    static final String API_URL = "http://46.101.8.217:500/";

    public XeemApiService() {}

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

        @Headers("Content-Type: application/json")
        @PATCH("blanks/{id}")
        Call<ResponseBody> editBlank(@Path("id") String id, @Header("If-Match") String etag, @Body String blank);

    }

    ApiMethods API = retrofit.create(ApiMethods.class);

    public void deleteBlank(BlankObject deletable) {
        Call<ResponseBody> rmBlankCall = API.rmBlank(deletable.getID(), deletable.getEtag());
        rmBlankCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("XEEMDBG", "[DELETE] Success: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XEEMDBG", "[DELETE] Fail" + t.getMessage());
            }
        });
    }

    public void editBlank(BlankObject _blank) {
        Call<ResponseBody> editBlankCall = API.editBlank(_blank.getID(), _blank.getEtag(), _blank.rmEtag().toJSON());
        editBlankCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("XEEMDBG", "[PATCH Body: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("XEEMDBG", "[PATCH] Success: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XEEMDBG", "[PATCH] Fail" + t.getMessage());
            }
        });
    }

    public static class blankListResponse {
        public LinkedList<BlankObject> _items;

        public blankListResponse(LinkedList<BlankObject> _items) {
            this._items = _items;
        }
    }

    public void postBlank(BlankObject _blank) {
        Call<ResponseBody> postBlankCall = API.postBlank(_blank);
        postBlankCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("XEEMDBG", "[POSTING] Success: " + response.code());
                try {
                    if (response.body() != null)
                        Log.d("XEEMDBG", "[POSTING] Response: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XEEMDBG", "[POSTING] Fail: " + t.getMessage());
            }
        });
    }

    public void updateBlanks(Callback<blankListResponse> callback) {
        Call<blankListResponse> blankGetCall = API.loadBlanks();
        blankGetCall.enqueue(callback);
    }

}
