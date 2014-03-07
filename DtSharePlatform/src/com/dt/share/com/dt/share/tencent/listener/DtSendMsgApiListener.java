package com.dt.share.com.dt.share.tencent.listener;

import com.dt.share.com.dt.share.tencent.base.DtTencentTag;
import com.dt.share.com.dt.share.tencent.base.DtTencentUserDate;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 下午6:19
 */
public class DtSendMsgApiListener implements IRequestListener
{
    private DtTencentOperatorListener mTencentOperatorListener;

    public DtSendMsgApiListener(DtTencentOperatorListener listener)
    {
        mTencentOperatorListener = listener;
    }

    @Override
    public void onComplete(JSONObject jsonObject, Object o)
    {
        DtTencentUserDate info = new DtTencentUserDate();
        try
        {
            int ret = jsonObject.getInt("ret");
            if (jsonObject.has("data"))
            {
                mTencentOperatorListener.onSuccessed(DtTencentTag.SEND_TXT_MESSAGE_SUCCESS, info);
            }
            if (ret == 100030)
            {
                mTencentOperatorListener.onError(DtTencentTag.OAUTH_TIME_OUT, "授权已过期,请重新授权");
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
            mTencentOperatorListener.onError(DtTencentTag.JSONERROR, e.getMessage());
        }
    }

    @Override
    public void onIOException(IOException e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }

    @Override
    public void onMalformedURLException(MalformedURLException e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }

    @Override
    public void onJSONException(JSONException e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }

    @Override
    public void onConnectTimeoutException(ConnectTimeoutException e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }

    @Override
    public void onSocketTimeoutException(SocketTimeoutException e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }

    @Override
    public void onNetworkUnavailableException(NetworkUnavailableException e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }

    @Override
    public void onHttpStatusException(HttpStatusException e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }

    @Override
    public void onUnknowException(Exception e, Object o)
    {
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
    }
}
