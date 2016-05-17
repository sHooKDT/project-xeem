package shook.xeem.services;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
import shook.xeem.UserList;
import shook.xeem.interfaces.BlankUpdateListener;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.TestResult;
import shook.xeem.objects.UserObject;

public class XeemApiService {

    static final String API_URL = "http://46.101.8.217:500/";

    private ArrayList<BlankUpdateListener> blankUpdateListeners = new ArrayList<>();

    static private UserList userList;

    public UserList getUsers() {
        return userList;
    }
//    static private ArrayList<UserObject> userList;
//    public ArrayList<UserObject> getUsers() {return userList;}

    public void registerBlankUpdateListener(BlankUpdateListener _listener) {
        blankUpdateListeners.add(_listener);
    }

    private void notifyUpdate(@Nullable BlankObject.blankListResponse response) {
        Iterator<BlankUpdateListener> listenerIterator = blankUpdateListeners.iterator();
        while (listenerIterator.hasNext()) {
            if (response != null) listenerIterator.next().onUpdate(response._items);
            else listenerIterator.next().onUpdate(null);
        }
    }


    public XeemApiService() {}

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface ApiMethods {

        @GET("blanks")
        Call<BlankObject.blankListResponse> loadBlanks();

        @POST("blanks")
        Call<ResponseBody> postBlank(@Body BlankObject _blank);

        @POST("results")
        Call<ResponseBody> postResult(@Body TestResult _result);

        @GET("users")
        Call<UserObject.UserListResponse> loadUsers();

        @POST("users")
        Call<ResponseBody> postUser(@Body UserObject _user);

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
                loadBlanks();
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
                loadBlanks();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XEEMDBG", "[PATCH] Fail" + t.getMessage());
            }
        });
    }

    public void postBlank(BlankObject _blank) {
        Call<ResponseBody> postBlankCall = API.postBlank(_blank);
        postBlankCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("XEEMDBG", "[BLANK-POSTING] Success: " + response.code());
                try {
                    if (response.body() != null)
                        Log.d("XEEMDBG", "[BLANK-POSTING] Response: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loadBlanks();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XEEMDBG", "[POSTING] Fail: " + t.getMessage());
            }
        });
    }

    public void postResult(TestResult _result) {
        Call<ResponseBody> postResultCall = API.postResult(_result);
        postResultCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("XEEMDBG", "[RESULT-POSTING] Success: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XEEMDBG", "[RESULT-POSTING] Fail: " + t.getMessage());
            }
        });
    }

    public void postUser(UserObject _user) {
        Call<ResponseBody> postUserCall = API.postUser(_user);
        postUserCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("XEEMDBG", "[USERS] Sent success: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("XEEMDBG", "[USERS] Send fail: " + t.getMessage());
            }
        });
    }

    public void loadBlanks() {
        Call<BlankObject.blankListResponse> blankGetCall = API.loadBlanks();
        blankGetCall.enqueue(new Callback<BlankObject.blankListResponse>() {
            @Override
            public void onResponse(Call<BlankObject.blankListResponse> call, Response<BlankObject.blankListResponse> response) {
                Log.d("XEEMDBG", "[UPDATE] Success " + response.code());
                notifyUpdate(response.body());
            }

            @Override
            public void onFailure(Call<BlankObject.blankListResponse> call, Throwable t) {
                Log.d("XEEMDBG", "[UPDATE] Failed " + t.getMessage());
            }
        });
    }

    public void loadUsers() {
        Call<UserObject.UserListResponse> usersGetCall = API.loadUsers();
        usersGetCall.enqueue(new Callback<UserObject.UserListResponse>() {
            @Override
            public void onResponse(Call<UserObject.UserListResponse> call, Response<UserObject.UserListResponse> response) {
                userList = response.body()._items;
                notifyUpdate(null);
                Log.d("XEEMDBG", "[USERS] Load success " + response.code());
            }

            @Override
            public void onFailure(Call<UserObject.UserListResponse> call, Throwable t) {
                Log.d("XEEMDBG", "[USERS] Failed " + t.getMessage());
            }
        });
    }

}
