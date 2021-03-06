package com.android.tools.bluelightfiltereyecarenightmode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.model.ShortcutModel;

import java.util.List;

public class ShortcutAdapter extends RecyclerView.Adapter<ShortcutAdapter.ViewHolderShortcut> {
    private Context context;
    private int layout;
    private List<ShortcutModel> shortcutModelList;

    public ShortcutAdapter(Context context, int layout, List<ShortcutModel> shortcutModelList) {
        this.context = context;
        this.layout = layout;
        this.shortcutModelList = shortcutModelList;
    }

    class ViewHolderShortcut extends RecyclerView.ViewHolder {
        ImageView imgShortcut;
        TextView txtShortcut;

        ViewHolderShortcut(View itemView) {
            super(itemView);
            imgShortcut = itemView.findViewById(R.id.imgShortcut);
            txtShortcut = itemView.findViewById(R.id.txtShortcut);
        }
    }

    @Override
    public ViewHolderShortcut onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolderShortcut(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderShortcut holder, int position) {
        ShortcutModel shortcutModel = shortcutModelList.get(position);
        holder.imgShortcut.setImageResource(shortcutModel.getIcon());
        holder.txtShortcut.setText(shortcutModel.getValue());
    }

    @Override
    public int getItemCount() {
        return shortcutModelList.size();
    }


}
