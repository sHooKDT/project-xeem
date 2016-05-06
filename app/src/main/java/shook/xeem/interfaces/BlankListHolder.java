package shook.xeem.interfaces;

import shook.xeem.BlankList;
import shook.xeem.UserList;

public interface BlankListHolder {

    BlankList getBlankList();

    UserList getUserList();

    void deleteBlankClick(int position);

    void editBlankClick(int position);

    void passBlankClick(int position);

}
