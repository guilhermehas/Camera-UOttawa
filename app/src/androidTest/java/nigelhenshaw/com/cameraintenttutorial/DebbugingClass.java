package nigelhenshaw.com.cameraintenttutorial;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guilherme on 23/11/16.
 */

public class DebbugingClass extends ApplicationTestCase<Application> {
    public  DebbugingClass() throws JSONException {
        super(Application.class);

    }

    public void TestJson() throws JSONException {
        String urlFace = "maria";
        String result = "{\n" +
                "    \"url\":\""+urlFace+"\"\n" +
                "}";

        JSONObject json = new JSONObject();
        json.put("url",urlFace);
        String body = json.toString();
        Log.d("AAAA",body);
        assertEquals(body,result);
    }

    public void TestJson2() throws JSONException {
        String faceId = "joao";
        String content = "{    \n" +
                "    \"personGroupId\":\"0\",\n" +
                "    \"faceIds\":[\n" +
                "        \""+faceId+"\"\n" +
                "    ],\n" +
                "    \"maxNumOfCandidatesReturned\":1,\n" +
                "    \"confidenceThreshold\": 0.5\n" +
                "}";

        JSONObject json = new JSONObject();
        json.put("personGroupId","0");
        JSONArray jsonA = new JSONArray();
        jsonA.put(faceId);
        json.put("faceIds",jsonA);
        json.put("maxNumOfCandidatesReturned",1);
        json.put("confidenceThreshold",0.5);
        String body = json.toString();

        Log.d("myjson",body);
    }

}
