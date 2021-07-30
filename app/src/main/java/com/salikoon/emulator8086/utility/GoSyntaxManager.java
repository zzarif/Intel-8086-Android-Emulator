package com.salikoon.emulator8086.utility;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;
import com.salikoon.emulator8086.R;

import java.util.regex.Pattern;

public class GoSyntaxManager {

        //Language Keywords
        private static final Pattern PATTERN_KEYWORDS =
                Pattern.compile("\\b(mov|MOV|add|ADD|sub|SUB|mul|MUL|div|DIV|inc|INC|" +
                        "dec|DEC|xor|XOR|not|NOT|end|END|ret|RET|main|MAIN|endp|ENDP|int|INT|cmp|CMP|" +
                        "jz|JZ|jg|JG|jl|JL|jb|JB|jnz|JNZ|jge|JGE|jle|JLE|jc|JC)\\b");

        private static final Pattern PATTERN_KEYWORDS_2 =
            Pattern.compile("\\b(sum|SUM|difference|DIFFERENCE|multiplication|MULTIPLICATION|" +
                    "division|DIVISION|db|DB|dw|DW|org|ORG|\\.data|\\.DATA|\\.code|\\.CODE)\\b");


        //Data
        private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
        private static final Pattern PATTERN_LABEL = Pattern.compile("\\b([a-zA-Z0-9_]+:)\\b");
        private static final Pattern PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'");
        private static final Pattern PATTERN_HEX = Pattern.compile("(0x[0-9a-fA-F]+|[0-9a-fA-F]+[hH])");
        private static final Pattern PATTERN_BIN = Pattern.compile("([01]+b)");
        private static final Pattern PATTERN_COMMENT = Pattern.compile(";.*");

        public static void applyMonokaiTheme(Context context, CodeView codeView) {
            codeView.resetSyntaxPatternList();

            //Syntax Colors
            codeView.addSyntaxPattern
                    (PATTERN_HEX, context.getResources().getColor(R.color.hex_0));
            codeView.addSyntaxPattern
                    (PATTERN_BIN, context.getResources().getColor(R.color.bin_0));
            codeView.addSyntaxPattern
                    (PATTERN_CHAR, context.getResources().getColor(R.color.char_0));
            codeView.addSyntaxPattern
                    (PATTERN_NUMBERS, context.getResources().getColor(R.color.hex_0));
            codeView.addSyntaxPattern
                    (PATTERN_KEYWORDS, context.getResources().getColor(R.color.keyword_0_extra));
            codeView.addSyntaxPattern
                    (PATTERN_KEYWORDS_2, context.getResources().getColor(R.color.keyword_2));
            codeView.addSyntaxPattern
                    (PATTERN_LABEL, context.getResources().getColor(R.color.label_0));
            codeView.addSyntaxPattern
                    (PATTERN_COMMENT, context.getResources().getColor(R.color.gray_4));
            //Default Color
            codeView.setTextColor( context.getResources().getColor(R.color.black));

            codeView.reHighlightSyntax();
        }

}
