package com.games.user.agendaimss;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Edit extends AppCompatActivity {
    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 0;
    private final String CARPETA_RAIZ = "misImagenesPrueba/";
    private final String RUTA_IMAGEN = CARPETA_RAIZ + "PasesIMSS";
    Button upd_el, del_btn;
    EditText horas, motivo;
    RadioButton entrada, salida, intermedio;
    TextView fecha;
    ImageView imagen;
    String paths, radiobtn, name, Fecha;
    long id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        upd_el = findViewById(R.id.upd_element);
        del_btn = findViewById(R.id.del_btn);
        fecha = findViewById(R.id.txvEfecha);
        horas = findViewById(R.id.address);
        motivo = findViewById(R.id.motivo);
        imagen = findViewById(R.id.imagenId);
        entrada = findViewById(R.id.entrada);
        salida = findViewById(R.id.salida);
        intermedio = findViewById(R.id.intermedio);

        Intent i = getIntent();
        id = i.getLongExtra("id", 0);
        Fecha = i.getStringExtra("name");

        //name.setText(Fecha);
        fecha.setText(Fecha.charAt(0) + "" + Fecha.charAt(1) + "/" + Fecha.charAt(2) + "" + Fecha.charAt(3) + "/" +
                Fecha.charAt(4) + "" + Fecha.charAt(5) + Fecha.charAt(6) + Fecha.charAt(7));

        switch (i.getStringExtra("lastname")) {
            case "ENTRADA":
                entrada.setChecked(true);
                break;
            case "SALIDA":
                salida.setChecked(true);
                break;
            case "INTERMEDIO":
                intermedio.setChecked(true);
                break;
        }
        horas.setText(i.getStringExtra("address"));
        motivo.setText(i.getStringExtra("email"));


        String paths = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + 0 + Fecha + i.getStringExtra("email") + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(paths);
        imagen.setImageBitmap(bitmap);

        upd_el.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fecha.getText().toString().length() > 0) {

                    if (entrada.isChecked()) {
                        radiobtn = "ENTRADA";
                    }
                    if (salida.isChecked()) {
                        radiobtn = "SALIDA";
                    }
                    if (intermedio.isChecked()) {
                        radiobtn = "INTERMEDIO";
                    }
                    Contact c = new Contact(getBaseContext());
                    c.open();
                    c.updateContact(id, Fecha, radiobtn, horas.getText().toString(), motivo.getText().toString());
                    horas.setText("");
                    Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();
                   onBackPressed();

                } else {
                    Toast.makeText(getBaseContext(), "Error!!", Toast.LENGTH_LONG).show();
                }
            }
        });


        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);

                builder.setTitle(" - Confirmar - ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Contact c = new Contact(getBaseContext());
                        c.open();
                        c.deleteContact(id);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado !!", Toast.LENGTH_LONG).show();

                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


}

