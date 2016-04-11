package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import org.bson.Document;

import java.util.ArrayList;

public class QuestionObject implements Parcelable {

    // Question contents
    private String qText;
    private String qPic;
    private int qCorrect;
    private int qPoints;
    private ArrayList<AnswerObject> qAnswers;

    // JSON converter
    public Document toDoc() {

        Document[] _answers = new Document[getAnswers().size()];
        for (int i = 0; i < _answers.length; i++) {
            _answers[i] = getAnswers().get(i).toDoc();
        }

        Document result = new Document()
                .append("text", getText())
                .append("pic", getPic())
                .append("correct", getCorrect())
                .append("points", getPoints())
                .append("answers", _answers);

        return result;
    }

    // Public interface
    public String       getText() {
        return this.qText;
    }
    public void         addAnswer(String _text) {
        getAnswers().add(new AnswerObject(_text));
    }
    public void         removeAnswer(int position) {
        getAnswers().remove(position);
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
        dest.writeString(getText());
        dest.writeString(getPic());
        dest.writeInt(getCorrect());
        dest.writeInt(getPoints());
        dest.writeTypedList(getAnswers());
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


    // Getters and setters
    public String getPic() {
        return qPic;
    }
    public int getCorrect() {
        return qCorrect;
    }
    public int getPoints() {
        return qPoints;
    }
    public ArrayList<AnswerObject> getAnswers() {
        return qAnswers;
    }
}
