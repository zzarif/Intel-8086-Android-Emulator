package com.salikoon.emulator8086.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amrdeveloper.codeview.CodeView;
import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.syntax_check.SyntaxSlip;
import com.salikoon.emulator8086.ui_helper.UIHandler;
import com.salikoon.emulator8086.utility.ErrorUtils;
import com.salikoon.emulator8086.utility.FileManager;
import com.salikoon.emulator8086.utility.GoSyntaxManager;
import com.salikoon.emulator8086.utility.IntentKey;
import com.salikoon.emulator8086.utility.PreferenceManager;
import com.salikoon.emulator8086.utility.UndoRedoHelper;

import java.io.File;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    private CodeView mCodeView;
    private TextView tvLineNum;
    private String filePath=null;
    private UndoRedoHelper undoRedoHelper;

    private TextView tvErrorMsg;
    private RelativeLayout rlErrorMsg;
    private ImageView ivErrorMsg;

    PreferenceManager preferenceManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_editor_options, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceManager = new PreferenceManager(this);

        mCodeView = findViewById(R.id.code_view);
        tvLineNum = findViewById(R.id.tv_line_num);
        tvErrorMsg = findViewById(R.id.tv_error_msg);
        rlErrorMsg = findViewById(R.id.rl_error_msg);
        ivErrorMsg = findViewById(R.id.iv_error_msg);

        final String[] languageKeywords = getResources().getStringArray(R.array.keywords);
        final int layoutId = R.layout.item_keyword_suggestion;
        final int viewId = R.id.tv_keyword;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, viewId, languageKeywords);
        mCodeView.setAdapter(adapter);

        GoSyntaxManager.applyMonokaiTheme(this, mCodeView);
        undoRedoHelper = new UndoRedoHelper(mCodeView);

        mCodeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                setLineNumber();
            }
        });

        if (getIntent().hasExtra(IntentKey.FILE_PATH.getKey())) {
            filePath = getIntent().getStringExtra(IntentKey.FILE_PATH.getKey());
        }
        if (getIntent().hasExtra(IntentKey.USER_CODE.getKey())) {
            mCodeView.setText(getIntent()
                    .getStringExtra(IntentKey.USER_CODE.getKey()));
            setLineNumber();
        }
        if (getIntent().hasExtra(IntentKey.EDITOR_TITLE.getKey())) {
            String receivedTitle = getIntent().getStringExtra(IntentKey.EDITOR_TITLE.getKey());
            getSupportActionBar().setTitle(receivedTitle);
        } else {
            getSupportActionBar().setTitle("Untitled");
        }

        ivErrorMsg.setOnClickListener(v-> rlErrorMsg.setVisibility(View.GONE));

        findViewById(R.id.rl_paste).setOnClickListener(view -> pasteFromClipboard());
        findViewById(R.id.rl_undo).setOnClickListener(view -> {
            if (undoRedoHelper.getCanUndo()) undoRedoHelper.undo();
        });
        findViewById(R.id.rl_redo).setOnClickListener(view -> {
            if (undoRedoHelper.getCanRedo()) undoRedoHelper.redo();
        });

        String fontSize = preferenceManager.getEditorFontSize();
        mCodeView.setTextSize(Float.parseFloat(fontSize));
        tvLineNum.setTextSize(Float.parseFloat(fontSize));
    }

    private void setLineNumber() {
        mCodeView.post(new Runnable() {
            @Override
            public void run() {
                int lines = mCodeView.getLineCount();
                Log.d("ASDSFSDDFG", "setLineNumber: "+mCodeView.getLineCount());
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
            case R.id.compile:
                if (mCodeView.getText().toString().trim().isEmpty())
                    Toast.makeText(this,"Write some code please",Toast.LENGTH_SHORT).show();
                else emulateCode();
                return true;
            case R.id.save:
                if (filePath!=null)
                    if (FileManager.overwriteFile(filePath,
                            mCodeView.getText().toString())) {
                        finish();
                    } else ErrorUtils.genericError(this);
                else getFileNameAndSave();
                return true;
            case R.id.help:
                startActivity(new Intent(this,HelpActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pasteFromClipboard() {
        String textToPaste = null;
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            ClipData clip = clipboard.getPrimaryClip();
            textToPaste = clip.getItemAt(0).coerceToText(this).toString();
        }
        if (!TextUtils.isEmpty(textToPaste))
            mCodeView.setText(textToPaste);
    }

    private void emulateCode() {
        String[] lines = mCodeView.getText().toString().split("\\r?\\n");
        String[] finalLines = new String[lines.length+1];
        finalLines[0]="";
        System.arraycopy(lines, 0, finalLines, 1, lines.length + 1 - 1);

        List<SyntaxSlip> syntaxSlips = UIHandler.setCode(finalLines);

        if (syntaxSlips.isEmpty()) {
            tvErrorMsg.setText("");
            rlErrorMsg.setVisibility(View.GONE);
            Intent intent = new Intent(this, EmulateActivity.class);
            intent.putExtra("MyCode",finalLines);
            startActivity(intent);
        } else {
            tvErrorMsg.setText("");
            rlErrorMsg.setVisibility(View.VISIBLE);
            for (SyntaxSlip syntaxSlip: syntaxSlips) {
                tvErrorMsg.append("Line "+syntaxSlip.lineNumber()+": "+syntaxSlip.mistake()+"\n\n");
            }
        }
    }

    private void getFileNameAndSave() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_save_file,null);
        view.setClipToOutline(true);
        TextView btnSave = view.findViewById(R.id.btn_save);
        TextView btnCancel = view.findViewById(R.id.btn_cancel);
        TextView tvFilePath = view.findViewById(R.id.tv_file_path);
        LinearLayout llErrorMessage = view.findViewById(R.id.ll_error_message);
        EditText etFileName = view.findViewById(R.id.et_file_name);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        etFileName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() &&
                        !charSequence.toString().contains("/")) {
                    llErrorMessage.setVisibility(View.GONE);
                    tvFilePath.setText(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                            .toString()+File.separator+charSequence.toString()+".txt");
                }
                else  {
                    llErrorMessage.setVisibility(View.VISIBLE);
                    tvFilePath.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        btnSave.setOnClickListener(v -> {
            String fileName = etFileName.getText().toString();
            String contents = mCodeView.getText().toString();
            if (!fileName.isEmpty() && !fileName.contains("/")) {
                llErrorMessage.setVisibility(View.GONE);
                if (FileManager.createFile(
                        fileName+".txt",contents)) {
                    dialog.dismiss();
                    finish();
                }
            }
            else  llErrorMessage.setVisibility(View.VISIBLE);
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}