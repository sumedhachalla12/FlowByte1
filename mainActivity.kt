
// ===== CategorizationFragment.java =====
package com.example.flowbyte;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class CategorizationFragment extends Fragment {
    private List<CategoryItem> itemList;
    private CategoryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorization, container, false);
        itemList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.itemRecyclerView);
        adapter = new CategoryAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Button calcButton = view.findViewById(R.id.btn_calculate_total);
        calcButton.setOnClickListener(v -> {
            double total = 0;
            for (CategoryItem item : itemList) {
                total += item.getCost();
            }
            Toast.makeText(getContext(), "Total: ₹" + total, Toast.LENGTH_LONG).show();
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> showAddItemDialog());

        return view;
    }

    private void showAddItemDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View sheetView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_add_item, null);
        dialog.setContentView(sheetView);

        EditText name = sheetView.findViewById(R.id.et_item_name);
        EditText cost = sheetView.findViewById(R.id.et_item_cost);

        sheetView.findViewById(R.id.btn_submit).setOnClickListener(v -> {
            String itemName = name.getText().toString().trim();
            String itemCostStr = cost.getText().toString().trim();

            if (!itemName.isEmpty() && !itemCostStr.isEmpty()) {
                double itemCost = Double.parseDouble(itemCostStr);
                itemList.add(new CategoryItem(itemName, itemCost));
                adapter.notifyItemInserted(itemList.size() - 1);
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Please fill both fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}

// ===== CategoryAdapter.java =====
package com.example.flowbyte;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final List<CategoryItem> itemList;

    public CategoryAdapter(List<CategoryItem> itemList) {
        this.itemList = itemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, cost;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_item_name);
            cost = itemView.findViewById(R.id.tv_item_cost);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryItem item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.cost.setText("₹ " + item.getCost());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

// ===== CategoryItem.java =====
package com.example.flowbyte;

public class CategoryItem {
    private String name;
    private double cost;

    public CategoryItem(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    public double getCost() { return cost; }
}

// ===== ImageSliderAdapter.java =====
package com.example.flowbyte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder> {
    private Context context;
    private List<Integer> imageList;

    public ImageSliderAdapter(Context context, List<Integer> images) {
        this.context = context;
        this.imageList = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_slide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}

// ===== SavingEntry.java =====
package com.example.flowbyte;

public class SavingEntry {
    private String description;
    private double amount;

    public SavingEntry(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}
