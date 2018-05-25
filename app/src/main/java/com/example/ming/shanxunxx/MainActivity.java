package com.example.ming.shanxunxx;


import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button btn;
    private MessageObserver messageObserver;
    public static TextView authCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        messageObserver = new MessageObserver(this, new Handler());
        authCodeTextView = findViewById(R.id.auth_code);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("1065930051", "mm");
                authCodeTextView.setText("指令已发送");
                //注册短信监听
                getContentResolver().registerContentObserver(Uri.parse("content://sms"),
                        true, messageObserver);
            }
        });
    }



    //发送短信
    public void sendMessage(String phoneNumber, String message){
        android.telephony.SmsManager smsManager =
                android.telephony.SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
