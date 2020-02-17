package com.botcraft.wseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.botcraft.wseapp.adapter.EditItemTouchHelperCallback;
import com.botcraft.wseapp.adapter.ItemsAdapter;
import com.botcraft.wseapp.adapter.OnStartDragListener;
import com.botcraft.wseapp.model.Electronics;
import com.botcraft.wseapp.viewmodel.ItemsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnStartDragListener {

    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        itemsAdapter = new ItemsAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.Callback callback =
                new EditItemTouchHelperCallback(itemsAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(itemsAdapter);

        ItemsViewModel itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        itemsViewModel.getItems().observe(this, new Observer<List<Electronics>>() {
            @Override
            public void onChanged(List<Electronics> electronics) {
                itemsAdapter.setItems(electronics);
            }
        });

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
