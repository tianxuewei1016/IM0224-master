package com.atguigu.im0224.utils;

import android.content.Context;
import android.os.Process;
import android.widget.Toast;

import com.atguigu.im0224.common.MyApplication;

/**
 * 作者：田学伟 on 2017/7/3 13:11
 * QQ：93226539
 * 作用：
 */

public class UiUtils {

    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 保证runnable在主线程中运行
     *
     * @param runnable
     */
    public static void UIThread(Runnable runnable) {
        if (MyApplication.getPid() == Process.myTid()) {
            runnable.run();
        } else {
            MyApplication.getHandler().post(runnable);
        }
    }

    /**
     * 显示吐司
     * @param message
     */
    public static void showToast(final String message) {
        UIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
