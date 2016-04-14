package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.ArrayList;
import java.util.Random;


public class BlankObject implements Parcelable {

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

    // Public constructors
    public                  BlankObject (String _title, boolean _public) {
        this.bTitle = _title;
        this.bAuthor = "Me and my cat";
        this.bDate = new Date().getTime();
        this.bIsPublic = _public;
        this.bID = new Random().nextLong();
        this.bQuestions = new ArrayList<QuestionObject>();
    }
    public                  BlankObject (String _json) {
        Gson g = new Gson();
        BlankObject result = g.fromJson(_json, BlankObject.class);
        this.bTitle = result.getTitle();
        this.bDate = result.getDate();
        this.bAuthor = result.getAuthor();
        this.bIsPublic = result.isPublic();
        this.bID = result.getID();
        this.bQuestions = result.getQuestions();
    }
    protected               BlankObject (Parcel in) {
        bTitle = in.readString();
        bDate = in.readLong();
        bID = in.readLong();
        bIsPublic = in.readByte() != 0;
        bAuthor = in.readString();
        bQuestions = in.createTypedArrayList(QuestionObject.CREATOR);
    }


    // Parcelable methods
    public int              describeContents() {
        return 0;
    }
    public static final     Creator<BlankObject> CREATOR = new Creator<BlankObject>() {
        @Override
        public BlankObject createFromParcel(Parcel in) {
            return new BlankObject(in);
        }

        @Override
        public BlankObject[] newArray(int size) {
            return new BlankObject[size];
        }
    };
    public void             writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTitle());
        dest.writeLong(getDate());
        dest.writeLong(getID());
        dest.writeByte((byte) (isPublic() ? 1 : 0));
        dest.writeString(getAuthor());
        dest.writeTypedList(getQuestions());
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
}
