package com.arstotzka.tristen.vakantietrackernederland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//Made by Tristen
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, VakantieTaskListener{
ListView hoofdList;
ArrayAdapter adapter;
ArrayList<VakantieItem> vakanties;
VakantieItem vakantieItem;
    ArrayList<Tijdvak> tijden;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vakanties = new ArrayList<VakantieItem>();

        this.hoofdList = (ListView) findViewById(R.id.hoofdList);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        tijden = new ArrayList();

        adapter= new hoofdAdapter(this,vakanties);
        hoofdList.setAdapter(adapter);
        hoofdList.setOnItemClickListener(this);
        fetch();
    }

    public void fetch(){
        SchoolVakantieTask fetcher = new SchoolVakantieTask(this);
        String[] urls = new String[]
                {"https://opendata.rijksoverheid.nl/v1/sources/rijksoverheid/infotypes/schoolholidays/schoolyear/2016-2017?output=json"};
        fetcher.execute(urls);
    }

    @Override
    public void onVakantieItemAvailable(VakantieItem item){
        vakanties.add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterview, View view, int i, long l){
        VakantieItem item = vakanties.get(i);
        Intent intent = new Intent(getApplicationContext(),VakantieDetail.class);
        intent.putExtra("VAKANTIE_ITEM", item);
        startActivity(intent);
    }
}
