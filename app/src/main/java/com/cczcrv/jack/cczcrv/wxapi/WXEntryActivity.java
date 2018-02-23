package com.cczcrv.jack.cczcrv.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.cczcrv.jack.cczcrv.ApiModel.GetAccessTokenApi;
import com.cczcrv.jack.cczcrv.Base.BaseActivity;
import com.cczcrv.jack.cczcrv.MainActivity;
import com.cczcrv.jack.cczcrv.R;
import com.cczcrv.jack.cczcrv.Tools.okHttp.callback.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.ButterKnife;


public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private Button gotoBtn, regBtn, launchBtn, checkBtn, scanBtn;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    SendAuth.Req req;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_entry_activity);
        ButterKnife.bind(this);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);

        // 将该app注册到微信
        api.registerApp(APP_ID);




        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(OpenID=="")
        {
            Toast.makeText(this,AuthorizationCount+"",Toast.LENGTH_SHORT);
            if(AuthorizationCount<1)
            {
                req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "diandi_wx_login";
                api.sendReq(req);
                AuthorizationCount++;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }



    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                //goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                //goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;

        Toast.makeText(this, "baseresp.getType = " + resp.getType(), Toast.LENGTH_SHORT).show();

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                String code = ((SendAuth.Resp) resp).code;
                getAccessToken(code, new IgetAccessToken() {
                    @Override
                    public void getAccessToken_Return() {
                        Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;

                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

//    private void goToGetMsg() {
//        Intent intent = new Intent(this, GetFromWXActivity.class);
//        intent.putExtras(getIntent());
//        startActivity(intent);
//        finish();
//    }

//    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
//        WXMediaMessage wxMsg = showReq.message;
//        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//        StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
//        msg.append("description: ");
//        msg.append(wxMsg.description);
//        msg.append("\n");
//        msg.append("extInfo: ");
//        msg.append(obj.extInfo);
//        msg.append("\n");
//        msg.append("filePath: ");
//        msg.append(obj.filePath);
//
//        Intent intent = new Intent(this, ShowFromWXActivity.class);
//        intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
//        intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
//        intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
//        startActivity(intent);
//        finish();
//    }

    private void getAccessToken(String code, final IgetAccessToken ifun) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID + "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code";
        OkGo.get(url)
                .execute(new JsonCallback(GetAccessTokenApi.class) {
                    @Override
                    public void onSuccess(Response response) {
                        GetAccessTokenApi getAccessTokenApi = (GetAccessTokenApi) response.body();
                        AccessToken = getAccessTokenApi.getAccess_token();
                        OpenID = getAccessTokenApi.getOpenid();
                        RefreshToken = getAccessTokenApi.getRefresh_token();
                        Long expires_in = getAccessTokenApi.getExpires_in();
                        Unionid = getAccessTokenApi.getUnionid();
                        ifun.getAccessToken_Return();
                    }
                });
    }

    interface IgetAccessToken {
        void getAccessToken_Return();
    }

}