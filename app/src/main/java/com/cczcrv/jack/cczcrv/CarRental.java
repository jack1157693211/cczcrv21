package com.cczcrv.jack.cczcrv;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

import static com.cczcrv.jack.cczcrv.Tools.util.DateUtils.dayNames;

/**
 * Created by jack on 2018/2/9.
 */

public class CarRental extends AppCompatActivity {
    ;

    @BindView(R.id.car_rental_start_time)
    TextView mcar_rental_start_time;
    @BindView(R.id.car_rental_start_str)
    TextView mcar_rental_start_str;
    @BindView(R.id.car_rental_end_time)
    TextView mcar_rental_end_time;
    @BindView(R.id.car_rental_end_str)
    TextView mcar_rental_end_str;
    @BindView(R.id.total_day_count)
    TextView mtotal_day_count;

    private int StataStartTimeAndEndTime = 0; //    0未打开时间选择 1开始时间打开  2结束时间打开


    @OnClick(R.id.car_rental_start_time)
    void startTimeClickEvent() {
        StataStartTimeAndEndTime = 1;
        openDateTimePicker();
    }

    @OnClick(R.id.car_rental_end_time)
    void endTimeClickEvent() {
        StataStartTimeAndEndTime = 2;
        openDateTimePicker();
    }

    void openDateTimePicker() {
        final DatePicker picker = new DatePicker(this);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);


        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2100, 1, 1);
        picker.setRangeStart(2018, 1, 1);
        picker.setSelectedItem(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String week = "";
                Calendar cal = Calendar.getInstance();
                String timeStr = "";
                if (StataStartTimeAndEndTime == 1) {
                    timeStr = year + "-" + month + "-" + day;
                    mcar_rental_start_time.setText(timeStr);
                    try {
                        Date date = sdf.parse(timeStr);
                        cal.setTime(date);
                        week = dayNames[cal.get(Calendar.DAY_OF_WEEK)];
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mcar_rental_start_str.setText(week);
                } else if (StataStartTimeAndEndTime == 2) {
                    timeStr = year + "-" + month + "-" + day;
                    mcar_rental_end_time.setText(timeStr);
                    try {
                        Date date = sdf.parse(timeStr);
                        cal.setTime(date);
                        week = dayNames[cal.get(Calendar.DAY_OF_WEEK)];
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mcar_rental_end_str.setText(week);
                }
                try {
                    Date startTime = sdf.parse(mcar_rental_start_time.getText().toString());
                    Date endTime = sdf.parse(mcar_rental_end_time.getText().toString());
                    Long days = (endTime.getTime() - startTime.getTime()) / (60 * 60 * 1000 * 24) + 1;
                    if (days > 0) {
                        mtotal_day_count.setText(days + "天");
                    } else {
                        mtotal_day_count.setText("0天");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        picker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                StataStartTimeAndEndTime = 0;
            }
        });

        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }

        });
        picker.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_rental);
        ButterKnife.bind(this);

    }
}
