package com.example.tpdm_u2_practica1_maldonado;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {
    Button buscar,regresa,eliminar;
    EditText busc;
    TextView descr,ubic,fecha,presupu;
    int id;
    Proyectos vector[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        buscar=findViewById(R.id.consultar);
        regresa =findViewById(R.id.reg);


        busc=findViewById(R.id.bus);


        descr=findViewById(R.id.desc);
        ubic =findViewById(R.id.ubi);
        fecha=findViewById(R.id.fech);
        presupu=findViewById(R.id.pres);



        regresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busqueda();
            }
        });
    }

    private void busqueda() {
        Proyectos pryecto = new Proyectos(this);
        vector = pryecto.consultar();
        String mensaje;
        if(!(vector==null)){
        descr.setText("Descripcion: "+vector[0].getDescr());
        ubic.setText("Ubicacion: "+vector[0].getUbicacion());
        fecha.setText("Fecha: "+vector[0].getFecha());
        presupu.setText("Presupuesto: "+vector[0].getPresupuesto());



        }else{
            mensaje = "No se encontro algun registro con ese id o descripcion";
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("ATENCION").setMessage(mensaje)
                    .setPositiveButton("ok",null).show();
            descr.setText("Descripcion: ");
            ubic.setText("Ubicacion: ");
            fecha.setText("Fecha: ");
            presupu.setText("Presupuesto: ");
        }
    }


}
