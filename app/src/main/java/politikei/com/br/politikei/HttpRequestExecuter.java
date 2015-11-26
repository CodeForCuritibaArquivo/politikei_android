package politikei.com.br.politikei;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public abstract class HttpRequestExecuter extends AsyncTask<String, Void, String> {


    private JSONObject json;
//
//    {
//        "email" : "jean.hhansen@gmail.com   ",
//            "password" : "12345"
//    }
    //https://politikei-api.herokuapp.com/api/v1/auth
    //https://politikei-api.herokuapp.com/api/v1/proposicoes?token=

    public HttpRequestExecuter(JSONObject jsonObject) {
        this.json = jsonObject;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            String result = executeHttpRequest(urls[0], urls[1], json);
            return result;
        } catch (Exception e) {
            return "Unable to retrieve request. URL may be invalid.";
        }
    }

    protected abstract void onPostExecute(String result);

    private String executeHttpRequest(String myurl, String metodo, JSONObject json) throws IOException {
        InputStream is = null;
        String contentAsString = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setDoInput(true);
            conn.setRequestMethod(metodo.toUpperCase());

            if (metodo.equalsIgnoreCase("post")) {

                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                DataOutputStream printout;
                printout = new DataOutputStream(conn.getOutputStream());
                String strJson = null;

                if (json != null) {
                    if (json.has("param")) {

                        try {
                            JSONArray jsonArray = json.getJSONArray("param");
                            strJson = jsonArray.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        strJson = json.toString();
                    }
//                    printout.write(URLEncoder.encode(strJson, "UTF-8").getBytes());
                    printout.write(strJson.getBytes());
                }
                printout.flush();
                printout.close();
            }
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            // Convert the InputStream into a string
            contentAsString = readIt(is);
            contentAsString = contentAsString.trim();
            return contentAsString;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return contentAsString;
    }

    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        char[] buffer = new char[10000];
        int nbCharRead = 0;

        StringBuilder result = new StringBuilder();
        String current;
        while ((current = reader.readLine()) != null) {
            Log.i(Utils.tag, current + "");
            result.append(current);
        }

        return result.toString();
    }
}
