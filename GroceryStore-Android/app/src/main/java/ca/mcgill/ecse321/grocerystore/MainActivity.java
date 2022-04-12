package ca.mcgill.ecse321.grocerystore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.mcgill.ecse321.grocerystore.databinding.ActivityMainBinding;
import cz.msebera.android.httpclient.Header;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String error = null;
    private String customerEmail = null;
    private String userType = null;
    private String customerName = null;
    private String customerAddress = null;
    private String customerPassword = null;

    private static Map<String,Integer> cart = new HashMap<String,Integer>();


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public String getCustomerEmail(){
        if(customerEmail == null){
            return "";
        }else{
            return customerEmail;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        binding.fab.setVisibility(View.GONE);

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



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }
    public void setCustomerEmail(String email) {
        this.customerEmail = email;
    }
    public void setCustomerAddress(String address) {
        this.customerAddress = address;
    }
    public void setUserType(String type) {
        this.userType = type;
    }

    void createErrorAlertDialog(String message) {
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

    private void createSuccessAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setTitle("Success!");
// Add the buttons
        builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void login(View v) {

        final TextView emailTextView = (TextView) findViewById(R.id.EmailLogin);
        final TextView passwordTextView = (TextView) findViewById(R.id.PasswordLogin);

        if (emailTextView.getText().toString().isEmpty() || passwordTextView.getText().toString().isEmpty()) {
            createErrorAlertDialog("Please fill all the fields!");
        } else {
            try {
                HttpUtils.post("/login/?email=" + URLEncoder.encode(emailTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(passwordTextView.getText().toString(), StandardCharsets.UTF_8.toString()), new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        try {
                            customerEmail = response.getString("email");
                            userType = response.getString("userType");
                            customerName = response.getString("name");
                            customerAddress = response.getString("address");
                            customerPassword = response.getString("password");
                            emailTextView.setText("");
                            passwordTextView.setText("");
                            List<Fragment> fragments = getSupportFragmentManager().getFragments();
                            NavHostFragment.findNavController(fragments.get(fragments.size() - 1))
                                    .navigate(R.id.action_Login_to_Menu);
                        } catch (Exception e) {
                            System.out.println("Non");
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                        createErrorAlertDialog(errorMessage);
                    }
                });
            } catch (Exception e) {
                System.out.println("Encoding Error");
            }
        }
    }

    public void deleteCustomer(View v) {

        final TextView passwordTextView = (TextView) findViewById(R.id.PasswordDelete);
        final TextView confirmPasswordTextView = (TextView) findViewById(R.id.ConfirmPasswordDelete);

        if (confirmPasswordTextView.getText().toString().isEmpty() || passwordTextView.getText().toString().isEmpty()) {
            createErrorAlertDialog("Please fill all the fields!");
        } else if (!confirmPasswordTextView.getText().toString().equals(passwordTextView.getText().toString())) {
            createErrorAlertDialog("Passwords don't match!");
        } else if (!customerPassword.equals(confirmPasswordTextView.getText().toString())) {
            createErrorAlertDialog("Incorrect password");
        } else {
            try {
                HttpUtils.delete("/delete_customer/?email=" + customerEmail, new RequestParams(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response) {
                        try {
                            createSuccessAlertDialog("Account successfully deleted!");
                            List<Fragment> fragments = getSupportFragmentManager().getFragments();
                            NavHostFragment.findNavController(fragments.get(fragments.size() - 1))
                                    .navigate(R.id.action_Update_to_Login);
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        createErrorAlertDialog(responseBody.toString());
                    }
                });
            } catch (Exception e) {
                System.out.println("Encoding Error");
            }
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

        if (emailTextView.getText().toString().isEmpty() || passwordTextView.getText().toString().isEmpty() || addressTextView.getText().toString().isEmpty() || nameTextView.getText().toString().isEmpty() || confirmPasswordTextView.getText().toString().isEmpty()) {
            createErrorAlertDialog("Please fill all the fields!");
        } else if (!confirmPasswordTextView.getText().toString().equals(passwordTextView.getText().toString())) {
            createErrorAlertDialog("The passwords don't match!");
        } else {
            try {
                HttpUtils.post("/create_customer/?email=" + URLEncoder.encode(emailTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(passwordTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&name=" + URLEncoder.encode(nameTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&address=" + URLEncoder.encode(addressTextView.getText().toString(), StandardCharsets.UTF_8.toString()), new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        emailTextView.setText("");
                        passwordTextView.setText("");
                        confirmPasswordTextView.setText("");
                        nameTextView.setText("");
                        addressTextView.setText("");
                        createSuccessAlertDialog("Your account was created successfully, please login now!");
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                        createErrorAlertDialog(errorMessage);
                    }
                });
            } catch (Exception e) {
                System.out.println("Encoding Error");
            }
        }
    }

    public void updateCustomer(View v) {
        error = "";

        final TextView passwordTextView = (TextView) findViewById(R.id.PasswordUpdate);
        final TextView nameTextView = (TextView) findViewById(R.id.NameUpdate);
        final TextView addressTextView = (TextView) findViewById(R.id.AddressUpdate);
        final TextView confirmPasswordTextView = (TextView) findViewById(R.id.ConfirmPasswordUpdate);

        if (passwordTextView.getText().toString().isEmpty() || addressTextView.getText().toString().isEmpty() || nameTextView.getText().toString().isEmpty() || confirmPasswordTextView.getText().toString().isEmpty()) {
            createErrorAlertDialog("Please fill all the fields!");
        } else if (!confirmPasswordTextView.getText().toString().equals(passwordTextView.getText().toString())) {
            createErrorAlertDialog("The passwords don't match!");
        } else {
            try {
                HttpUtils.put("/update_customer/?email=" + URLEncoder.encode(customerEmail, StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(passwordTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&name=" + URLEncoder.encode(nameTextView.getText().toString(), StandardCharsets.UTF_8.toString()) + "&address=" + URLEncoder.encode(addressTextView.getText().toString(), StandardCharsets.UTF_8.toString()), new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        passwordTextView.setText("");
                        confirmPasswordTextView.setText("");
                        nameTextView.setText("");
                        addressTextView.setText("");
                        createSuccessAlertDialog("Your account was successfully updated!");
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                        createErrorAlertDialog(errorMessage);
                    }
                });
            } catch (Exception e) {
                System.out.println("Encoding error");
            }
        }

    }


    /**
     * @author Karl Rouhana
     * Get all the shoppable items from the system
     */

    public void getShoppableItems(View view){

        error = "";
        HttpUtils.get("view_all_shoppable_item/", new RequestParams(), new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                try {

                    String[] allItems = new String[response.length()];

                    //Loop through all shoppable items in system
                    for(int i = 0; i < response.length(); i++){

                        JSONObject item = response.getJSONObject(i);

                        //Get the correct attributes
                        String name = item.getString("name");
                        String price = item.getString("price");
                        String quantityAvailable = item.getString("quantityAvailable");

                        String itemString = "";

                        //Add the fields
                        itemString+=name+", $ " +price+"," +quantityAvailable+" in stock";

                        allItems[i]=itemString;

                    }

                    //Get the correct dropdown
                    Spinner allItemsSpinner = (Spinner) findViewById(R.id.itemsAvailable);

                    //Add a listener to get the item chosen
                    allItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        //Show that the item has been selected
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

                    //Output the list on the dropdown menu
                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    allItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    allItemsSpinner.setAdapter(allItemsAdapter);

                } catch (Exception e) {
                    error += e.getMessage();
                    System.out.println(error);
                    createErrorAlertDialog(error);
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                 createErrorAlertDialog(errorMessage);
            }
        });
    }


    /**
     * @author Karl Rouhana
     * Add the items the customer wants to a temporary hash map to keep
     * track of which items are wanted and how many of them
     */

    public void addToCart(View view){

        //Get the proper fields and item
        final TextView quantityWantedView = (TextView) findViewById(R.id.selectedQuantity);
        final Spinner itemChosen =(Spinner) findViewById(R.id.itemsAvailable);
        String itemString = itemChosen.getSelectedItem().toString();

        //Since we know the name is right before the first "," I can get it this way
        String[] array = itemString.split(",");
        itemString = array[0];

        //Since we also know that the quantity is after the second "," I can get it this was

        int availableInSystem = 0;
        try{
            String[] availableInSystemString = array[2].split(" ");
             availableInSystem = Integer.parseInt(availableInSystemString[0]);
        }catch(Exception e){
            createErrorAlertDialog("Enter a valid quantity !");
            return;
        }


        //Check if integer
        if(quantityWantedView.getText().toString().matches("[0-9]+")) {
            cart.put(itemString, Integer.parseInt(quantityWantedView.getText().toString()));
        } else{
            createErrorAlertDialog("Enter a valid quantity !");
            return;
        }
        int quantityWanted = Integer.parseInt(quantityWantedView.getText().toString());

        //Check if there's enough items in system
        if( quantityWanted <= availableInSystem) {
            cart.put(itemString, quantityWanted);
        } else{
            createErrorAlertDialog("There not enough items in the store !");
            return;
        }


        ArrayList<String> allItemsInCart = new ArrayList<>();

        //Get all items in the cart from the hash map
        for(Map.Entry<String, Integer> entry: cart.entrySet()) {
            allItemsInCart.add(entry.getKey() + ", Quantity wanted: "+ entry.getValue());
        }

        //Add the items in cart in the dropdown
        Spinner itemsInCart = findViewById(R.id.itemsInCart);
        ArrayAdapter<String> allItemsInCartAdapter = new ArrayAdapter (getApplicationContext(),android.R.layout.simple_spinner_item, allItemsInCart);
        allItemsInCartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemsInCart.setAdapter(allItemsInCartAdapter);

    }


    /**
     * @author Karl Rouhana
     * Remove the items the customer does not want to a temporary hash map to keep
     * track of which items are wanted and how many of them
     */

    public void removeFromCart(View view){

        //Get the proper item
        final Spinner itemChosen = (Spinner) findViewById(R.id.itemsInCart);

        //Since we know the name is right before the first "," I can get it this way
        String itemString = itemChosen.getSelectedItem().toString();
        String[] array = itemString.split(",");
        itemString = array[0];


        //Remove the item we don't want
        cart.remove(itemString);

        ArrayList<String> allItemsInCart = new ArrayList<>();
        //Get all items in the cart from the hash map
        for(Map.Entry<String, Integer> entry: cart.entrySet()) {
            allItemsInCart.add(entry.getKey() + ", Quantity wanted: "+ entry.getValue() );
        }

        //Add the items in cart in the dropdown
        Spinner itemsInCart = findViewById(R.id.itemsInCart);
        ArrayAdapter<String> allItemsInCartAdapter = new ArrayAdapter (getApplicationContext(),android.R.layout.simple_spinner_item, allItemsInCart);
        allItemsInCartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemsInCart.setAdapter(allItemsInCartAdapter);

    }


    /**
     * @author Karl Rouhana
     * Create an order for the customer
     */
    public void createOrderForCustomer(View view){

        //Get the proper item
        final Spinner orderTypeChosen = findViewById(R.id.orderType);

        //Put the correct parameters
        RequestParams rp = new RequestParams();
        rp.put("orderType", orderTypeChosen.getSelectedItem().toString());
        rp.put("email", getCustomerEmail());

        error = "";
        HttpUtils.post("create_order/", rp, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                try {

                    //get the order id
                    Long orderId = Long.parseLong(response.getString("id"));
                    //create the order items for the order
                    createOrderItemsForCustomer(view, orderId);

                    createSuccessAlertDialog("Order placed !");

                } catch (Exception e) {
                    error += e.getMessage();
                    System.out.println(error);
                    createErrorAlertDialog(error);
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                createErrorAlertDialog(errorMessage);
            }

        });


    }

    /**
     * @author Karl Rouhana
     * Create order items for the order
     */
    public void createOrderItemsForCustomer (View view, Long orderId){

        //Check each entry of the cart
        for(Map.Entry<String, Integer> entry: cart.entrySet()) {

            //Put each params
            RequestParams rp = new RequestParams();
            rp.put("quantity", entry.getValue() );
            rp.put("itemName", entry.getKey());
            rp.put("orderId", orderId);

            error = "";

            HttpUtils.post("create_order_item/", rp, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    try {

                    } catch (Exception e) {
                        error += e.getMessage();
                        System.out.println(error);
                        createErrorAlertDialog(error);
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                    createErrorAlertDialog(errorMessage);
                }
            });
        }
    }

    /**
     * @author Ralph Nassar
     */

    public void getUnavailableItems(View view){

        error = "";

        HttpUtils.get("view_all_unavailable_item/", new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                try {

                    String[] allItems = new String[response.length()];

                    //Loop through all unavailable items in system
                    for(int i = 0; i < response.length(); i++){

                        JSONObject item = response.getJSONObject(i);

                        //Get the correct attributes
                        String name = item.getString("name");
                        String price = item.getString("price");


                        String itemString = "";

                        //Add the fields
                        itemString+=name+", $ " +price;

                        allItems[i]=itemString;

                    }

                    //Get the correct dropdown
                    Spinner allItemsSpinner = (Spinner) findViewById(R.id.itemsUnavailable);

                    //Add a listener to get the item chosen
                    allItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        //Show that the item has been selected
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

                    //Output the list on the dropdown menu
                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    allItemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    allItemsSpinner.setAdapter(allItemsAdapter);

                } catch (Exception e) {
                    error += e.getMessage();
                    System.out.println(error);
                    createErrorAlertDialog(error);
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (Exception e) {
                    error += e.getMessage();
                    System.out.println(error);
                    createErrorAlertDialog(error);
                }

            }

        });


    }




}