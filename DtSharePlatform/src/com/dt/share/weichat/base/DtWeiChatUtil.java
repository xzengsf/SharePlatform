package com.dt.share.weichat.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.dt.share.R;
import com.tencent.mm.sdk.openapi.*;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-22
 * Time: 下午6:55
 */
public class DtWeiChatUtil
{
    private IWXAPI mWXApi;
    private static final int THUMB_SIZE = 150;
    private Context mContext;


    public DtWeiChatUtil(Context context, String appId)
    {
        mWXApi = WXAPIFactory.createWXAPI(context, appId);
        mWXApi.registerApp(appId);
        mContext = context;
    }

    public void sendWeichatToSessionText(String title, String content)
    {
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = content;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction�ֶ�����Ψһ��ʶһ������
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        mWXApi.sendReq(req);
    }

    public void sendWeichatToTimelineText(String title, String content)
    {
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = content;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction�ֶ�����Ψһ��ʶһ������
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mWXApi.sendReq(req);
    }

    public void sendWeichatToSessionPic(Bitmap bitmap)
    {
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = "sdsadas";
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mWXApi.sendReq(req);
    }

    public void sendWeichatToTimelinePic(Bitmap bitmap)
    {
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = "sdsadas";
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mWXApi.sendReq(req);
    }

    public void sendWeichatToSessionMusic(String musicUrl, String title, String content, Bitmap bmp)
    {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = content;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 72, 72, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mWXApi.sendReq(req);
    }

    public void sendWeichatToTimelinMusic(String musicUrl, String title, String content, Bitmap bmp)
    {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = content;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 72, 72, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mWXApi.sendReq(req);
    }

    private String buildTransaction(final String type)
    {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public IWXAPI getWXApi()
    {
        return mWXApi;
    }
}
