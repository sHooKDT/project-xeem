package shook.xeem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnswersEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers_edit);

        Intent startIntent = getIntent();
        int questionPos = startIntent.getIntExtra("position", 0);

    }
}
