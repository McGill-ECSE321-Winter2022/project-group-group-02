package ca.mcgill.ecse321.grocerystore;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import ca.mcgill.ecse321.grocerystore.databinding.UpdateCustomerBinding;

public class UpdateCustomer extends Fragment {
    private UpdateCustomerBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = UpdateCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.NameUpdate.setText(((MainActivity)getActivity()).getCustomerName());
        binding.AddressUpdate.setText(((MainActivity)getActivity()).getCustomerAddress());

        binding.updateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
