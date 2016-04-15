package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionObject {

    // Question contents
    @SerializedName("text") private String qText;
    @SerializedName("pic") private String qPic;
    @SerializedName("correct") private int qCorrect;
    @SerializedName("points") private int qPoints;
    @SerializedName("answers") private ArrayList<AnswerObject> qAnswers;

    // Public interface
    public void putAns(String _text) {
        getAnswers().add(new AnswerObject(_text));
    }
    public void rmAns(int position) {
        getAnswers().remove(position);
    }

    // Public constructors
    public QuestionObject(String _text) {
        this.setText(_text);
        this.qAnswers = new ArrayList<AnswerObject>();
    }

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
