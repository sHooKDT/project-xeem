package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

import org.bson.Document;

class AnswerObject implements Parcelable {

    // Answer contents
    private String aText;
    private String aPic;

    // JSON converter
    public Document toDoc() {
        Document result = new Document()
                .append("text", getText())
                .append("pic", getPic());
        return result;
    }
    public AnswerObject (Document in) {
        this.aText = in.getString("text");
        this.aPic = in.getString("pic");
    }

    // Public constructors
    public AnswerObject(String _text) {
        this.aText = _text;
        this.aPic = null;
    }
    public AnswerObject(String _text, String _pic) {
        this.aText = _text;
        this.aPic = _pic;
    }
    protected AnswerObject(Parcel in) {
        aText = in.readString();
        aPic = in.readString();
    }

    // Parcelable methods
    public static final Creator<AnswerObject> CREATOR = new Creator<AnswerObject>() {
        @Override
        public AnswerObject createFromParcel(Parcel in) {
            return new AnswerObject(in);
        }

        @Override
        public AnswerObject[] newArray(int size) {
            return new AnswerObject[size];
        }
    };
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getText());
        dest.writeString(getPic());
    }

    // Getters and setters
    public String getText() {
        return aText;
    }
    public String getPic() {
        return aPic;
    }
}
