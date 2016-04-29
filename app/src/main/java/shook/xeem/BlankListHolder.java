package shook.xeem;

import java.util.LinkedList;

import shook.xeem.objects.BlankObject;

public interface BlankListHolder {

    LinkedList<BlankObject> getBlankList();

    void deleteBlankClick(int position);

    void editBlankClick(int position);

    void passBlankClick(int position);

}
