package com.digw.it;

/**
 * digw创建于17-5-5.
 */

public class Constant {
    public static final boolean isDebug=true;
    public static final String APP_NAME="IT面试宝典";
    public static final int APP_INIT_DONE=0x001;
    public static final int APP_INIT_ERROR=0X002;

    public static final String SHARED_USER="user_sp";

    public static final String URL_POST_TITLE="http://www.digw.info/IT/api/getMenuTitle.php";
    public static final String URL_POST_USER_REGISTER="http://www.digw.info/IT/api/doRegister.php";
    public static final String URL_POST_USER_LOGIN="http://www.digw.info/IT/api/doLogin.php";

    public static final String LOGIN_REQUEST_KEY_USERNAME="username";
    public static final String LOGIN_REQUEST_KEY_PASSWORD="password";

    public static class UserTable{
        public static String id="u_id";
        public static String type="u_type";
        public static String name="u_name";
        public static String email="u_email";
        public static String sex="u_sex";
        public static String password="u_password";
    }
}
