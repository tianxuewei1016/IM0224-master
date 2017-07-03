package com.atguigu.im0224.common;

import android.content.Context;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * 作者：田学伟 on 2017/7/3 13:50
 * QQ：93226539
 * 作用：
 */

public class GlobalListener {

    public GlobalListener(Context context) {
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }

    /**
     * 设置全局监听
     */
    EMContactListener emContactListener = new EMContactListener() {
        //好友请求被同意
        @Override
        public void onContactAdded(String s) {

        }
        //好友请求被拒绝
        @Override
        public void onContactDeleted(String s) {

        }
        //收到好友邀请
        @Override
        public void onContactInvited(String s, String s1) {

        }
        //被删除时回调此方法
        @Override
        public void onContactAgreed(String s) {

        }
        //增加了联系人时回调此方法
        @Override
        public void onContactRefused(String s) {

        }
    };
}
