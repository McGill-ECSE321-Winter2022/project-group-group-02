package ca.mcgill.ecse321.grocerystore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String error = null;
    private String customerEmail = null;
    private String userType = null;
    private String customerName = null;
    private String customerAddress = null;

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
        //refreshErrorMessage();
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
        if (id == R.id.action_itemView) {
           setContentView(R.layout.itemview);
            return true;
        }

        if (id == R.id.action_orderView) {
            setContentView(R.layout.order_view);
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

    private void createAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setTitle("Error!");
// Add the buttons
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void login(View v) {

        final TextView emailTextView = (TextView) findViewById(R.id.EmailLogin);
        final TextView passwordTextView = (TextView) findViewById(R.id.PasswordLogin);

        if (emailTextView.getText().toString().isEmpty() || passwordTextView.toString().isEmpty()) {
            createAlertDialog("Please fill all the fields!");
        }
        try {
            HttpUtils.post("/login/?email=" + URLEncoder.encode(emailTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(passwordTextView.getText().toString(), StandardCharsets.UTF_8.toString()) , new RequestParams(), new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    //refreshErrorMessage();
                    try {
                        customerEmail = response.getString("email");
                        userType = response.getString("userType");
                        customerName = response.getString("name");
                        customerAddress = response.getString("address");
                        emailTextView.setText("");
                        passwordTextView.setText("");
                    } catch (Exception e) {
                        System.out.println("Non");
                    }

                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                    createAlertDialog(errorMessage);
                }
            });
        } catch (Exception e) {
            System.out.println("Encoding Error");
        }
    }

    /**
     * @author anaelle.drai
     * @param v
     */
    public void signInCustomer(View v) {
        error = "";
        final TextView emailTextView = (TextView) findViewById(R.id.EmailSignIn);
        final TextView passwordTextView = (TextView) findViewById(R.id.PasswordSignIn);
        final TextView nameTextView = (TextView) findViewById(R.id.NameSignIn);
        final TextView addressTextView = (TextView) findViewById(R.id.AddressSignIn);
        final TextView confirmPasswordTextView = (TextView) findViewById(R.id.ConfirmPasswordSignIn);

        if (emailTextView.getText().toString().isEmpty() || passwordTextView.toString().isEmpty() || addressTextView.toString().isEmpty() || nameTextView.toString().isEmpty() || confirmPasswordTextView.toString().isEmpty()) {
            createAlertDialog("Please fill all the fields!");
        }

        if (!confirmPasswordTextView.getText().toString().equals(passwordTextView.getText().toString())) {
            createAlertDialog("The passwords don't match!");
        } else {
            try {
                HttpUtils.post("/create_customer/?email=" + URLEncoder.encode(emailTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(passwordTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&name=" + URLEncoder.encode(nameTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&address=" + URLEncoder.encode(addressTextView.getText().toString(), StandardCharsets.UTF_8.toString()), new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                        emailTextView.setText("");
                        passwordTextView.setText("");
                        confirmPasswordTextView.setText("");
                        nameTextView.setText("");
                        addressTextView.setText("");
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                        createAlertDialog(errorMessage);
                    }
                });
            } catch (Exception e) {
                System.out.println("Encoding Error");
            }
        }
    }

    public void updateCustomer(View v) {
        error = "";
        final TextView emailTextView = (TextView) findViewById(R.id.EmailUpdate);
        final TextView passwordTextView = (TextView) findViewById(R.id.PasswordUpdate);
        final TextView nameTextView = (TextView) findViewById(R.id.NameUpdate);
        final TextView addressTextView = (TextView) findViewById(R.id.AddressUpdate);
        final TextView confirmPasswordTextView = (TextView) findViewById(R.id.ConfirmPasswordUpdate);

        if (emailTextView.getText().toString().isEmpty() || passwordTextView.toString().isEmpty() || addressTextView.toString().isEmpty() || nameTextView.toString().isEmpty() || confirmPasswordTextView.toString().isEmpty()) {
            createAlertDialog("Please fill all the fields!");
        }

        if (!confirmPasswordTextView.getText().toString().equals(passwordTextView.getText().toString())) {
            createAlertDialog("The passwords don't match!");
        } else {
            try {
                HttpUtils.put("/update_customer/?email=" + URLEncoder.encode(emailTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(passwordTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&name=" + URLEncoder.encode(nameTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&address=" + URLEncoder.encode(addressTextView.getText().toString(), StandardCharsets.UTF_8.toString()), new RequestParams(), new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                        emailTextView.setText("");
                        passwordTextView.setText("");
                        confirmPasswordTextView.setText("");
                        nameTextView.setText("");
                        addressTextView.setText("");

                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                        createAlertDialog(errorMessage);
                    }
                });
            } catch (Exception e) {
                System.out.println("Encoding error");
            }
        }

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

        HttpUtils.get("view_all_orders_for_customer?email=Romy@mail", new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                try {

//                    for(int i = 0; i < response.length(); i++) {
//
//                        JSONObject serverResp = response.getJSONObject(i);
//                    }

                    JSONObject serverResp = response.getJSONObject(0);


                    //TODO: Insert values at the table here
                    dateTV.setText(serverResp.getJSONObject("date").toString());
                    timeTV.setText(serverResp.getJSONObject("time").toString());
                    typeTV.setText(serverResp.getJSONObject("orderType").toString());
                    statusTV.setText(serverResp.getJSONObject("orderStatus").toString());
                    totalTV.setText(serverResp.getJSONObject("subtotal").toString());


                } catch (JSONException e) {
                    error += e.getMessage();


                }

                //refreshErrorMessage();

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    System.out.println("Failure method");

                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
               // refreshErrorMessage();
            }
        });



    }


    /**
     * @author Karl Rouhana
     */

    public void getShoppableItems(View view){

        error = "";
        System.out.println("itemString");
        HttpUtils.get("view_all_shoppable_item/", new RequestParams(), new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                try {




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
                        //System.out.println(itemString);

                    }

                    Spinner allItemsSpinner = (Spinner) findViewById(R.id.itemsAvailable);

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


                    ArrayList<String> list = new ArrayList<>(Arrays.asList(allItems));

                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, allItems);
                    allItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    allItemsSpinner.setAdapter(allItemsAdapter);




                } catch (JSONException e) {
                    error += e.getMessage();
                }

                //refreshErrorMessage();

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                //refreshErrorMessage();
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

               // refreshErrorMessage();

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
               // refreshErrorMessage();
            }


        });


    }




}