package ca.mcgill.ecse321.grocerystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ca.mcgill.ecse321.grocerystore.databinding.FragmentFirstBinding;

public class ItemView extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Spinner itemsAvailableSpinner = view.findViewById(R.id.itemsAvailable);
//
//        ArrayAdapter<String> itemsAvailableAdapter = ArrayAdapter.createFromResource(this.getContext(),, android.R.layout.simple_spinner_item);
//
//        itemsAvailableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        itemsAvailableSpinner.setAdapter(itemsAvailableAdapter);
//        itemsAvailableSpinner.setOnItemSelectedListener(this);

        ((MainActivity)getActivity()).getShoppableItems(view);



//
//        Spinner itemsUnavailableSpinner = view.findViewById(R.id.itemsUnavailable);
//
//        //Dk why adding old adapter
//        itemsUnavailableSpinner.setAdapter(itemsAvailableAdapter);
//        itemsUnavailableSpinner.setOnItemSelectedListener(this);






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
