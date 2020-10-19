package com.example.calculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Button> buttonList = new ArrayList<>();
        // 数字ボタンを取得
        buttonList.add((Button) findViewById(R.id.bt0));
        buttonList.add((Button) findViewById(R.id.bt1));
        buttonList.add((Button) findViewById(R.id.bt2));
        buttonList.add((Button) findViewById(R.id.bt3));
        buttonList.add((Button) findViewById(R.id.bt4));
        buttonList.add((Button) findViewById(R.id.bt5));
        buttonList.add((Button) findViewById(R.id.bt6));
        buttonList.add((Button) findViewById(R.id.bt7));
        buttonList.add((Button) findViewById(R.id.bt8));
        buttonList.add((Button) findViewById(R.id.bt9));
        // 記号ボタンを取得
        buttonList.add((Button) findViewById(R.id.btPlus));
        buttonList.add((Button) findViewById(R.id.btSubtract));
        buttonList.add((Button) findViewById(R.id.btMultiply));
        buttonList.add((Button) findViewById(R.id.btDivide));
        buttonList.add((Button) findViewById(R.id.btEqual));
        // その他ボタンを取得
        buttonList.add((Button) findViewById(R.id.btClear));
        buttonList.add((Button) findViewById(R.id.btDelete));
        buttonList.add((Button) findViewById(R.id.btPoint));
        buttonList.add((Button) findViewById(R.id.btPlusMinus));
        buttonList.add((Button) findViewById(R.id.btPercent));

        ButtonListener listener = new ButtonListener();

        for(Button button : buttonList){
            button.setOnClickListener(listener);
        }
    }
    private class ButtonListener implements View.OnClickListener {

        private List<BigDecimal> _numList = new ArrayList<>();
        private List<Character> _opeList = new ArrayList<>();
        private String _inputValue = "";
        private boolean _isPoint = true;
        @Override
        public void onClick(View view) {
            TextView Text = findViewById(R.id.Text);
            TextView sumText = findViewById(R.id.sumText);
            // ボタン毎の処理を定義
            int btId = view.getId();
            char inputChar;
            switch (btId) {
                // 数字ボタンの場合
                case R.id.bt0:
                    inputChar = '0';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt1:
                    inputChar = '1';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt2:
                    inputChar = '2';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt3:
                    inputChar = '3';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt4:
                    inputChar = '4';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt5:
                    inputChar = '5';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt6:
                    inputChar = '6';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt7:
                    inputChar = '7';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt8:
                    inputChar = '8';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                case R.id.bt9:
                    inputChar = '9';
                    addTextView(Text, inputChar);
                    _inputValue += inputChar;
                    break;
                // 記号ボタンの場合
                case R.id.btPlus:
                    inputChar = '+';
                    if(!(_inputValue.equals(""))) {
                        addList(Text, _inputValue, inputChar);
                        _isPoint = true;
                    }
                    break;
                case R.id.btSubtract:
                    inputChar = '-';
                    if(!(_inputValue.equals(""))) {
                        addList(Text, _inputValue, inputChar);
                        _isPoint = true;
                    }
                    break;
                case R.id.btMultiply:
                    inputChar = '×';
                    if(!(_inputValue.equals(""))) {
                        addList(Text, _inputValue, inputChar);
                        _isPoint = true;
                    }
                    break;
                case R.id.btDivide:
                    inputChar = '÷';
                    if(!(_inputValue.equals(""))) {
                        addList(Text, _inputValue, inputChar);
                        _isPoint = true;
                    }
                    break;
                case R.id.btEqual:
                    inputChar = '=';
                    String str = Text.getText().toString();
                    String pattern = "[0-9]$";
                    Pattern p = Pattern.compile(pattern);
                    if(!(_inputValue.equals(""))) {
                        addList(Text, _inputValue, inputChar);
                    }
                    if(p.matcher(str).find()) {
                        String result = calculate().toString();
                        sumText.setText(result);
                        _inputValue = result;
                        _numList.clear();
                        _opeList.clear();
                    }
                    break;
                // その他ボタンの場合の処理
                case R.id.btClear:
                    Text.setText("");
                    sumText.setText("");
                    _numList.clear();
                    _opeList.clear();
                    _inputValue= "";
                    _isPoint = true;
                    break;
                case R.id.btDelete:
                    String formulaStr = Text.getText().toString();
                    if(!TextUtils.isEmpty(formulaStr)) {
                        char formulaStrLastChar = formulaStr.charAt(formulaStr.length() - 1);

                        if (isFourArithmeticOpe(formulaStrLastChar)) {
                            _opeList.remove(_opeList.size() - 1);
                            _isPoint = false;
                        }
                        if (!formulaStr.isEmpty()) {
                            Text.setText(formulaStr.subSequence(0, formulaStr.length() - 1));
                        }
                        if (!_inputValue.isEmpty()) {
                            _inputValue = _inputValue.substring(0, _inputValue.length() - 1);
                        }
                    }
                    break;
                case R.id.btPoint:
                    inputChar = '.';
                    if(!(_inputValue.equals(""))) {
                        if(!(_inputValue.endsWith(".")) && _isPoint) {
                            addTextView(Text, inputChar);
                            _inputValue += inputChar;
                            _isPoint = false;
                        }
                    }
                    break;
            }
        }

        private void addList(TextView Text, String inputValue, char ope) {
            if (ope != '=') {
                addTextView(Text, ope);
            }
                _numList.add(new BigDecimal(inputValue));
                _opeList.add(ope);
                _inputValue = "";

        }

        private void addTextView(TextView textView, char addStr) {
            textView.setText(textView.getText().toString() + addStr);
        }

        private BigDecimal calculate() {
            int i = 0;

            while(i < _opeList.size()) {
                if(_opeList.get(i) == '×' | _opeList.get(i) == '÷') {
                    BigDecimal resultMultiDiv = _opeList.get(i) == '×' ? _numList.get(i).multiply(_numList.get(i+1)) : _numList.get(i).divide(_numList.get(i+1));

                    _numList.set(i, resultMultiDiv);
                    _numList.remove(i+1);
                    _opeList.remove(i);
                    i--;
                }
                else if(_opeList.get(i) == '-') {
                    _opeList.set(i, '+');
                    _numList.set(i+1, _numList.get(i+1).negate());
                }
                i++;
            }

            BigDecimal result = new BigDecimal("0");
            for(BigDecimal j : _numList) {
                result = result.add(j);
            }

            return result;
        }

        private boolean isFourArithmeticOpe(char c) {
            if(c == '+' | c == '-' | c == '*' | c == '/') return true;
            return false;
        }
    }
}
