package alei.switchpro.task;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class RepeatPreference extends ListPreference {

    // Initial value that can be set with the values saved in the database.
    private Task.DaysOfWeek mDaysOfWeek = new Task.DaysOfWeek(0);
    // New value that will be set if a positive result comes back from the
    // dialog.
    private Task.DaysOfWeek mNewDaysOfWeek = new Task.DaysOfWeek(0);

    public RepeatPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        String[] weekdays = new DateFormatSymbols().getWeekdays();
        String[] values = new String[]{weekdays[Calendar.MONDAY], weekdays[Calendar.TUESDAY],
                weekdays[Calendar.WEDNESDAY], weekdays[Calendar.THURSDAY], weekdays[Calendar.FRIDAY],
                weekdays[Calendar.SATURDAY], weekdays[Calendar.SUNDAY],};
        setEntries(values);
        setEntryValues(values);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            mDaysOfWeek.set(mNewDaysOfWeek);
            setSummary(mDaysOfWeek.toString(getContext(), true));
        }
    }

    @Override
    protected void onPrepareDialogBuilder(Builder builder) {
        CharSequence[] entries = getEntries();

        builder.setMultiChoiceItems(entries, mDaysOfWeek.getBooleanArray(),
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        mNewDaysOfWeek.set(which, isChecked);
                    }
                });
    }

    public Task.DaysOfWeek getDaysOfWeek() {
        return mDaysOfWeek;
    }

    public void setDaysOfWeek(Task.DaysOfWeek dow) {
        mDaysOfWeek.set(dow);
        mNewDaysOfWeek.set(dow);
        setSummary(dow.toString(getContext(), true));
    }
}
