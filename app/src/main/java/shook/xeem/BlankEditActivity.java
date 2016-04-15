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
    BlankObject.Factory newBlankFactory = new BlankObject.Factory();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_edit);

        ListView questionsList = (ListView) findViewById(R.id.questionsList);

        if (getIntent().getAction() == "EDIT") {
            blankAdapter = new BlankEditAdapter(this, currentBlank);
            currentBlank = BlankObject.fromJSON(getIntent().getStringExtra("blank_to_edit"));
            blankAdapter = new BlankEditAdapter(this, currentBlank);
        } else if (getIntent().getAction() == "ADD") {
            //BlankObject.Factory newBlankFactory = new BlankObject.Factory();
            newBlankFactory.fillExample();
//            blankAdapter = new BlankEditAdapter(this, newBlankFactory.build());
            blankAdapter = new BlankEditAdapter(this, newBlankFactory.getPreview());
        }
        questionsList.setAdapter(blankAdapter);

        findViewById(R.id.addQuestionButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                .putExtra("edited_blank", newBlankFactory.build().toJSON());
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
                newBlankFactory.putQuestion(new QuestionObject(input.getText().toString()));
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
