package ru.nsu.fit.lesson2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public final class SecondActivity extends AppCompatActivity {

    private static final String LOG_TAG = SecondActivity.class.getSimpleName();
    private String currentUrl = null;
    private ProgressBar loadPageProgress = null;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Intent intent = getIntent();
        currentUrl = new Uri.Builder()
                .scheme("https")
                .authority(intent.getStringExtra(MainActivity.EXTRA_MESSAGE))
                .build()
                .toString();

        final WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e(LOG_TAG, error.toString());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                final EditText currentUrl = SecondActivity.this.findViewById(R.id.current_url);
                currentUrl.setText(url);
                addProgress();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dropProgress();
            }
        });

        webView.loadUrl(getCurrentUrl());
    }

    public final void onAnotherSearch(View view) {
        Log.i(LOG_TAG, "Find another clicked!");
        final EditText url = findViewById(R.id.current_url);
        currentUrl = url.getText().toString();

        final WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(getCurrentUrl());

        dismissKeyboard(SecondActivity.this);
    }

    private String getCurrentUrl() {
        return currentUrl;
    }

    private void addProgress() {
        final LinearLayout searchLayout = findViewById(R.id.search_layout);
        loadPageProgress = new ProgressBar(SecondActivity.this, null, android.R.attr.progressBarStyleSmall);
        loadPageProgress.setLayoutParams(findViewById(R.id.find_another_button).getLayoutParams());
        searchLayout.addView(loadPageProgress);
    }

    private void dropProgress() {
        final LinearLayout searchLayout = findViewById(R.id.search_layout);
        if (loadPageProgress != null) {
            searchLayout.removeView(loadPageProgress);
        }
    }

    public void dismissKeyboard(Activity activity) {
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus()) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
        }
    }
}