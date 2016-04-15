package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.ArrayList;
import java.util.Random;


public class BlankObject {

    // Blank contents
    @SerializedName("title") private String                             bTitle;
    @SerializedName("date") private long                                bDate;
    @SerializedName("id") private long                                  bID;
    @SerializedName("public") private boolean                           bIsPublic;
    @SerializedName("author") private String                            bAuthor;
    @SerializedName("questions") private ArrayList<QuestionObject>      bQuestions;


    // Delete as fast as possible
    public static BlankObject generateSome() {
        BlankObject result = new BlankObject("Test title", false);

        int curQ = result.addQuestion(new QuestionObject("Blank question number one"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion(new QuestionObject("Question number two"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion(new QuestionObject("Long long long long long long question"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        curQ = result.addQuestion(new QuestionObject("Ultra mega super long long long long long question"));
        result.addAnswer(curQ, "Answer 1");
        result.addAnswer(curQ, "Answer 2");
        result.addAnswer(curQ, "Answer 3");

        return result;
    }
    public void addAnswer(int ques, String _text) {
        bQuestions.get(ques).putAns(_text);
    }

    public String toJSON () {
        return (new Gson()).toJson(this);
    }
    public static BlankObject fromJSON (String _json) { return (new Gson()).fromJson(_json, BlankObject.class); }

    // Public constructors
    public                  BlankObject (String _title, boolean _public) {
        this.bTitle = _title;
        this.bAuthor = "Me and my cat";
        this.bDate = new Date().getTime();
        this.bIsPublic = _public;
        this.bID = new Random().nextLong();
        this.bQuestions = new ArrayList<QuestionObject>();
    }

    // Getters && setters
    public String getTitle() {
        return bTitle;
    }
    public long getDate() {
        return bDate;
    }
    public long getID() {
        return bID;
    }
    public boolean isPublic() {
        return bIsPublic;
    }
    public String getAuthor() {
        return bAuthor;
    }
    public ArrayList<QuestionObject> getQuestions() { return bQuestions; }
    public void setQuestions(ArrayList<QuestionObject> _questions) {
        this.bQuestions = _questions;
    }
    public int addQuestion(QuestionObject _question) {
        bQuestions.add(_question);
        return bQuestions.indexOf(_question);
    }
    public void removeQuestion(int position) {bQuestions.remove(position);}

    public static class Factory {

        BlankObject factored_blank;

        Factory () {
            factored_blank = new BlankObject("", false);
        }

        public void fillExample() {
            // just for example !!!!!!!!!
            this.factored_blank = BlankObject.fromJSON("{\"title\": \"Very hard IQ test\", \"date\": 14749221, \"id\": 3242112411, \"public\": false, \"author\": \"John Smith\", \"questions\": [{\"text\": \"How many legs does horse have?\", \"pic\": \"\", \"correct\": 2, \"points\": 5, \"answers\": [{\"text\": \"I dont know\", \"pic\": \"\"}, {\"text\": \"Maybe three?\", \"pic\": \"\"}, {\"text\": \"Exactly four\", \"pic\": \"\"} ] } ] }");
        }

        public void loadJSON(String _json) {
            this.factored_blank = BlankObject.fromJSON(_json);
        }

        public void putQuestion(QuestionObject _question) {
            this.factored_blank.bQuestions.add(_question);
        }

        public BlankObject getPreview() {
            try {
                return (BlankObject) factored_blank.clone();
            } catch (Exception e) {e.printStackTrace();}
                return factored_blank;
        }

        public BlankObject build() {
            return factored_blank;
        }

    }
}
