package shook.xeem.interfaces;

import shook.xeem.BlankList;

public interface BlankListHolder {

    BlankList getBlankList();

    void deleteBlankClick(int position);

    void editBlankClick(int position);

    void passBlankClick(int position);

}
