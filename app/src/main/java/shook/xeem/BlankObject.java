package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.Date;
import java.util.ArrayList;


public class BlankObject implements Parcelable {

    // Blank contents
    private String                  bTitle;
    private Date                    bDate;
    private long                    bID;
    private boolean                 bIsPublic;
    private String                  bAuthor;
    private ArrayList<Question>     bQuestions;

    // Public interface
    public int              addQuestion(String text) {
        bQuestions.add(new Question(text));
        return bQuestions.size()-1;
    }
    public void             removeQuestion(int position) {
        this.bQuestions.remove(position);
    }
    public Question         getQuestion (int position) {
        return this.bQuestions.get(position);
    }
    public void             addAnswer(int qPos, String text) {
        bQuestions.get(qPos).addAnswer(text);
    }
    public int              questionCount() {
        return bQuestions.size();
    }

    // Functions for using parcels
    public int              describeContents() {
        return 0;
    }
    public void             writeToParcel(Parcel dest, int flags) {
        dest.writeString(bTitle);
        dest.writeList(bQuestions);
    }
    public static final     Parcelable.Creator<BlankObject> CREATOR = new Parcelable.Creator<BlankObject>() {
        public BlankObject createFromParcel(Parcel in) {
            return new BlankObject(in);
        }
        public BlankObject[] newArray(int size) {
            return new BlankObject[size];
        }
    };


    // Public constructors
    public                  BlankObject (String title) {
        this.bQuestions = new ArrayList<Question>();
        this.bTitle = title;
    }
    public                  BlankObject (Parcel parcel) {
    }

    // Question object
    public class            Question {

        // Question contents
        private String                  qText;
        private String                  qPic;
        private int                     qCorrect;
        private int                     qPoints;
        private ArrayList<Answer>       qAnswers;

        public String getText() {
            return this.qText;
        }

        public void addAnswer(String _text) {
            qAnswers.add(new Answer(_text));
        }
        public void removeAnswer(int position) { qAnswers.remove(position); }

        // Constructor
        public Question (String _text) {
            this.qText = _text;
            this.qAnswers = new ArrayList<Answer>();
        }

        // Answer class
        private class Answer {
            // Constructors
            public Answer(String _text) {
                this.aText = _text;
                this.aPic = null;
            }
            public Answer(String _text, String _pic) {
                this.aText = _text;
                this.aPic = _pic;
            }
            // Variables
            private String aText;
            private String aPic;
        }

    }

}
