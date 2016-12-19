package com.reddit.luisordoniez.reddit.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reddit.luisordoniez.reddit.R;
import com.reddit.luisordoniez.reddit.models.Data;
import com.reddit.luisordoniez.reddit.utils.Constants;
import com.reddit.luisordoniez.reddit.utils.GlobalClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.util.Date;

public class DetailTema extends AppCompatActivity {

    /**
     * Atributos
     */

    Data data = new Data();
    Boolean online;

    private static final int WITH_BANNER = 0;
    private static final int WITHOUT_BANNER =1;

    TextView Info_conexion;
    GlobalClass globalClass = new GlobalClass();

    /* elemeto s utlilizados cartview de cabecera*/

    public ImageView banner_img;
    public ImageView header_img;
    public TextView title;
    public TextView type;
    public TextView time;
    public TextView public_description;
    public RelativeLayout share;

    /* elemeto s utlilizados cartview de more info*/

    public TextView text_key;
    public TextView text_susc;
    public TextView text_lan;
    public TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tema);

        /** se recupea la informacion que se paso por medio del bundle**/

        Bundle myBundle = getIntent().getExtras();
        globalClass = (GlobalClass) this.getApplicationContext();
        data = (Data) myBundle.getSerializable("data");
        online = myBundle.getBoolean("online");

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            // Habilitar el Up Button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Cambiar icono del Up Button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
            actionBar.setTitle(Constants.DETAIL);
        }

        View withbanner = findViewById(R.id.withbanner);
        View withoutbanner = findViewById(R.id.withoutbanner);

        if (getType()==WITH_BANNER) {
            withoutbanner.setVisibility(View.GONE);
            viewWithBanner(withbanner);

            if (data.getBanner_img()!=null)
                if (!data.getBanner_img().isEmpty())
                    if (online)
                        Picasso.with(this).load(data.getBanner_img()).error(R.drawable.ic_no_image_gray).fit().into(banner_img);
                    else
                        Picasso.with(this).load(new File(data.getBanner_img())).error(R.drawable.ic_no_image_gray).fit().into(banner_img);

            ViewGroup.LayoutParams params = banner_img.getLayoutParams();

            if (data.getBanner_size()!= null)
                params.height = data.getBanner_size().get(1);

            banner_img.setLayoutParams(params);

        }
        else
        {
            withbanner.setVisibility(View.GONE);
            viewWithoutBanner(withoutbanner);

            if (data.getHeader_img()!=null){
                if (!data.getHeader_img().isEmpty()) {
                    if (online)
                        Picasso.with(this).load(data.getHeader_img()).error(R.drawable.ic_no_image_gray).fit().into(header_img);
                    else
                        Picasso.with(this).load(new File(data.getHeader_img())).error(R.drawable.ic_no_image_gray).fit().into(header_img);
                }
                else
                    header_img.setVisibility(View.GONE);
            }
            else
                header_img.setVisibility(View.GONE);


            ViewGroup.LayoutParams params = header_img.getLayoutParams();

            if (data.getBanner_size()!= null) {
                params.width = data.getHeader_size().get(0);
                params.height = data.getHeader_size().get(1);
            }
            header_img.setLayoutParams(params);
        }

        title.setText(data.getTitle());
        type.setText(data.getUrl());

        Date created = new Date(data.getCreated_utc());
        Date now = new Date();
        long diff =  (now.getTime()- created.getTime())/(1000*60*60);

        time.setText(diff+" h");
        public_description.setText(data.getPublic_description());

        text_key   =(TextView) findViewById(R.id.text_key);
        text_susc  =(TextView) findViewById(R.id.text_susc);
        text_lan   =(TextView) findViewById(R.id.text_lan);
        description=(TextView) findViewById(R.id.description);

        text_key.setText(data.getId()+"");
        text_susc.setText(data.getSubscribers()+"");
        text_lan.setText(data.getLang());
        Spanned sp = Html.fromHtml( data.getDescription() );
        description.setText(sp);

        Info_conexion = (TextView) findViewById(R.id.info_conexion);

        if (!online) {
            Info_conexion.setVisibility(View.VISIBLE);
            Info_conexion.setText(Constants.SIN_CONEXION+ globalClass.getFechaBachup());
        }
        else
            Info_conexion.setVisibility(View.GONE);


    }

    /**
     * método para referenciar elementos de un xml
     * @param v xml que contiene los elementos
     **/

    private void viewWithBanner(View v) {
        banner_img = (ImageView)v.findViewById(R.id.banner_img);
        title = (TextView)v.findViewById(R.id.title);
        type =  (TextView)v.findViewById(R.id.type);
        time =  (TextView)v.findViewById(R.id.time);
        public_description =  (TextView)v.findViewById(R.id.public_description);
        share = (RelativeLayout)v.findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    /**
     * método para referenciar elementos de un xml
     * @param v xml que contiene los elementos
     **/

    private void viewWithoutBanner(View v) {
        header_img = (ImageView)v.findViewById(R.id.header_img);
        title = (TextView)v.findViewById(R.id.title);
        type =  (TextView)v.findViewById(R.id.type);
        time =  (TextView)v.findViewById(R.id.time);
        public_description =  (TextView)v.findViewById(R.id.public_description);
        share = (RelativeLayout)v.findViewById(R.id.share);
    }

    /**
     * método para compartir informacion
     **/

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =data.getUrl()+","+data.getTitle() ;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Reddit");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        this.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *  método que indica el tipo de item se esta mostrando
     *
     *  @return entereo que contiene el tipo de item
     */

    public int getType() {
        if (data.getBanner_img() != null) {
            if (data.getBanner_img().equals(""))
                return WITHOUT_BANNER;
            else
                return WITH_BANNER;
        }else
            return WITHOUT_BANNER;
    }

}
