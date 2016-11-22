package nigelhenshaw.com.cameraintenttutorial;


// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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


class HttpGetWithEntity extends HttpPost {

    public final static String METHOD_NAME = "GET";

    public HttpGetWithEntity(URI url) {
        super(url);
    }

    public HttpGetWithEntity(String url) {
        super(url);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}



public class FaceAPI
{
    public String url = "";
    public String faceId = "";
    public String candidate = "";
    public String name = "";

    public void getFaceId()
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/face/v1.0/detect");

            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks", "false");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "575e4d4bae464455be8f3b8c9df0d74a");

            //System.out.println(url);

            String body = "{\n" +
                    "    \"url\":\""+url+"\"\n" +
                    "}";

            //System.out.println(body);
            // Request body
            StringEntity reqEntity = new StringEntity(body);
            //System.out.println(reqEntity);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                //System.out.println(EntityUtils.toString(entity));
                JSONObject json = new JSONArray(EntityUtils.toString(entity)).getJSONObject(0);
                faceId = json.getString("faceId");
                System.out.println(faceId);
            }


        }
        catch (URISyntaxException | IOException | ParseException e)
        {
            System.out.println(e.getMessage());
        }

    }


    public void identifyId()
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/face/v1.0/identify");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "575e4d4bae464455be8f3b8c9df0d74a");


            String body = "{    \n" +
                    "    \"personGroupId\":\"0\",\n" +
                    "    \"faceIds\":[\n" +
                    "        \""+faceId+"\",\n" +
                    "    ],\n" +
                    "    \"maxNumOfCandidatesReturned\":1,\n" +
                    "    \"confidenceThreshold\": 0.5\n" +
                    "}";

            // Request body
            StringEntity reqEntity = new StringEntity(body);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                String str = EntityUtils.toString(entity);
                System.out.println(str);
                candidate = (new JSONArray(str)).getJSONObject(0).getJSONArray("candidates").getJSONObject(0).getString("personId");
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    public void idToName()
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/face/v1.0/persongroups/0/persons/"+candidate);


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", "575e4d4bae464455be8f3b8c9df0d74a");


            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                String str = EntityUtils.toString(entity);
                System.out.println(str);
                name = new JSONObject(str).getString("name");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String getName(String url){
        this.url = url;
        getFaceId();
        System.out.println(faceId);
        if(!faceId.equals("")){
            identifyId();
            if(!candidate.equals("")){
                idToName();
                return name;
            }
        }
        return "";
    }
}
