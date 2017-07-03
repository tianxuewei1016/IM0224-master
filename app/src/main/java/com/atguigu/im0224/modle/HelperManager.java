package com.atguigu.im0224.modle;

import android.content.Context;

import com.atguigu.im0224.modle.dao.ContactDAO;
import com.atguigu.im0224.modle.dao.InvitationDAO;
import com.atguigu.im0224.modle.db.DBHelper;

/**
 * 作者：田学伟 on 2017/7/3 19:06
 * QQ：93226539
 * 作用：
 */

public class HelperManager {

    private final DBHelper dbHelper;
    private final ContactDAO contactDAO;
    private final InvitationDAO invitationDAO;

    public HelperManager(Context context, String name) {
        dbHelper = new DBHelper(context, name);
        contactDAO = new ContactDAO(dbHelper);
        invitationDAO = new InvitationDAO(dbHelper);
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public ContactDAO getContactDAO() {
        return contactDAO;
    }

    public InvitationDAO getInvitationDAO() {
        return invitationDAO;
    }
}
