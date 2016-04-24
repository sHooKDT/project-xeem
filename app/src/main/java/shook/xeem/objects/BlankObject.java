package shook.xeem.objects;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.ArrayList;
import java.util.Random;

import shook.xeem.Jsonable;


public class BlankObject extends Jsonable<BlankObject> {

    // Blank contents
    @SerializedName("title") private String                             bTitle;
    @SerializedName("date") private long                                bDate;
    @SerializedName("public") private boolean                           bIsPublic;
    @SerializedName("author") private String                            bAuthor;
    @SerializedName("questions") private ArrayList<QuestionObject>      bQuestions;
    @SerializedName("_id") private String                               bID;
    @SerializedName("_etag")
    private String bEtag;

    // Public constructors
    private BlankObject () {}

    public static BlankObject fromJSON(String _json) {
        return converter.fromJson(_json, BlankObject.class);
    }

    // Getters
    public void rmEtag() { this.bEtag = null; }
    public String getTitle() {
        return bTitle;
    }
    public long getDate() {
        return bDate;
    }
    public String getID() {
        return bID;
    }
    public String getEtag() { return bEtag; }
    public boolean isPublic() {
        return bIsPublic;
    }
    public String getAuthor() {
        return bAuthor;
    }
    public ArrayList<QuestionObject> getQuestions() { return bQuestions; }

    public static class Factory {

        BlankObject factored_blank;

        public Factory () {
            factored_blank = new BlankObject();
            factored_blank.bIsPublic = false;
            factored_blank.bQuestions = new ArrayList<QuestionObject>();
        }

        public void loadJSON(String _json) {
            this.factored_blank = BlankObject.fromJSON(_json);
        }

        // Setters
        public void setTitle(String _title) { this.factored_blank.bTitle = _title; }

        public void setPublic() {
            this.factored_blank.bIsPublic = !this.factored_blank.bIsPublic;
        }

        public void setAuthor(String _author) {
            this.factored_blank.bAuthor = _author;
        }

        public void putQuestion(QuestionObject _question) {
            this.factored_blank.bQuestions.add(_question);
        }
        public void replaceQuestion(int index, QuestionObject _question) {
            this.factored_blank.bQuestions.set(index, _question);
        }
        public void rmQuestion(int position) {
            this.factored_blank.bQuestions.remove(position);
        }

        public BlankObject getPreview() {
                return factored_blank;
        }

        public BlankObject build() {
            if (this.factored_blank.bTitle == null) this.factored_blank.bTitle = "Untitled";
            if (this.factored_blank.bDate == 0) this.factored_blank.bDate = new Date().getTime();
            return this.factored_blank;
        }

    }
}
