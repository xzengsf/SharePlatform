package com.dt.share.com.dt.share.tencent.listener;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import com.dt.share.com.dt.share.tencent.base.DtTencentTag;
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
 * Time: 上午5:56
 */
public class DtGetTencentInfoRequestListener implements IRequestListener
{
    private DtTencentOperatorListener mDtTencentOperatorListener;

    public DtGetTencentInfoRequestListener(DtTencentOperatorListener listener)
    {
        mDtTencentOperatorListener = listener;
    }

    @Override
    public void onComplete(final JSONObject response, Object o)
    {
        System.out.println("QQ 登录返回信息:" + response.toString());
        mDtTencentOperatorListener.onSuccessed(DtTencentTag.LOGIN_SUCCESS, null);
    }

    @Override
    public void onIOException(IOException e, Object o)
    {

    }

    @Override
    public void onMalformedURLException(MalformedURLException e, Object o)
    {
    }

    @Override
    public void onJSONException(JSONException e, Object o)
    {
    }

    @Override
    public void onConnectTimeoutException(ConnectTimeoutException e, Object o)
    {
    }

    @Override
    public void onSocketTimeoutException(SocketTimeoutException e, Object o)
    {
    }

    @Override
    public void onNetworkUnavailableException(NetworkUnavailableException e, Object o)
    {
    }

    @Override
    public void onHttpStatusException(HttpStatusException e, Object o)
    {
    }

    @Override
    public void onUnknowException(Exception e, Object o)
    {
    }
}
