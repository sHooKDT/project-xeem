package shook.xeem;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addBlankClick (View v) {

        BlankObject blankToEdit = generateBlank();
        Intent editIntent = new Intent(this, BlankEditActivity.class);

        editIntent.putExtra("blank", blankToEdit);

        startActivity(editIntent);
    }

    public BlankObject generateBlank () {
        BlankObject result = new BlankObject("Test title");

        int curQ = result.addQuestion("Blank question number one");
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion("Question number two");
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion("Long long long long long long question");
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion("Ultra mega super long long long long long question");
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        return result;
    }

}
