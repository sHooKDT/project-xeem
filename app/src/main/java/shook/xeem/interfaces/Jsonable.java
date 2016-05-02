package shook.xeem.interfaces;

import com.google.gson.Gson;

public abstract class Jsonable<T> {

    protected static Gson converter = new Gson();

    public String toJSON() {
        return converter.toJson(this);
    }

}
