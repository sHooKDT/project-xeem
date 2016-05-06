package shook.xeem;

import java.util.ArrayList;
import java.util.Objects;

import shook.xeem.objects.UserObject;


public class UserList extends ArrayList<UserObject> {

    public UserObject findUserById(String _id) {
        for (UserObject user : this) {
            if ((Objects.equals(user.userId, _id))) return user;
        }
        return null;
    }

}
