package com.example.test;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.os.Handler;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button getButton;
    private TextView txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getButton = findViewById(R.id.button);
        getButton.setOnClickListener(new MyClick());
        txv = findViewById(R.id.textView);
    }
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            getButton.setText("got data");
            getData();
            System.out.println("*********************************");
            //String a = "test";
            //System.out.println(a);

            //txv.setText(data);
        }
    }

    private void getData() {
        String data = "";
        String getUrl = "http://192.168.1.45:8000/get";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUrl)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.getMessage().toString();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // final String data = response.body().string();
                Message msg = handler.obtainMessage();
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String m = (String) msg.obj;
            try {
                txv.setText(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    });
}
