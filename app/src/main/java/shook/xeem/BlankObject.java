package shook.xeem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlankObject {

    // Empty blank constructor
    public BlankObject (String title) {
        this.questions = new ArrayList<Question>();
        this.title = title;
    }

    // Blank variables
    private String title = "";
    private ArrayList<Question> questions;

    public int addQuestion(String text) {
        questions.add(new Question(text));
        return questions.size()-1;
    }

    public ArrayList<Question> getQuestions () {
        return this.questions;
    }

    public void addAnswer(int qindex, String text) {
        questions.get(qindex).addAnswer(text);
    }

    // Question object
    public class Question {

        // Constructor
        public Question (String text/*, List<String> atts*/) {
            this.text = text;
            this.answers = new ArrayList<Answer>();
//            this.attachments = new ArrayList<String>();
//            this.attachments.addAll(atts);
        }

        public String getText() {
            return this.text;
        }

        // Class variables
        private String text;
        private List<Answer> answers; // = new ArrayList<Answer>();
//        private List<String> attachments;

        public void addAnswer(String text) {
            answers.add(new Answer(text));
        }

        private class Answer {
            public Answer(String text) {
                this.text = text;
            }
            private String text;
        }

    }

}
