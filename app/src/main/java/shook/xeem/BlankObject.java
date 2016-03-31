package shook.xeem;

import java.util.ArrayList;
import java.util.List;

public class BlankObject {

    private String title = "";

    private List<Question> questions = new ArrayList<Question>();

    public int addQuestion(Question target) {
        questions.add(target);
        return 0;
    }

    class Question {

        public void Question (String text) {
            this.text = text;
            this.answers = new ArrayList<Answer>();
        }

        private String text;

        private List<Answer> answers; // = new ArrayList<Answer>();

        public int addAnswer(String text) {
            answers.add(new Answer(text));
            return answers.size();
        }

        class Answer {
            public Answer(String text) {
                this.text = text;
            }
            private String text;
        }

    }

}
