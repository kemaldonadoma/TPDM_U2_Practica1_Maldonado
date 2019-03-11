package com.example.tpdm_u2_practica1_maldonado;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Main3Activity extends AppCompatActivity {
    EditText ubicacion,descripcion,presupuesto,fecha;
    Button regresar,actualizar,eliminar;
    int id;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ubicacion=findViewById(R.id.ubicacionP2);
        descripcion=findViewById(R.id.descripcionP2);
        fecha=findViewById(R.id.fechaP2);
        presupuesto=findViewById(R.id.presupuestoP2);
        actualizar=findViewById(R.id.actualizarP2);
        regresar=findViewById(R.id.regresarP2);
        eliminar=findViewById(R.id.elim);
        Bundle parametros = getIntent().getExtras();

        descripcion.setText(parametros.getString("DESCRIPCION"));
        ubicacion.setText(parametros.getString("UBICACION"));
        presupuesto.setText(""+parametros.getFloat("PRESUPUESTO"));

        fecha.setText(parametros.getString("FECHAR"));
        id = parametros.getInt("id");

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPro();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPro();
            }
        });
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Main3Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyy/mm/dd: " + year + "/" + month + "/" + day);

                String date = year + "/" + month + "/" + day;
                fecha.setText(date);
            }
        };

    }

    private void actualizarPro() {
        Proyectos proyecto = new Proyectos(this);
        String mensaje;
        boolean respuesta = proyecto.actualizar(new Proyectos(id,descripcion.getText().toString(),
                ubicacion.getText().toString(),fecha.getText().toString(),Float.parseFloat(presupuesto.getText().toString())));
        if(respuesta){
            mensaje = "se pudo insertar el proyecto "+descripcion.getText().toString();
        }else{
            mensaje = "Error! no se pudo actualizar el proyecto, respuesta de SQLITE: "+proyecto.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }

    private void eliminarPro() {
        Proyectos proyecto = new Proyectos(this);
        String mensaje;
        boolean respuesta = proyecto.eliminar(id+"");
        if(respuesta){
            mensaje = "se pudo eliminar el proyecto ";

        }else{
            mensaje = "Error! no se pudo actualizar el proyecto, respuesta de SQLITE: "+proyecto.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();

    }
}
