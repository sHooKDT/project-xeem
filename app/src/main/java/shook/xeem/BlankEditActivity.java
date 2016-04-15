package shook.xeem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONObject;

import shook.xeem.list_adapters.BlankEditAdapter;

public class BlankEditActivity extends AppCompatActivity {

    BlankEditAdapter blankAdapter;
    public BlankObject currentBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_edit);

        if (getIntent().getAction() == "EDIT") {

            currentBlank = getIntent().getParcelableExtra("blank");
        }
        else if (getIntent().getAction() == "ADD") {

        }

        ListView questionsList = (ListView) findViewById(R.id.questionsList);

        currentBlank = getIntent().getParcelableExtra("blank");
        blankAdapter = new BlankEditAdapter(this, currentBlank);
        questionsList.setAdapter(blankAdapter);

        JSONObject myjsonblank = new JSONObject();
        try {
            myjsonblank.put("blank", currentBlank.toJSON());
        } catch (Exception e) {e.printStackTrace();}
        Log.d("MYTAG", myjsonblank.toString());

        findViewById(R.id.addQuestionButton).setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                addQuestionClick(v);
            }
        });
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        Log.d("MYTAG", "Some activity sent result");
        if (requestCode == 29) {
            Log.d("MYTAG", "Question edited");
        }
    }

    public void finishEdit (View v) {
        Intent intent = new Intent()
                .putExtra("blank", currentBlank);
        setResult(RESULT_OK, intent);
        finish();
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
                blankAdapter.notifyDataSetChanged();
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

}
