package com.salikoon.emulator8086;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.amrdeveloper.codeview.CodeView;
import com.salikoon.emulator8086.Utility.GoSyntaxManager;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class EditorActivity extends AppCompatActivity {

    private CodeView mCodeView;
    private TextView tvLineNum;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_editor_options, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCodeView = findViewById(R.id.code_view);
        tvLineNum = findViewById(R.id.tv_line_num);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.paste:
                String textToPaste = null;
                ClipboardManager clipboard = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard.hasPrimaryClip()) {
                    ClipData clip = clipboard.getPrimaryClip();
                    textToPaste = clip.getItemAt(0).coerceToText(this).toString();
                }
                if (!TextUtils.isEmpty(textToPaste))
                    mCodeView.setText(textToPaste);
                return true;
            case R.id.undo:
                Toast.makeText(this, "Undo", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.redo:
                Toast.makeText(this, "Redo", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.compile:
                startActivity(new Intent(this, EmulateActivity.class));
                return true;
            case R.id.save:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reset:
                Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}