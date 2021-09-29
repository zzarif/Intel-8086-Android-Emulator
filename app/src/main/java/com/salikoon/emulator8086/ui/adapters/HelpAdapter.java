package com.salikoon.emulator8086.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.salikoon.emulator8086.R;
import com.salikoon.emulator8086.ui.models.HelpModel;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {

    private Context context;
    private List<HelpModel> objects;
    private ItemClickListener mClickListener;

    public HelpAdapter(Context context,List<HelpModel> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HelpModel object = objects.get(position);
        holder.tvTitle.setText(object.getTitle());
        holder.tvSyntax.setText(object.getSyntax());
        holder.tvDescription.setText(object.getDescription());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_help, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle,tvSyntax,tvDescription;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSyntax = itemView.findViewById(R.id.tv_syntax);
            tvDescription = itemView.findViewById(R.id.tv_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
