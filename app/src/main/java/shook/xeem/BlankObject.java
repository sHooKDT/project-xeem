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
    private List<Question> questions;

    public void setTitle (String newtitle) {
        this.title = newtitle;
    }

    public void addQuestion (String text, String[] atts) {
        this.questions.add(new Question(text, Arrays.asList(atts)));
    }




    // Question object
    private class Question {

        // Constructor
        public Question (String text, List<String> atts) {
            this.text = text;
            this.answers = new ArrayList<Answer>();
            this.attachments = new ArrayList<String>();
            this.attachments.addAll(atts);
        }

        // Class variables
        private String text;
        private List<Answer> answers; // = new ArrayList<Answer>();
        private List<String> attachments;

        public int addAnswer(String text) {
            answers.add(new Answer(text));
            return answers.size();
        }

        private class Answer {
            public Answer(String text) {
                this.text = text;
            }
            private String text;
        }

    }

}
