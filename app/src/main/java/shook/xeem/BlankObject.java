package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

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


    // Public interface
    public int              addQuestion(String _text) {
        bQuestions.add(new QuestionObject(_text));
        return bQuestions.size()-1;
    }
    public void             removeQuestion(int position) {
        this.bQuestions.remove(position);
    }
    public QuestionObject   getQuestion (int position) {
        return this.bQuestions.get(position);
    }
    public void             addAnswer(int qPos, String text) {
        bQuestions.get(qPos).addAnswer(text);
    }
    public int              questionCount() {
        return bQuestions.size();
    }


    // Public constructors
    public                  BlankObject (String _title) {
        this.bQuestions = new ArrayList<QuestionObject>();
        this.bTitle = _title;
        this.bAuthor = "";
        this.bDate = new Date();
        this.bIsPublic = true;
        this.bID = 1;
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
        dest.writeString(bTitle);
        dest.writeLong(bDate.getTime());
        dest.writeLong(bID);
        dest.writeByte((byte) (bIsPublic ? 1 : 0));
        dest.writeString(bAuthor);
        dest.writeTypedList(bQuestions);
    }


}
