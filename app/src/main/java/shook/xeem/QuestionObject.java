package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class QuestionObject implements Parcelable {

    // Question contents
    private String qText;
    private String qPic;
    private int qCorrect;
    private int qPoints;
    private ArrayList<AnswerObject> qAnswers;


    // Public interface
    public String       getText() {
        return this.qText;
    }
    public void         addAnswer(String _text) {
        qAnswers.add(new AnswerObject(_text));
    }
    public void         removeAnswer(int position) {
        qAnswers.remove(position);
    }


    // Public constructors
    public QuestionObject(String _text) {
        this.qText = _text;
        this.qAnswers = new ArrayList<AnswerObject>();
    }
    protected QuestionObject(Parcel in) {
        qText = in.readString();
        qPic = in.readString();
        qCorrect = in.readInt();
        qPoints = in.readInt();
        qAnswers = in.createTypedArrayList(AnswerObject.CREATOR);
    }


    // Parcelable methods
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(qText);
        dest.writeString(qPic);
        dest.writeInt(qCorrect);
        dest.writeInt(qPoints);
        dest.writeTypedList(qAnswers);
    }
    public int describeContents() {
        return 0;
    }
    public static final Creator<QuestionObject> CREATOR = new Creator<QuestionObject>() {
        @Override
        public QuestionObject createFromParcel(Parcel in) {
            return new QuestionObject(in);
        }

        @Override
        public QuestionObject[] newArray(int size) {
            return new QuestionObject[size];
        }
    };


}
