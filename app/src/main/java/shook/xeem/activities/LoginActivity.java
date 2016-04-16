package shook.xeem.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import shook.xeem.R;
import shook.xeem.XeemApiService;
import shook.xeem.XeemAuthService;
import shook.xeem.objects.BlankObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        XeemAuthService.init(this);

    }

    public void clickEnter (View v) {
        XeemAuthService.auth(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XeemAuthService.getRequestCode()) {
            XeemAuthService.loadAccountInfo(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
            if (Auth.GoogleSignInApi.getSignInResultFromIntent(data).isSuccess()) {
                Intent goMain = new Intent(this, MainActivity.class);
                startActivity(goMain);
            }
        }
    }

}
