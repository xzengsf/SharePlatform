package com.dt.share.sina.listener;

import android.content.Context;
import android.text.TextUtils;
import com.dt.share.sina.base.AccessTokenKeeper;
import com.dt.share.sina.base.DtSinaTag;
import com.dt.share.sina.net.RequestListener;
import com.sina.weibo.sdk.exception.WeiboException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 下午4:13
 */
public class DtSinaLogoutRequestListener implements RequestListener
{
    private Context                mContext;
    private DtSinaOperatorListener mSinaOperatorListener;

    public DtSinaLogoutRequestListener(Context context, DtSinaOperatorListener listener)
    {
        mContext = context;
        mSinaOperatorListener = listener;
    }
    public void setSinaOperatorListener(DtSinaOperatorListener listener)
    {
        mSinaOperatorListener = listener;
    }
    @Override
    public void onComplete(String response)
    {
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject obj = new JSONObject(response);
                String value = obj.getString("result");
                if ("true".equalsIgnoreCase(value))
                {
                    AccessTokenKeeper.clear(mContext);
                    mSinaOperatorListener.onSuccessed(DtSinaTag.LOGOUT_SUCCESS, null);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
                mSinaOperatorListener.onError(DtSinaTag.JSONERROR, e.getMessage());
            }
        }
    }

    @Override
    public void onComplete4binary(ByteArrayOutputStream responseOS)
    {
    }

    @Override
    public void onIOException(IOException e)
    {
        e.printStackTrace();
    }

    @Override
    public void onError(WeiboException e)
    {
        e.printStackTrace();
    }
}
