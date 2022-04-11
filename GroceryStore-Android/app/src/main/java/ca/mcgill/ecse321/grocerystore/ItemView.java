package ca.mcgill.ecse321.grocerystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ca.mcgill.ecse321.grocerystore.databinding.ItemviewBinding;

public class ItemView extends Fragment implements AdapterView.OnItemSelectedListener {

    private ItemviewBinding binding;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = ItemviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner orderTypeSpinner = view.findViewById(R.id.orderType);

        ArrayAdapter<CharSequence> ordertypeAdapter = ArrayAdapter.createFromResource(this.getContext(),R.array.orderType, android.R.layout.simple_spinner_item);

        ordertypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderTypeSpinner.setAdapter(ordertypeAdapter);
        orderTypeSpinner.setOnItemSelectedListener(this);

        ((MainActivity)getActivity()).getShoppableItems(view);
        ((MainActivity)getActivity()).getUnavailableItems(view);





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }











}
