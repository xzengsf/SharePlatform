package com.dt.share.com.dt.share.tencent.listener;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 下午1:04
 */
public interface DtTencentOperatorListener
{
    public void onSuccessed(int type, Object o);

    public void onError(int type, String code);

    public void onCancel();
}
