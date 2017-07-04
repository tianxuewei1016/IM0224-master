package com.atguigu.im0224.common;

import android.content.Context;

import com.atguigu.im0224.modle.bean.InvitationInfo;
import com.atguigu.im0224.modle.bean.UserInfo;
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
        //收到好友邀请  别人加你
        @Override
        public void onContactInvited(String username, String reason) {
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setUserInfo(new UserInfo(username,username));
            //添加InvitationInfo
            Modle.getInstance().getHelperManager()
                    .getInvitationDAO()
                    .addInvitation(invitationInfo);
            //保存小红点的状态
        }

        //好友请求被同意  你加别人的时候 别人同意了
        @Override
        public void onContactAgreed(String username) {

        }

        //被删除时回调此方法
        @Override
        public void onContactDeleted(String username) {

        }

        //增加了联系人时回调此方法  当你同意添加好友
        @Override
        public void onContactAdded(String username) {

        }

        //好友请求被拒绝  你加别人 别人拒绝了
        @Override
        public void onContactRefused(String username) {

        }
    };
}
