package com.perfectljy.ersanshi.Widget;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.perfectljy.ersanshi.Base.BaseObserverFragment;
import com.perfectljy.ersanshi.Event.NotifyInfo;
import com.perfectljy.ersanshi.Event.helper.KeepNotifyCenterHelper;
import com.perfectljy.ersanshi.R;
import com.perfectljy.ersanshi.db.model.RecordModel;

import java.util.Calendar;

import static com.perfectljy.ersanshi.R.id.alarm_date_tv;
import static com.perfectljy.ersanshi.R.id.title;

/**
 * Created by PerfectLjy on 2016/3/17.
 */

public class RecordFragment extends BaseObserverFragment implements DateFragment.OnTimeSetListener {
    //判断是添加记录还是删除记录
    private static int ISUPDATA = 0;
    private Button addRecord;
    private EditText title;
    private TextView date;
    private EditText weather;
    private EditText content;
    private RecordModel recordModel;
    private long mAlarmsTime = 0;

    public RecordFragment() {
    }

    public RecordFragment(RecordModel recordModel) {
        this.recordModel = recordModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onChange(NotifyInfo notifyInfo) {

    }

    @Override
    protected String[] getObserverEventType() {
        return new String[0];
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.layout_add_record;
    }

    @Override
    public void findView(View view) {
        addRecord = (Button) view.findViewById(R.id.save_record);
        title = (EditText) view.findViewById(R.id.record_title_et);
        date = (TextView) view.findViewById(alarm_date_tv);
        weather = (EditText) view.findViewById(R.id.add_weather_et);
        content = (EditText) view.findViewById(R.id.record_content_et);


    }

    @Override
    public void initView() {
        //判空  如果为空则说明fragment由无参构造方法创建  即由添加记录的按钮触发的事件 反之则为recyclerview 的item点击事件
        if (recordModel != null) {
            title.setText(recordModel.getTitle());
            date.setText(recordModel.getDate());
            weather.setText(recordModel.getWeather());
            content.setText(recordModel.getContent());
            addRecord.setText("保存编辑");
            ISUPDATA = 1;
        } else {
            recordModel = new RecordModel();
            ISUPDATA = 0;
        }
    }

    @Override
    public void initDate() {

    }


    @Override
    public void setOnClick() {
        addRecord.setOnClickListener(this);
        date.setOnClickListener(this);


    }

    @Override
    public void initSvgView() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.save_record:
                //如果是添加事件
                if (ISUPDATA == 0) {
                    setModel();
                    ContentResolver resolver = this.getActivity().getContentResolver();
                    resolver.insert(recordModel.getContentUri(), recordModel.values());
                    notifyRecordChange();
                    getActivity().onBackPressed();
                    break;
                } else {
                    //如果记录被修改
                    if (chackIsUpdata()) {
                        setModel();
                        ContentResolver resolver = this.getActivity().getContentResolver();
                        resolver.update(recordModel.getContentUri(), recordModel.values(), "_id=?", new String[]{recordModel.getId()});
                        notifyRecordChange();
                    }
                    getActivity().onBackPressed();
                    break;
                }
            case R.id.alarm_date_tv:
                showDateDialog();
                break;
        }
    }

    //判断记录是否被修改
    private boolean chackIsUpdata() {
        String mTitle = title.getText().toString();
        String mData = date.getText().toString();
        String mWeather = weather.getText().toString();
        String mContent = content.getText().toString();
        if (mTitle.equals(recordModel.getTitle()) && mData.equals(recordModel.getDate()) && mWeather.equals(recordModel.getWeather()) && mContent.equals(recordModel.getContent())) {
            return false;
        }
        return true;


    }

    private void notifyRecordChange() {
        KeepNotifyCenterHelper.getInstance().notifyRefreshRecord();
    }

    private void setModel() {
        String rTitle = title.getText().toString();
        String rDate = date.getText().toString();
        String rWeather = weather.getText().toString();
        String rContent = content.getText().toString();
        recordModel.setTitle(rTitle);
        recordModel.setDate(rDate);
        recordModel.setWeather(rWeather);
        recordModel.setContent(rContent);
    }

    private void showDateDialog() {
        DateFragment dateFragment = new DateFragment();
        dateFragment.setOnTimeSetListener(this);
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onInitDateTimeSet(DatePicker datePicker, TimePicker timePicker) {
        if (mAlarmsTime != 0) {
            Calendar dateTime = Calendar.getInstance();
            dateTime.setTimeInMillis(mAlarmsTime);
            int year = dateTime.get(Calendar.YEAR);
            int month = dateTime.get(Calendar.MONTH);
            int day = dateTime.get(Calendar.DAY_OF_MONTH);
            int hourOfDay = dateTime.get(Calendar.HOUR_OF_DAY);
            int minute = dateTime.get(Calendar.MINUTE);
            if (datePicker != null && timePicker != null) {
                datePicker.updateDate(year, month, day);
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(hourOfDay);
                timePicker.setCurrentMinute(minute);
            }
        }
    }

    @Override
    public void onDateTimeSet(int year, int month, int day, int hourOfDay, int minute) {
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month, day, hourOfDay, hourOfDay, minute);
        mAlarmsTime = dateTime.getTimeInMillis();
        date.setText(year + "年" + (month + 1) + "月" + day + "日" + " " + hourOfDay + ":" + minute);
    }
}
