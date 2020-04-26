package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://api.alternative.me/v2/ticker/";

    // Member Variables:
    TextView mPriceTextView;
    public static String itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bitcoin", "" + parent.getItemAtPosition(position));
                itemSelected = String.valueOf(parent.getItemAtPosition(position));
                letsDoSomeNetworking(setParameterForBitcoinRequest((String) parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected");
            }
        });
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(RequestParams params) {

        HttpsUtils.get(HttpsUtils.BITCOIN_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                BitcoinModelController bitcoinModelController = BitcoinModelController.fromJson(response);
                Log.d("Bitcoin: onSuccess", "---------------- has response : ");
                mPriceTextView.setText(String.valueOf(bitcoinModelController.getBitcoinPrice()));
                System.out.println(".....................List.........................");
                System.out.println("getPrice: " + bitcoinModelController.getBitcoinPrice());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin", "onFailure! Status code: " + statusCode);
                Log.d("Bitcoin", "onFailure: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private RequestParams setParameterForBitcoinRequest(String name){

        RequestParams params = new RequestParams();
        params.put("convert", name);
       return params;
    }


}
