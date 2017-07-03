package com.atguigu.im0224.modle.table;

/**
 * 作者：田学伟 on 2017/7/3 13:58
 * QQ：93226539
 * 作用：
 */

public class ContactTable {

    public static final String TABLE_NAME = "contact";
    public static final String COL_USER_HXID = "userhxid";
    public static final String COL_USER_NAME = "username";
    public static final String COL_USER_PHOTO = "userphoto";
    public static final String COL_USER_NICK = "usernick";
    public static final String COL_IS_CONTACT = "contact";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL_USER_HXID + " text primary key,"
            + COL_USER_NAME + " text,"
            + COL_USER_PHOTO + " text,"
            + COL_USER_NICK + " text,"
            + COL_IS_CONTACT + " integer)";
}
