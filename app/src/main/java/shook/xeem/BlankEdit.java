package shook.xeem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class BlankEdit extends AppCompatActivity {

    BlankObject testBlank = generateBlank();
    ListView questionsList = (ListView) findViewById(R.id.questionsList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_edit);

//        questionsList.setAdapter();
    }

//    class blankAdapter extends ArrayAdapter<BlankObject> {
//
//    }

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

        return result;
    }
}
