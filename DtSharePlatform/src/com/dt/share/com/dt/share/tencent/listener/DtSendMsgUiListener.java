package com.dt.share.com.dt.share.tencent.listener;

import com.dt.share.com.dt.share.tencent.base.DtTencentTag;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 上午5:48
 */
public class DtSendMsgUiListener implements IUiListener
{
    private DtTencentOperatorListener mTencentOperatorListener;

    public DtSendMsgUiListener(DtTencentOperatorListener tencentOperatorListener)
    {
        mTencentOperatorListener = tencentOperatorListener;
    }

    @Override
    public void onComplete(JSONObject jsonObject)
    {
        System.out.println("分享成功；内部");
        mTencentOperatorListener.onSuccessed(DtTencentTag.SEND_TXT_MESSAGE_SUCCESS, jsonObject);
    }

    @Override
    public void onError(UiError uiError)
    {
        System.out.println("分享失败；内部");
        mTencentOperatorListener.onError(DtTencentTag.SEND_TXT_MESSAGE_FAILD, uiError.errorMessage);
    }

    @Override
    public void onCancel()
    {
        System.out.println("取消分享 内部");
        mTencentOperatorListener.onCancel();
    }
}
