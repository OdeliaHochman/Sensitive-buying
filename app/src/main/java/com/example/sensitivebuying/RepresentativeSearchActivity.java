package com.example.sensitivebuying;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeSearchActivity extends AppCompatActivity implements View.OnClickListener  {

 private SearchView mySearchView;
 private RecyclerView mRecycler;
 private FloatingActionButton floatingButton;
 private List<Product> productList;


    final String activity = "RepresentativeSearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("debug",activity);
        setContentView(R.layout.activity_representative_search);

        mySearchView = (SearchView)findViewById(R.id.searchLine);

        mRecycler = (RecyclerView)findViewById(R.id.recyclerView_products);

        floatingButton = (FloatingActionButton)findViewById(R.id.floating_button_search);
        floatingButton.setOnClickListener(this);

        new FirebaseProductsHelper().readProducts(new FirebaseProductsHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Product> list, List<String> keys) {
               findViewById(R.id.progressBar).setVisibility(View.GONE);

                       productList=list;

                new RecyclerView_config().setConfig(mRecycler,RepresentativeSearchActivity.this,productList,keys);

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        if(mySearchView != null){
            mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }




    }

    @Override
    public void onClick(View v) {

        if(v == floatingButton)
        {
            Intent intent = new Intent(RepresentativeSearchActivity.this,RepresentativeAddProductActivity.class);
            startActivity(intent);
        }
    }

    private void search (String str) {

        ArrayList<Product>searchList = new ArrayList<>();
        ArrayList<String> searchKeys = new ArrayList<>();

        for ( Product p : productList) {
            if (p.getProductName().toLowerCase().contains(str) || p.getBarcode().contains(str))  {
                searchList.add(p);
                searchKeys.add(p.getBarcode());
            }
        }

        new RecyclerView_config().setConfig(mRecycler,RepresentativeSearchActivity.this,searchList,searchKeys);


    }
}
