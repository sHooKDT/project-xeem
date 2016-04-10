package shook.xeem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BlankEditAdapter extends BaseAdapter{

    Context context;
    LayoutInflater lInflater;
    BlankObject curBlank;

    BlankEditAdapter(Context _context, BlankObject _blank) {
        this.context = _context;
        this.curBlank = _blank;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BlankObject getBlank() { return curBlank; }

    @Override
    public int getCount() {
        return curBlank.questionCount();
    }

    @Override
    public Object getItem(int position) {
        return curBlank.getQuestion(position);
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
            view = lInflater.inflate(R.layout.edit_question_layout, parent, false);
        }

        QuestionObject q = (QuestionObject) getItem(position);

        ((TextView) view.findViewById(R.id.questionText)).setText(q.getText());

        // Click handler for REMOVE button
        Button removeBut = (Button) view.findViewById(R.id.removeButton);
        removeBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                curBlank.removeQuestion(pos);
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
        Intent intent = new Intent(context, AnswersEdit.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    public void addQuestion (String _title) {
        curBlank.addQuestion(_title);
    }

}
