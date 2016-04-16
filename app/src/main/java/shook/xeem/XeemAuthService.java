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

public class XeemAuthService {

    static private GoogleApiClient googleApiClient;
    static private final int SIGNIN_REQUEST_CODE = 25;

    static private GoogleSignInAccount authAccount;

    public static void loadAccountInfo(GoogleSignInResult _result) {
        if (_result.isSuccess()) {
            authAccount = _result.getSignInAccount();
            Log.d("MYTAG", "[AUTH] Signed in as " + authAccount.getDisplayName());
        }
    }

    public static int getRequestCode() { return SIGNIN_REQUEST_CODE; }

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
    }

    public static void auth(Context context) {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        ((FragmentActivity) context).startActivityForResult(signInIntent, SIGNIN_REQUEST_CODE);

    }

}
