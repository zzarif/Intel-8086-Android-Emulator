package com.salikoon.emulator8086;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.amrdeveloper.codeview.CodeView;
import com.salikoon.emulator8086.Utility.GoSyntaxManager;

public class MainActivity extends AppCompatActivity {

    private CodeView mCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCodeView = findViewById(R.id.codeView);
        GoSyntaxManager.applyMonokaiTheme(this, mCodeView);
        configLanguageAutoComplete();
    }

    private void configLanguageAutoComplete() {
        //Load current Programming Language
        final String[] languageKeywords = getResources().getStringArray(R.array.keywords);

        //Custom list item xml layout
        final int layoutId = R.layout.keyword_suggestion_item;

        //TextView id to put suggestion on it
        final int viewId = R.id.tvKeyword;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, viewId,
                languageKeywords);

        //Add Custom Adapter to the CodeView
        mCodeView.setAdapter(adapter);
    }
}