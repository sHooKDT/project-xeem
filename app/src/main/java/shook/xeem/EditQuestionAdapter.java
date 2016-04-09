package shook.xeem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditQuestionAdapter extends BaseAdapter{

    Context context;
    LayoutInflater lInflater;
    BlankObject curBlank;

    EditQuestionAdapter(Context _context, BlankObject _blank) {
        this.context = _context;
        this.curBlank = _blank;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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

        BlankObject.Question q = (BlankObject.Question) getItem(position);

        ((TextView) view.findViewById(R.id.questionText)).setText(q.getText());
        Button removeBut = (Button) view.findViewById(R.id.removeButton);
        removeBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                removeQuestion(pos);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void removeQuestion (int position) {
        curBlank.removeQuestion(position);
    }

    public void addQuestion (String _title) {
        curBlank.addQuestion(_title);
    }

}
