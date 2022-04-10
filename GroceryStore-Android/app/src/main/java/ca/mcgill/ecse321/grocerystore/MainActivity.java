package ca.mcgill.ecse321.grocerystore;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.mcgill.ecse321.grocerystore.databinding.ActivityMainBinding;
import cz.msebera.android.httpclient.entity.mime.Header;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String error = null;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // initialize error message text view
        refreshErrorMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    public void login(View v) {
        error = "";
        final TextView emailTextView = (TextView) findViewById(R.id.Email);
        final TextView passwordTextView = (TextView) findViewById(R.id.Password);
        HttpUtils.post("/login/?email=" + emailTextView.getText().toString() + "&password=" + passwordTextView.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                emailTextView.setText("");
                passwordTextView.setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    public void signInCustomer(View v) {
        error = "";
        final TextView emailTextView = (TextView) findViewById(R.id.Email);
        final TextView passwordTextView = (TextView) findViewById(R.id.Password);
        HttpUtils.post("/login/?email=" + emailTextView.getText().toString() + "&password=" + passwordTextView.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                emailTextView.setText("");
                passwordTextView.setText("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }


    /**
     * @author Matthieu Hakim
     */
    public void getOrdersOfCustomer(View v) {
        error = "";

        final TextView dateTV = (TextView) findViewById(R.id.orderDateLabel);
        final TextView timeTV = (TextView) findViewById(R.id.orderTimeLabel);
        final TextView typeTV = (TextView) findViewById(R.id.orderTypeLabel);
        final TextView statusTV = (TextView) findViewById(R.id.orderStatusLabel);
        final TextView totalTV = (TextView) findViewById(R.id.orderTotalLabel);

        HttpUtils.get("view_all_orders_for_customer/?email=Romy@me", new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());

                    //TODO: Insert values at the table here
                    dateTV.setText(serverResp.getJSONObject("date").toString());
                    timeTV.setText(serverResp.getJSONObject("time").toString());
                    typeTV.setText(serverResp.getJSONObject("orderType").toString());
                    statusTV.setText(serverResp.getJSONObject("orderStatus").toString());
                    totalTV.setText(serverResp.getJSONObject("total").toString());

                } catch (JSONException e) {
                    error += e.getMessage();
                }

                refreshErrorMessage();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });



    }


    /**
     * @author Karl Rouhana
     */

    public void getShoppableItems(View view){

        error = "";

        HttpUtils.get("view_all_shoppable_item/", new RequestParams(), new JsonHttpResponseHandler(){


            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {


                    Spinner allItemsSpinner = findViewById(R.id.itemsAvailable);

                    String[] allItems = new String[response.length()];


                    for(int i = 0; i < response.length(); i++){

                        JSONObject item = response.getJSONObject(i);

                        String name = item.getJSONObject("name").toString();
                        String price = item.getJSONObject("price").toString();
                        String quantityAvailable = item.getJSONObject("quantityAvailable").toString();

                        String itemString = "";
                        itemString+=name+", $ "
                                +price+","
                                +quantityAvailable+" available";

                        allItems[i]=itemString;

                    }

                    ArrayList<String> list = new ArrayList<>(Arrays.asList(allItems));

                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    allItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    allItemsSpinner.setAdapter(allItemsAdapter);
                    allItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });



                } catch (JSONException e) {
                    error += e.getMessage();
                }

                refreshErrorMessage();

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }





        });














    }

    /**
     * @author Ralph Nassar
     */

    public void getUnavailableItems(View view){

        error = "";

        HttpUtils.get("view_all_unavailable_item/", new RequestParams(), new JsonHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {


                    Spinner allItemsSpinner = findViewById(R.id.itemsAvailable);

                    String[] allItems = new String[response.length()];


                    for (int i = 0; i < response.length(); i++) {

                        JSONObject item = response.getJSONObject(i);

                        String name = item.getJSONObject("name").toString();
                        String price = item.getJSONObject("price").toString();

                        String itemString = "";
                        itemString += name + ", $ "
                                + price + ",";

                        allItems[i] = itemString;

                    }

                    ArrayList<String> list = new ArrayList<>(Arrays.asList(allItems));

                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    allItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    allItemsSpinner.setAdapter(allItemsAdapter);
                    allItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } catch (JSONException e) {
                    error += e.getMessage();
                }

                refreshErrorMessage();

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }


        });


    }




}