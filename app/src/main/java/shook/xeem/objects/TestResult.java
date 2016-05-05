package shook.xeem.objects;

import shook.xeem.interfaces.Jsonable;

public class TestResult extends Jsonable<TestResult> {

    public String testid;
    public String testetag;
    public String userid;
    public long date;
    public int points;
    public int max_points;
    public int right_questions;
    public int max_questions;

    public TestResult() {
    }

    public static TestResult fromJSON(String _json) {
        return (converter.fromJson(_json, TestResult.class));
    }

}
