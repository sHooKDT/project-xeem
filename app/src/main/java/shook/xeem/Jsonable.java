package shook.xeem;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import shook.xeem.objects.BlankObject;

public abstract class Jsonable<T> {

    protected static Gson converter = new Gson();

    public String toJSON() {
        return converter.toJson(this);
    }

    ;

}
