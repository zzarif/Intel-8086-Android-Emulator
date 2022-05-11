package com.salikoon.emulator8086.ui;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_STATIC_DP;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.ui_helper.UIHandler;
import com.salikoon.emulator8086.ui_helper.UIPacket;
import com.salikoon.emulator8086.utility.ShowCaseHelper;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;

import com.salikoon.emulator8086.databinding.ActivityEmulateBinding;
import com.salikoon.emulator8086.ui.fragments.*;

/**
 *
 * @author zibranzarif
 */
public class EmulateActivity extends AppCompatActivity{
    NestedScrollView scrollView;
    private String[] lines;
    TextView tvExec,tvExecNext;
//    TextView ah,al,bh,bl,ch,cl,dh,dl;
//    TextView of,df, _if,tf,sf,zf,af,pf,cf;
    Button btnExec;
    private int currentLine = 1;

    ViewPager2 viewPager2;
    private int currtab = 0;
    public static HashMap<String,Short> elements = new HashMap<>();

    ActivityEmulateBinding binding;
    // tab titles
    private final String[] titles = new String[]{"Registers", "Flags", "Segments", "Pointers"};


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_emulate);
        binding = ActivityEmulateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        // initialize parent views
        scrollView = findViewById(R.id.scroll_view);
        tvExec = findViewById(R.id.tv_exec);
        tvExecNext = findViewById(R.id.tv_exec_next);
        btnExec = findViewById(R.id.btn_exec);

        // get code from editor
        lines = getIntent().getStringArrayExtra("MyCode");
        tvExecNext.setText(lines[1]);

        // onclick handler for execute button
        PushDownAnim.setPushDownAnimTo(btnExec)
                .setScale(MODE_STATIC_DP, 8)
                .setOnClickListener( view -> {
                    try {

//                        deFocusAllElements();

                        // get/set updated elements

                        if (UIHandler.executionIncomplete()) {
                            UIPacket uiPacket = UIHandler.execute();
//                            HashMap<String,Short> elements = uiPacket.updatedMemoryElements.getNewValues();
//                            for (Map.Entry<String, Short> entry : elements.entrySet()) {
//                                Log.d("Zarif_0002", "onCreate: L"+currentLine+" "+entry.getKey()+" "+entry.getValue());
//                                setValue(entry.getKey(),entry.getValue());
//                            }
                            elements = uiPacket.updatedMemoryElements.getNewValues();
                            Log.d("Zarif_0002", elements.toString());
                            currtab = binding.tabLayout.getSelectedTabPosition();
                            init();
                            binding.tabLayout.getTabAt(currtab).select();

                            tvExec.setText(lines[uiPacket.lineJustExecuted]);
                            if (uiPacket.lineJustExecuted+1<lines.length)
                                tvExecNext.setText(lines[uiPacket.lineJustExecuted+1]);
                            else tvExecNext.setText("None");

                        } else {
                            tvExec.setText("None");
                            Toast.makeText(this,"Finished",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                })
                .setOnLongClickListener(view -> {
                    ShowCaseHelper.show(this,btnExec,"Execute the line shown at the very top");
                    return true;
                });
    }

    private void init() {
        // removing toolbar elevation
        // getSupportActionBar().setElevation(0);

        binding.viewPager.setAdapter(new EmulateActivity.ViewPagerFragmentAdapter(this));

        // attaching tab mediator
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    tab.setText(titles[position]);
                }).attach();
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new RegistersFragment();
                case 1:
                    return new FlagsFragment();
                case 2:
                    return new SegmentsFragment();
                case 3:
                    return new PointersFragments();
            }
            return new RegistersFragment();
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}