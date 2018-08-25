package com.games.user.agendaimss;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    Button all, n;
    Contact data;
    private final String CARPETA_RAIZ = "misImagenesPrueba/";
    private final String RUTA_IMAGEN = CARPETA_RAIZ + "PasesIMSS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        n = (Button) findViewById(R.id.new_element);
        data = new Contact(this);
        data.open();

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, New.class);
                startActivity(i);
            }
        });
        final List<Contact> values = data.getAll();


        // ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);
        //ListView listView = getListView();
        //Drawable res = getResources().getDrawable(R.drawable.btnagregar);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int ancho = metrics.widthPixels / (100) * 28;
        int anchobtn = metrics.widthPixels / (100) * 28;

        ArrayList<Category> category = new ArrayList<Category>();
        for (int i = 0; i < values.size(); i++) {

            String paths = Environment.getExternalStorageDirectory() +
                    File.separator + RUTA_IMAGEN + File.separator + 0 + values.get(i).fecha + values.get(i).radibuttonnid + ".jpg";

            Bitmap bitmap = BitmapFactory.decodeFile(paths);
            category.add(new Category("olo" + values.get(i).id, "Servicio 1", values.get(i).fecha + '\n'  + values.get(i).radibuttonnid, bitmap));
        }
        ListView listView = findViewById(android.R.id.list);
        AdapterCategory adapter = new AdapterCategory(this, category);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(MainActivity.this, Edit.class);
                i.putExtra("id", values.get(position).id);
                i.putExtra("name", values.get(position).fecha);
                i.putExtra("lastname", values.get(position).radibuttonnid);
                i.putExtra("address", values.get(position).horas);
                i.putExtra("email", values.get(position).motivo);
                startActivity(i);
                fileList();

            }

        });

    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();

        //Refresh your stuff here
        // final List<Contact> values = data.getAll();
        // ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK  && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MenuPrincipal.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}