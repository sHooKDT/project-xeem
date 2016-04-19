package shook.xeem.list_adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import shook.xeem.objects.AnswerObject;
import shook.xeem.objects.QuestionObject;
import shook.xeem.R;

public class AnswerAdapter extends BaseAdapter{

    Context context;
    QuestionObject loadedQuestion;
    LayoutInflater layoutInflater;

    public AnswerAdapter(Context _context, QuestionObject _question) {
        this.context = _context;
        this.loadedQuestion = _question;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return loadedQuestion.getAnswers().size();
    }

    @Override
    public Object getItem(int i) {
        return loadedQuestion.getAnswers().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final AnswerObject curAnswer = (AnswerObject) getItem(position);
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.edit_answer_list_item, parent, false);
        }

        ((TextView) view.findViewById(R.id.answer_text)).setText(curAnswer.getText());

        ((Button) view.findViewById(R.id.editButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MYTAG", "Your are trying to edit answer");
            }
        });

        ((Button) view.findViewById(R.id.delButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadedQuestion.rmAns(pos);
                notifyDataSetChanged();
            }
        });

        return  view;
    }

}
