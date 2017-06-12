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

    //服务器地址
    public static final String URL_HOST="http://www.digw.info/IT/api/";
    public static final String URL_POST_TITLE=URL_HOST+"getMenuTitle.php";
    public static final String URL_POST_USER_REGISTER=URL_HOST+"doRegister.php";
    public static final String URL_POST_USER_LOGIN=URL_HOST+"doLogin.php";

    //新闻
    public static String NEWS_GROUP="T1348649580692";//IT
    public static int NEWS_LIST_START_NUM=0;
    public static int NEWS_LIST_END_NUM=40;
    public static final String URL_GET_NEWS_LIST="http://c.m.163.com/nc/article/headline/"+NEWS_GROUP+"/"+NEWS_LIST_START_NUM+"-"+NEWS_LIST_END_NUM+".html";

    public static final String LOGIN_REQUEST_KEY_USERNAME="username";
    public static final String LOGIN_REQUEST_KEY_PASSWORD="password";

    public static final String REGISTER_REQUEST_KEY_USERNAME="username";
    public static final String REGISTER_REQUEST_KEY_PASSWORD="password";
    public static final String REGISTER_REQUEST_KEY_EMAIL="email";
    public static final String REGISTER_REQUEST_KEY_SEX="sex";

    public static class UserTable{
        public static String id="u_id";
        public static String type="u_type";
        public static String name="u_name";
        public static String email="u_email";
        public static String sex="u_sex";
        public static String password="u_password";
    }
}
