package com.ledboot.toffee;

import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ledboot.toffee.base.BaseActivity;


/**
 * Created by Gwynn on 17/10/24.
 */
public class FingeprintActivity extends BaseActivity {

    private TextView fingerTx = null;

    public static String publicKeyStr = "MIGWMA0GCSqGSIb3DQEBAQUAA4GEADCBgAJ5AL2KNNzht9zQVQPiXcpQGC0kd0aMUJscviBkaaGRL36U26r9UvG7yTVSYxLWRN7FU7eYy2J4otWLASk6atN8LEYkd5KQKuCn12Asp4T8JbJHi805sUvjrN5BfjHW+uN104CRwb/bK/2hHCcLmvRl5wu5MCRmbvZ8uwIDAQAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        final WebView webview = new WebView(this);

        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setText("刷新");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.reload();
            }
        });

        fingerTx = new TextView(this);
        fingerTx.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        webview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        webview.loadUrl("file:///android_asset/bs.html");
        WebSettings webSetting = webview.getSettings();


        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptFileSchemeCookies(true);

        webSetting.setAppCacheEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                CookieSyncManager.getInstance().sync();
            }
        });


        root.addView(fingerTx);
        root.addView(button);
        root.addView(webview);
        setContentView(root);
//        startDFP();
    }

//    private void startDFP() {
//        try {
//            String fingerprint = FRMS.getInstance().get(5000);
//            StringBuilder builder = new StringBuilder();
//            DeviceFingerPrint dfp = ValidateManager.decryptDFPInfo(fingerprint, publicKeyStr);
//            builder.append("fingerprint:" + fingerprint);
//            builder.append("内码:" + dfp.getInnerCode());
//            builder.append("是否过期:" + dfp.isExpire());
//            builder.append("IP:" + dfp.getIp());
//            builder.append("版本:" + dfp.getVersion());
//            builder.append("平台:" + dfp.getPlatform());
//            builder.append("isVM:" + dfp.isVm());
//            builder.append("isIdc:" + dfp.isIdc());
//            builder.append("isVpn:" + dfp.isVpn());
//            builder.append("isRoot:" + dfp.isRoot());
//            builder.append("isProxy:" + dfp.isProxy());
//            builder.append("isSuspicion:" + dfp.isSuspicion());
//            builder.append("isValid:" + dfp.isValid());
//            builder.append("错误码:" + dfp.getErrCode());
//            builder.append("错误信息:" + dfp.getErrMsg());
//
//            fingerTx.setText(builder.toString());
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        } catch (InternalException e) {
//            e.printStackTrace();
//        } catch (InvalidStateException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }

}