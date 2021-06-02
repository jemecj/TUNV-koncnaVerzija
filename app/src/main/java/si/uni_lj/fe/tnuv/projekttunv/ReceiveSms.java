package si.uni_lj.fe.tnuv.projekttunv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ReceiveSms extends BroadcastReceiver{

    private static String name = new String();
    private static String previousName = new String();

    private static String number = new String();
    private static String type = new String();
    private static String location = new String();
    private static String phone = new String();


    private String msgBody;
    private String msg_from;



    private String shownText = "";

    private  static ArrayList listNames = new ArrayList<>();
    private  static ArrayList listLocations = new ArrayList<>();
    private  static ArrayList listNumbers = new ArrayList<>();
    private  static ArrayList listType = new ArrayList<>();
    private  static ArrayList listPhone = new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;


            if (bundle != null){
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    }
                    //Toast.makeText(context, "From: " + msg_from + "\nBody: " + msgBody, Toast.LENGTH_LONG).show();
                     name =  get("ime", msgBody);
                     location =  get("lokacija", msgBody);
                     number =  get("stevilka", msgBody);
                     type = get("alarm", msgBody);
                     phone = get("tel",msgBody);
                     shownText = shownText + "Oseba: "+name + "\nŠt. zavarovanja: " +number + "\nVrsta napada: " +type +"\nLokacija: " +location + "\nTel. številka: " + phone;


                    if(!previousName.equals(name)){
                        listNames.add(name);
                        listLocations.add(location);
                        listNumbers.add(number);
                        listType.add(type);
                        listPhone.add(phone);
                        previousName = name;

                    }


                    Activity2.getInstace().updateTheTextView(shownText);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static String get(String key, String stream){
        int start = stream.indexOf(key);
        String endKey = "/" + key;
        int end = stream.indexOf(endKey);
        return stream.substring(start + key.length() +1, end -1);
    }

    public static String getName(){
        return name;
    }

    public static String getLocation(){
        return location;
    }

    public static String getNumber(){
        return number;
    }

    public static String getType(){
        return type;
    }

    public static String getPhone(){
        return phone;
    }

    public static ArrayList getNames() {
        return listNames;
    }
    public static ArrayList getLocations() {
        return listLocations;
    }
    public static ArrayList getNumbers() {
        return listNumbers;
    }
    public static ArrayList getTypes() {
        return listType;
    }
    public static ArrayList getPhones() {
        return listPhone;
    }

}




