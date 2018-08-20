package com.games.user.agendaimss;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    EditText  address,phone,email,lastname;
    RadioButton entrada, salida, intermedio;
    TextView  name;
    ImageView imagen;
    String path,radiobtn;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        upd_el = (Button) findViewById(R.id.upd_element);
        del_btn = (Button) findViewById(R.id.del_btn);
        name = (TextView) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        imagen = (ImageView) findViewById(R.id.imagemId);
        entrada = (RadioButton) findViewById(R.id.entrada);
        salida = (RadioButton) findViewById(R.id.salida);
        intermedio = (RadioButton) findViewById(R.id.intermedio);

        Intent i = getIntent();
        id = i.getLongExtra("id", 0);
        name.setText(i.getStringExtra("name"));

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
        address.setText(i.getStringExtra("address"));
        phone.setText(i.getStringExtra("phone"));

        String paths = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + 0 + name.getText().toString() + ".jpg";
        // Toast.makeText(this, paths, Toast.LENGTH_LONG).show();
        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(paths));
            imagen.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.getCause();
            //handle exception
        }

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        // imagen.setImageBitmap(bitmap);
        imagen.setImageURI(Uri.parse(paths));

        upd_el.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() > 0 && phone.getText().toString().length() > 0) {

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
                    c.updateContact(id, name.getText().toString(), radiobtn, address.getText().toString(), phone.getText().toString(), "phone");
                    address.setText("");
                    phone.setText("");
                    Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();
                    Intent intentds = new Intent(Edit.this,MainActivity.class);
                    startActivity(intentds);
                    finish();

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    imagen.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento", "Path: " + path);
                                }
                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);

                    break;
            }


        }
    }
}

