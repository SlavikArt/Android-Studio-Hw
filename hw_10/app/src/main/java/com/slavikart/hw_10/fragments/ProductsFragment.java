package com.slavikart.hw_10.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.slavikart.hw_10.R;
import com.slavikart.hw_10.adapters.ProductsAdapter;
import com.slavikart.hw_10.db.DatabaseOperations;
import com.slavikart.hw_10.model.Product;
import com.slavikart.hw_10.model.Type;
import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    private RecyclerView rv;
    private ProductsAdapter adapter;
    private DatabaseOperations dbOps;
    private FloatingActionButton fab;
    private long listId;
    private ArrayList<Type> types;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);
        
        dbOps = new DatabaseOperations(getContext());
        rv = v.findViewById(R.id.rv_products);
        fab = v.findViewById(R.id.fab_add_product);
        
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductsAdapter(new ArrayList<>(), this::onProductClick);
        rv.setAdapter(adapter);
        
        fab.setOnClickListener(v1 -> showCreateProductDialog());
        
        if (getArguments() != null)
            listId = getArguments().getLong("list_id");
        
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbOps.open();
        types = dbOps.getAllTypes();
        loadProducts();
    }

    @Override
    public void onPause() {
        super.onPause();
        dbOps.close();
    }

    private void loadProducts() {
        adapter.updateProducts(dbOps.getProductsForList(listId));
    }

    private void onProductClick(Product product) {
        showEditProductDialog(product);
    }

    private void showCreateProductDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_product, null);
        TextInputEditText etName = dialogView.findViewById(R.id.et_name);
        TextInputEditText etCount = dialogView.findViewById(R.id.et_count);
        AutoCompleteTextView spinnerType = dialogView.findViewById(R.id.spinner_type);
        
        ArrayAdapter<Type> typeAdapter = new ArrayAdapter<>(getContext(),
            android.R.layout.simple_dropdown_item_1line, types);
        spinnerType.setAdapter(typeAdapter);
        
        // Set default selection to first type
        if (!types.isEmpty()) {
            spinnerType.setText(types.get(0).toString(), false);
        }
        
        AlertDialog dialog = new AlertDialog.Builder(getContext())
            .setTitle(R.string.new_product)
            .setView(dialogView)
            .setPositiveButton(R.string.add, null)
            .setNegativeButton(R.string.cancel, null)
            .create();
            
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String name = etName.getText().toString().trim();
                String countStr = etCount.getText().toString().trim();
                
                android.util.Log.d("ProductsFragment", "Dialog values - Name: '" + name + "', Count: '" + countStr + "'");
                
                if (!name.isEmpty() && !countStr.isEmpty()) {
                    try {
                        double count = Double.parseDouble(countStr);
                        
                        // Find the selected type by matching the text
                        Type selectedType = null;
                        String selectedText = spinnerType.getText().toString();
                        for (Type type : types) {
                            if (type.toString().equals(selectedText)) {
                                selectedType = type;
                                break;
                            }
                        }
                        
                        if (selectedType == null) {
                            android.util.Log.e("ProductsFragment", "No type selected");
                            return;
                        }
                        
                        android.util.Log.d("ProductsFragment", "Creating product: " + name + ", count: " + count + ", type: " + selectedType.getLabel());
                        Product product = new Product(0, name, count, listId, 0, selectedType.getId());
                        long result = dbOps.insertProduct(product);
                        android.util.Log.d("ProductsFragment", "Product insert result: " + result);
                        loadProducts();
                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        android.util.Log.e("ProductsFragment", "Invalid number format: " + countStr);
                        etCount.setError("Please enter a valid number");
                    }
                } else {
                    if (name.isEmpty()) {
                        etName.setError("Please enter a name");
                    }
                    if (countStr.isEmpty()) {
                        etCount.setError("Please enter a count");
                    }
                    android.util.Log.e("ProductsFragment", "Empty name or count - Name length: " + name.length() + ", Count length: " + countStr.length());
                }
            });
        });
        
        dialog.show();
    }

    private void showEditProductDialog(Product product) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_product, null);
        TextInputEditText etName = dialogView.findViewById(R.id.et_name);
        TextInputEditText etCount = dialogView.findViewById(R.id.et_count);
        AutoCompleteTextView spinnerType = dialogView.findViewById(R.id.spinner_type);
        
        etName.setText(product.getName());
        etCount.setText(String.valueOf(product.getCount()));
        
        ArrayAdapter<Type> typeAdapter = new ArrayAdapter<>(getContext(),
            android.R.layout.simple_dropdown_item_1line, types);
        spinnerType.setAdapter(typeAdapter);
        
        // Set the current type
        for (Type type : types) {
            if (type.getId() == product.getCountType()) {
                spinnerType.setText(type.toString(), false);
                break;
            }
        }
        
        AlertDialog dialog = new AlertDialog.Builder(getContext())
            .setTitle(R.string.edit_product)
            .setView(dialogView)
            .setPositiveButton(R.string.save, null)
            .setNegativeButton(R.string.cancel, null)
            .setNeutralButton(R.string.delete, null)
            .create();
            
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String name = etName.getText().toString().trim();
                String countStr = etCount.getText().toString().trim();
                
                if (!name.isEmpty() && !countStr.isEmpty()) {
                    try {
                        double count = Double.parseDouble(countStr);
                        
                        // Find the selected type by matching the text
                        Type selectedType = null;
                        String selectedText = spinnerType.getText().toString();
                        for (Type type : types) {
                            if (type.toString().equals(selectedText)) {
                                selectedType = type;
                                break;
                            }
                        }
                        
                        if (selectedType == null) {
                            android.util.Log.e("ProductsFragment", "No type selected");
                            return;
                        }
                        
                        product.setName(name);
                        product.setCount(count);
                        product.setCountType(selectedType.getId());
                        
                        dbOps.updateProduct(product);
                        loadProducts();
                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        android.util.Log.e("ProductsFragment", "Invalid number format: " + countStr);
                        etCount.setError("Please enter a valid number");
                    }
                } else {
                    if (name.isEmpty()) {
                        etName.setError("Please enter a name");
                    }
                    if (countStr.isEmpty()) {
                        etCount.setError("Please enter a count");
                    }
                }
            });
            
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
                dbOps.deleteProduct(product.getId());
                loadProducts();
                dialog.dismiss();
            });
        });
        
        dialog.show();
    }
} 