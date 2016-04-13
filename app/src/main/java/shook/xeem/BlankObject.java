package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.ArrayList;


public class BlankObject implements Parcelable {

    // Blank contents
    private String                      bTitle;
    private Date                        bDate;
    private long                        bID;
    private boolean                     bIsPublic;
    private String                      bAuthor;
    private ArrayList<QuestionObject>   bQuestions;

    // Delete as fast as possible
    public void addAnswer(int ques, String _text) {
        bQuestions.get(ques).addAnswer(_text);
    }

    // JSON converter
    private JSONArray getJsonQuestions() throws Exception {
        JSONArray result = new JSONArray();
        for (QuestionObject x: getQuestions()) {
            result.put(x.getJson());
        }
        return result;
    }
    public JSONObject getJSON() throws Exception {

        return new JSONObject()
                .put("title", getTitle())
                .put("date", getDate().getTime())
//                .put("id", getID())
                .put("public", isPublic())
                .put("author", getAuthor())
                .put("questions", getJsonQuestions());
    }

    // Public constructors
    public                  BlankObject (String _title) {
        this.bQuestions = new ArrayList<QuestionObject>();
        this.bTitle = _title;
        this.bAuthor = "Me and my cat";
        this.bDate = new Date();
        this.bIsPublic = true;
        this.bID = 111;
    }
    protected               BlankObject(Parcel in) {
        bTitle = in.readString();
        bDate = new Date(in.readLong());
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
        dest.writeLong(getDate().getTime());
        dest.writeLong(getID());
        dest.writeByte((byte) (isPublic() ? 1 : 0));
        dest.writeString(getAuthor());
        dest.writeTypedList(getQuestions());
    }

    // Getters && setters
    public String getTitle() {
        return bTitle;
    }
    public Date getDate() {
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
