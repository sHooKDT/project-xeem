package shook.xeem.objects;

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

    public String toJSON () {
        return (new Gson()).toJson(this);
    }
    public static BlankObject fromJSON (String _json) { return (new Gson()).fromJson(_json, BlankObject.class); }

    // Public constructors
    private BlankObject () {}

    // Getters
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

    public static class Factory {

        BlankObject factored_blank;

        public Factory () {
            factored_blank = new BlankObject();
            factored_blank.bIsPublic = false;
            factored_blank.bQuestions = new ArrayList<QuestionObject>();
        }

        public void fillExample() {
            // just for example !!!!!!!!!
            this.factored_blank = BlankObject.fromJSON("{\"title\": \"Very hard IQ test\", \"date\": 14749221, \"id\": 3242112411, \"public\": false, \"author\": \"John Smith\", \"questions\": [{\"text\": \"How many legs does horse have?\", \"pic\": \"\", \"correct\": 2, \"points\": 5, \"answers\": [{\"text\": \"I dont know\", \"pic\": \"\"}, {\"text\": \"Maybe three?\", \"pic\": \"\"}, {\"text\": \"Exactly four\", \"pic\": \"\"} ] } ] }");
        }

        public void loadJSON(String _json) {
            this.factored_blank = BlankObject.fromJSON(_json);
        }

        // Setters
        public void setTitle(String _title) { this.factored_blank.bTitle = _title; }
        public void togglePublic() {this.factored_blank.bIsPublic = !this.factored_blank.bIsPublic;}

        public void putQuestion(QuestionObject _question) {
            this.factored_blank.bQuestions.add(_question);
        }
        public void rmQuestion(int position) {
            this.factored_blank.bQuestions.remove(position);
        }

        public BlankObject getPreview() {
                return factored_blank;
        }

        public BlankObject build() {
            if (this.factored_blank.bTitle == null) this.factored_blank.bTitle = "Example title";
            this.factored_blank.bAuthor = "Me and my cat"; // TODO: Replace with authenticated client id
            this.factored_blank.bID = (new Random()).nextLong();
            this.factored_blank.bDate = new Date().getTime();
            return this.factored_blank;
        }

    }
}
