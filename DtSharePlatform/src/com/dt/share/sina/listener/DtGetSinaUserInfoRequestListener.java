package com.dt.share.sina.listener;

import android.text.TextUtils;
import com.dt.share.sina.base.DtResultUserInfo;
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
 * Time: 上午3:47
 */
public class DtGetSinaUserInfoRequestListener implements RequestListener
{
    private DtSinaOperatorListener mSinaOperatorListener;

    public DtGetSinaUserInfoRequestListener(DtSinaOperatorListener listener)
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
                mSinaOperatorListener.onError(DtSinaTag.GET_USER_INFO_FAILD, message);
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
                //字符串型的用户ID
                String uid = obj.getString("idstr");
                //用户昵称
                String screen_name = obj.getString("screen_name");
                //友好显示名称
                String name = obj.getString("name");
                //用户所在省级ID
                int province = obj.getInt("province");
                //用户所在城市ID
                int city = obj.getInt("city");
                //用户所在地
                String location = obj.getString("location");
                //用户个人描述
                String description = obj.getString("description");
                //用户博客地址
                String url = obj.getString("url");
                //用户头像地址 50*50
                String profile_image_url = obj.getString("profile_image_url");
                //用户的微博同一URL地址
                String profile_url = obj.getString("profile_url");
                //用户的个性化域名
                String domain = obj.getString("domain");
                //用户的微号
                String weihao = obj.getString("weihao");
                //性别，m:男 f:女 n:未知
                String gender = obj.getString("gender");
                //粉丝数
                int followers_count = obj.getInt("followers_count");
                //关注数
                int friends_count = obj.getInt("friends_count");
                //微博数
                int statuses_count = obj.getInt("statuses_count");
                //收藏数
                int favourites_count = obj.getInt("favourites_count");
                //用户注册时间
                String created_at = obj.getString("created_at");
                //是否允许所有人给我发私信，true为是 false为否
                boolean allow_all_act_msg = obj.getBoolean("allow_all_act_msg");
                //是否允许标识用户的地理位置，true为是，false为否
                boolean geo_enabled = obj.getBoolean("geo_enabled");
                //是否是微博认证用户，即加V用户，true为是，false为否
                boolean verified = obj.getBoolean("verified");
                //用户的最近一条微博信息字段
                Object status = obj.get("status");
                //是否允许所有人对我的微博进行评论，true：是，false：否
                boolean allow_all_comment = obj.getBoolean("allow_all_comment");
                //用户头像地址（大图），180×180像素
                String avatar_large = obj.getString("avatar_large");
                //用户头像地址（高清），高清头像原图
                String avatar_hd = obj.getString("avatar_hd");
                //认证原因
                String verified_reason = obj.getString("verified_reason");
                //该用户是否关注当前登录用户，true：是，false：否
                boolean follow_me = obj.getBoolean("follow_me");
                //用户的在线状态，0：不在线、1：在线
                int online_status = obj.getInt("online_status");
                //用户的互粉数
                int bi_followers_count = obj.getInt("bi_followers_count");
                //用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
                String lang = obj.getString("lang");
                DtResultUserInfo userInfo = new DtResultUserInfo();
                userInfo.setUid(uid);
                userInfo.setNickName(screen_name);
                userInfo.setImgHeadUrl(avatar_large);
                userInfo.setDescription(description);
                mSinaOperatorListener.onSuccessed(DtSinaTag.GET_USER_INFO_SUCCESS, userInfo);
                System.out.println("uid:" + uid + " 昵称" + screen_name + " 所在城市:" + location + " 头像地址:" + profile_image_url);
            } catch (JSONException e)
            {
                e.printStackTrace();
                mSinaOperatorListener.onError(DtSinaTag.GET_USER_INFO_FAILD, e.getMessage());
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
        mSinaOperatorListener.onError(DtSinaTag.GET_USER_INFO_FAILD, e.getMessage());
    }

    @Override
    public void onError(WeiboException e)
    {
        mSinaOperatorListener.onError(DtSinaTag.GET_USER_INFO_FAILD, e.getMessage());
    }
}
