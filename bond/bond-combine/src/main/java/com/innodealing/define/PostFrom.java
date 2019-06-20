package com.innodealing.define;

/**
 * Created by wanglc-comp on 2017/3/16.
 */
public enum PostFrom {
    QQ(0, "qq"), WECHAT(1, "wechat"), PC_CLIENT(2, "pc-client"), PC_WEB(3, "pc-web"),
    CONSOLE(5, "console"), IM(6, "im"), ANDROID(7, "android"), IOS(8, "ios");

//    发送来源，0 QQ，1微信，2 PC客户端，3PC网页，5管理平台->console，6 im, 7 Android 8 IOS

    public final int value;
    public final String text;

    PostFrom(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
