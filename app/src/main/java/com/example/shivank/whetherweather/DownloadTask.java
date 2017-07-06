/**
 * Created by Shivank on 05-07-2017.
 */
package com.example.shivank.whetherweather;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        String result="";
        URL url;
        HttpURLConnection urlConnection=null;
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection)url.openConnection();
            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());

            int data = reader.read();
            while (data!=-1){
                char current = (char)data;
                result+=current;
                data=reader.read();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject weatherData = jsonObject.getJSONObject("main");

            double tempKelvin = Double.parseDouble(weatherData.getString("temp"));
            double tempCelsius = tempKelvin-273.15;

            String placeName = jsonObject.getString("name");

            MainActivity.placeTextView.setText(String.valueOf(placeName));
            MainActivity.temperatureTextView.setText(String.valueOf(tempCelsius));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
