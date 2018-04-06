package com.sdu.andrumbrella.utilities;


import com.sdu.andrumbrella.UpcomingDays;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michael on 25-Mar-18.
 */

public class GeneralUtils {


    public static ArrayList<String> getWeatherData(String day, Boolean metric){
        ArrayList<String> clickedDayData = new ArrayList<>();
        if(Integer.valueOf(day) < 10){
            day = "0" + day;
        }
        for(int i = 0; i < UpcomingDays.weatherData.length; i++){
            if(UpcomingDays.weatherData[i].split("\\s")[0].split("-")[2].equals(day)){
                if(metric) {
                    clickedDayData.add("From: " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3) + ":00"
                            + "        To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                            + "Current Temperature: " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°C\n"
                            + "Max Temperature: " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°C\n"
                            + "Minimum Temperature: " + kToC(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[4])) + "°C");
                }else{
                    clickedDayData.add("From: " + String.valueOf(Integer.valueOf(UpcomingDays.weatherData[i].split("\\s")[1].split(":")[0]) - 3) + ":00"
                            + "         To: " + UpcomingDays.weatherData[i].split("\\s")[1].substring(0, 5) + "\n\n\n"
                            + "Current Temperature: " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[2])) + "°F\n"
                            + "Max Temperature: " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[3])) + "°F\n"
                            + "Minimum Temperature: " + kToF(Float.parseFloat(UpcomingDays.weatherData[i].split("\\s")[4])) + "°F");
                }
            }
        }
        return clickedDayData;
    }


    //Get Name of a month
    public static String getMonthByName(String date){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = inFormat.parse(date);
            SimpleDateFormat outFormat = new SimpleDateFormat("MM");
            return getMonth(outFormat.format(newDate));
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    //Get Name of a day
    public static String getDayByName(String date){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = inFormat.parse(date);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            return outFormat.format(newDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    //Parse date
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    //Convert Kelvin to Celsius
    private static float kToC(float kelvinDegrees){
        float celsius = kelvinDegrees - 273.15F;
        return Float.parseFloat(String.format ("%.2f", celsius));
    }

    //Convert Kelvin to Fahrenheit
    private static float kToF(float kelvinDegrees){
        float fahrenheit = (float) 1.8 * (kelvinDegrees - 273) + 32;
        return Float.parseFloat(String.format ("%.2f", fahrenheit));
    }

    //Get number of a day
    public static int getDayByNumber(String date){
        Date newDate = parseDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    //Get month name
    private static String getMonth(String month){
        String monthString;
        switch (month) {
            case "01":
                monthString = "January";
                break;
            case "02":
                monthString = "February";
                break;
            case "03":
                monthString = "March";
                break;
            case "04":
                monthString = "April";
                break;
            case "05":
                monthString = "May";
                break;
            case "06":
                monthString = "June";
                break;
            case "07":
                monthString = "July";
                break;
            case "08":
                monthString = "August";
                break;
            case "09":
                monthString = "September";
                break;
            case "10":
                monthString = "October";
                break;
            case "11":
                monthString = "November";
                break;
            case "12":
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
        }
}

