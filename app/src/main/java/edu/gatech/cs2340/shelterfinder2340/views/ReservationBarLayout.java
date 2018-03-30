package edu.gatech.cs2340.shelterfinder2340.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.Room;


/**
 * Created by Sylvia Li on 2018/3/25.
 */

public class ReservationBarLayout extends LinearLayout {
    private TextView typeView;
    private NumberPicker picker;

    public int getSelectedRoom() {
        return selectedRoom;
    }

    public String getType() {
        return typeView.getText().toString();
    }

    private int selectedRoom;

    public ReservationBarLayout(Context context, int cap, String type) {
        super(context);
        initView(context, cap, type);
    }

    public ReservationBarLayout(Context context, Room room) {
        super(context);
        initView(context, room.getNumVacancies(), room.getRoomType());
    }

    public ReservationBarLayout(Context context, AttributeSet attrs, int cap, String type) {
        super(context, attrs);
        initView(context, cap, type);
    }

    public ReservationBarLayout(Context context, AttributeSet attrs, int defStyle, int cap, String type) {
        super(context, attrs, defStyle);
        initView(context, cap, type);
    }

    private void initView(Context context, int capacity, String type) {
        LayoutInflater.from(context).inflate(R.layout.reservation_bar, this, true);
        typeView = findViewById(R.id.type);
        typeView.setText(type);
        picker = findViewById(R.id.number_picker);
        picker.setMaxValue(capacity);
        picker.setMinValue(0);
        picker.setValue(0);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                selectedRoom = i1;
            }
        });
    }

    @Override
    public String toString() {
        return selectedRoom + " " + getType() + " room(s)";
    }
}
