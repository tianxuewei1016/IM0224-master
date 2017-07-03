package com.atguigu.im0224.modle.table;

/**
 * 作者：田学伟 on 2017/7/3 14:00
 * QQ：93226539
 * 作用：
 */

public class InvitationTable {
    public static final String TABLE_NAME = "invitation";
    public static final String COL_USER_HXID = "userhxid";
    public static final String COL_USER_NAME = "username";
    public static final String COL_REASON = "reason";
    public static final String COL_STATE = "state";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL_USER_HXID + " text primary key,"
            + COL_USER_NAME + " text,"
            + COL_REASON + " text,"
            + COL_STATE + " integer)";
}
