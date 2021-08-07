package com.salikoon.emulator8086;

import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jmedeisis.draglinearlayout.DragLinearLayout;
import com.salikoon.emulator8086.hardware.StringParameter;
import com.salikoon.emulator8086.ui_helper.UIHandler;
import com.salikoon.emulator8086.ui_helper.UIPacket;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zibranzarif
 */
public class EmulateActivity extends AppCompatActivity{
    ScrollView scrollView;
    private String[] lines;
    TextView tvExec;
    TextView ah,al,bh,bl,ch,cl,dh,dl;
    TextView of,df, _if,tf,sf,zf,af,pf,cf;
    LinearLayout btnExec;
    private int currentLine = 1;

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

        // initialize parent views
        scrollView = findViewById(R.id.scroll_view);
        tvExec = findViewById(R.id.tv_exec);
        btnExec = findViewById(R.id.btn_exec);
        LinearLayout llRegisters = findViewById(R.id.layout_registers);
        LinearLayout llSegments = findViewById(R.id.layout_segments);
        LinearLayout llPointers = findViewById(R.id.layout_pointers);
        LinearLayout llFlags = findViewById(R.id.layout_flags);

        // initialize register views
        ah = llRegisters.findViewById(R.id.ah);
        al = llRegisters.findViewById(R.id.al);
        bh = llRegisters.findViewById(R.id.bh);
        bl = llRegisters.findViewById(R.id.bl);
        ch = llRegisters.findViewById(R.id.ch);
        cl = llRegisters.findViewById(R.id.cl);
        dh = llRegisters.findViewById(R.id.dh);
        dl = llRegisters.findViewById(R.id.dl);

        // initialize flag views
        of = llFlags.findViewById(R.id.of);
        df = llFlags.findViewById(R.id.df);
        _if = llFlags.findViewById(R.id._if);
        tf = llFlags.findViewById(R.id.tf);
        sf = llFlags.findViewById(R.id.sf);
        zf = llFlags.findViewById(R.id.zf);
        af = llFlags.findViewById(R.id.af);
        pf = llFlags.findViewById(R.id.pf);
        cf = llFlags.findViewById(R.id.cf);

        // initialize expand and collapse
        controlColExp(llRegisters);
        controlColExp(llSegments);
        controlColExp(llPointers);
        controlColExp(llFlags);

        // initialize drag and drop
        DragLinearLayout dragLinearLayout = (DragLinearLayout) findViewById(R.id.drag_linear_layout);
        for(int i = 0; i < dragLinearLayout.getChildCount(); i++){
            View child = dragLinearLayout.getChildAt(i);
            dragLinearLayout.setViewDraggable(child,child);
        }
        dragLinearLayout.setContainerScrollView(scrollView);

        // get code from editor
        lines = getIntent().getStringArrayExtra("MyCode");
        tvExec.setText(lines[currentLine]);

        // set code via UI Handler
        UIHandler.setCode(lines);

        // onclick handler for execute button
        btnExec.setOnClickListener(view -> {
            try {
                if (currentLine<lines.length) {
                    deFocusAllElements();

                    // get/set updated elements
                    UIPacket uiPacket = UIHandler.execute();
                    HashMap<String,Short> elements = uiPacket.updatedMemoryElements.getNewValues();
                    for (Map.Entry<String, Short> entry : elements.entrySet()) {
                        Log.d("Zarif_0002", "onCreate: L"+currentLine+" "+entry.getKey()+" "+entry.getValue());
                        setValue(entry.getKey(),entry.getValue());
                    }

                    // set next instruction to be executed
                    if (++currentLine<lines.length)
                        tvExec.setText(lines[currentLine]);
                }
                else {
                    Toast.makeText(this,"Finished",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
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

    /**
     * controls transition for
     * expanding and collapsing
     * register containers
     * @param llContainer parent/container
     */
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

    /**
     * sets the updated value
     * to the corresponding register
     * @param element register to update
     * @param value updated value: short type
     */
    private void setValue(String element,short value) {
        try {
            switch (element) {
                case StringParameter.AX:
                    setText_16bit(ah,al,value);
                    break;
                case StringParameter.BX:
                    setText_16bit(bh,bl,value);
                    break;
                case StringParameter.CX:
                    setText_16bit(ch,cl,value);
                    break;
                case StringParameter.DX:
                    setText_16bit(dh,dl,value);
                    break;
                case StringParameter.AH:
                    setText_8bit(ah,value);
                    break;
                case StringParameter.AL:
                    setText_8bit(al,value);
                    break;
                case StringParameter.BH:
                    setText_8bit(bh,value);
                    break;
                case StringParameter.BL:
                    setText_8bit(bl,value);
                    break;
                case StringParameter.CH:
                    setText_8bit(ch,value);
                    break;
                case StringParameter.CL:
                    setText_8bit(cl,value);
                    break;
                case StringParameter.DH:
                    setText_8bit(dh,value);
                    break;
                case StringParameter.DL:
                    setText_8bit(dl,value);
                    break;
                case StringParameter.OverflowFlag:
                    setText_1bit(of,value);
                    break;
                case StringParameter.DirectionFlag:
                    setText_1bit(df,value);
                    break;
                case StringParameter.InterruptFlag:
                    setText_1bit(_if,value);
                    break;
                case StringParameter.TrapFlag:
                    setText_1bit(tf,value);
                    break;
                case StringParameter.SignFlag:
                    setText_1bit(sf,value);
                    break;
                case StringParameter.ZeroFlag:
                    setText_1bit(zf,value);
                    break;
                case StringParameter.AuxiliaryFlag:
                    setText_1bit(af,value);
                    break;
                case StringParameter.ParityFlag:
                    setText_1bit(pf,value);
                    break;
                case StringParameter.CarryFlag:
                    setText_1bit(cf,value);
                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // remove highlights
    private void deFocusAllElements() {
        // registers
        deFocusElement(ah);
        deFocusElement(al);
        deFocusElement(bh);
        deFocusElement(bl);
        deFocusElement(ch);
        deFocusElement(cl);
        deFocusElement(dh);
        deFocusElement(dl);
        // flags
        deFocusElement(of);
        deFocusElement(df);
        deFocusElement(_if);
        deFocusElement(tf);
        deFocusElement(sf);
        deFocusElement(zf);
        deFocusElement(af);
        deFocusElement(pf);
        deFocusElement(cf);
    }

    // remove highlight
    private void deFocusElement(TextView textView) {
        textView.setTypeface(textView.getTypeface(),Typeface.NORMAL);
        textView.setTextColor(getResources().getColor(R.color.gray_4));
    }

    // set 1-bit values e.g. flags
    private void setText_1bit(TextView textView,short value) {
        textView.setText(Short.toString(value));
        textView.setTypeface(textView.getTypeface(),Typeface.BOLD);
        textView.setTextColor(getResources().getColor(R.color.keyword_0_extra));
    }

    // set 8-bit values
    private void setText_8bit(TextView textView,short value) {
        String sValue = String.format("%02x",value);
        textView.setText(sValue);
        textView.setTypeface(textView.getTypeface(),Typeface.BOLD);
        textView.setTextColor(getResources().getColor(R.color.keyword_0_extra));
    }

    // set 16-bit values
    private void setText_16bit(TextView high,TextView low,short value) {
        byte lValue = (byte) value; // low
        byte hValue= (byte) (value >> 8); // high
        setText_8bit(high,hValue);
        setText_8bit(low,lValue);
    }
}