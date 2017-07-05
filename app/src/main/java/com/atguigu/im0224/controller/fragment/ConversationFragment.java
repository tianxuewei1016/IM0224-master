package com.atguigu.im0224.controller.fragment;

import android.content.Intent;

import com.atguigu.im0224.controller.activity.ChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

/**
 * 作者：田学伟 on 2017/7/3 10:44
 * QQ：93226539
 * 作用：
 */

public class ConversationFragment extends EaseConversationListFragment {

    @Override
    protected void setUpView() {
        super.setUpView();
    }

    @Override
    protected void initView() {
        super.initView();
        // 监听消息会话的变化 （消息通知）
        EMClient.getInstance().chatManager()
                .addMessageListener(emMessageListener);
        //item点击事件
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                //跳转到聊天界面
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName());
                startActivity(intent);
            }
        });
    }

    private EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            // 设置数据
            EaseUI.getInstance().getNotifier().onNewMesg(list);
            // 刷新列表
            refresh();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };
}
