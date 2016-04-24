package shook.xeem.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import shook.xeem.Jsonable;

public class AnswerObject extends Jsonable<AnswerObject> {

    // Answer contents
    @SerializedName("text") private String aText;
    @SerializedName("pic") private String aPic;

    // Public constructors
    public AnswerObject(String _text) {
        this.setText(_text);
        this.setPic("somebase64");
    }
    public AnswerObject(String _text, String _pic) {
        this.setText(_text);
        this.setPic(_pic);
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

    @Override
    public String toString() {
        return getText();
    }
}
