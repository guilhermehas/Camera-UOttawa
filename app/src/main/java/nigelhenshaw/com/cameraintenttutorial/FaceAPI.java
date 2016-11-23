package nigelhenshaw.com.cameraintenttutorial;


// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

public class FaceAPI
{
    //method POST
    public String getFaceId(String urlFace)
    {
        String faceId = "";

        try
        {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https");
            builder.authority("api.projectoxford.ai")
                    .appendPath("face")
                    .appendPath("v1.0")
                    .appendPath("detect");
            builder.appendQueryParameter("returnFaceId", "true");
            builder.appendQueryParameter("returnFaceLandmarks", "false");

            URL url = new URL(builder.build().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "575e4d4bae464455be8f3b8c9df0d74a");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("url",urlFace);
            String body = jsonBody.toString();

            OutputStream output = connection.getOutputStream();
            DataOutputStream os = new DataOutputStream(output);
            os.writeBytes(body);
            os.flush();
            os.close();

            connection.connect();

            String response = "";

            InputStream iStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream, "utf8"));
            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            response = sb.toString();
            Log.d("faceIds",response);

            JSONArray jsonArray = new JSONArray(response);
            if(jsonArray.length() > 0) {
                JSONObject json = jsonArray.getJSONObject(0);
                faceId = json.getString("faceId");
                return faceId;
            }else{
                Log.d("passouAqui","1");
                return "@1";
            }


        }
        catch (IOException e)
        {
            Log.e("erroDebug", e.getMessage());
        }
        return faceId;
    }


    public String identifyId(String faceId)
    {
        String candidate = "";

        try
        {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https");
            builder.authority("api.projectoxford.ai")
                    .appendPath("face")
                    .appendPath("v1.0")
                    .appendPath("identify");


            URL url = new URL(builder.build().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "575e4d4bae464455be8f3b8c9df0d74a");


            JSONObject json = new JSONObject();
            json.put("personGroupId","0");
            JSONArray jsonA = new JSONArray();
            jsonA.put(faceId);
            json.put("faceIds",jsonA);
            json.put("maxNumOfCandidatesReturned",1);
            json.put("confidenceThreshold",0.5);
            String body = json.toString();


            Log.d("body",body);


            OutputStream output = connection.getOutputStream();
            DataOutputStream os = new DataOutputStream(output);
            os.writeBytes(body);
            os.flush();
            os.close();
            connection.connect();

            String response = "";


            InputStream iStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream, "utf8"));
            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            response = sb.toString();


            Log.d("HHHH",response);
            JSONArray jsonArray = (new JSONArray(response)).getJSONObject(0).getJSONArray("candidates");
            if(jsonArray.length()>0) {
                candidate = jsonArray.getJSONObject(0).getString("personId");
                return candidate;
            }else{
                return "@2";
            }
        }

        catch (Exception e) {
            Log.e("erroaqui", e.getMessage());
        }
        return candidate;
    }


    public String idToName(String candidate)
    {
        String name = "";

        try
        {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https");
            builder.authority("api.projectoxford.ai")
                    .appendPath("face")
                    .appendPath("v1.0")
                    .appendPath("persongroups")
                    .appendPath("0")
                    .appendPath("persons")
                    .appendPath(candidate);

            Log.d("urlAqui", builder.build().toString());
            URL url = new URL(builder.build().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "575e4d4bae464455be8f3b8c9df0d74a");

            String response = "";

            InputStream iStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream, "utf8"));
            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            response = sb.toString();

            Log.d("response",response);
            name = new JSONObject(response).getString("name");

        }
        catch (Exception e)
        {
            Log.e("erroaqui", e.getMessage());
        }

        return name;
    }

    public String getName(String url){
        String faceId = getFaceId(url);
        if(faceId.equals("@1")){
            return faceId;
        }
        else if(!faceId.equals("")){
            String candidate = identifyId(faceId);
            if(candidate.equals("@2")){
                return candidate;
            }
            else if(!candidate.equals("")){
                String name = idToName(candidate);
                return name;
            }
        }
        return "";
    }
}
