package ca.mcgill.ecse321.grocerystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import ca.mcgill.ecse321.grocerystore.databinding.OrderViewBinding;

public class ViewOrder extends Fragment {
    private OrderViewBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = OrderViewBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getOrdersOfCustomer(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    /**
     * @author Matthieu Hakim
     */
    public void getOrdersOfCustomer(View v) {

        HttpUtils.get("/view_all_orders_for_customer?email=deschamps@me", new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    TextView selectOrder = ((MainActivity)getActivity()).findViewById(R.id.list_of_orders);

                    selectOrder.setVisibility(View.VISIBLE);
                    ArrayList<String> displayOrders = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject serverResp = response.getJSONObject(i);

                        String date = serverResp.getString("date");
                        String time = serverResp.getString("time");
                        String orderType = serverResp.getString("orderType");
                        String orderStatus = serverResp.getString("orderStatus");
                        String subtotal = serverResp.getString("subtotal");
                        displayOrders.add("Date: " + date + "\n" + "Time: " + time + "\n" + "Type: " +orderType + "\n" + "Status: " + orderStatus + "\n" + "Subtotal: " + subtotal);

                    }

                    ListView ordersListView = ((MainActivity)getActivity()).findViewById(R.id.orderViewList);
                    ArrayAdapter<String> orderArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, displayOrders);
                    ordersListView.setAdapter(orderArrayAdapter);

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                ((MainActivity)getActivity()).createErrorAlertDialog(errorMessage);
            }
        });
    }
}
