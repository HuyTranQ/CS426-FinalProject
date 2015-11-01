package com.example.huytr.finalproject20;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by huytr on 24-08-2015.
 */
public class DialogDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static TextView dateView;
    static ActivityTransactionAdd.Date date;

    public static DialogDate CreateDateDialog(TextView textView,
                                              ActivityTransactionAdd.Date tempDate)
    {
        DialogDate dialogDate = new DialogDate();
        dateView = textView;
        date = tempDate;
        return dialogDate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
                getActivity() ,
                this ,
                calendar.get(Calendar.YEAR) ,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date.day = dayOfMonth;
        date.month = monthOfYear;
        date.year = year;
        dateView.setText(date.day + "-" + (1 + date.month) + "-" + date.year);
    }
}
