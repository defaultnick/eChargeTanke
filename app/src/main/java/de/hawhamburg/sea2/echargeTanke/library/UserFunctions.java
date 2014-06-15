package de.hawhamburg.sea2.echargeTanke.library;

/**
 * Created by Simon on 04.03.14.
 */

import android.content.Context;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserFunctions {
    private JSONParser jsonParser;
    //URL of the PHP API

    private static String login_tag = "login";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
    /**
     * Function to Login
     **/
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(Consts.apiURL, params);
        return json;
    }
    /**
     * Function to change password
     **/
    public JSONObject chgPass(String newpas, String email){
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", chgpass_tag));
        params.add(new BasicNameValuePair("newpas", newpas));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(Consts.apiURL, params);
        return json;
    }
    /**
     * Function to reset the password
     **/
    public JSONObject forPass(String forgotpassword){
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl(Consts.apiURL, params);
        return json;
    }

    public JSONObject addBudget(Double kws, Double price) {
        DatabaseHandler db = new DatabaseHandler(Consts.context);
        HashMap hm = db.getUserDetails();
        String email = hm.get("email").toString();

        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", "addBill"));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("kws", kws.toString()));
        params.add(new BasicNameValuePair("price", price.toString()));
        JSONObject json = jsonParser.getJSONFromUrl(Consts.apiURL, params);
        return json;
    }

    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
}