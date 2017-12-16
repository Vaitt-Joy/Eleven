package com.eleven.virtualhost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 show();
             }
         });
    }

    private void show(){
        if (PluginManager.getInstance(this).getLoadedPlugin("com.eleven.virtualplugin.webview") == null) {
            Toast.makeText(this, "plugin  not loaded", Toast.LENGTH_SHORT).show();
            // 获取后台的插件
        } else {
            Intent intent = new Intent();
            intent.setClassName("cn.nzy.virualapkplugindemo", "com.eleven.virtualplugin.WebViewActivity");
            startActivity(intent);
        }
    }
}
