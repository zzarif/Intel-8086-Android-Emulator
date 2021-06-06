package com.salikoon.emulator8086;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.amrdeveloper.codeview.CodeView;
import com.salikoon.emulator8086.Utility.GoSyntaxManager;

public class EditorActivity extends AppCompatActivity {

    private CodeView mCodeView;
    private TextView tvLineNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mCodeView = findViewById(R.id.code_view);
        tvLineNum = findViewById(R.id.tv_line_num);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((ImageView)toolbar.findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((ImageView)toolbar.findViewById(R.id.iv_undo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditorActivity.this, "Undo", Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)toolbar.findViewById(R.id.iv_redo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditorActivity.this, "Redo", Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)toolbar.findViewById(R.id.iv_reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditorActivity.this, "Reset", Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)toolbar.findViewById(R.id.iv_play)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditorActivity.this, EmulateActivity.class);
                startActivity(intent);
            }
        });

        final String[] languageKeywords = getResources().getStringArray(R.array.keywords);
        final int layoutId = R.layout.item_keyword_suggestion;
        final int viewId = R.id.tv_keyword;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, viewId, languageKeywords);
        mCodeView.setAdapter(adapter);

        GoSyntaxManager.applyMonokaiTheme(this, mCodeView);

        mCodeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                int lines = mCodeView.getLineCount();
                StringBuilder lineText = new StringBuilder();
                for (int j=1; j<=lines; ++j)
                    lineText.append(j).append("\n");
                tvLineNum.setText(lineText);
            }
        });
    }

}