package com.salikoon.emulator8086;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amrdeveloper.codeview.CodeView;
import com.salikoon.emulator8086.Utility.GoSyntaxManager;

public class MainActivity extends AppCompatActivity {

    private CodeView mCodeView;
    private TextView tvLineNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCodeView = findViewById(R.id.code_view);
        tvLineNum = findViewById(R.id.tv_line_num);

        final String[] languageKeywords = getResources().getStringArray(R.array.keywords);
        final int layoutId = R.layout.keyword_suggestion_item;
        final int viewId = R.id.tvKeyword;
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