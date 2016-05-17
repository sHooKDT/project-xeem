package shook.xeem.interfaces;

import shook.xeem.objects.BlankList;
import shook.xeem.objects.UserList;

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
