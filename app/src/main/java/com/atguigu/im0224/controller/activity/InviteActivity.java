package com.atguigu.im0224.controller.activity;

import android.widget.ListView;

import com.atguigu.im0224.R;
import com.atguigu.im0224.base.BaseActivity;
import com.atguigu.im0224.common.MyApplication;
import com.atguigu.im0224.controller.adapter.InviteAdapter;

import butterknife.Bind;

public class InviteActivity extends BaseActivity {

    @Bind(R.id.lv_invite)
    ListView lvInvite;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        InviteAdapter adapter = new InviteAdapter(MyApplication.getContext());
        lvInvite.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }
}
