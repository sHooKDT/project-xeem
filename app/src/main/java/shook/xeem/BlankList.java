package shook.xeem;

import com.google.gson.Gson;

import java.util.ArrayList;

import shook.xeem.objects.BlankObject;

public class BlankList extends ArrayList<BlankObject> {

    public String toJSON() {
        return (new Gson()).toJson(this);
    }

}
