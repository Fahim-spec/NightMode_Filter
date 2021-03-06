package com.android.tools.bluelightfiltereyecarenightmode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.dao.ScheduleDAO;
import com.android.tools.bluelightfiltereyecarenightmode.interfaces.ISelectColor;

import java.util.List;

public class DialogSelectColorAdapter extends RecyclerView.Adapter<DialogSelectColorAdapter.ViewHolder> {

    private Context context;
    private int layout;
    private List<Integer> listImage;
    private int id;
    ScheduleDAO scheduleDAO;
    ISelectColor iSelectColor;

    DialogSelectColorAdapter(Context context, int layout, List<Integer> listImage, int id,ISelectColor iSelectColor) {
        this.context = context;
        this.layout = layout;
        this.listImage = listImage;
        this.id = id;
        this.iSelectColor = iSelectColor;

        scheduleDAO = new ScheduleDAO(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFilter;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFilter = itemView.findViewById(R.id.imgFilter);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.imgFilter.setImageResource(listImage.get(position));
        final String[] arrColor = {"b67b44", "f04715", "ff6d32", "f67c0b", "518848", "f9af09", "c5ffff"};
        viewHolder.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSelectColor.selectColor(position);
                boolean kt = scheduleDAO.updateColor(arrColor[position], id);
                if (kt) {
                    Toast.makeText(context, context.getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.that_bai), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }


}
