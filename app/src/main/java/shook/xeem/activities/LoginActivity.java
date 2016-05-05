package shook.xeem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import shook.xeem.R;
import shook.xeem.services.XeemAuthService;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSharedPreferences("XeemPrefs", 0).edit().putBoolean("isLoginCached", true).commit();

        XeemAuthService.init(this, new XeemAuthService.signinSuccess() {
            @Override
            public void onSuccess() {
                goMain();
            }
        });


        if (XeemAuthService.isLogged()) {
            XeemAuthService.silent();
        } else {
            // Notify user need to manually sign in
            Toast.makeText(this, "Войдите в приложение", Toast.LENGTH_SHORT).show();
        }


    }

    public void clickEnter (View v) {
        XeemAuthService.manual(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case XeemAuthService.SIGNIN_REQUEST_CODE:
                XeemAuthService.setManualResult(data);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("XEEMDBG", "Destroying login activity");
    }

    private void goMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }

}
