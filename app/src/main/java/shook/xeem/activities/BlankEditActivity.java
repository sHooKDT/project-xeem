package shook.xeem.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.Objects;

import shook.xeem.R;
import shook.xeem.fragments.QuestionEditFragment;
import shook.xeem.interfaces.BlankEditor;
import shook.xeem.list_adapters.BlankEditAdapter;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;
import shook.xeem.services.XeemAuthService;

public class BlankEditActivity extends AppCompatActivity implements BlankEditor {

    private BlankEditAdapter blankAdapter;
    private BlankObject.Builder blankBuilder = BlankObject.newBuilder();
    private EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_edit);
        initView();

        if (!XeemAuthService.isOnline()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Уведомление");
            builder.setMessage("Вы не сможете сохранить бланк, пока не подключитесь к интернету. \n Продолжить редактирование?");
            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishEdit(null);
                }
            });
            builder.create().show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        editTitle.setText(blankBuilder.build().getTitle());
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Закончить редактирование?");
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("XEEMDBG", "[EDIT] Return to edit");
            }
        });
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("XEEMDBG", "[EDIT] Edit ended");
                finishEdit(null);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initView() {
        // Initialisation of the view stuff
        editTitle = (EditText) findViewById(R.id.editTitle);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                blankBuilder.setTitle(editTitle.getText().toString());
            }
        });

        ListView questionsList = (ListView) findViewById(R.id.questionsList);
        if (questionsList != null) {
            View addFooter = getLayoutInflater().inflate(R.layout.blank_edit_footer, null);
            addFooter.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addQuestionClick(null);
                }
            });
            questionsList.addFooterView(addFooter);
            View addHeader = getLayoutInflater().inflate(R.layout.blank_edit_header, null);
            questionsList.addHeaderView(addHeader);
            questionsList.setHeaderDividersEnabled(false);
            questionsList.setFooterDividersEnabled(false);
        }
        if (Objects.equals(getIntent().getAction(), "EDIT")) {
            blankBuilder = BlankObject.fromJSON(getIntent().getStringExtra("blank_to_edit")).getBuilder();
        } else if (Objects.equals(getIntent().getAction(), "ADD")) {
            blankBuilder.setAuthor(XeemAuthService.getUserId());
        }

        blankAdapter = new BlankEditAdapter(this);

        if (questionsList != null) {
            questionsList.setAdapter(blankAdapter);
        }

    }

    public void finishEdit(@Nullable View v) {
        Intent intent = new Intent()
                .putExtra("edited_blank", blankBuilder.build().toJSON());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void addQuestionClick(@Nullable View v) {
        blankBuilder.putQuestion(new QuestionObject(""));
        blankAdapter.notifyDataSetChanged();
    }


    public void applyQuestionEdit(int index, QuestionObject edited) {
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.remove(getFragmentManager().findFragmentById(R.id.question_edit_fragment_container));
//        ft.commit();
        blankBuilder.replaceQuestion(index, edited);
    }

    @Override
    public BlankObject.Builder getBuilder() {
        return blankBuilder;
    }

    @Override
    public void startQuestionEdit(int index, QuestionObject _question) {
        QuestionEditFragment editFragment = new QuestionEditFragment();
        Bundle data = new Bundle();
        data.putInt("index", index);
        data.putString("question", (new Gson()).toJson(_question));
        editFragment.setArguments(data);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.question_edit_fragment_container, editFragment)
                .addToBackStack("question-edit")
                .commit();
        FrameLayout fragment_frame = (FrameLayout) findViewById(R.id.question_edit_fragment_container);
        if (fragment_frame != null) {
            fragment_frame.setVisibility(View.VISIBLE);
        }
    }
}
