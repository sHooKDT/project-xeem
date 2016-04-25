package shook.xeem.objects;

import shook.xeem.Jsonable;

public class TestResult extends Jsonable<TestResult> {

    String blank_name;
    String participant_id;
    String blank_id;
    String blank_etag;
    int points;
    int maxpoints;
    int qright;
    int qcount;


    public static TestResult fromJSON(String _json) {
        return (converter.fromJson(_json, TestResult.class));
    }

    public TestResult(String blank_name, String blank_id, String participant_id, String blank_etag, int points, int maxpoints, int qright, int qcount) {
        this.blank_name = blank_name;
        this.blank_id = blank_id;
        this.participant_id = participant_id;
        this.blank_etag = blank_etag;
        this.points = points;
        this.maxpoints = maxpoints;
        this.qright = qright;
        this.qcount = qcount;
    }

    public String getBlankName() {
        return blank_name;
    }

    public String getEtag() {
        return blank_etag;
    }

    public String getBlankId() {
        return blank_id;
    }

    public int getMaxPoints() {
        return maxpoints;
    }

    public String getParticipantId() {
        return participant_id;
    }

    public int getPoints() {
        return points;
    }

    public int getQCount() {
        return qcount;
    }

    public int getQRight() {
        return qright;
    }
}
