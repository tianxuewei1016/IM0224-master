package com.atguigu.im0224.controller.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.im0224.R;
import com.atguigu.im0224.common.Constant;
import com.atguigu.im0224.common.Modle;
import com.atguigu.im0224.controller.activity.AddContactActivity;
import com.atguigu.im0224.controller.activity.ChatActivity;
import com.atguigu.im0224.controller.activity.InviteActivity;
import com.atguigu.im0224.modle.bean.UserInfo;
import com.atguigu.im0224.utils.SPUtils;
import com.atguigu.im0224.utils.UiUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：田学伟 on 2017/7/3 10:43
 * QQ：93226539
 * 作用：
 */

public class ContactListFragment extends EaseContactListFragment {

    private BroadcastReceiver inviteReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("invited", "onReceive: ");
            //接收广播
            isShowRedView();
        }
    };
    private ImageView redView;

    private BroadcastReceiver contactReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //添加或者删除好友以后调用此方法

            refreshData();
        }
    };
    private List<UserInfo> contacts;

    @Override
    protected void initView() {
        super.initView();
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        initHeadView();

        isShowRedView();

        titleBar.setRightImageResource(R.drawable.ease_blue_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });
        //注册监听
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        //邀请信息发生改变
        manager.registerReceiver(inviteReciver, new IntentFilter(Constant.NEW_INVITE_CHANGE));
        //联系人发生改变
        manager.registerReceiver(contactReceiver, new IntentFilter(Constant.CONTACT_CHANGE));

        //展示联系人
        showContact();

        //删除联系人
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return false;
                }
                //弹警告
                showDialog(position);
                return true;
            }
        });
    }

    /**
     * 弹警告
     *
     * @param position
     */
    private void showDialog(final int position) {
        new AlertDialog.Builder(getActivity())
                .setMessage("确定要删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Modle.getInstance().getGlobalThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                //网络
                                UserInfo userInfo = contacts.get(position - 1);
                                try {
                                    EMClient.getInstance().contactManager()
                                            .deleteContact(userInfo.getHxid());
                                    //本地
                                    Modle.getInstance().getHelperManager().getContactDAO()
                                            .deleteContactByHxId(userInfo.getHxid());
                                    //内存和页面
                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                refreshData();
                                            }
                                        });
                                    }
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                    UiUtils.showToast(e.getMessage());
                                }
                            }
                        });
                    }
                }).setNegativeButton("取消", null).show();
    }

    /**
     * 展示联系人
     */
    private void showContact() {
        //判断是否是第一次进入应用 第一次需要从服务器获取联系人 否则直接从数据库
        refreshServer();
    }

    /**
     * 从服务器获取好友列表
     */
    private void refreshServer() {
        Modle.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {


                try {
                    //网络
                    List<String> contacts
                            = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    //本地
                    //数据转换
                    List<UserInfo> userinfos = new ArrayList<UserInfo>();
                    for (String contacs : contacts) {
                        UserInfo userinfo = new UserInfo(contacs, contacs);
                        userinfos.add(userinfo);
                    }
                    //保存从服务器获取的联系人
                    Modle.getInstance().getHelperManager()
                            .getContactDAO().saveContacts(userinfos, true);

                    //内存和页面
                    if (getActivity() == null) {
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshData();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从本地获取联系人数据
     */
    private void refreshData() {
        //从数据库获取所有联系人
        contacts = Modle.getInstance().getHelperManager().getContactDAO().getContacts();
        //校验
        if (contacts != null) {
            //添加数据
            Map<String, EaseUser> map = new HashMap<>();
            //数据类型转换
            for (UserInfo info : contacts) {
                map.put(info.getHxid(), new EaseUser(info.getUsername()));
            }
            setContactsMap(map);
            //获取数据
            getContactList();
            //刷新数据
            contactListLayout.refresh();
        }
    }

    /**
     * 判断小红点是否展示
     */
    private void isShowRedView() {
        //获取小红点的状态
        Boolean bolValue = SPUtils.getSpUtils().getBolValue(SPUtils.NEW_INVITE);
        //是否显示小红点
        redView.setVisibility(bolValue ? View.VISIBLE : View.GONE);
    }

    private void initHeadView() {
        View headView = View.inflate(getActivity(), R.layout.head_view, null);
        LinearLayout friends = (LinearLayout) headView.findViewById(R.id.ll_new_friends);
        redView = (ImageView) headView.findViewById(R.id.contanct_iv_invite);
        listView.addHeaderView(headView);

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InviteActivity.class));
            }
        });
    }

    /**
     * 当界面再次展示的时候进行回调
     */
    @Override
    public void onResume() {
        super.onResume();
        isShowRedView();
    }
}
