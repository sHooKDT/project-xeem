package shook.xeem.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.Date;

import shook.xeem.R;
import shook.xeem.interfaces.testPassHolder;
import shook.xeem.list_adapters.BlankPassRecyclerAdapter;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;
import shook.xeem.objects.TestResult;
import shook.xeem.services.XeemAuthService;

public class PassTestActivity extends AppCompatActivity implements testPassHolder {

    private BlankObject loadedBlank;
    private RecyclerViewPager testPassRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_test);

        loadedBlank = BlankObject.fromJSON(getIntent().getStringExtra("blank_to_pass"));
        // BAD PATTERN: making default checked item not match with correct (if it's 0)
        for (QuestionObject x : loadedBlank.getQuestions())
            x.setChecked(-2);

        // View
        testPassRecycler = (RecyclerViewPager) findViewById(R.id.test_pass_questions_list);
        // Adapter
        BlankPassRecyclerAdapter testPassAdapter = new BlankPassRecyclerAdapter(this);
        // Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        // Apply
        if (testPassRecycler != null) {
            testPassRecycler.setAdapter(testPassAdapter);
            testPassRecycler.setLayoutManager(layoutManager);
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Закончить прохождение?");
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("XEEMDBG", "[PASS] Returning to pass");
            }
        });
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("XEEMDBG", "[PASS] Pass ended");
                score();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public BlankObject getBlank() {
        return loadedBlank;
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

    public void scroll_next() {
        testPassRecycler.smoothScrollToPosition(testPassRecycler.getCurrentPosition() + 1);
    }

    public void scroll_previous() {
        testPassRecycler.smoothScrollToPosition(testPassRecycler.getCurrentPosition() - 1);
    }

    public void score() {

        // Making counters null
        int points = 0, maxpoints = 0, qright = 0, qcount = 0;

        // Counting right answers, points
        for (QuestionObject x : loadedBlank.getQuestions()) {
            if (x.getChecked() == x.getCorrect()) {
                points += x.getPoints();
                qright ++;
            }
            maxpoints += x.getPoints();
            qcount ++;
        }

        // Building final result object
        TestResult result = new TestResult();
        result.userid = XeemAuthService.getUserId();
        result.testid = getBlank().getID();
        result.testetag = getBlank().getEtag();
        result.date = (new Date()).getTime();
        result.max_points = maxpoints;
        result.max_questions = qcount;
        result.points = points;
        result.right_questions = qright;

        Intent resultIntent = new Intent()
                .putExtra("result", result.toJSON());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
