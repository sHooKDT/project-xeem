package shook.xeem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BlankEditAdapter extends BaseAdapter{

    static final int EDIT_QUESTION_REQUEST = 29;

    Context context;
    LayoutInflater lInflater;
    BlankObject loadedBlank;

    BlankEditAdapter(Context _context, BlankObject _blank) {
        this.context = _context;
        this.loadedBlank = _blank;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return loadedBlank.getQuestions().size();
    }

    @Override
    public Object getItem(int position) {
        return loadedBlank.getQuestions().get(position);
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

        QuestionObject q = (QuestionObject) getItem(position);

        ((TextView) view.findViewById(R.id.questionText)).setText(q.getText());

        // Click handler for REMOVE button
        Button removeBut = (Button) view.findViewById(R.id.removeButton);
        removeBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                loadedBlank.removeQuestion(pos);
                notifyDataSetChanged();
            }
        });

        // Click handler for EDIT button
        Button editBut = (Button) view.findViewById(R.id.editButton);
        editBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                editQuestion(pos);
                notifyDataSetChanged();
            }
        });


        return view;
    }

    public void editQuestion (int position) {
        Intent intent = new Intent(context, QuestionEdit.class);
        intent.putExtra("question", loadedBlank.getQuestions().get(position));
        ((Activity) context).startActivityForResult(intent, EDIT_QUESTION_REQUEST);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        Log.d("MYTAG", "Some activity sent result");
        if (requestCode == EDIT_QUESTION_REQUEST) {
            Log.d("MYTAG", "Question edited");
        }
    }

    public void addQuestion (String _title) {
        loadedBlank.addQuestion(new QuestionObject(_title));
    }

}
