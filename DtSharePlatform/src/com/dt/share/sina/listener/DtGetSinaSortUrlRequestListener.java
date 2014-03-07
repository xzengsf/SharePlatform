package com.dt.share.sina.listener;

import android.text.TextUtils;
import com.dt.share.sina.base.DtResultUserInfo;
import com.dt.share.sina.base.DtSinaTag;
import com.dt.share.sina.net.RequestListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 上午3:47
 */
public class DtGetSinaSortUrlRequestListener implements RequestListener
{
    private DtSinaOperatorListener mSinaOperatorListener;

    public DtGetSinaSortUrlRequestListener(DtSinaOperatorListener listener)
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
                mSinaOperatorListener.onError(DtSinaTag.GET_SORT_URL_FAILD, message);
            } catch (JSONException e)
            {
                e.printStackTrace();
                mSinaOperatorListener.onError(DtSinaTag.JSONERROR, e.getMessage());
            }
        } else
        {
            try
            {
                JSONObject obj = new JSONObject(response);
                JSONArray urls = obj.getJSONArray("urls");
                System.out.println(urls.toString());
                JSONObject jShort = urls.getJSONObject(0);
                System.out.println(jShort.toString());
                String sortUrl = jShort.getString("url_short");
                System.out.println(sortUrl.toString());
                mSinaOperatorListener.onSuccessed(DtSinaTag.GET_SORT_URL_SUCCESS, sortUrl);

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
        mSinaOperatorListener.onError(DtSinaTag.GET_SORT_URL_FAILD, e.getMessage());
    }

    @Override
    public void onError(WeiboException e)
    {
        mSinaOperatorListener.onError(DtSinaTag.GET_SORT_URL_FAILD, e.getMessage());
    }
}
