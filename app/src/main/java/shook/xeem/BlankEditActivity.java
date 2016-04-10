package shook.xeem;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class BlankEditActivity extends AppCompatActivity {

    BlankEditAdapter blankAdapter;
    public BlankObject currentBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_edit);

        ListView questionsList = (ListView) findViewById(R.id.questionsList);

        currentBlank = getIntent().getParcelableExtra("blank");
        blankAdapter = new BlankEditAdapter(this, currentBlank);
        questionsList.setAdapter(blankAdapter);

        findViewById(R.id.addQuestionButton).setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                addQuestionClick(v);
                blankAdapter.notifyDataSetChanged();
            }
        });

    }

    public void addQuestionClick (View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type the question");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                blankAdapter.addQuestion(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
