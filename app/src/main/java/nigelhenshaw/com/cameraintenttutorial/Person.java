package nigelhenshaw.com.cameraintenttutorial;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by guilherme on 18/11/16.
 */

public class Person {
    String name = "";
    private String urlGoogle = "http://www.google.com/search?q=";
    private String urlBing;
    public String getName(String f) throws IOException {
        String url;
        try {
            url = new Cloud().upload(f);
            Log.d("CloudURL",url);
        }catch (Error e){
            return "@3";
        }
        try{
            name = new FaceAPI().getName(url);
            return name;
        }catch (Error e){
            return "@4";
        }
    }

    public String getUrlApi(String option) throws UnsupportedEncodingException {
        if(option.equals("Google")){
            return urlGoogle+ URLEncoder.encode(name, "UTF-8");
        }
        else{ //Bing upload
            return urlBing + URLEncoder.encode(name, "UTF-8");
        }
    }
}
