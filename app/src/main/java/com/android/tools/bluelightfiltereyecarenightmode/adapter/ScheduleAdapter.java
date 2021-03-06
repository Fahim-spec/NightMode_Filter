package com.android.tools.bluelightfiltereyecarenightmode.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.activity.UpdateTimeFillterActivity;
import com.android.tools.bluelightfiltereyecarenightmode.ads.Idelegate;
import com.android.tools.bluelightfiltereyecarenightmode.ads.MyAdsController;
import com.android.tools.bluelightfiltereyecarenightmode.dao.ScheduleDAO;
import com.android.tools.bluelightfiltereyecarenightmode.interfaces.ISelectColor;
import com.android.tools.bluelightfiltereyecarenightmode.interfaces.IUpdateSchedule;
import com.android.tools.bluelightfiltereyecarenightmode.model.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewholderSchedule> {

    private Context context;
    private int layout;
    private List<ScheduleModel> list;
    private ScheduleDAO scheduleDAO;
    private IUpdateSchedule iUpdateSchedule;
    private Dialog dialogSelectColor;
    private RecyclerView recyclerSelectColor;
    private List<Integer> listImage;
    private DialogSelectColorAdapter filterAdapter;

    public ScheduleAdapter(Context context, int layout, List<ScheduleModel> list, IUpdateSchedule iUpdate) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        scheduleDAO = new ScheduleDAO(context);
        listImage = new ArrayList<>();
        this.iUpdateSchedule = iUpdate;
        addList();
    }


    class ViewholderSchedule extends RecyclerView.ViewHolder {
        TextView txtPhanTram, txtStart;
        ImageView imgTang, imgGiam, imgThem, imgXoa, imgColor;

        ViewholderSchedule(View itemView) {
            super(itemView);
            txtPhanTram = itemView.findViewById(R.id.txtPhanTram);
            txtStart = itemView.findViewById(R.id.txtStart);
            imgTang = itemView.findViewById(R.id.imgTang);
            imgGiam = itemView.findViewById(R.id.imgGiam);
            imgThem = itemView.findViewById(R.id.imgThem);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            imgColor = itemView.findViewById(R.id.imgColor);
        }
    }

    @Override
    public ViewholderSchedule onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewholderSchedule(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewholderSchedule holder, int position) {
        final ScheduleModel scheduleModel = list.get(position);
        switch (scheduleModel.getColor()) {
            case "b67b44":
                holder.imgColor.setImageResource(listImage.get(0));
                break;
            case "f04715":
                holder.imgColor.setImageResource(listImage.get(1));
                break;
            case "ff6d32":
                holder.imgColor.setImageResource(listImage.get(2));
                break;
            case "f67c0b":
                holder.imgColor.setImageResource(listImage.get(3));
                break;
            case "518848":
                holder.imgColor.setImageResource(listImage.get(4));
                break;
            case "f9af09":
                holder.imgColor.setImageResource(listImage.get(5));
                break;
            case "c5ffff":
                holder.imgColor.setImageResource(listImage.get(6));
                break;

        }


        if (list.size() <= 2) {
            holder.imgXoa.setVisibility(View.GONE);
        } else {
            holder.imgXoa.setVisibility(View.VISIBLE);
        }

        if (scheduleModel.getPercent() < 10) {
            holder.txtPhanTram.setText(context.getResources().getString(R.string.khong) + scheduleModel.getPercent() + context.getResources().getString(R.string.phan_tram));
        } else {
            holder.txtPhanTram.setText(scheduleModel.getPercent() + context.getResources().getString(R.string.phan_tram));
        }
        holder.txtStart.setText(scheduleModel.getTimeStart());



        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean kt = scheduleDAO.deleteSchedule(scheduleModel.getId());
                if (kt) {
                    Toast.makeText(context, context.getResources().getString(R.string.xoa_thanh_cong), Toast.LENGTH_LONG).show();
                    iUpdateSchedule.update(scheduleDAO.getAll());
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.xoa_that_bai), Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.imgTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = scheduleModel.getPercent();
                dem++;
                if (dem >= 80) {
                    dem = 80;
                } else if (dem <= 0) {
                    dem = 0;
                }
                boolean kt = scheduleDAO.updatePercent(scheduleModel.getId(), dem);
                if (kt) {
                    iUpdateSchedule.update(scheduleDAO.getAll());
                }
            }
        });
        holder.imgGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = scheduleModel.getPercent();
                dem--;
                if (dem >= 80) {
                    dem = 80;
                } else if (dem <= 0) {
                    dem = 0;
                }
                boolean kt = scheduleDAO.updatePercent(scheduleModel.getId(), dem);
                if (kt) {
                    iUpdateSchedule.update(scheduleDAO.getAll());
                }
            }
        });
        holder.imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSelectColor = new Dialog(context);
                dialogSelectColor.setContentView(R.layout.dialog_select_color);

                recyclerSelectColor = dialogSelectColor.findViewById(R.id.recyclerSelectColor);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                filterAdapter = new DialogSelectColorAdapter(context, R.layout.item_filter, listImage, scheduleModel.getId(), new ISelectColor() {
                    @Override
                    public void selectColor(int position) {
                        holder.imgColor.setImageResource(listImage.get(position));
                        dialogSelectColor.cancel();
                    }
                });


                recyclerSelectColor.setAdapter(filterAdapter);
                recyclerSelectColor.setLayoutManager(layoutManager);

                dialogSelectColor.show();
            }
        });
        holder.imgThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyAdsController.showAdsFullBeforeDoAction(new Idelegate() {
                    @Override
                    public void callBack(Object value, int where) {
                        String thoiGianBatDau = scheduleModel.getTimeStart();
                        String thoiGianKetThuc = scheduleModel.getTimeEnd();
                        Intent intent = new Intent(context, UpdateTimeFillterActivity.class);
                        intent.putExtra("ID", -1);
                        intent.putExtra("thoiGianBatDau", thoiGianBatDau);
                        intent.putExtra("thoiGianKetThuc", thoiGianKetThuc);
                        context.startActivity(intent);
                    }
                });

            }
        });

        holder.txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyAdsController.showAdsFullBeforeDoAction(new Idelegate() {
                    @Override
                    public void callBack(Object value, int where) {
                        Intent intent = new Intent(context, UpdateTimeFillterActivity.class);
                        intent.putExtra("ID", scheduleModel.getId());
                        context.startActivity(intent);
                    }
                });

            }
        });
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
