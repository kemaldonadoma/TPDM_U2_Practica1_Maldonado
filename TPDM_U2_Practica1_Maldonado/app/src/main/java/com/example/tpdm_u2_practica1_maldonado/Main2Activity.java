package com.example.tpdm_u2_practica1_maldonado;

import android.app.DatePickerDialog;
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

public class Main2Activity extends AppCompatActivity {
    EditText ubicacion,descripcion,presupuesto,fecha;
    Button regresar,insertar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ubicacion=findViewById(R.id.ubicacionP);
        descripcion=findViewById(R.id.descripcionP);
        fecha=findViewById(R.id.fechaP);
        presupuesto=findViewById(R.id.presupuestoP);
        insertar=findViewById(R.id.insertarP);
        regresar=findViewById(R.id.regresarP);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarPro();
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
                        Main2Activity.this,
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

    private void insertarPro() {
        Proyectos proyecto = new Proyectos(this);
        String mensaje;
        boolean respuesta = proyecto.insertar(new Proyectos(-1,descripcion.getText().toString(),
                ubicacion.getText().toString(),fecha.getText().toString(),Float.parseFloat(presupuesto.getText().toString())));
        if(respuesta){
            mensaje = "se pudo insertar el proyecto "+descripcion.getText().toString();
        }else{
            mensaje = "Error! no se pudo crear proyecto, respuesta de SQLITE: "+proyecto.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }
}
