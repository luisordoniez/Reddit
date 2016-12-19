package com.reddit.luisordoniez.reddit.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.reddit.luisordoniez.reddit.R;
import com.reddit.luisordoniez.reddit.activities.DetailTema;
import com.reddit.luisordoniez.reddit.models.Child;
import com.reddit.luisordoniez.reddit.models.Data;
import com.reddit.luisordoniez.reddit.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by luisordoniez on 13/12/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TemaViewHolder> {

    /**
     * Atributos
     */

    private List<Child> temas;
    private Context contex;
    private Boolean online;

    private static final int WITH_BANNER = 0;
    private static final int WITHOUT_BANNER = 1;

    @Override
    public TemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        contex = parent.getContext();

        if (viewType == WITH_BANNER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_with_banner, parent, false);
            return new WhithBanner(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_without_banner, parent, false);
            return new WhithoutBanner(v);
        }
    }

    @Override
    public void onBindViewHolder(TemaViewHolder viewHolder, final int position) {
        Data temp = temas.get(position).getData();

        Date created = new Date(temp.getCreated());
        Date now = new Date();
        long diff = now.getTime() - created.getTime();
        float horas = diff /(1000 * 60 * 60);
        float dias = horas/24;
        float meses = dias /30;
        float anios = meses/12;

        if (viewHolder.getItemViewType() == WITH_BANNER) {
            WhithBanner holder = (WhithBanner) viewHolder;
            if (temp.getBanner_img() != null)
                if (!temp.getBanner_img().isEmpty())
                    if (online)
                        Picasso.with(contex).load(temp.getBanner_img()).error(R.drawable.ic_no_image_gray).fit().into(holder.banner_img);
                    else
                        Picasso.with(contex).load(new File(temp.getBanner_img())).error(R.drawable.ic_no_image_gray).fit().into(holder.banner_img);

            ViewGroup.LayoutParams params = holder.banner_img.getLayoutParams();

            if (temp.getBanner_size() != null)
                params.height = temp.getBanner_size().get(1);

            holder.banner_img.setLayoutParams(params);

            holder.title.setText(temp.getTitle());
            holder.type.setText(temp.getUrl());

            holder.time.setText(new DecimalFormat("#.#").format(anios) + Constants.ANIOS);
            holder.public_description.setText(temp.getPublic_description());
        } else {
            WhithoutBanner holder = (WhithoutBanner) viewHolder;
            if (temp.getHeader_img() != null) {
                if (!temp.getHeader_img().isEmpty()) {
                    if (online)
                        Picasso.with(contex).load(temp.getHeader_img()).error(R.drawable.ic_no_image_gray).fit().into(holder.header_img);
                    else
                        Picasso.with(contex).load(new File(temp.getHeader_img())).error(R.drawable.ic_no_image_gray).fit().into(holder.header_img);

                } else
                    holder.header_img.setVisibility(View.GONE);
            } else
                holder.header_img.setVisibility(View.GONE);


            ViewGroup.LayoutParams params = holder.header_img.getLayoutParams();

            if (temp.getBanner_size() != null) {
                params.width = temp.getHeader_size().get(0);
                params.height = temp.getHeader_size().get(1);
            }

            holder.header_img.setLayoutParams(params);

            holder.title.setText(temp.getTitle());
            holder.type.setText(temp.getUrl());

            holder.time.setText(new DecimalFormat("#.#").format(anios)+ " años");
            holder.public_description.setText(temp.getPublic_description());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return temas.size();
    }

    /**
     *  constructor parametrizado
     *
     *  @param temas lista de items que van a mostrar
     *  @param online bandera que indica el tipo conexion
     */

    public RecyclerViewAdapter(List<Child> temas, boolean online) {
        this.temas = temas;
        this.online = online;
    }

    /**
     *  método que indica el tipo de item segun la posicion
     *
     *  @param position posicion del item al que se le quiere saber su tipo
     *  @return entereo que contiene el tipo de item
     */

    @Override
    public int getItemViewType(int position) {
        if (temas.get(position).getData().getBanner_img() != null) {
            if (temas.get(position).getData().getBanner_img().equals(""))
                return WITHOUT_BANNER;
            else
                return WITH_BANNER;
        } else
            return WITHOUT_BANNER;
    }


    /**
     * Clase Auxiliar estatica para inflar direrentes .xml "vistas" segun el tipo de item
     */

    public static class TemaViewHolder extends RecyclerView.ViewHolder {
        public TemaViewHolder(View v) {
            super(v);
        }
    }

    public class WhithBanner extends TemaViewHolder implements View.OnClickListener {

        /**
         * Atributos
         */

        public CardView cv;
        public ImageView banner_img;
        public TextView title;
        public TextView type;
        public TextView time;
        public TextView public_description;
        public RelativeLayout share;

        public WhithBanner(View v) {
            super(v);

            cv = (CardView) v.findViewById(R.id.cv);
            banner_img = (ImageView) v.findViewById(R.id.banner_img);
            title = (TextView) v.findViewById(R.id.title);
            type = (TextView) v.findViewById(R.id.type);
            time = (TextView) v.findViewById(R.id.time);
            public_description = (TextView) v.findViewById(R.id.public_description);
            share = (RelativeLayout) v.findViewById(R.id.share);

            cv.setOnClickListener(this);
            share.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int possition = getLayoutPosition();

            if (v.equals(share)) {

                /**
                 * codigo para compartir informacion
                 **/

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = temas.get(possition).getData().getUrl() + "," + temas.get(possition).getData().getTitle();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Reddit");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                contex.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
            if (v.equals(cv)) {

                /**
                 * codigo para pasar al activity de detalles
                 **/

                Intent myIntent = new Intent();
                myIntent.setClass(contex, DetailTema.class);
                Bundle myBundle = new Bundle();
                myBundle.putSerializable("data", temas.get(possition).getData());
                myBundle.putBoolean("online", online);
                myIntent.putExtras(myBundle);
                contex.startActivity(myIntent);
            }
        }
    }

    public class WhithoutBanner extends TemaViewHolder implements View.OnClickListener {

        /**
         * Atributos
         */

        public CardView cv;
        public ImageView header_img;
        public TextView title;
        public TextView type;
        public TextView time;
        public TextView public_description;
        public RelativeLayout share;

        public WhithoutBanner(View v) {
            super(v);

            cv = (CardView) v.findViewById(R.id.cv);
            header_img = (ImageView) v.findViewById(R.id.header_img);
            title = (TextView) v.findViewById(R.id.title);
            type = (TextView) v.findViewById(R.id.type);
            time = (TextView) v.findViewById(R.id.time);
            public_description = (TextView) v.findViewById(R.id.public_description);
            share = (RelativeLayout) v.findViewById(R.id.share);

            cv.setOnClickListener(this);
            share.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int possition = getLayoutPosition();

            if (v.equals(share)) {

                /**
                 * codigo para compartir informacion
                 **/

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = temas.get(possition).getData().getUrl() + "," + temas.get(possition).getData().getTitle();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Reddit");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                contex.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
            if (v.equals(cv)) {

                /**
                 * codigo para pasar al activity de detalles
                 **/

                Intent myIntent = new Intent();
                myIntent.setClass(contex, DetailTema.class);
                Bundle myBundle = new Bundle();
                myBundle.putSerializable("data", temas.get(possition).getData());
                myBundle.putBoolean("online", online);
                myIntent.putExtras(myBundle);
                contex.startActivity(myIntent);
            }
        }
    }

}


