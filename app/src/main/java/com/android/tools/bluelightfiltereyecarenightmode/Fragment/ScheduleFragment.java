package com.android.tools.bluelightfiltereyecarenightmode.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.adapter.ScheduleAdapter;
import com.android.tools.bluelightfiltereyecarenightmode.dao.ScheduleDAO;
import com.android.tools.bluelightfiltereyecarenightmode.interfaces.IUpdateSchedule;
import com.android.tools.bluelightfiltereyecarenightmode.model.ScheduleModel;

import java.util.Calendar;
import java.util.List;

public class ScheduleFragment extends Fragment implements View.OnClickListener, IUpdateSchedule {
    RecyclerView recyclerViewSchedule;

    private ScheduleAdapter adapter;
    private TextView txtStart, txtEnd, txtHuy, txtDongY;
    private Dialog dialogSetTime;
    private CheckBox cbSchedule;
    private List<ScheduleModel> scheduleModelList;
    private TimePicker timePicker;
    private Calendar calendar;
    private ScheduleModel scheduleModel;
    private ScheduleDAO scheduleDAO;
    private String endTime, startTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerViewSchedule = view.findViewById(R.id.recyclerViewSchedule);
        txtStart = view.findViewById(R.id.txtStart);
        txtEnd = view.findViewById(R.id.txtEnd);
        cbSchedule = view.findViewById(R.id.cbSchedule);

        scheduleModel = new ScheduleModel();
        scheduleDAO = new ScheduleDAO(getActivity());
        updateAdapter();

        cbSchedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startTime = txtStart.getText().toString();
                    endTime = txtEnd.getText().toString();
                    scheduleModel.setPercent(50);
                    scheduleModel.setColor("b67b44");
                    scheduleModel.setTimeEnd(endTime);
                    scheduleModel.setTimeStart(startTime);
                    scheduleDAO.addSchedule(scheduleModel);

                    List<ScheduleModel> list = scheduleDAO.getAll();
                    ScheduleModel model = list.get(list.size() - 1);
                    String[] arr = model.getTimeStart().split(":");
                    int gio = Integer.parseInt(arr[0]);
                    int phut = Integer.parseInt(arr[1]);

                  //  AlarmUtils.addAlarm(getContext(), gio, phut, model.getId(), "f9af09", 50);

                    updateAdapter();

                }
            }
        });
        txtEnd.setOnClickListener(this);
        txtStart.setOnClickListener(this);
        calendar = Calendar.getInstance();

        return view;
    }

    private void updateAdapter() {
        scheduleModelList = scheduleDAO.getAll();
        adapter = new ScheduleAdapter(getActivity(), R.layout.item_schedule, scheduleModelList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSchedule.setLayoutManager(layoutManager);
        recyclerViewSchedule.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtStart:
                setTimeDialog(txtStart);
                break;
            case R.id.txtEnd:
                setTimeDialog(txtEnd);
                break;
            case R.id.txtHuy:
                dialogSetTime.cancel();
                break;
        }
    }

    private void setTimeDialog(final TextView txt) {
        dialogSetTime = new Dialog(getActivity());
        dialogSetTime.setContentView(R.layout.dialog_settime);
        txtHuy = dialogSetTime.findViewById(R.id.txtHuy);
        txtDongY = dialogSetTime.findViewById(R.id.txtDongY);
        timePicker = dialogSetTime.findViewById(R.id.dateTime);
        txtDongY.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                txt.setText(gio + ":" + phut);
                dialogSetTime.cancel();
            }
        });
        dialogSetTime.show();
    }

    @Override
    public void update(List<ScheduleModel> list) {
        adapter = new ScheduleAdapter(getActivity(), R.layout.item_schedule, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSchedule.setLayoutManager(layoutManager);
        recyclerViewSchedule.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
