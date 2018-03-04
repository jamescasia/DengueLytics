package xyz.aetherapps1.a8;
public class Report
{
    double latitude;
    double longitude;
    String uid;
    int cases;
    String place;
    long time;
    boolean confirmed;
    double pressure;
    double humidity;
    double temperature;
    double windspeed;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }




    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Report(String username ,String uid ,double latitude, double longitude,  int cases,int time, boolean confirmed, String place, double temperature , double windspeed, double pressure,double humidity)
    {
        this.latitude = latitude;
        this.confirmed = confirmed;
        this.longitude = longitude;
        this.uid = uid;
        this.cases = cases;
        this.place = place;
        this.temperature = temperature;
        setTemperature(temperature);
        setHumidity(humidity);
        setPressure(pressure);
        this.humidity = humidity;
        this.pressure = pressure;
        this.windspeed = windspeed;
        this.time = time;
        this.username = username;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }
}