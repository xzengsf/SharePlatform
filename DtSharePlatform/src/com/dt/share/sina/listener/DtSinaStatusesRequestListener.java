package com.dt.share.sina.listener;

import android.util.Log;
import com.dt.share.sina.base.DtSinaTag;
import com.dt.share.sina.net.RequestListener;
import com.sina.weibo.sdk.exception.WeiboException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 下午12:06
 */
public class DtSinaStatusesRequestListener implements RequestListener
{
    private DtSinaOperatorListener mSinaOperatorListener;

    public DtSinaStatusesRequestListener(DtSinaOperatorListener listener)
    {
        mSinaOperatorListener = listener;
    }

    @Override
    public void onComplete(String response)
    {
        System.out.println("发送微博成功");
        Log.d("DtSinaStatusesRequestListener success", "发送微博成功");
        mSinaOperatorListener.onSuccessed(DtSinaTag.SEND_TXT_MESSAGE_SUCCESS, null);
    }

    @Override
    public void onComplete4binary(ByteArrayOutputStream responseOS)
    {
    }

    @Override
    public void onIOException(IOException e)
    {
        e.printStackTrace();
        mSinaOperatorListener.onError(DtSinaTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
        Log.d("DtSinaStatusesRequestListener erroe", "发送微博失败");
    }

    @Override
    public void onError(WeiboException e)
    {
        e.printStackTrace();
        mSinaOperatorListener.onError(DtSinaTag.SEND_TXT_MESSAGE_FAILD, e.getMessage());
        Log.d("DtSinaStatusesRequestListener erroe", "发送微博失败");
    }
}
