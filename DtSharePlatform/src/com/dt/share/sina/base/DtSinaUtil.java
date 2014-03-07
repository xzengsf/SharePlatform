package com.dt.share.sina.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.dt.share.sina.api.*;
import com.dt.share.sina.listener.*;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 下午12:26
 */
public class DtSinaUtil
{
    private WeiboAuth         mWeiboAuth;
    private Oauth2AccessToken mAccessToken;
    private SsoHandler        mSsoHandler;
    private String            mAppKey;
    private Context           mContext;

    public DtSinaUtil(Context context, String appKey)
    {
        mContext = context;
        mAppKey = appKey;
    }

    public void init()
    {
        mWeiboAuth = new WeiboAuth(mContext, mAppKey, DtSinaConstants.REDIRECT_URL, DtSinaConstants.SCOPE);
        mAccessToken = AccessTokenKeeper.readAccessToken(mContext);
    }


    public void login(Activity activity, DtSinaOperatorListener listener)
    {
        mSsoHandler = new SsoHandler(activity, mWeiboAuth);
        mSsoHandler.authorize(new DtSinaAuthListener(mContext, mAccessToken, listener));
    }

    public void sendTextWeibo(Activity activity, String content, DtSinaOperatorListener listener)
    {
        if (mAccessToken.isSessionValid())
        {
            new StatusesAPI(mAccessToken).update(content, "", "", new DtSinaStatusesRequestListener(listener));
        } else
        {
            login(activity, listener);
        }
    }

    public void sendPicWeibo(Activity activity, String content, String filePath, DtSinaOperatorListener listener)
    {
        if (mAccessToken.isSessionValid())
        {
            new StatusesAPI(mAccessToken).uploadPic(content, filePath, new DtSinaStatusesRequestListener(listener));
        } else
        {
            login(activity, listener);
        }
    }

    public void getSortUrl(String urls, DtSinaOperatorListener listener)
    {
        new ShortUrlAPI(mAccessToken).shorten(urls, new DtGetSinaSortUrlRequestListener(listener));
    }

    public void getUid(DtSinaOperatorListener listener)
    {
        new AccountAPI(mAccessToken).getUid(new DtGetSinaUidRequestListener(listener));
    }

    public void getUserInfo(long uid, DtSinaOperatorListener listener)
    {
        new UsersAPI(mAccessToken).show(uid, new DtGetSinaUserInfoRequestListener(listener));
    }

    public void logout(DtSinaOperatorListener listener)
    {
        new LogoutAPI(mAccessToken).logout(new DtSinaLogoutRequestListener(mContext, listener));
    }

    public SsoHandler getSsoHandler()
    {
        return mSsoHandler;
    }

    public Oauth2AccessToken getAccessToken()
    {
        return mAccessToken;
    }

    public void setAccessToken(Oauth2AccessToken accessToken)
    {
        mAccessToken = accessToken;
    }
}
