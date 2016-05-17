package shook.xeem.interfaces;

import shook.xeem.BlankList;
import shook.xeem.UserList;

public interface BlankListHolder {

    BlankList getBlankList();

    UserList getUserList();

    boolean hasBlanks();

    boolean hasUsers();

    void hideLoading();

    void deleteBlankClick(int position);

    void editBlankClick(int position);

    void passBlankClick(int position);

}
