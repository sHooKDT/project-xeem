package shook.xeem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import shook.xeem.objects.BlankObject;

public class BlankList extends ArrayList<BlankObject> {

    public String toJSON() {
        return (new Gson()).toJson(this);
    }

    public BlankObject findBlankById(String _id) {
        for (BlankObject x: this) {
            if (Objects.equals(x.getID(), _id)) return x;
        }
        return null;
    }

}
