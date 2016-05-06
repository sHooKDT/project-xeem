package shook.xeem.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import shook.xeem.activities.LoginActivity;

public class XeemAuthService {

    static public final int SIGNIN_REQUEST_CODE = 25;
    private static final String PREFS_NAME = "XeemPrefs";

    static private GoogleApiClient googleApiClient;
    static private GoogleSignInAccount authAccount;
    public static SharedPreferences AppPreferences;

    private static LoginActivity activity;

    private static signinSuccess successCallback;

    public static boolean isLogged() {
        return AppPreferences.getBoolean("isLoginCached", false);
    }

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected() && authAccount != null;
    }

    public static String getCachedUsername() {
        return AppPreferences.getString("cached_username", null);
    }

    public static String getUserId() {
        if (authAccount != null) return authAccount.getId();
        return "offline";
    }

    public static void init(Context context, signinSuccess callback) {

        activity = (LoginActivity) context;

        // Variables initialisation
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestProfile()
                .build();

        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d("XEEMDBG", "[GOOGLEAPI] Something failed");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        AppPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        successCallback = callback;
    }

    private static void updateCache(GoogleSignInResult result) {
        SharedPreferences.Editor prefEditor = AppPreferences.edit();
        prefEditor.putBoolean("isLoginCached", true);
        if (result.isSuccess() && result.getSignInAccount() != null) {
            prefEditor.putString("cached_username", result.getSignInAccount().getDisplayName());
            prefEditor.putString("cached_id", result.getSignInAccount().getId());
        }
        prefEditor.apply();
    }

    public static void silent() {
        OptionalPendingResult<GoogleSignInResult> result = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        result.setResultCallback(new ResultCallback<GoogleSignInResult>() {
            @Override
            public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                if (googleSignInResult.isSuccess()) {
                    Log.d("XEEMDBG", "[GOOGLEAPI] Silent signin success");
                    updateCache(googleSignInResult);
                    authAccount = googleSignInResult.getSignInAccount();
                    successCallback.onSuccess();
                } else {
                    // Ask user to sign in manually
                    Log.d("XEEMDBG", "[GOOGLEAPI Silent signin failed. Try to signin manually");
                }
            }
        });
    }

    public static void manual(Activity context) {
        Log.d("XEEMDBG", "Start signInIntent");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        activity.startActivityForResult(signInIntent, SIGNIN_REQUEST_CODE);
    }

    public static void setManualResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            Log.d("XEEMDBG", "[GOOGLEAPI] Manual signin success");
            updateCache(result);
            authAccount = result.getSignInAccount();
            successCallback.onSuccess();
        } else {
            // Ask user to sign in manually
            Log.d("XEEMDBG", "[GOOGLEAPI Manual signin failed");
        }
    }


    public interface signinSuccess {
        void onSuccess();
    }

}
