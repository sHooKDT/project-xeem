package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionObject implements Parcelable {

    // Question contents
    private String qText;
    private String qPic;
    private int qCorrect;
    private int qPoints;
    private ArrayList<AnswerObject> qAnswers;

    // JSON converter
    public JSONArray getJsonAnswers() throws Exception {
        JSONArray _answers = new JSONArray();
        for (AnswerObject x: getAnswers()) {
            _answers.put(new JSONObject()
                    .put("text", x.getText())
                    .put("pic", x.getPic()));
        }
        return _answers;
    }
    public JSONObject getJson() throws Exception {
        JSONObject result = new JSONObject()
                .put("text", getText())
                .put("pic", getPic())
                .put("correct", getCorrect())
                .put("points", getPoints())
                .put("answers", getJsonAnswers());
        return result;
    }

    // Public interface
    public void         addAnswer(String _text) {
        getAnswers().add(new AnswerObject(_text));
    }
    public void         removeAnswer(int position) {
        getAnswers().remove(position);
    }


    // Public constructors
    public QuestionObject(String _text) {
        this.setText(_text);
        this.qAnswers = new ArrayList<AnswerObject>();
    }
    protected QuestionObject(Parcel in) {
        setText(in.readString());
        setPic(in.readString());
        setCorrect(in.readInt());
        setPoints(in.readInt());
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
    public String getText() {
        return this.qText;
    }
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
    public void setAnswers (ArrayList<AnswerObject> _answers) {
        this.qAnswers = _answers;
    }

    public void setText(String qText) {
        this.qText = qText;
    }
    public void setPic(String qPic) {
        this.qPic = qPic;
    }
    public void setCorrect(int qCorrect) {
        this.qCorrect = qCorrect;
    }
    public void setPoints(int qPoints) {
        this.qPoints = qPoints;
    }
}
