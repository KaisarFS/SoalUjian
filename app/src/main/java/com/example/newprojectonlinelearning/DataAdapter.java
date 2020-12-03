package com.example.newprojectonlinelearning;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DatakuViewHolder> {
    private ArrayList<Model> dataList;
    private Callback callback;
    View viewku;
    int posku;

    interface Callback {
        void onClick(int position);
        void test();
    }


    public DataAdapter(ArrayList<Model> dataList, Callback callback) {
        this.callback = callback;
        this.dataList = dataList;
        Log.d("makanan", "MahasiswaAdapter: "+dataList.size()+"");
    }

    @Override
    public DatakuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_list, parent, false);
        return new DatakuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DatakuViewHolder holder, final int position) {
        holder.strTeam.setText(dataList.get(position).getStrTeam());
        holder.strGender.setText(dataList.get(position).getStrGender());
        holder.strCountry.setText(dataList.get(position).getStrCountry());
        holder.IntFormedYear.setText(dataList.get(position).getIntFormedYear());
        Log.d("makananku", "onBindViewHolder: "+dataList.get(position).getStrTeamBadge());
        //pakai glide karena untuk nampilkan data gambar dari URL / permission / graddle
        Glide.with(holder.itemView)
                .load(dataList.get(position).getStrTeamBadge())
                //.override(Target.SIZE_ORIGINAL)
                .apply(new RequestOptions().override(600, 200))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivprofile);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DatakuViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView strTeam, strGender, strCountry, IntFormedYear;
        CardView card;
        ImageView ivprofile;

        public DatakuViewHolder(View itemView) {
            super(itemView);
            viewku=itemView;
            card = (CardView) itemView.findViewById(R.id.cardData);
            ivprofile = (ImageView) itemView.findViewById(R.id.adapterProfile);
            strTeam = (TextView) itemView.findViewById(R.id.strTeam);
            strGender = (TextView) itemView.findViewById(R.id.strGender);
            strCountry = (TextView) itemView.findViewById(R.id.strCountry);
            IntFormedYear = (TextView) itemView.findViewById(R.id.IntFormedYear);

            itemView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
//            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
//            posku=getAdapterPosition();
//            Edit.setOnMenuItemClickListener(onEditMenu);
//            Delete.setOnMenuItemClickListener(onEditMenu);
        }

    }
    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case 1:
                    //Do stuff
                    Toast.makeText(viewku.getContext(), ""+posku, Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    //Do stuff

                    break;
            }
            return true;
        }
    };

}
