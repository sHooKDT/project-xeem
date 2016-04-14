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
    static JSONObject blanksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        blanksList = ApiWorker.getBlanksList();

    }

    public void addBlankClick (View v) {

        BlankObject blankToEdit = generateBlank();
        Intent editIntent = new Intent(this, BlankEditActivity.class);

        editIntent.putExtra("blank", blankToEdit);

        startActivityForResult(editIntent, EDIT_BLANK_REQUEST);
    }

    // To delete
    public BlankObject generateBlank () {
        BlankObject result = new BlankObject("Test title");

        int curQ = result.addQuestion(new QuestionObject("Blank question number one"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion(new QuestionObject("Question number two"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion(new QuestionObject("Long long long long long long question"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion(new QuestionObject("Ultra mega super long long long long long question"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        return result;
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        // Edited blank callback
        if (requestCode == EDIT_BLANK_REQUEST) {
            BlankObject _blank = result.getParcelableExtra("blank");
            try {
                Log.d("MYTAG", String.format("Blank edited: %s", _blank.getJSON().toString()));
            } catch (Exception e) {e.printStackTrace();}
        }
    }

}
