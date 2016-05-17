package shook.xeem.objects;

import com.google.gson.annotations.SerializedName;

import shook.xeem.interfaces.Jsonable;

public class UserObject extends Jsonable<UserObject> {

    public UserObject() {
    }

    @SerializedName("userid")
    public String userId;
    @SerializedName("username")
    public String userName;
    @SerializedName("userpic")
    public String userPic;
    @SerializedName("regdate")
    public long regDate;

    public static UserObject fromJSON(String _json) {
        return converter.fromJson(_json, UserObject.class);
    }

    public class UserListResponse {
        public UserList _items;

        //        public ArrayList<UserObject> _items;
        public UserListResponse(UserList _items) {
            this._items = _items;
        }
    }

}
