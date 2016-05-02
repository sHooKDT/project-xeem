package shook.xeem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;

import shook.xeem.R;
import shook.xeem.services.XeemAuthService;

import static shook.xeem.services.XeemAuthService.getRequestCode;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        XeemAuthService.init(this);

        XeemAuthService.getPendingResult().setResultCallback(new ResultCallback<GoogleSignInResult>() {
            @Override
            public void onResult(GoogleSignInResult googleSignInResult) {
                if (googleSignInResult.isSuccess()) {
                    XeemAuthService.loadAccountInfo(googleSignInResult);
                    goMain();
                }
            }
        });
    }

    public void clickEnter (View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(XeemAuthService.getClient());
        startActivityForResult(signInIntent, getRequestCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case XeemAuthService.SIGNIN_REQUEST_CODE:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    XeemAuthService.loadAccountInfo(result);
                    goMain();
                } else { Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show(); }
                break;
        }
    }

    public void goMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

}
