package com.dt.share.sina.listener;

import android.text.TextUtils;
import com.dt.share.sina.base.DtSinaTag;
import com.dt.share.sina.net.RequestListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 上午3:32
 */
public class DtGetSinaUidRequestListener implements RequestListener
{
    private DtSinaOperatorListener mSinaOperatorListener;

    public DtGetSinaUidRequestListener(DtSinaOperatorListener listener)
    {
        mSinaOperatorListener = listener;
    }
    public void setSinaOperatorListener(DtSinaOperatorListener listener)
    {
        mSinaOperatorListener = listener;
    }
    @Override
    public void onComplete(String response)
    {
        if (TextUtils.isEmpty(response) || response.contains("error_code"))
        {
            try
            {
                JSONObject obj = new JSONObject(response);
                String errorMsg = obj.getString("error");
                String errorCode = obj.getString("error_code");
                String message = "error_code: " + errorCode + "error_message: " + errorMsg;
                LogUtil.e("GetUidError", "GetUid Failed: " + message);
                mSinaOperatorListener.onError(DtSinaTag.GET_UID_FAILD, message);
            } catch (JSONException e)
            {
                e.printStackTrace();
                mSinaOperatorListener.onError(DtSinaTag.JSONERROR, "json解析错误");
            }
        } else
        {
            try
            {
                JSONObject obj = new JSONObject(response);
                String uid = obj.getString("uid");
                System.out.println("获取到的UID:" + uid);
                //此处实际用时可用通知器实现
                long temp = Long.valueOf(uid);
                mSinaOperatorListener.onSuccessed(DtSinaTag.GET_UID_SUCCESS, temp);
            } catch (JSONException e)
            {
                e.printStackTrace();
                mSinaOperatorListener.onError(DtSinaTag.JSONERROR, "json解析错误");
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
        mSinaOperatorListener.onError(DtSinaTag.GET_UID_FAILD, e.getMessage());
    }

    @Override
    public void onError(WeiboException e)
    {
        mSinaOperatorListener.onError(DtSinaTag.GET_UID_FAILD, e.getMessage());
    }
}
