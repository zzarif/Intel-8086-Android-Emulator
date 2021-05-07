package com.salikoon.emulator8086.Utility;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;
import com.salikoon.emulator8086.R;

import java.util.regex.Pattern;

public class GoSyntaxManager {

        //Language Keywords
        private static final Pattern PATTERN_KEYWORDS =
                Pattern.compile("\\b(break|default|func|interface|case|defer|" +
                        "go|map|struct|chan|else|goto|package|switch|const" +
                        "|fallthrough|if|range|type|continue|for|import|return|var|" +
                        "string|true|false|new|nil|byte|bool|int|int8|int16|int32|int64)\\b");

        //Brackets and Colons
        private static final Pattern PATTERN_BUILTINS = Pattern.compile("[,:;[->]{}()]");

        //Data
        private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
        private static final Pattern PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'");
        private static final Pattern PATTERN_STRING = Pattern.compile("\".*\"");
        private static final Pattern PATTERN_HEX = Pattern.compile("0x[0-9a-fA-F]+");
        private static final Pattern PATTERN_TODO_COMMENT = Pattern.compile("//TODO[^\n]*");
        private static final Pattern PATTERN_COMMENT =
                Pattern.compile("//(?!TODO )[^\\n]*" + "|" + "/\\*(.|\\R)*?\\*/");
        private static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\.[a-zA-Z0-9_]+");
        private static final Pattern PATTERN_OPERATION =
                Pattern.compile( ":|==|>|<|!=|>=|<=|->|=|>|<|%|-|-=|%=|\\+|\\-|\\-=|\\+=|\\^|\\&|\\|::|\\?|\\*");

        public static void applyMonokaiTheme(Context context, CodeView codeView) {
            codeView.resetSyntaxPatternList();
            //View Background
            codeView.setBackgroundColor
                    (codeView.getResources().getColor(R.color.darkGray));

            //Syntax Colors
            codeView.addSyntaxPattern
                    (PATTERN_HEX, context.getResources().getColor(R.color.monokai_purple));
            codeView.addSyntaxPattern
                    (PATTERN_CHAR, context.getResources().getColor(R.color.monokai_green));
            codeView.addSyntaxPattern
                    (PATTERN_STRING, context.getResources().getColor(R.color.monokai_orange));
            codeView.addSyntaxPattern
                    (PATTERN_NUMBERS, context.getResources().getColor(R.color.monokai_purple));
            codeView.addSyntaxPattern
                    (PATTERN_KEYWORDS, context.getResources().getColor(R.color.monokai_pink));
            codeView.addSyntaxPattern
                    (PATTERN_BUILTINS, context.getResources().getColor(R.color.white));
            codeView.addSyntaxPattern
                    (PATTERN_COMMENT, context.getResources().getColor(R.color.monokai_gray));
            codeView.addSyntaxPattern
                    (PATTERN_ATTRIBUTE, context.getResources().getColor(R.color.monokai_blue));
            codeView.addSyntaxPattern
                    (PATTERN_OPERATION, context.getResources().getColor(R.color.monokai_pink));
            //Default Color
            codeView.setTextColor( context.getResources().getColor(R.color.white));

            codeView.addSyntaxPattern
                    (PATTERN_TODO_COMMENT, context.getResources().getColor(R.color.gold));

            codeView.reHighlightSyntax();
        }

}
