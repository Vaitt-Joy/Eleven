package com.eleven.virtualplugin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;



public class WebViewActivity extends AppCompatActivity {
    private String title, url;
    private AgentWeb mAgentWeb;
    private TextView mTitleTextView;
    private boolean titleCallback = true;
    private String type;// todo 支付字段预留
    private String data;// todo 支付字段预留


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //设置状态栏的颜色
        StateBarTranslucentUtils.setStateBarColor(this, R.color.default_actionbar);
        Intent i = getIntent();
        title = i.getStringExtra("title");
        url = i.getStringExtra("url");
        type = i.getStringExtra("type");
        titleCallback = i.getBooleanExtra("titleBack", true);
        data = getIntent().getStringExtra("pdata");// 支付类的使用
        if (TextUtils.isEmpty(url)) {
            CommonUtils.toast(this, "url地址为空！");
            finish();
        }
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        boolean titleBarShow = i.getBooleanExtra("titleBarShow", true);
        findViewById(R.id.titleBar).setVisibility(!titleBarShow ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(title))
            mTitleTextView.setText(title);
        findViewById(R.id.actionbar_back_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWebView();
    }

    private void initWebView() {
        // AgentWeb 保持了 WebView 的使用 ，
        if (!isEmpty(url)) {
            mAgentWeb = AgentWeb.with(this)//
                    .setAgentWebParent((ViewGroup) findViewById(R.id.container), new LinearLayout.LayoutParams(-1, -1))//
                    .useDefaultIndicator()//
                    .defaultProgressBarColor()
                    .setReceivedTitleCallback(mCallback)
                    .setWebChromeClient(mWebChromeClient)
                    .setWebViewClient(mWebViewClient)
                    .setSecutityType(AgentWeb.SecurityType.default_check)
                    .createAgentWeb()//
                    .ready()
                    .go(url);
            log(url, TAG);

            try {
                if (url.contains("haolianpay")) {
                    if (!isEmpty(data)) {
                        startActivity(url + "?" + data.replace("=>", "="));
                    } else {
                        startActivity(url);
                    }
                } else {
                    if (!isEmpty(data)) {
                        mAgentWeb.getLoader().postUrl(url, EncodingUtils.getBytes(data.replace("=>", "="), "UTF-8"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mTitleTextView != null && titleCallback)
                mTitleTextView.setText(title);
        }
    };


    //WebViewClient
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(final WebView view, String url, Bitmap favicon) {
            //do you  work
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final WebView.HitTestResult hitTestResult = ((WebView) view).getHitTestResult();
                    // 如果是图片类型或者是带有图片链接的类型
                    if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                        // 弹出保存图片的对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("提示");
                        builder.setMessage("保存图片到本地");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String picUrl = hitTestResult.getExtra();//获取图片链接
                                //保存图片到相册
                                url2bitmap(picUrl);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            // 自动dismiss
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;
                    }
                    return false;//保持长按可以复制文字
                }
            });
        }

        @Override
        public void onPageFinished(final WebView view, String url) {
            super.onPageFinished(view, url);
            //去掉支付webview的顶部栏
            mAgentWeb.getJsEntraceAccess().quickCallJs("hideGoback");
            //去掉活动详情的顶部栏
            mAgentWeb.getJsEntraceAccess().quickCallJs("hidePhoneHeader");
        }
    };

    private void startActivity(String url) throws Exception {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    //WebChromeClient
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
    };

    @Override
    protected void onPause() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
        //mAgentWeb.destroy();
    }

    public void url2bitmap(String url) {
        File appDir = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        if (!appDir.exists()) appDir.mkdir();
        String fileName = MD5.getMD5(url);
        if (TextUtils.isEmpty(fileName))
            fileName = "xxxxxxx";
        final File file = new File(appDir, fileName + ".jpg");
        if (file.exists()) {
            Toast.makeText(context, "图片已经保存！", Toast.LENGTH_SHORT).show();
            return;
        }
        log("************************" + url);
        final String finalFileName = fileName;
        ImageLoader.loadImageSimpleTarget(context, url, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap bitmap, GlideAnimation glideAnimation) {
                BitmapCache.saveImageToGallery(context, bitmap, finalFileName + ".jpg");
                if (bitmap == null) {
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                    try {
                        file.delete();
                    } catch (Exception e) {

                    }
                } else {
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
