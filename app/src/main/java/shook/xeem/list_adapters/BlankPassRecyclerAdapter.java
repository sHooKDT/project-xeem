package shook.xeem.list_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import shook.xeem.R;
import shook.xeem.objects.AnswerObject;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;

public class BlankPassRecyclerAdapter extends RecyclerView.Adapter<BlankPassRecyclerAdapter.ViewHolder> {

    BlankObject loadedBlank;
    Context context;

    public BlankPassRecyclerAdapter(Context _context, BlankObject _blank) {
        this.loadedBlank = _blank;
        this.context = _context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_question_passing_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QuestionObject question = loadedBlank.getQuestions().get(position);

        holder.questionNumberText.setText(String.format("Вопрос %d", position+1));
        holder.questionText.setText(question.getText());
        holder.answersList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        holder.answersList.setAdapter(new ArrayAdapter<AnswerObject>(context, android.R.layout.simple_list_item_single_choice, question.getAnswers()));

    }

    @Override
    public int getItemCount() {
        return loadedBlank.getQuestions().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView questionNumberText;
        private TextView questionText;
        private ListView answersList;
        private Button skipQuestionButton;

        public ViewHolder(View itemView) {
            super(itemView);
            this.questionNumberText = (TextView) itemView.findViewById(R.id.question_number_text);
            this.questionText = (TextView) itemView.findViewById(R.id.question_text);
            this.answersList = (ListView) itemView.findViewById(R.id.answers_list);
            this.skipQuestionButton = (Button) itemView.findViewById(R.id.skip_question_button);
        }

    }
}
