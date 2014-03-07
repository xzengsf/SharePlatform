package com.dt.consulting.news;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.dt.share.com.dt.share.tencent.base.DtTencentTag;
import com.dt.share.com.dt.share.tencent.base.DtTencentUtil;
import com.dt.share.com.dt.share.tencent.listener.DtTencentOperatorListener;
import com.z.shareutil.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: work
 * Date: 14-1-22
 * Time: 上午5:29
 */
public class ShareTestActivity extends Activity
{
    private ImageView mImgView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maincp);
        btn=(Button)findViewById(R.id.btn_test);
        btn.setText("dshajhsajdhasjkh");
        btn.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
        mImgView=(ImageView)findViewById(R.id.img_share_qzone);
        TextView txt=(TextView)findViewById(R.id.txt);
        txt.setText("dsadas");
        txt.setBackground(smd.a);
        final ArrayList<String> aa=new ArrayList<String>();
        aa.add("http://wwww.baidu.com/sds.jpg");
        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DtTencentUtil util=new DtTencentUtil(ShareTestActivity.this,"100547257");
                util.login(ShareTestActivity.this,new DtTencentOperatorListener() {
                    @Override
                    public void onSuccessed(int type, Object o) {
                        if (type==DtTencentTag.LOGIN_SUCCESS){
                            util.sendQzoneMsg(ShareTestActivity.this,"sss","sssss","http://www.baidu.com",aa,this);
                        }else if (type==DtTencentTag.SEND_TXT_MESSAGE_SUCCESS){
                            System.out.println("发送成功");
                        }
                    }

                    @Override
                    public void onError(int type, String code) {
                        System.out.println("外部失败");
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("外部取消");
                    }
                });
            }
        });
    }
}
