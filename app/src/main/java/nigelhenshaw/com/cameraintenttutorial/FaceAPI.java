package nigelhenshaw.com.cameraintenttutorial;


// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

            String body = "{\n" +
                    "    \"url\":\""+urlFace+"\"\n" +
                    "}";

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

             JSONObject json = new JSONArray(response).getJSONObject(0);
             faceId = json.getString("faceId");
             Log.d("AAAA",faceId);


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

            String body = "{    \n" +
                    "    \"personGroupId\":\"0\",\n" +
                    "    \"faceIds\":[\n" +
                    "        \""+faceId+"\"\n" +
                    "    ],\n" +
                    "    \"maxNumOfCandidatesReturned\":1,\n" +
                    "    \"confidenceThreshold\": 0.5\n" +
                    "}";

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
            candidate = (new JSONArray(response)).getJSONObject(0).getJSONArray("candidates").getJSONObject(0).getString("personId");

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
        if(!faceId.equals("")){
            String candidate = identifyId(faceId);
            if(!candidate.equals("")){
                String name = idToName(candidate);
                return name;
            }
        }
        return "";
    }
}
