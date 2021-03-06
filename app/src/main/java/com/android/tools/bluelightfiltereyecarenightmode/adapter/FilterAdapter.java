package com.android.tools.bluelightfiltereyecarenightmode.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.tools.bluelightfiltereyecarenightmode.Fragment.FilterFragment;
import com.android.tools.bluelightfiltereyecarenightmode.ServiceBackground;
import com.android.tools.bluelightfiltereyecarenightmode.activity.MainActivity;
import com.android.tools.bluelightfiltereyecarenightmode.interfaces.ISelectColor;
import com.android.tools.bluelightfiltereyecarenightmode.R;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolderFilter> {
    private Context context;
    private String colorName ="";
    private int layout;
    private List<Integer> list;
    private ISelectColor iSelectColor;
    public FilterAdapter(Context context, int layout, List<Integer> list,ISelectColor iSelectColor){
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.iSelectColor = iSelectColor;
    }

     class ViewHolderFilter extends RecyclerView.ViewHolder {
        ImageView imgFilter;
         ViewHolderFilter(View itemView) {
            super(itemView);
            imgFilter = itemView.findViewById(R.id.imgFilter);
        }
    }
    @Override
    public ViewHolderFilter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new ViewHolderFilter(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderFilter holder, final int position) {

        holder.imgFilter.setImageResource(list.get(position));

        holder.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iSelectColor.selectColor(position);
                switch (position){
                    case 0:
                        colorName = context.getResources().getString(R.string.anh_sang_mat_trang);
                        break;
                    case 1:
                        colorName = context.getResources().getString(R.string.anh_sang_den_nen);
                        break;
                    case 2:
                        colorName = context.getResources().getString(R.string.anh_sang_binh_minh);
                        break;
                    case 3:
                        colorName = context.getResources().getString(R.string.anh_sang_den_soi_dot);
                        break;
                    case 4:
                        colorName = context.getResources().getString(R.string.anh_sang_den_huynh_quang);
                        break;
                    case 5:
                        colorName = context.getResources().getString(R.string.anh_sang_trong_rung);
                        break;
                    case 6:
                        colorName = context.getResources().getString(R.string.anh_sang_mat_troi);
                        break;
                }
                MainActivity.swService.setChecked(true);
                MainActivity.checkService = true;
                FilterFragment.check_On_Off  =true;
                Intent intent;
                intent = new Intent(context, ServiceBackground.class);
                intent.putExtra("ON", "ON");
                context.startService(intent);

                Toast.makeText(context,context.getResources().getString(R.string.toast_mausac)+" "+colorName,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
