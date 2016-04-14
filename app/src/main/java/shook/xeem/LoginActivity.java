package shook.xeem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            // XeemApiService.updateBlanks(this);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void clickEnter (View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void testDB (View v) throws Exception {

        XeemApiService.postBlank(BlankObject.generateSome());

    }

}
