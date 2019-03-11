package com.example.tpdm_u2_practica1_maldonado;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Proyectos vector[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.ListaProyectos);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarAlerta(position);
            }
        });
    }

    private void mostrarAlerta(final int pos) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        if (!(vector==null)){
            alerta.setTitle("Attencion")
                    .setMessage("Deseas modifica/editar el proyecto "+vector[pos].getDescr()+"?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            invocarEliminarActualizar(pos);
                        }
                    }).setNegativeButton("NO",null)
                    .show();
        }
    }

    private void invocarEliminarActualizar(int pos) {
        Intent eliminaractualiza = new Intent(this,Main3Activity.class);

        eliminaractualiza.putExtra("id",vector[pos].getId());
        eliminaractualiza.putExtra("DESCRIPCION",vector[pos].getDescr());
        eliminaractualiza.putExtra("UBICACION",vector[pos].getUbicacion());
        eliminaractualiza.putExtra("FECHAR",vector[pos].getFecha());
        eliminaractualiza.putExtra("PRESUPUESTO",vector[pos].getPresupuesto());

        startActivity(eliminaractualiza);
    }

    @Override
    protected void onStart(){
        Proyectos pryecto = new Proyectos(this);
        vector = pryecto.consultar();
        String[] descUbi = null;
        if (vector==null){
            descUbi=new String[1];
            descUbi[0]="no hay proyectos capturados";
        }else{
            descUbi = new String[vector.length] ;
            for (int i=0;i<vector.length;i++){
                Proyectos temp = vector[i];
                descUbi[i]= temp.getDescr()+"\n"+temp.getUbicacion();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,descUbi);

        lista.setAdapter(adaptador);

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.crear :
                Intent nuevoProyecto = new Intent(this,Main2Activity.class);
                startActivity(nuevoProyecto);
                break;
            case R.id.consultar:
                Intent consultarAbogado = new Intent(this,Main4Activity.class);
                startActivity(consultarAbogado);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
