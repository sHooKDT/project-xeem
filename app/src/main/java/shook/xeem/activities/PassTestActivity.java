package shook.xeem.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import shook.xeem.R;
import shook.xeem.list_adapters.BlankPassRecyclerAdapter;
import shook.xeem.objects.BlankObject;

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
}
