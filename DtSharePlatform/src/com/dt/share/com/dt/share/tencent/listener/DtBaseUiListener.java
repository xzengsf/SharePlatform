package com.dt.share.com.dt.share.tencent.listener;

import com.dt.share.com.dt.share.tencent.base.DtTencentTag;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 上午5:48
 */
public class DtBaseUiListener implements IUiListener
{
    private DtTencentOperatorListener mTencentOperatorListener;

    public DtBaseUiListener(DtTencentOperatorListener tencentOperatorListener)
    {
        mTencentOperatorListener = tencentOperatorListener;
    }

    @Override
    public void onComplete(JSONObject jsonObject)
    {
        try
        {
            String openId = jsonObject.getString("openid");
            mTencentOperatorListener.onSuccessed(DtTencentTag.LOGIN_SUCCESS, openId);
        } catch (JSONException e)
        {
            e.printStackTrace();
            mTencentOperatorListener.onError(DtTencentTag.JSONERROR, e.getMessage());
        }
    }

    @Override
    public void onError(UiError uiError)
    {
        mTencentOperatorListener.onError(DtTencentTag.LOGIN_FAILD, uiError.errorMessage);
    }

    @Override
    public void onCancel()
    {
        mTencentOperatorListener.onCancel();
    }
}
