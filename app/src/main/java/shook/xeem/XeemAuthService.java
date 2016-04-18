package shook.xeem;

import android.content.Context;
import android.content.Intent;
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

public class XeemAuthService {

    static private GoogleApiClient googleApiClient;
    static public final int SIGNIN_REQUEST_CODE = 25;

    static private GoogleSignInAccount authAccount;

    static private OptionalPendingResult<GoogleSignInResult> pendingResult;

    public static OptionalPendingResult<GoogleSignInResult> getPendingResult() {
        return pendingResult;
    }

    public static void loadAccountInfo(GoogleSignInResult _result) {
        if (_result.isSuccess() && authAccount == null) {
            authAccount = _result.getSignInAccount();
            Log.d("MYTAG", "[AUTH] Signed in as " + authAccount.getDisplayName());
        }
    }

    public static int getRequestCode() { return SIGNIN_REQUEST_CODE; }

    public static GoogleApiClient getClient() {return googleApiClient;}

    public static GoogleSignInAccount getAccount() {
        return authAccount;
    }

    public static void init(Context context) {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d("MYTAG", "[GOOGLEAPI] Something failed");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        pendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
    }

    public static void auth(Context context) {
        final Context activity_context = context;
        if (pendingResult.isDone()) {
            loadAccountInfo(pendingResult.get());
        } else {
            // There's no immediate result ready, displays some progress indicator and waits for the
            // async callback.
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    if (result.isSuccess()) {
                        loadAccountInfo(pendingResult.get());
                    } else {
                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                        ((FragmentActivity) activity_context).startActivityForResult(signInIntent, SIGNIN_REQUEST_CODE);
                    }
                }
            });
        }

    }

}
