package shook.xeem.list_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import shook.xeem.R;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;
import shook.xeem.testPassHolder;

public class BlankPassRecyclerAdapter extends RecyclerView.Adapter<BlankPassRecyclerAdapter.ViewHolder> {

    BlankObject loadedBlank;
    testPassHolder context;

    public BlankPassRecyclerAdapter(testPassHolder _context) {
        this.context = _context;
        this.loadedBlank = context.getBlank();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_question_passing_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int pos = holder.getAdapterPosition();

        QuestionObject question = loadedBlank.getQuestions().get(position);

        holder.questionNumberText.setText(String.format("Вопрос %d", position + 1));
        holder.questionText.setText(question.getText());
        holder.answersList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        holder.answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadedBlank.getQuestions().get(pos).setChecked(i);
            }
        });

        holder.skipQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XEEMDBG", "Skip question");
            }
        });

        holder.answersList.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        holder.answersList.setAdapter(new ArrayAdapter<>((Context) context, android.R.layout.simple_list_item_single_choice, question.getAnswers()));

    }

    @Override
    public int getItemCount() {
        if (loadedBlank.getQuestions() != null) {
            return loadedBlank.getQuestions().size();
        } else {
            return 0;
        }
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
