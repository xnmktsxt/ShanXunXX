package com.example.ming.shanxunxx;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageObserver extends ContentObserver {

    private Context context;
    private String authCode = "";

    public MessageObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {

        //按降序排序短信数据库
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"),
                new String[] {"_id", "address", "body", "read" }, "address=? and read=?",
                new String[] { "1065930051", "0" }, "date desc");

        if(cursor != null){
            if(cursor.moveToFirst()){
                //获取短信内容
                String messageBody = cursor.getString(cursor.getColumnIndex("body"));
                MainActivity.authCodeTextView.setText(messageBody);
                System.out.println(messageBody);
            }
        }
    }

    //正则匹配6位随机密码
    public String getAuthCode(String messageBody){

        Pattern authCodePattern = Pattern.compile("(\\d{6})");
        Matcher matcher = authCodePattern.matcher(messageBody);
        if(matcher.find()){
            authCode = matcher.group();
        }
        return authCode;
    }
}
