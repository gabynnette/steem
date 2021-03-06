package com.steemapp.lokisveil.steemapp.Interfaces;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.steemapp.lokisveil.steemapp.CentralConstants;
import com.steemapp.lokisveil.steemapp.OpenOtherGuyBlog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boot on 3/24/2018.
 */

public class WebAppInterface {
    Context mContext;
    ArticleActivityInterface articleActivityInterface;
    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }
    public WebAppInterface(ArticleActivityInterface c) {
        articleActivityInterface = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void UserClicked(String user){
        articleActivityInterface.UserClicked(user);
        /*Intent i = new Intent(mContext, OpenOtherGuyBlog.class);
        i.putExtra(CentralConstants.OtherGuyNamePasser,user);
        mContext.startActivity(i);*/
        //Toast.makeText(mContext, user, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void LinkClicked(String href){
        Pattern pat = Pattern.compile("(https?://)(.*)/(.*)/(.*)/(.*)");
        Matcher matcher = pat.matcher(href);
        String tag = null;
        String name = null;
        String link = null;
        while (matcher.find()){
            tag = href.substring(matcher.start(3),matcher.end(3));
            name = href.substring(matcher.start(4)+1,matcher.end(4));
            link = href.substring(matcher.start(5),matcher.end(5));
        }

        if(tag != null && name != null && link != null){
            articleActivityInterface.linkClicked(tag,name,link);
        }


        //articleActivityInterface.UserClicked(user);
        /*Intent i = new Intent(mContext, OpenOtherGuyBlog.class);
        i.putExtra(CentralConstants.OtherGuyNamePasser,user);
        mContext.startActivity(i);*/
        //Toast.makeText(mContext, user, Toast.LENGTH_SHORT).show();
    }

}
