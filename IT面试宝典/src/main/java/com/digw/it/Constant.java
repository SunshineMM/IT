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
    public static final String URL_HOST="http://119.29.167.210/IT/api/";
    public static final String URL_POST_TITLE=URL_HOST+"getMenuTitle.php";
    //public static final String URL_POST_USER_REGISTER=URL_HOST+"doRegister.php";
    //public static final String URL_POST_USER_LOGIN=URL_HOST+"doLogin.php";
    public static final String URL_POST_REQUEST_QUESTION=URL_HOST+"getQuestion.php";

    //NOWCODER
    public static final String URL_KEY_USER_AGENT="User-Agent";
    public static final String URL_NOWCODER_USER_AGENT="nowcoder android 2.15.1.2443";
    private static final String URL_NOWCODER_HOST="http://m.nowcoder.com/";
    //验证手机号 参数:phone  结果:{"code":0,"msg":"OK"}  {"code":1,"msg":"手机号已注册"}
    public static final String URL_GET_CHECK_PHONE=URL_NOWCODER_HOST+"mobile/checkPhone";
    public static final String CHECK_PHONE_REQUEST_KEY_PHONE="phone";
    //发送验证码 参数:phone User-Agent=nowcoder android 2.15.1.2443
    public static final String URL_POST_SEND_CHECK_PHONE=URL_NOWCODER_HOST+"mobile/sendCode";

    //注册
    public static final String URL_POST_USER_REGISTER=URL_NOWCODER_HOST+"register/do";
    public static final String REGISTER_REQUEST_KEY_PHONE="phone";
    public static final String REGISTER_REQUEST_KEY_PASSWORD="password";
    public static final String REGISTER_REQUEST_KEY_CODE="code";

    //登录
    public static final String URL_POST_USER_LOGIN=URL_NOWCODER_HOST+"login/do";
    public static final String LOGIN_REQUEST_KEY_ACCOUNT="account";
    public static final String LOGIN_REQUEST_KEY_PASSWORD="password";

    //新闻
    public static String NEWS_GROUP="T1348649580692";//IT
    public static int NEWS_LIST_START_NUM=0;
    public static int NEWS_LIST_END_NUM=40;
    public static final String URL_GET_NEWS_LIST="http://c.m.163.com/nc/article/headline/"+NEWS_GROUP+"/"+NEWS_LIST_START_NUM+"-"+NEWS_LIST_END_NUM+".html";
    public static final String URL_PREFIX_NEWS_INFO="http://3g.163.com/news/article/";

    public static final String QUESTION_REQUEST_KEY_COUNT="questionCount";
    public static final int QUESTION_REQUEST_COUNT=10;
    public static final String QUESTION_REQUEST_KEY_TAGID="tagIds";

    public static class UserTable{
        public static String id="u_id";
        public static String type="u_type";
        public static String name="u_name";
        public static String email="u_email";
        public static String sex="u_sex";
        public static String password="u_password";
    }
}
