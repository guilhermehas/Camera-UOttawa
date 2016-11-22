package nigelhenshaw.com.cameraintenttutorial;

import android.app.Application;
import android.os.SystemClock;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() throws IOException {
        super(Application.class);
        IntegrateTest();
    }

    void TestFaceId(){
        Log.d("AAAA", "1234");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String urlCloud = "http://res.cloudinary.com/dgvlbqkpk/image/upload/v1479524856/416.jpg";
                FaceAPI fapi = new FaceAPI();
                String faceId = fapi.getFaceId(urlCloud);
                Log.d("AAAA", faceId);
            }
        };
        new Thread(runnable).start();
        SystemClock.sleep(12000);
    }

    public void TestIdentifyId(){
        Log.d("AAAA", "1234");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String faceId = "f5739744-3251-4c20-a53c-b1fc4b16312f";
                FaceAPI fapi = new FaceAPI();
                String candidate = fapi.identifyId(faceId);
                Log.d("AAAA", candidate);
            }
        };
        new Thread(runnable).start();
        SystemClock.sleep(12000);
    }

    public void TestGetName(){
        Log.d("AAAA", "1234");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String faceId = "20cd8842-a7a9-40e6-8eba-2f31e9ccb387";
                FaceAPI fapi = new FaceAPI();
                String name = fapi.idToName(faceId);
                Log.d("AAAA", name);
            }
        };
        new Thread(runnable).start();
        SystemClock.sleep(12000);
    }

    public void IntegrateTest(){
        Log.d("AAAA", "1234");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String urlCloud = "http://res.cloudinary.com/dgvlbqkpk/image/upload/v1479524856/416.jpg";
                FaceAPI fapi = new FaceAPI();
                String nome = fapi.getName(urlCloud);

                Log.d("MyName",nome);
            }
        };

        new Thread(runnable).start();
        SystemClock.sleep(12000);
    }

}