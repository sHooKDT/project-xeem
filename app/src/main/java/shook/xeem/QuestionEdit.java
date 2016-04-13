package shook.xeem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class QuestionEdit extends AppCompatActivity {

    QuestionObject currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_edit);

        Intent startIntent = getIntent();
        currentQuestion = startIntent.getParcelableExtra("question");

        Log.d("MYTAG", String.format("Question \" %s \" loaded for edit", currentQuestion.getText()));

    }

    public void finishEdit (View v) {
        Intent result = new Intent()
                .putExtra("question", currentQuestion);
        setResult(29, result);
        finish();
    }
}
