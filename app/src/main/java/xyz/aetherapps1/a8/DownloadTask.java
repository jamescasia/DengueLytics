package xyz.aetherapps1.a8;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class DownloadTask extends AsyncTask<String, Void, String>{
    public static  double PRESSURE;
    public static double TEMP;
    public static double WIND;
    public static double HUM;
    public double temperature;
    public String sky;
    public double pressure;
    public double humidity;
    public double windspeed;
    public float zero;

    public String skyDesc;

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while(data!=-1)
            {
                char current = (char) data;
                result+=current;
                data=reader.read();
            }
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;


    }
    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        try
        {


              JSONObject jsonObject = new JSONObject(s);
              JSONObject jsonObject1 = new JSONObject(s);


              JSONObject main = new JSONObject(jsonObject.getString("main"));
              JSONObject wind = new JSONObject(jsonObject1.getString("wind"));

              //this.sky = weather.getString("main").toString().trim();
                setPressure(Double.parseDouble(main.getString("pressure")) );
                setTemperature(Double.parseDouble(main.getString("temp")));
                setWindspeed(Double.parseDouble(wind.getString("speed")));
                setHumidity(Double.parseDouble(main.getString("humidity")));
                HUM = Double.parseDouble(main.getString("humidity"));
                PRESSURE = Double.parseDouble(main.getString("pressure"));
                WIND =  Double.parseDouble(wind.getString("speed")) ;
                TEMP =  Double.parseDouble(main.getString("temp"));
              this.pressure = Double.parseDouble(main.getString("pressure"));
              this.humidity =  Double.parseDouble(main.getString("humidity"));
              this.windspeed = Double.parseDouble(wind.getString("speed")) ;
              this.temperature =  Double.parseDouble(main.getString("temp"));

        }
        catch (Exception e)
        {
            System.out.print("FAILED");

        }

    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getPressure(){ return this.pressure;}
    public double getHumidity(){ return this.humidity;}
    public double getWindspeed(){ return  this.windspeed;}
    public String getSky(){return this.sky;}
    public double getTemperature(){return this.temperature;}
    public String getSkyDesc(){return  this.skyDesc;}


}
