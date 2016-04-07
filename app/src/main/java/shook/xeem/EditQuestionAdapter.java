package shook.xeem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditQuestionAdapter extends BaseAdapter{

    Context context;
    LayoutInflater lInflater;
    ArrayList<BlankObject.Question> questions;

    EditQuestionAdapter(Context _context, ArrayList<BlankObject.Question> _questions) {
        this.context = _context;
        this.questions = _questions;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.edit_question_layout, parent, false);
        }

        BlankObject.Question q = getQuestion(position);

        ((TextView) view.findViewById(R.id.questionText)).setText(q.getText());

        return view;
    }

    BlankObject.Question getQuestion(int position) {
        return((BlankObject.Question) getItem(position));
    }


}
