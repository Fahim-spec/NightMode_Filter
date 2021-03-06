package com.android.tools.bluelightfiltereyecarenightmode.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.ServiceBackground;
import com.android.tools.bluelightfiltereyecarenightmode.activity.MainActivity;
import com.android.tools.bluelightfiltereyecarenightmode.adapter.FilterAdapter;
import com.android.tools.bluelightfiltereyecarenightmode.dao.ScheduleDAO;
import com.android.tools.bluelightfiltereyecarenightmode.dao.StatusDAO;
import com.android.tools.bluelightfiltereyecarenightmode.interfaces.ISelectColor;
import com.android.tools.bluelightfiltereyecarenightmode.model.ModelCustom;
import com.android.tools.bluelightfiltereyecarenightmode.model.ScheduleModel;
import com.android.tools.bluelightfiltereyecarenightmode.model.StatusModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilterFragment extends Fragment implements ISelectColor, View.OnClickListener {

    FilterAdapter filterAdapter;
    List<Integer> listImage;
    RecyclerView recyclerViewFilter;
    TextView txtCuongDoLoc;
    CheckBox cbNightMode;
    Button btnMo, btnTat, btnLichTrinh;
    public static final int SYSTEM_ALERT_WINDOW_PERMISSION = 1010;
    int posColor;
    Intent intent;
    ScheduleDAO scheduleDAO;
    SeekBar skCuongDoLoc;
    public static String[] arrCuongDo =
            {"#00", "#03", "#05", "#08", "#0A", "#0D", "#0F", "#12", "#14", "#17", "#1A",
                    "#1C", "#1F", "#21", "#24", "#26", "#29", "#2B", "#2E", "#30", "#33",
                    "#36", "#38", "#3B", "#3D", "#40", "#42", "#45", "#47", "#4A", "#4D",
                    "#4F", "#52", "#54", "#57", "#59", "#5C", "#5E", "#61", "#63", "#66",
                    "#69", "#6B", "#6E", "#70", "#73", "#75", "#78", "#7A", "#7D", "#80",
                    "#82", "#85", "#87", "#8A", "#8C", "#8F", "#91", "#94", "#96", "#99",
                    "#9C", "#9E", "#A1", "#A3", "#A6", "#A8", "#AB", "#AD", "#B0", "#B3",
                    "#B5", "#B8", "#BA", "#BD", "#BF", "#C2", "#C4", "#C7", "#C9", "#CC",
                    "#CF", "#D1", "#D4", "#D6", "#D9", "#DB", "#DE", "#E0", "#E3", "#E6",
                    "#E8", "#EB", "#ED", "#F0", "#F2"};

    String[] arrColor = {"b67b44", "f04715", "ff6d32", "f67c0b", "518848", "f9af09", "c5ffff"};
    StatusDAO statusDAO;
    List<ScheduleModel> scheduleModelList;

    List<StatusModel> statusModelList;
    public static boolean check_On_Off = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        recyclerViewFilter = view.findViewById(R.id.recyclerViewFilter);
        txtCuongDoLoc = view.findViewById(R.id.txtCuongDoLoc);
        skCuongDoLoc = view.findViewById(R.id.skCuongDoLoc);
        btnTat = view.findViewById(R.id.btnTat);
        btnMo = view.findViewById(R.id.btnMo);
        btnLichTrinh = view.findViewById(R.id.btnLichTrinh);
        cbNightMode = view.findViewById(R.id.cbNightMode);
        scheduleDAO = new ScheduleDAO(getActivity());
        runService();
        addList();
        addEvent();
        cbNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    skCuongDoLoc.setMax(95);
                } else {
                    skCuongDoLoc.setMax(80);
                }
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        filterAdapter = new FilterAdapter(getActivity(), R.layout.item_filter, listImage, this);
        recyclerViewFilter.setAdapter(filterAdapter);
        recyclerViewFilter.setLayoutManager(layoutManager);
        return view;
    }

    private void runService() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            intent = new Intent(getContext(), ServiceBackground.class);
            intent.putExtra("ON", "ON");
            getActivity().startService(intent);
            //ContextCompat.startForegroundService(getActivity(), intent);

        } else if (!Settings.canDrawOverlays(getActivity())) {
            askPermission();
            //ContextCompat.startForegroundService(getContext(),intent);
        } else {

            intent = new Intent(getContext(), ServiceBackground.class);
            intent.putExtra("ON", "ON");
            getActivity().startService(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SYSTEM_ALERT_WINDOW_PERMISSION) {
            intent = new Intent(getContext(), ServiceBackground.class);
            intent.putExtra("ON", "ON");
            getActivity().startService(intent);
           // ContextCompat.startForegroundService(getContext(),intent);
        }
    }

    StatusModel status;

    private void addEvent() {
        skCuongDoLoc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtCuongDoLoc.setText(i + "%");
                MainActivity.swService.setChecked(true);
                MainActivity.checkService = true;
              //  ModelCustom.getInstance().changeSpeed(arrCuongDo[i], arrColor[posColor]);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                status = new StatusModel();
                status.setId(statusModelList.get(0).getId());
                status.setPercent(seekBar.getProgress());
                status.setIntensity(arrCuongDo[seekBar.getProgress()]);
                status.setColor(arrColor[posColor]);
                statusDAO.updateStatus(status);

                intent = new Intent(getContext(), ServiceBackground.class);
                intent.putExtra("ON", "ON");
                getActivity().startService(intent);
                //ContextCompat.startForegroundService(getContext(),intent);
            }
        });


        btnLichTrinh.setOnClickListener(this);
        btnMo.setOnClickListener(this);
        btnTat.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void addList() {
        listImage = new ArrayList<>();
        listImage.add(R.drawable.ic_moonlight_normal);
        listImage.add(R.drawable.ic_candle_selected);
        listImage.add(R.drawable.ic_dawn_normal);
        listImage.add(R.drawable.ic_light_bulb_normal);
        listImage.add(R.drawable.ic_forest_normal);
        listImage.add(R.drawable.ic_fluorecent_lamp_selected);
        listImage.add(R.drawable.ic_sunny_selected);
        statusDAO = new StatusDAO(getActivity());
        statusModelList = statusDAO.getStatus();

        txtCuongDoLoc.setText(statusModelList.get(0).getPercent() + "%");
        skCuongDoLoc.setProgress(statusModelList.get(0).getPercent());
        if (statusModelList.get(0).getPercent() > 80) {
            cbNightMode.setChecked(true);
            skCuongDoLoc.setMax(95);
        } else {
            cbNightMode.setChecked(false);
            skCuongDoLoc.setMax(80);
        }
    }

    @Override
    public void selectColor(int position) {
        posColor = position;
        ModelCustom.getInstance().changeSpeed(arrCuongDo[position], arrColor[position]);
        skCuongDoLoc.setProgress(ScheduleDAO.DEFAULT_PROGESS);


        status = new StatusModel();
        status.setId(statusModelList.get(0).getId());
        status.setPercent(ScheduleDAO.DEFAULT_PROGESS);
        status.setIntensity(arrCuongDo[ScheduleDAO.DEFAULT_PROGESS]);
        status.setColor(arrColor[position]);
        boolean kt = statusDAO.updateStatus(status);

        getActivity().startService(new Intent(getContext(), ServiceBackground.class));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMo:
                Toast.makeText(getActivity(), getContext().getResources().getString(R.string.toas_on), Toast.LENGTH_SHORT).show();
                check_On_Off = true;
                MainActivity.swService.setChecked(true);
                MainActivity.checkService = true;

                // Start service
                intent = new Intent(getContext(), ServiceBackground.class);
                intent.putExtra("ON", "ON");
                //getActivity().startService(intent);
                ContextCompat.startForegroundService(getContext(),intent);

                break;
            case R.id.btnTat:
                MainActivity.swService.setChecked(false);
                MainActivity.checkService = false;

                if (check_On_Off) {
                    Toast.makeText(getActivity(), getContext().getResources().getString(R.string.toas_off), Toast.LENGTH_SHORT).show();
                    check_On_Off = false;
                }
                // Stop service
                intent = new Intent(getContext(), ServiceBackground.class);
                getActivity().stopService(intent);
                break;
            case R.id.btnLichTrinh:
                // Start service
                scheduleModelList = new ArrayList<>();

                scheduleModelList = scheduleDAO.getAll();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

                String gioHienTai = dateFormat.format(calendar.getTime());


                for (ScheduleModel value : scheduleModelList) {
                    String gioBatDau = value.getTimeStart();
                    String gioKetThuc = value.getTimeEnd();

                    try {
                        Date dateHienTai = dateFormat.parse(gioHienTai);
                        Date dateBatDau = dateFormat.parse(gioBatDau);
                        Date dateKetThuc = dateFormat.parse(gioKetThuc);


                        // Kiểm tra thời gian hiện tại nằm có nằm trong khoảng bắt đầu và kết thúc k
                        if (dateHienTai.after(dateBatDau) && dateHienTai.before(dateKetThuc)) {
                            String persent = arrCuongDo[value.getPercent()];
                            String background = persent + value.getColor();
                            intent = new Intent(getContext(), ServiceBackground.class);
                            intent.putExtra("ON", "Off");
                            intent.putExtra("persent",value.getPercent());
                            intent.putExtra("background", background);

                           // getActivity().startService(intent);
                            ContextCompat.startForegroundService(getContext(),intent);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
