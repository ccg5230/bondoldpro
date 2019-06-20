package com.innodealing.utils;

import com.innodealing.define.PostFrom;

/**
 * Created by wanglc-comp on 2017/3/29.
 */
public class CheckValueUtil {

    public static Integer getPostFromByAppKey(String appKey) {
        Integer postFrom = PostFrom.ANDROID.value;
        if (appKey == null) {
            return postFrom;
        }
        for (PostFrom postFromEnum :
                PostFrom.values()) {
            if (appKey.toLowerCase().contains(postFromEnum.text.toLowerCase())) {
                postFrom = postFromEnum.value;
                break;
            }
        }

        return postFrom;
    }

}
