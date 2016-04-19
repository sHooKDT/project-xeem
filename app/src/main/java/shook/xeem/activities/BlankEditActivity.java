package shook.xeem.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.Objects;

import shook.xeem.QuestionEditFragment;
import shook.xeem.R;
import shook.xeem.XeemAuthService;
import shook.xeem.list_adapters.BlankEditAdapter;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;

public class BlankEditActivity extends AppCompatActivity {

    BlankEditAdapter blankAdapter;
    BlankObject.Factory newBlankFactory = new BlankObject.Factory();
    EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_edit);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                newBlankFactory.setTitle(editTitle.getText().toString());
            }
        });

        ListView questionsList = (ListView) findViewById(R.id.questionsList);
        View addFooter = getLayoutInflater().inflate(R.layout.blank_edit_footer, null);
        addFooter.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestionClick(view);
            }
        });
        questionsList.addFooterView(addFooter);
        View addHeader = getLayoutInflater().inflate(R.layout.blank_edit_header, null);
        questionsList.addHeaderView(addHeader);
        questionsList.setHeaderDividersEnabled(false);
        questionsList.setFooterDividersEnabled(false);

        if (Objects.equals(getIntent().getAction(), "EDIT")) {
            newBlankFactory.loadJSON(getIntent().getStringExtra("blank_to_edit"));
        } else if (Objects.equals(getIntent().getAction(), "ADD")) {
            newBlankFactory.withAuthorID(XeemAuthService.getAccount().getId());
        }

        blankAdapter = new BlankEditAdapter(this, newBlankFactory);
        questionsList.setAdapter(blankAdapter);

        findViewById(R.id.addQuestionButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addQuestionClick(v);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        editTitle.setText(newBlankFactory.getPreview().getTitle());
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        Log.d("MYTAG", "Some activity sent result");
    }

    @Override
    public void onBackPressed() {
        finishEdit(null);
    }

    public void finishEdit (@Nullable View v) {



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

    public void receiveEditedQuestion (int index, QuestionObject edited) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(getFragmentManager().findFragmentById(R.id.question_edit_fragment_container));
        newBlankFactory.replaceQuestion(index, edited);
    }

    public void editQuestionClick (QuestionObject _question) {
//        QuestionEditFragment editFragment = new QuestionEditFragment();
//        Bundle data = new Bundle();
//        data.putString("editable", (new Gson()).toJson(_question));
//        editFragment.setArguments(data);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.question_edit_fragment_container, editFragment).commit();
    }

}
