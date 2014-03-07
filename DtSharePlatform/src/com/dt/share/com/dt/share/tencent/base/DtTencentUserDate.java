package com.dt.share.com.dt.share.tencent.base;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-20
 * Time: 下午6:27
 */
public class DtTencentUserDate
{
    private String mUid;
    private String mNickName;
    private String mImgHeadUrl;
    private String mDescription;

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        mDescription = description;
    }

    public String getImgHeadUrl()
    {

        return mImgHeadUrl;
    }

    public void setImgHeadUrl(String imgHeadUrl)
    {
        mImgHeadUrl = imgHeadUrl;
    }

    public String getNickName()
    {

        return mNickName;
    }

    public void setNickName(String nickName)
    {
        mNickName = nickName;
    }

    public String getUid()
    {

        return mUid;
    }

    public void setUid(String uid)
    {
        mUid = uid;
    }
}
