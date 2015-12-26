package com.feardread.android.jxparser;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JXParser {

    final String TAG = "JXParser.java";

    static String json = "";
    static InputStream input = null;
    static JSONObject jsonObj = null;

    public JSONObject getJsonFromUrl (String url) {

        // make HTTP request
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            input = httpEntity.getContent();           

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

        // parse response
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "iso-8859-1"), 8);
            StringBuilder sbuilder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {

                sbuilder.append(line + "\n");
            }

            input.close();
            json = sbuilder.toString();

        } catch (Exception e) {

            Log.e(TAG, "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {

            jsonObj = new JSONObject(json);

        } catch (JSONException e) {

            Log.e(TAG, "Error parsing data " + e.toString());
        }

        // return JSON String
        return jsonObj;
    }
}
