package shook.xeem;

import java.util.LinkedList;

import shook.xeem.objects.BlankObject;

public interface BlankListHolder {

    public LinkedList<BlankObject> getBlankList();

    public void deleteBlankClick(int position);

    public void editBlankClick(int position);

    public void passBlankClick(int position);

}
