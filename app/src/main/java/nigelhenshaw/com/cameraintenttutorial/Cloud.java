package nigelhenshaw.com.cameraintenttutorial;

import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guilherme on 28/09/16.
 */

public class Cloud {
    private final Cloudinary cloudinary;
    private String urlGoogle = "https://images.google.com/searchbyimage?image_url=";
    private String url;

    public Cloud(){
        Map config = new HashMap();
        config.put("cloud_name", "dgvlbqkpk");
        config.put("api_key", "953732752852372");
        config.put("api_secret", "9x-SwE4nGg_AespxL7aleXjJJjI");
        cloudinary = new Cloudinary(config);
    }

    public String upload(String file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        url = (String) uploadResult.get("url");
        return url;
    }

    public String getUrl(String option) throws UnsupportedEncodingException {
        if(option.equals("Google")){
            return urlGoogle+url;
        }
        else{ //Bing upload
            return "http://www.bing.com/images/search?q=imgurl:" +
                    URLEncoder.encode(url, "UTF-8") +
                    "&view=detailv2&iss=sbi&FORM=IRSBIQ#enterInsights";
        }
    }
}
