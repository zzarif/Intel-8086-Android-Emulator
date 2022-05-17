package com.salikoon.emulator8086.ui;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_STATIC_DP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.ui_helper.UIHandler;
import com.salikoon.emulator8086.ui_helper.UIPacket;
import com.salikoon.emulator8086.utility.ExecutionDirector;
import com.salikoon.emulator8086.utility.ShowCaseHelper;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ListView listView;
//    TextView ah,al,bh,bl,ch,cl,dh,dl;
//    TextView of,df, _if,tf,sf,zf,af,pf,cf;
    Button btnExec,btnStepBack;

    ListAdapter listAdapter;
    LinearLayout llLineSelect;
    FrameLayout.LayoutParams mParams;
    private int lineSelectBaseMargin;

    ExecutionDirector executionDirector;

    ViewPager2 viewPager2;
    private int currtab = 0;
    public static HashMap<String,Short> elements;

    public static ActivityEmulateBinding motherOFAllBinding;
    public static String myOutputChar = "";
    // tab titles
    private final String[] titles = new String[]{"Registers", "Flags", "I/O"};


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_emulate);
        motherOFAllBinding = ActivityEmulateBinding.inflate(getLayoutInflater());
        setContentView(motherOFAllBinding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        elements = new HashMap<>();
        Log.d("ddr-newElem",String.valueOf(elements));

        init();

        // initialize parent views
        scrollView = findViewById(R.id.scroll_view);
        listView = findViewById(R.id.list_view);
        btnExec = findViewById(R.id.btn_exec);
//        btnStepBack = findViewById(R.id.btn_back);
        llLineSelect = findViewById(R.id.line_select);
        mParams = (FrameLayout.LayoutParams) llLineSelect.getLayoutParams();
        lineSelectBaseMargin = mParams.topMargin;

        // get code from editor
        lines = getIntent().getStringArrayExtra("MyCode");

        listAdapter = new ListAdapter(this, Arrays.asList(lines));
        Log.d("WHAATTTT", "onCreate: "+Arrays.asList(lines));
        listView.setAdapter(listAdapter);

        executionDirector = new ExecutionDirector();

        // onclick handler for execute button
        PushDownAnim.setPushDownAnimTo(btnExec)
                .setScale(MODE_STATIC_DP, 8)
                .setOnClickListener( view -> {
                    try {

//                        deFocusAllElements();

                        // get/set updated elements
                        myOutputChar="";

                        if (UIHandler.executionIncomplete()) {
                            UIPacket uiPacket = UIHandler.execute();

                            HashMap<String,Short> newElements = uiPacket.updatedMemoryElements.getNewValues();
                            selectLine(uiPacket.lineJustExecuted);

                            for (Map.Entry<String, Short> entry : newElements.entrySet()) {
                                elements.put(entry.getKey(), entry.getValue());
                            }
//                            HashMap<String,Short> elements = uiPacket.updatedMemoryElements.getNewValues();
//                            for (Map.Entry<String, Short> entry : elements.entrySet()) {
//                                Log.d("Zarif_0002", "onCreate: L"+currentLine+" "+entry.getKey()+" "+entry.getValue());
//                                setValue(entry.getKey(),entry.getValue());
//                            }
                            currtab = motherOFAllBinding.tabLayout.getSelectedTabPosition();
                            Log.d("ddr:Inside btnExec",String.valueOf(currtab));
                            init();
                            Log.d("Zarif_0002", elements.toString());
                            if (uiPacket.lineJustExecuted+1<lines.length) {
                                if (elements.get("AH")==1 && lines[uiPacket.lineJustExecuted+1].contains("INT")) {
                                    EmulateActivity.motherOFAllBinding.tabLayout.getTabAt(2).select();
                                }
                                else if (elements.get("AH")==2 && lines[uiPacket.lineJustExecuted].contains("INT")) {
                                    EmulateActivity.motherOFAllBinding.tabLayout.getTabAt(2).select();
                                    Log.d("Fixed!", "btnExec: "+myOutputChar );
                                }
                            }

                        } else {
                            elements.clear();
                            Toast.makeText(this,"Finished",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
//                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                })
                .setOnLongClickListener(view -> {
                    ShowCaseHelper.show(this,btnExec,"Execute the line shown at the very top");
                    return true;
                });

//        PushDownAnim.setPushDownAnimTo(btnStepBack)
//                .setScale(MODE_STATIC_DP, 8)
//                .setOnClickListener( view -> {
//                    try {
//                    } catch (Exception e) {
//                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                })
//                .setOnLongClickListener(view -> {
//                    ShowCaseHelper.show(this,btnExec,"Step back to the previous line");
//                    return true;
//                });
    }

    private void init() {
        // removing toolbar elevation
        // getSupportActionBar().setElevation(0);

        motherOFAllBinding.viewPager.setAdapter(new EmulateActivity.ViewPagerFragmentAdapter(this));

        // attaching tab mediator
        new TabLayoutMediator(motherOFAllBinding.tabLayout, motherOFAllBinding.viewPager,
                (tab, position) -> {
                    tab.setText(titles[position]);
                }).attach();
        motherOFAllBinding.tabLayout.getTabAt(currtab).select();
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
                    return new IOFragment();
//                case 3:
//                    return new PointersFragments();
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

    private void resetText(TextView rview) {
        rview.setTypeface(null, Typeface.NORMAL);
        rview.setTextColor(getResources().getColor(R.color.gray_4));
    }

    private void reset() {
        for (Map.Entry<String, Short> entry : elements.entrySet()) {
            switch (entry.getKey()) {
                case StringParameter.AX:
                    resetText(RegistersFragment.regbinding.ah);
                    resetText(RegistersFragment.regbinding.al);
                    break;
                case StringParameter.BX:
                    resetText(RegistersFragment.regbinding.bh);
                    resetText(RegistersFragment.regbinding.bl);
                    break;
                case StringParameter.CX:
                    resetText(RegistersFragment.regbinding.ch);
                    resetText(RegistersFragment.regbinding.cl);
                    break;
                case StringParameter.DX:
                    resetText(RegistersFragment.regbinding.dh);
                    resetText(RegistersFragment.regbinding.dl);
                    break;
                case StringParameter.AH:
                    resetText(RegistersFragment.regbinding.ah);
                    break;
                case StringParameter.AL:
                    resetText(RegistersFragment.regbinding.al);
                    break;
                case StringParameter.BH:
                    resetText(RegistersFragment.regbinding.bh);
                    break;
                case StringParameter.BL:
                    resetText(RegistersFragment.regbinding.bl);
                    break;
                case StringParameter.CH:
                    resetText(RegistersFragment.regbinding.ch);
                    break;
                case StringParameter.CL:
                    resetText(RegistersFragment.regbinding.cl);
                    break;
                case StringParameter.DH:
                    resetText(RegistersFragment.regbinding.dh);
                    break;
                case StringParameter.DL:
                    resetText(RegistersFragment.regbinding.dl);
                    break;
                case StringParameter.CarryFlag:
                    resetText(FlagsFragment.flagbinding.cf);
                    break;
                case StringParameter.AuxiliaryFlag:
                    resetText(FlagsFragment.flagbinding.af);
                    break;
                case StringParameter.DirectionFlag:
                    resetText(FlagsFragment.flagbinding.df);
                    break;
                case StringParameter.InterruptFlag:
                    resetText(FlagsFragment.flagbinding.eyef);
                    break;
                case StringParameter.OverflowFlag:
                    resetText(FlagsFragment.flagbinding.of);
                    break;
                case StringParameter.ParityFlag:
                    resetText(FlagsFragment.flagbinding.pf);
                    break;
                case StringParameter.SignFlag:
                    resetText(FlagsFragment.flagbinding.sf);
                    break;
                case StringParameter.TrapFlag:
                    resetText(FlagsFragment.flagbinding.tf);
                    break;
                case StringParameter.ZeroFlag:
                    resetText(FlagsFragment.flagbinding.zf);
                    break;
                default:
                    break;
            }
        }
        myOutputChar="";
    }

    @Override
    public void onBackPressed() {
        reset();
        super.onBackPressed();
    }


    public class ListAdapter extends ArrayAdapter<String> {

        private int resourceLayout;
        private Context mContext;

        public ListAdapter(Context context, List<String> items) {
            super(context, 0, items);
            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_line_list,null);
            }
            ((TextView) convertView.findViewById(R.id.tv_line))
                    .setText((String) getItem(position));
            return convertView;
        }

    }

    private void selectLine(int lineNo) {
        mParams.topMargin = lineSelectBaseMargin + lineNo*getListViewItemHeight();
    }

    private int getListViewItemHeight() {
        View childView = listAdapter.getView(0, null, listView);
        childView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return childView.getMeasuredHeight();
    }

    private static void getUserInput() {

    }
}