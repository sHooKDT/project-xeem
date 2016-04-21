package shook.xeem.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import shook.xeem.R;
import shook.xeem.list_adapters.BlankPassRecyclerAdapter;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;

public class PassTestActivity extends AppCompatActivity {

    private BlankObject loadedBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_test);

        loadedBlank = BlankObject.fromJSON(getIntent().getStringExtra("blank_to_pass"));
        Log.d("MYTAG", "Got for pass this test: " + loadedBlank.toJSON());

        RecyclerView testPassRecycler = (RecyclerView) findViewById(R.id.test_pass_questions_list);
        BlankPassRecyclerAdapter passadapter = new BlankPassRecyclerAdapter(this, loadedBlank);

        testPassRecycler.setAdapter(passadapter);
        testPassRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_test_activity_menu, menu);
        menu.findItem(R.id.finishAttemptButton).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                score();
                return true;
            }
        });
        return true;
    }

    private int score() {

        int result = 0;

        for (QuestionObject x : loadedBlank.getQuestions()) {
            if (x.getChecked() == x.getCorrect()) {
//                result += x.getPoints();
                result += 1;
            }
        }

        Toast.makeText(this, String.format("You scored %d of %d", result, loadedBlank.getQuestions().size()), Toast.LENGTH_SHORT).show();

        return 0;
    }
}
