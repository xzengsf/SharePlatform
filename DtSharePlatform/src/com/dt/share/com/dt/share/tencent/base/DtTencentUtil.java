package com.dt.share.com.dt.share.tencent.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.dt.share.com.dt.share.tencent.listener.*;
import com.tencent.tauth.Constants;
import com.tencent.tauth.Tencent;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 下午5:23
 */
public class DtTencentUtil
{
    private Tencent mTencent;
    private Context mContext;
    private String  mAppId;

    public DtTencentUtil(Context context, String appId)
    {
        mContext = context;
        mAppId = appId;
        mTencent = Tencent.createInstance(appId, context);
    }

    private boolean readInfo()
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tencent_msg", Context.MODE_WORLD_READABLE);
        String openid = sharedPreferences.getString("openid", "");
        String access_token = sharedPreferences.getString("access_token", "");
        long expires_in = sharedPreferences.getLong("expires_in", 0);
        if (expires_in == 0 | System.currentTimeMillis() > expires_in)
        {
            return false;
        } else
        {
            mTencent.setOpenId(openid);
            mTencent.setAccessToken(access_token, String.valueOf(expires_in));
            return true;
        }
    }

    public void saveInfo()
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tencent_msg", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        editor.putString("openid", mTencent.getOpenId());
        editor.putString("access_token", mTencent.getAccessToken());
        editor.putLong("expires_in", mTencent.getExpiresIn());
        editor.commit();
    }

    public void login(Activity activity, DtTencentOperatorListener listener)
    {
        if (!readInfo())
        {
            mTencent.login(activity, "all", new DtBaseUiListener(listener));
        } else
        {
            listener.onSuccessed(DtTencentTag.LOGIN_SUCCESS, "登陆成功");
        }
    }

    public void logout()
    {
        mTencent.logout(mContext);
    }

    public void getUserInfo(DtTencentOperatorListener listener)
    {
        Bundle params = new Bundle();
        params.putString("format", "json");
        mTencent.requestAsync(Constants.GRAPH_GET_INFO, params, Constants.HTTP_POST, new DtGetTQQInfoApiListener(listener), null);
    }

    public void sendTextWeibo(Activity activity, String content, DtTencentOperatorListener listener)
    {
        if (readInfo())
        {
            Bundle bundle = new Bundle();
            bundle.putString("format", "json");// 返回的数据格式
            bundle.putString("content", content);
            mTencent.requestAsync(Constants.GRAPH_ADD_T, bundle, Constants.HTTP_POST, new DtSendMsgApiListener(listener), null);
        } else
        {
            login(activity, listener);
        }
    }

    public void sendPicWeibo(Activity activity, String content, Bitmap bitmap, DtTencentOperatorListener listener)
    {
        if (readInfo())
        {
            Bundle bundle = new Bundle();
            bundle.putString("format", "json");
            bundle.putString("content", content);
            // 把 bitmap 转换为 byteArray , 用于发送请求
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] buff = baos.toByteArray();
            // Log.v(TAG, "length: " + buff.length);
            bundle.putByteArray("pic", buff);
            mTencent.requestAsync(Constants.GRAPH_ADD_PIC_T, bundle, Constants.HTTP_POST, new DtSendMsgApiListener(listener), null);
        } else
        {
            login(activity, listener);
        }

    }

    public void sendQQMsg(Activity activity, String title, String content, String targetUrl, String imgUrl, String appName, DtTencentOperatorListener listener)
    {
        Bundle params = new Bundle();
        params.putInt(Tencent.SHARE_TO_QQ_KEY_TYPE, Tencent.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(Tencent.SHARE_TO_QQ_TITLE, title);
        params.putString(Tencent.SHARE_TO_QQ_SUMMARY, content);
        params.putString(Tencent.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(Tencent.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(Tencent.SHARE_TO_QQ_APP_NAME, appName);
        mTencent.shareToQQ(activity, params, new DtSendMsgUiListener(listener));

    }

    public void sendQQMusic(Activity activity, String title, String content, String targetUrl, String imgUrl, String musicUrl, String appName, DtTencentOperatorListener listener)
    {
        Bundle params = new Bundle();
        params.putInt(Tencent.SHARE_TO_QQ_KEY_TYPE, Tencent.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(Tencent.SHARE_TO_QQ_TITLE, title);
        params.putString(Tencent.SHARE_TO_QQ_SUMMARY, content);
        params.putString(Tencent.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(Tencent.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(Tencent.SHARE_TO_QQ_AUDIO_URL, musicUrl);
        params.putString(Tencent.SHARE_TO_QQ_APP_NAME, appName);
        mTencent.shareToQQ(activity, params, new DtSendMsgUiListener(listener));
    }

    public void sendQzoneMsg(Activity activity, String title, String content, String targetUrl, ArrayList<String> imgUrls, DtTencentOperatorListener listener)
    {
        Bundle params = new Bundle();
        params.putString(Tencent.SHARE_TO_QQ_TITLE, title);
        params.putString(Tencent.SHARE_TO_QQ_SUMMARY, content);
        params.putString(Tencent.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putStringArrayList(Tencent.SHARE_TO_QQ_IMAGE_URL, imgUrls);
        mTencent.shareToQzone(activity, params, new DtSendMsgUiListener(listener));
    }


    public void reAuth(Activity activity, DtTencentOperatorListener listener)
    {
        mTencent.reAuth(activity, "all", new DtBaseUiListener(listener));
    }

    public Tencent getTencent()
    {
        return mTencent;
    }

    public void setTencent(Tencent tencent)
    {
        mTencent = tencent;
    }
}
