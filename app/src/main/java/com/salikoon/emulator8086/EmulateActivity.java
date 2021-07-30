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
import com.salikoon.emulator8086.handlers.UIHandler;
import com.salikoon.emulator8086.models.UIPacket;
import com.salikoon.emulator8086.utility.StringParameter;

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
                    removeFocusFromElements();

                    // get/set updated elements
                    UIPacket uiPacket = UIHandler.execute();
                    HashMap<String,Short> elements = uiPacket.updatedMemoryElements.getNewValues();
                    for (Map.Entry<String, Short> entry : elements.entrySet()) {
                        String key = entry.getKey();
                        Short value = entry.getValue();
                        setValue(key,value);
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
     * @param s_value updated value: short type
     */
    private void setValue(String element, Short s_value) {
        try {
            String value = Short.toString(s_value);
            switch (element) {
                case StringParameter.AH:
                    ah.setText(value);
                    focusElement(ah);
                    break;
                case StringParameter.AL:
                    al.setText(value);
                    focusElement(al);
                    break;
                case StringParameter.BH:
                    bh.setText(value);
                    focusElement(bh);
                    break;
                case StringParameter.BL:
                    bl.setText(value);
                    focusElement(bl);
                    break;
                case StringParameter.CH:
                    ch.setText(value);
                    focusElement(ch);
                    break;
                case StringParameter.CL:
                    cl.setText(value);
                    focusElement(cl);
                    break;
                case StringParameter.DH:
                    dh.setText(value);
                    focusElement(dh);
                    break;
                case StringParameter.DL:
                    dl.setText(value);
                    focusElement(dl);
                    break;
                case StringParameter.OverflowFlag:
                    of.setText(value);
                    focusElement(of);
                    break;
                case StringParameter.DirectionFlag:
                    df.setText(value);
                    focusElement(df);
                    break;
                case StringParameter.InterruptFlag:
                    _if.setText(value);
                    focusElement(_if);
                    break;
                case StringParameter.TrapFlag:
                    tf.setText(value);
                    focusElement(tf);
                    break;
                case StringParameter.SignFlag:
                    sf.setText(value);
                    focusElement(sf);
                    break;
                case StringParameter.ZeroFlag:
                    zf.setText(value);
                    focusElement(zf);
                    break;
                case StringParameter.AuxiliaryFlag:
                    af.setText(value);
                    focusElement(af);
                    break;
                case StringParameter.ParityFlag:
                    pf.setText(value);
                    focusElement(pf);
                    break;
                case StringParameter.CarryFlag:
                    cf.setText(value);
                    focusElement(cf);
                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // remove highlights
    private void removeFocusFromElements() {
        ah.setTypeface(ah.getTypeface(),Typeface.NORMAL);
        ah.setTextColor(getResources().getColor(R.color.gray_4));
        al.setTypeface(al.getTypeface(),Typeface.NORMAL);
        al.setTextColor(getResources().getColor(R.color.gray_4));

        bh.setTypeface(bh.getTypeface(),Typeface.NORMAL);
        bh.setTextColor(getResources().getColor(R.color.gray_4));
        bl.setTypeface(bl.getTypeface(),Typeface.NORMAL);
        bl.setTextColor(getResources().getColor(R.color.gray_4));

        ch.setTypeface(ch.getTypeface(),Typeface.NORMAL);
        ch.setTextColor(getResources().getColor(R.color.gray_4));
        cl.setTypeface(cl.getTypeface(),Typeface.NORMAL);
        cl.setTextColor(getResources().getColor(R.color.gray_4));

        dh.setTypeface(dh.getTypeface(),Typeface.NORMAL);
        dh.setTextColor(getResources().getColor(R.color.gray_4));
        dl.setTypeface(dl.getTypeface(),Typeface.NORMAL);
        dl.setTextColor(getResources().getColor(R.color.gray_4));

        of.setTypeface(of.getTypeface(),Typeface.NORMAL);
        of.setTextColor(getResources().getColor(R.color.gray_4));

        df.setTypeface(df.getTypeface(),Typeface.NORMAL);
        df.setTextColor(getResources().getColor(R.color.gray_4));

        _if.setTypeface(_if.getTypeface(),Typeface.NORMAL);
        _if.setTextColor(getResources().getColor(R.color.gray_4));

        tf.setTypeface(tf.getTypeface(),Typeface.NORMAL);
        tf.setTextColor(getResources().getColor(R.color.gray_4));

        sf.setTypeface(sf.getTypeface(),Typeface.NORMAL);
        sf.setTextColor(getResources().getColor(R.color.gray_4));

        zf.setTypeface(zf.getTypeface(),Typeface.NORMAL);
        zf.setTextColor(getResources().getColor(R.color.gray_4));

        af.setTypeface(af.getTypeface(),Typeface.NORMAL);
        af.setTextColor(getResources().getColor(R.color.gray_4));

        pf.setTypeface(pf.getTypeface(),Typeface.NORMAL);
        pf.setTextColor(getResources().getColor(R.color.gray_4));

        cf.setTypeface(cf.getTypeface(),Typeface.NORMAL);
        cf.setTextColor(getResources().getColor(R.color.gray_4));
    }

    // highlight an element
    private void focusElement(TextView textView) {
        textView.setTypeface(textView.getTypeface(),Typeface.BOLD);
        textView.setTextColor(getResources().getColor(R.color.keyword_0_extra));
    }
}
