package shook.xeem;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;


public class MainActivity extends Activity {

    static final int EDIT_BLANK_REQUEST = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String gotblank = getIntent().getStringExtra("blanklist");
        Log.d("MYTAG", String.format("Got blank list: %s", gotblank));
    }

    public void addBlankClick (View v) {

        BlankObject blankToEdit = BlankObject.generateSome();
        Intent editIntent = new Intent(this, BlankEditActivity.class);

        editIntent.putExtra("blank", blankToEdit);

        startActivityForResult(editIntent, EDIT_BLANK_REQUEST);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        // Edited blank callback
        if (requestCode == EDIT_BLANK_REQUEST) {
            BlankObject _blank = result.getParcelableExtra("blank");
            try {
                Log.d("MYTAG", String.format("Blank edited: %s", _blank.toJSON()));
            } catch (Exception e) {e.printStackTrace();}
        }
    }

}
