package com.tencent.shadow.sample.host;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.PluginManager;
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication;

public class MainActivity extends Activity {

    public static final int FROM_ID_START_ACTIVITY = 1001;
    public static final int FROM_ID_CALL_SERVICE = 1002;
    public static final int FROM_LOAD_PLUGIN = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(this);
        textView.setText("宿主App");
        linearLayout.addView(textView);

        {
            Button button = new Button(this);
            button.setText("启动插件Common");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    v.setEnabled(false);//防止点击重入

                    Bundle bundle = new Bundle();
                    bundle.putString("pluginZipPath", "/data/local/tmp/pluginCommon-debug.zip");
                    bundle.putString("KEY_PLUGIN_PART_KEY", "sample-common");

                    PluginManager pluginManager = InitApplication.getPluginManager();
                    pluginManager.enter(MainActivity.this, FROM_LOAD_PLUGIN, bundle, new EnterCallback() {
                        @Override
                        public void onShowLoadingView(View view) {
                            MainActivity.this.setContentView(view);//显示Manager传来的Loading页面
                        }

                        @Override
                        public void onCloseLoadingView() {
                            MainActivity.this.setContentView(linearLayout);
                            Toast.makeText(MainActivity.this, "commonPlugin初始化完成~", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onEnterComplete() {
                            v.setEnabled(true);
                        }
                    });
                }
            });

            linearLayout.addView(button);
        }
        
        {
            Button button = new Button(this);
            button.setText("启动插件1");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    v.setEnabled(false);//防止点击重入

                    Bundle bundle = new Bundle();
                    bundle.putString("pluginZipPath", "/data/local/tmp/plugin-debug.zip");
                    bundle.putString("KEY_PLUGIN_PART_KEY", "sample-plugin");
                    bundle.putString("KEY_ACTIVITY_CLASSNAME", "com.tencent.shadow.sample.plugin.MainActivity");

                    PluginManager pluginManager = InitApplication.getPluginManager();
                    pluginManager.enter(MainActivity.this, FROM_ID_START_ACTIVITY, bundle, new EnterCallback() {
                        @Override
                        public void onShowLoadingView(View view) {
                            MainActivity.this.setContentView(view);//显示Manager传来的Loading页面
                        }

                        @Override
                        public void onCloseLoadingView() {
                            MainActivity.this.setContentView(linearLayout);
                        }

                        @Override
                        public void onEnterComplete() {
                            v.setEnabled(true);
                        }
                    });
                }
            });

            linearLayout.addView(button);
        }

        {
            Button button = new Button(this);
            button.setText("启动插件2");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    v.setEnabled(false);//防止点击重入

                    PluginManager pluginManager = InitApplication.getPluginManager();

                    Bundle bundle = new Bundle();
                    bundle.putString("pluginZipPath", "/data/local/tmp/plugin2-debug.zip");
                    bundle.putString("KEY_PLUGIN_PART_KEY", "sample-plugin2");
                    bundle.putString("KEY_ACTIVITY_CLASSNAME", "com.tencent.shadow.sample.plugin2.MainActivity");
                    
                    pluginManager.enter(MainActivity.this, FROM_ID_START_ACTIVITY, bundle, new EnterCallback() {
                        @Override
                        public void onShowLoadingView(View view) {
                            MainActivity.this.setContentView(view);//显示Manager传来的Loading页面
                        }

                        @Override
                        public void onCloseLoadingView() {
                            MainActivity.this.setContentView(linearLayout);
                        }

                        @Override
                        public void onEnterComplete() {
                            v.setEnabled(true);
                        }
                    });
                }
            });

            linearLayout.addView(button);
        }

        Button callServiceButton = new Button(this);
        callServiceButton.setText("调用插件Service，结果打印到Log");
        callServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);//防止点击重入

                PluginManager pluginManager = InitApplication.getPluginManager();
                pluginManager.enter(MainActivity.this, FROM_ID_CALL_SERVICE, null, null);
            }
        });

        linearLayout.addView(callServiceButton);

        setContentView(linearLayout);
    }
}