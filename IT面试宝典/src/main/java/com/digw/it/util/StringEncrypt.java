package com.digw.it.util;

/**
 * digw创建于17-6-4.
 */

public class StringEncrypt {

    public static String decrypt(String ssoToken)
    {
        try
        {
            String name = "";
            java.util.StringTokenizer st=new java.util.StringTokenizer(ssoToken,"%");
            while (st.hasMoreElements()) {
                int asc =  Integer.parseInt((String)st.nextElement()) - 27;
                name = name + (char)asc;
            }

            return name;
        }catch(Exception e)
        {
            e.printStackTrace() ;
            return null;
        }
    }

    public static String encrypt(String ssoToken)
    {
        try
        {
            byte[] _ssoToken = ssoToken.getBytes("UTF-8");
            String name = "";
            for (int i = 0; i < _ssoToken.length; i++) {
                int asc = _ssoToken[i];
                _ssoToken[i] = (byte) (asc + 27);
                name = name + (asc + 27) + "%";
            }
            return name;
        }catch(Exception e)
        {
            e.printStackTrace() ;
            return null;
        }
    }

}
