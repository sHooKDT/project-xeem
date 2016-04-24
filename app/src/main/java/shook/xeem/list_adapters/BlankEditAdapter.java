package shook.xeem.list_adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import shook.xeem.BlankEditor;
import shook.xeem.QuestionEditFragment;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;
import shook.xeem.R;

public class BlankEditAdapter extends BaseAdapter{

    static final int EDIT_QUESTION_REQUEST = 29;

    Context context;
    LayoutInflater lInflater;
    BlankObject.Builder loadedBuilder;

    public BlankEditAdapter(Context _context) {
        this.context = _context;
        this.loadedBuilder = ((BlankEditor) context).getBuilder();
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return loadedBuilder.build().getQuestions().size();
    }

    @Override
    public Object getItem(int position) {
        return loadedBuilder.build().getQuestions().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.edit_question_list_item, parent, false);
        }

        final QuestionObject q = (QuestionObject) getItem(position);

        ((TextView) view.findViewById(R.id.questionText)).setText(q.getText());

        // Click handler for REMOVE button
        Button removeBut = (Button) view.findViewById(R.id.removeButton);
        removeBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                loadedBuilder.rmQuestion(pos);
                notifyDataSetChanged();
            }
        });

        // Click handler for EDIT button
        Button editBut = (Button) view.findViewById(R.id.editButton);
        editBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                ((BlankEditor) context).startQuestionEdit(pos, q);
                notifyDataSetChanged();
            }
        });

        return view;
    }

//    public void editQuestion (int position) {
//        final QuestionObject editable = loadedBuilder.build().getQuestions().get(position);
//        AppCompatActivity activity = (AppCompatActivity) context;
//        QuestionEditFragment editFragment = new QuestionEditFragment();
//        Bundle data = new Bundle();
//        data.putInt("index", position);
//        data.putString("editable", (new Gson()).toJson(editable));
//        editFragment.setArguments(data);
//        activity.getSupportFragmentManager().beginTransaction()
//                .add(R.id.question_edit_fragment_container, editFragment)
//                .addToBackStack("question-edit")
//                .commit();
//        FrameLayout fragment_frame = (FrameLayout) ((AppCompatActivity) context).findViewById(R.id.question_edit_fragment_container);
//        fragment_frame.setVisibility(View.VISIBLE);
//    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }
}
