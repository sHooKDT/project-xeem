package shook.xeem;

import android.os.Parcel;
import android.os.Parcelable;

class AnswerObject implements Parcelable {

    // Answer contents
    private String aText;
    private String aPic;

    // Public constructors
    public AnswerObject(String _text) {
        this.setText(_text);
        this.setPic("somebase64");
    }
    public AnswerObject(String _text, String _pic) {
        this.setText(_text);
        this.setPic(_pic);
    }
    protected AnswerObject(Parcel in) {
        setText(in.readString());
        setPic(in.readString());
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
    public void setText(String aText) {
        this.aText = aText;
    }
    public void setPic(String aPic) {
        this.aPic = aPic;
    }

}
