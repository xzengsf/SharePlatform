package com.dt.share.sina.listener;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.dt.share.sina.api.AccountAPI;
import com.dt.share.sina.base.AccessTokenKeeper;
import com.dt.share.sina.base.DtSinaTag;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 上午1:21
 */
public class DtSinaAuthListener implements WeiboAuthListener
{
    private Oauth2AccessToken      mAccessToken;
    private Context                mContext;
    private DtSinaOperatorListener mDtSinaOperatorListener;

    public DtSinaAuthListener(Context context, Oauth2AccessToken accessToken, DtSinaOperatorListener listener)
    {
        mContext = context;
        mAccessToken = accessToken;
        mDtSinaOperatorListener = listener;
    }
    public void setSinaOperatorListener(DtSinaOperatorListener listener)
    {
        mDtSinaOperatorListener = listener;
    }
    @Override
    public void onComplete(Bundle bundle)
    {
        mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (mAccessToken.isSessionValid())
        {
            Toast.makeText(mContext, "认证成功", Toast.LENGTH_SHORT).show();
            AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
            mDtSinaOperatorListener.onSuccessed(DtSinaTag.LOGIN_SUCCESS, mAccessToken);

        } else
        {
            mDtSinaOperatorListener.onError(DtSinaTag.LOGIN_FAILD, bundle.getString("code"));
            Toast.makeText(mContext, "code:" + bundle.getString("code"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWeiboException(WeiboException e)
    {
        Toast.makeText(mContext, "错误:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel()
    {
        Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
    }
}
