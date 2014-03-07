package com.dt.consulting.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.dt.share.com.dt.share.tencent.base.DtTencentTag;
import com.dt.share.com.dt.share.tencent.base.DtTencentUserDate;
import com.dt.share.com.dt.share.tencent.base.DtTencentUtil;
import com.dt.share.com.dt.share.tencent.listener.DtTencentOperatorListener;
import com.dt.share.sina.base.*;
import com.dt.share.sina.listener.DtSinaOperatorListener;
import com.dt.share.weichat.base.DtWeiChatUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.z.shareutil.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

public class MyActivity extends Activity implements View.OnClickListener
{
    private Button        mBtnSina;
    private Button        mBtnSinaLogout;
    private Button        mBtnQQ;
    private Button        mBtnWeichat;
    private ImageView     mImageView;
    private DtSinaUtil    mDtSinaUtil;
    private DtTencentUtil mTencentUtil;
    private DtWeiChatUtil mWeiChatUtil;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mBtnSina = (Button) findViewById(R.id.btn_weibo);
        mImageView = (ImageView) findViewById(R.id.img_test);
        mBtnQQ = (Button) findViewById(R.id.btn_tencent);
        mBtnSinaLogout = (Button) findViewById(R.id.btn_weibo_logout);
        mBtnWeichat = (Button) findViewById(R.id.btn_weichat);
        mBtnSinaLogout.setOnClickListener(this);
        mBtnQQ.setOnClickListener(this);
        mBtnSina.setOnClickListener(this);
        mBtnWeichat.setOnClickListener(this);
        mDtSinaUtil = new DtSinaUtil(this, "2627679301");
        mDtSinaUtil.init();
        mTencentUtil = new DtTencentUtil(this, "100506593");
        mWeiChatUtil = new DtWeiChatUtil(this, "wx120677fa4c83e47b");
    }

    private DtTencentOperatorListener mTencentOperatorListener = new DtTencentOperatorListener()
    {
        @Override
        public void onSuccessed(int type, Object o)
        {
            if (type == DtTencentTag.LOGIN_SUCCESS)
            {
                mTencentUtil.saveInfo();
                System.out.println("登录成功");
            } else if (type == DtTencentTag.GET_USER_INFO_SUCCESS)
            {
                DtTencentUserDate info = (DtTencentUserDate) o;
                System.out.println("返回信息:" + info.getUid() + " " + info.getNickName() + " " + info.getImgHeadUrl() + " " + info.getDescription());
            } else if (type == DtTencentTag.SEND_TXT_MESSAGE_SUCCESS)
            {
                Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(int type, String code)
        {
            if (type == DtTencentTag.LOGIN_FAILD)
            {
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
            } else if (type == DtTencentTag.GET_USER_INFO_FAILD)
            {
                Toast.makeText(getApplicationContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
            } else if (type == DtTencentTag.OAUTH_TIME_OUT)
            {
                mTencentUtil.reAuth(MyActivity.this, this);
            } else if (type == DtTencentTag.SEND_TXT_MESSAGE_FAILD)
            {
                Toast.makeText(getApplicationContext(), "分享微博失败", Toast.LENGTH_SHORT).show();
                System.out.println("CODE:" + code);
            }
            System.out.println("错误值:" + code);
        }

        @Override
        public void onCancel()
        {
            Toast.makeText(getApplicationContext(), "用户取消", Toast.LENGTH_SHORT).show();
        }
    };

    private DtSinaOperatorListener mSinaOperatorListener = new DtSinaOperatorListener()
    {
        @Override
        public void onSuccessed(int type, Object o)
        {
            if (type == DtSinaTag.LOGIN_SUCCESS)
            {
                mDtSinaUtil.init();
                mDtSinaUtil.sendTextWeibo(MyActivity.this, "ssss", mSinaOperatorListener);
                //                Oauth2AccessToken accessToken = (Oauth2AccessToken) o;
                //                mDtSinaUtil.setAccessToken(accessToken);
                //                String path = "";
                //                try
                //                {
                //                    File file = saveResourceToFile(MyActivity.this, R.drawable.img_splash, Environment.getExternalStorageDirectory().getAbsolutePath(), "test.jpg");
                //                    path = file.getAbsolutePath();
                //                } catch (IOException e)
                //                {
                //                    e.printStackTrace();
                //                }
                //                mDtSinaUtil.getUid(this);
                //                String ss = "http://www.duoting.fm";
                //                mDtSinaUtil.getSortUrl(ss, this);
                //                System.out.println("登录成功");
            } else if (type == DtSinaTag.SEND_TXT_MESSAGE_SUCCESS)
            {
                System.out.println("分享成功");
            } else if (type == DtSinaTag.GET_UID_SUCCESS)
            {
                System.out.println("获取UID成功");
                long uid = Long.valueOf(o.toString());
                mDtSinaUtil.getUserInfo(uid, this);
            } else if (type == DtSinaTag.GET_USER_INFO_SUCCESS)
            {
                System.out.println("获取用户信息成功");
                DtResultUserInfo info = (DtResultUserInfo) o;
                System.out.println("用户信息:" + info.getUid() + " " + info.getNickName() + " " + info.getImgHeadUrl() + " " + info.getDescription());
            } else if (type == DtSinaTag.LOGOUT_SUCCESS)
            {
                mDtSinaUtil.setAccessToken(null);
                System.out.println("退出成功");
            } else if (type == DtSinaTag.GET_SORT_URL_SUCCESS)
            {
                String sortUrl = (String) o;
                System.out.println("短网址:" + sortUrl);
                String path = "";
                try
                {
                    File file = saveResourceToFile(MyActivity.this, R.drawable.img_splash, Environment.getExternalStorageDirectory().getAbsolutePath(), "test.jpg");
                    path = file.getAbsolutePath();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                mDtSinaUtil.sendPicWeibo(MyActivity.this, "啦啦啦haha啦啦 " + sortUrl, path, mSinaOperatorListener);
            }
        }

        @Override
        public void onError(int type, String code)
        {
            if (type == DtSinaTag.LOGIN_FAILD)
            {
                System.out.println("登录失败");
            } else if (type == DtSinaTag.SEND_TXT_MESSAGE_FAILD)
            {
                System.out.println("分享失败");
            } else if (type == DtSinaTag.JSONERROR)
            {
                System.out.println("json解析错误");
            } else if (type == DtSinaTag.GET_UID_FAILD)
            {
                System.out.println("获取UID失败");
            } else if (type == DtSinaTag.LOGOUT_FAILD)
            {
                System.out.println("退出失败");
            } else if (type == DtSinaTag.GET_SORT_URL_FAILD)
            {
                System.out.println("获取短网址失败:" + code);
            }
        }
    };

    @Override
    public void onClick(View view)
    {
        if (view == mBtnSina)
        {
            //            if (mDtSinaUtil.getAccessToken().isSessionValid())
            //            {
            //
            //                String ss = "http://www.duoting.fm";
            //                mDtSinaUtil.getSortUrl(ss, mSinaOperatorListener);
            //            } else
            //            {
            //                mDtSinaUtil.login(this, mSinaOperatorListener);
            //            }
            mDtSinaUtil.sendTextWeibo(this, "ttttt", mSinaOperatorListener);
        } else if (view == mBtnQQ)
        {
            mTencentUtil.login(this, mTencentOperatorListener);
        } else if (view == mBtnSinaLogout)
        {
            if (mDtSinaUtil.getAccessToken() != null && mDtSinaUtil.getAccessToken().isSessionValid())
            {
                mDtSinaUtil.logout(mSinaOperatorListener);
            }
        } else if (view == mBtnWeichat)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
            mWeiChatUtil.sendWeichatToTimelinMusic("http://x.duoting.fm/sleep_music/sleep_music1.mp3", "测试一下", "测试内容", bitmap);
            //            mWeiChatUtil.sendWeichatToSessionText("dasdasds", "dasdasd");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (mDtSinaUtil.getSsoHandler() != null)
        {
            mDtSinaUtil.getSsoHandler().authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public static File saveResourceToFile(Context context, int resId, String path, String fileName) throws IOException
    {
        File filePath = new File(path);
        filePath.mkdirs();
        File file = new File(filePath, fileName);
        FileOutputStream out = new FileOutputStream(file);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        if (out != null)
        {
            out.close();
        }
        return file;
    }
}
