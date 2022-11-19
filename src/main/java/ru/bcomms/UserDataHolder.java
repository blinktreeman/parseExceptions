package ru.bcomms;

import java.util.List;

public class UserDataHolder {
    private static UserDataHolder userDataHolder;
    private static List<String> userDataList;
    private static UserData userData;

    public static UserDataHolder getUserDataHolder() {
        if (userDataHolder == null) {
            return new UserDataHolder();
        } else {
            return userDataHolder;
        }
    }

    public static List<String> getUserDataList() {
        return userDataList;
    }

    public static void setUserDataList(List<String> userDataList) {
        UserDataHolder.userDataList = userDataList;
    }

    public static UserData getUserData() {
        if (userData == null) {
            UserDataHolder.userData = new UserData();
        }
        return UserDataHolder.userData;
    }

    public static void setUserData(UserData userData) {
        UserDataHolder.userData = userData;
    }
}
