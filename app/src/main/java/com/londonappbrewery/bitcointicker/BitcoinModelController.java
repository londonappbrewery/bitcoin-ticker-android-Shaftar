package com.londonappbrewery.bitcointicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BitcoinModelController {


    private int bitcoinPrice;

    public static BitcoinModelController fromJson(JSONObject jsonObject){

        BitcoinModelController bitcoinModel = new BitcoinModelController();

        try {
            bitcoinModel.bitcoinPrice = jsonObject.getJSONObject("data").
                    getJSONObject("1").getJSONObject("quotes").
                    getJSONObject(MainActivity.itemSelected).
                    getInt("price");
            return bitcoinModel;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }


    public int getBitcoinPrice() {
        return bitcoinPrice;
    }
}
