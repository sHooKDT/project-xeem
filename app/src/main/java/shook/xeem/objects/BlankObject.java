package shook.xeem.objects;

import android.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Modifier;
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

    public static Builder newBuilder() {
        return new BlankObject().new Builder();
    }

    public Builder getBuilder() {
        return this.new Builder();
    }

    public static BlankObject fromJSON(String _json) {
        return converter.fromJson(_json, BlankObject.class);
    }

    // Getters
    public BlankObject rmEtag() {
        this.bEtag = null;
        return this;
    }
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

    public class Builder {

        private Builder() {
        }

        // Setters
        public void setTitle(String _title) {
            BlankObject.this.bTitle = _title;
        }
        public void setPublic() {
            BlankObject.this.bIsPublic = !BlankObject.this.bIsPublic;
        }
        public void setAuthor(String _author) {
            BlankObject.this.bAuthor = _author;
        }

        public void putQuestion(QuestionObject _question) {
            BlankObject.this.bQuestions.add(_question);
        }
        public void replaceQuestion(int index, QuestionObject _question) {
            BlankObject.this.bQuestions.set(index, _question);
        }
        public void rmQuestion(int position) {
            BlankObject.this.bQuestions.remove(position);
        }

        public BlankObject build() {
            if (BlankObject.this.bTitle == null) BlankObject.this.bTitle = "Untitled";
            if (BlankObject.this.bDate == 0) BlankObject.this.bDate = new Date().getTime();
            return BlankObject.this;
        }

    }
}
