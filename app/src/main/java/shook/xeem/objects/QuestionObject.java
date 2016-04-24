package shook.xeem.objects;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import shook.xeem.Jsonable;

public class QuestionObject extends Jsonable<QuestionObject> {

    // Question contents
    @SerializedName("text") private String qText;
    @SerializedName("pic") private String qPic;
    @SerializedName("correct") private int qCorrect;
    @SerializedName("points") private int qPoints;
    @SerializedName("answers") private ArrayList<AnswerObject> qAnswers;
    @SerializedName("checked")
    private int qChecked;

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
        this.setPoints(1);
        this.setAnswers(new ArrayList<AnswerObject>());
    }

    // Getters and setters
    public String getText() {
        return qText;
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

    public void setChecked(int position) {
        this.qChecked = position;
    }

    public int getChecked() {
        return qChecked;
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
