package com.salikoon.emulator8086;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jmedeisis.draglinearlayout.DragLinearLayout;

public class EmulateActivity extends AppCompatActivity {
    ScrollView scrollView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_emulator_options, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_emulate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scrollView = findViewById(R.id.scroll_view);
        LinearLayout llRegisters = findViewById(R.id.layout_general);
        LinearLayout llSegments = findViewById(R.id.layout_segments);
        LinearLayout llPointers = findViewById(R.id.layout_pointers);
        LinearLayout llFlags = findViewById(R.id.layout_flags);

        controlColExp(llRegisters);
        controlColExp(llSegments);
        controlColExp(llPointers);
        controlColExp(llFlags);


        DragLinearLayout dragLinearLayout = (DragLinearLayout) findViewById(R.id.drag_linear_layout);
        for(int i = 0; i < dragLinearLayout.getChildCount(); i++){
            View child = dragLinearLayout.getChildAt(i);
            dragLinearLayout.setViewDraggable(child,child);
        }
        dragLinearLayout.setContainerScrollView(scrollView);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.view_mode:
                Toast.makeText(this, "Hex/Bin/Dec", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void controlColExp(LinearLayout llContainer) {
        ImageView ivColExp = llContainer.findViewById(R.id.iv_expand);
        ivColExp.setOnClickListener(v -> {
            TableLayout tableLayout = llContainer.findViewById(R.id.table_layout);
            TransitionManager.beginDelayedTransition(scrollView);
            if (tableLayout.getVisibility()==View.GONE) {
                tableLayout.setVisibility(View.VISIBLE);
                ivColExp.setImageResource(R.drawable.ic_baseline_expand_less_24);
            } else {
                tableLayout.setVisibility(View.GONE);
                ivColExp.setImageResource(R.drawable.ic_baseline_expand_more_24);
            }
        });
    }
}
