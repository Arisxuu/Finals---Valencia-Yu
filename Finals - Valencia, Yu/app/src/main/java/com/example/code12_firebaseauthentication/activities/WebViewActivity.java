package com.example.code12_firebaseauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView wvContent;
    private ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        wvContent = findViewById(R.id.wv_content);
        ivClose = findViewById(R.id.iv_close);

        //Removes action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //needs "https://" on link, otherwise di gagana. Also, this link wont work: https://kidzania.com/en/what-is-kidzania
        wvContent.loadUrl("https://manilaforkids.com/discover-manila/kidzania-manila/");
        wvContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            /*@Override
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(WebViewActivity.this, url + " has finished loading", Toast.LENGTH_SHORT).show();
                sf.setLatestUrlVisited(url);
            }*/
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(wvContent.canGoBack()){
            wvContent.goBack();
        }else {
            super.onBackPressed();
        }

    }
}