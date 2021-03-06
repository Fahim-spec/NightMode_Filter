package com.android.tools.bluelightfiltereyecarenightmode.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.adapter.ShortcutAdapter;
import com.android.tools.bluelightfiltereyecarenightmode.model.ShortcutModel;

import java.util.ArrayList;
import java.util.List;

public class ShortcutFragment extends Fragment {
    RecyclerView recyclerViewShortcut;
    List<ShortcutModel> shortcutModelList;
    ShortcutAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shortcut,container,false);
        recyclerViewShortcut = view.findViewById(R.id.recyclerViewShortcut);
        addList();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new ShortcutAdapter(getContext(),R.layout.item_shortcut,shortcutModelList);
        recyclerViewShortcut.setAdapter(adapter);
        recyclerViewShortcut.setLayoutManager(layoutManager);
        return view;
    }

    private void addList() {
        shortcutModelList = new ArrayList<>();
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_enable_filter,getResources().getString(R.string.enable_filter)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_disable_filter,getResources().getString(R.string.vo_hieu_bo_loc)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_enable_disable_filter,getResources().getString(R.string.kich_hoat_vo_hieu)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.muoi_lam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.hai_muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.hai_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.ba_muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.ba_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.bon_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.bon_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.nam_muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.nam_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.sau_muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.sau_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.bay_muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.bay_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.tam_muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.tam_nam)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.chin_muoi)));
        shortcutModelList.add(new ShortcutModel(R.drawable.ic_filer_custom,getResources().getString(R.string.chin_nam)));
    }
}
