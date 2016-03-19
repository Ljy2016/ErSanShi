package com.perfectljy.ersanshi.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.perfectljy.ersanshi.R;

import java.util.jar.Attributes;

/**
 * Created by PerfectLjy on 2016/3/13.
 */
public class MyTextViewDelay extends TextView {
    String text;
    int textIndex = 0;
    String subText;
    ShowTextThread showTextThread;

    public MyTextViewDelay(Context context) {
        super(context);
        text = "";
    }

    public MyTextViewDelay(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myTextView);
        text = typedArray.getString(R.styleable.myTextView_myText);


    }

    public void Begin(int delay) {
        textIndex=0;
        showTextThread = new ShowTextThread(delay);
        showTextThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setText(subText);
        super.onDraw(canvas);
    }

    class ShowTextThread extends Thread {
        int delay;
        public ShowTextThread(int delay){
            this.delay=delay;
        }
        @Override
        public void run() {
            while (textIndex != text.length() + 1) {
                subText = text.substring(0, textIndex);
                postInvalidate();

                textIndex++;
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
