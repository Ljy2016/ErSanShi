package com.perfectljy.ersanshi.Widget;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.perfectljy.ersanshi.R;
import com.perfectljy.ersanshi.db.model.SafeModel;

public class SafeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView safeTV1;
    private TextView safeTV2;
    private EditText safeET1;
    private EditText safeET2;
    private Button safeBT;
    private String password;
    private String question;
    private String answer;
    private static int isQusettion = 0;
    private SafeModel safeModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        init();
        safeModel = new SafeModel();
        safeBT.setOnClickListener(this);
    }

    private void init() {
        safeTV1 = (TextView) findViewById(R.id.safeTextView1);
        safeTV2 = (TextView) findViewById(R.id.safeTextView2);
        safeET1 = (EditText) findViewById(R.id.safeEditText);
        safeET2 = (EditText) findViewById(R.id.safeEditText2);
        safeBT = (Button) findViewById(R.id.safeButton);
    }

    @Override
    public void onClick(View v) {

        switch (isQusettion) {

            case 0:
                if (checkIsRight() != null) {
                    password = checkIsRight();
                    isQusettion = 1;
                    safeTV1.setText("请输入密保问题");
                    safeTV2.setText("请输入答案");
                    safeET1.setText("");
                    safeET2.setText("");
                    safeET1.setInputType(InputType.TYPE_CLASS_TEXT);
                    safeET2.setInputType(InputType.TYPE_CLASS_TEXT);
                    safeBT.setText("完成设置");
                } else {
                    Toast.makeText(this, "密码为空或两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                question = (String) safeET1.getText().toString();
                answer = (String) safeET2.getText().toString();
                if (!answer.equals("") && !question.equals("")) {
                    safeModel.setPassword(password);
                    safeModel.setQuestion(question);
                    safeModel.setAnswer(answer);
                    new AlertDialog.Builder(this).setTitle("请确认密保问题").setMessage("问题 :" + question + "\n" + "答案 :" + answer).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentResolver contentResolver = getContentResolver();
                            contentResolver.insert(safeModel.getContentUri(), safeModel.values());
                            startActivity(new Intent(SafeActivity.this, MainActivity.class));
                            SafeActivity.this.finish();
                        }
                    }).setNegativeButton("取消", null).show();

                } else {
                    Toast.makeText(this, "请输入密保问题与答案", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private String checkIsRight() {
        String password1 = safeET1.getText().toString();
        String password2 = safeET2.getText().toString();
        Log.d("memeda", "checkIsRight: " + password1 + "------" + password2);
        if (!password1.equals("") && password1.equals(password2)) {
            return password1;
        } else
            return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isQusettion = 0;

    }
}
