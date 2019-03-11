package com.example.tpdm_u2_practica1_maldonado;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;



public class Proyectos {
    private BaseDatos base;

    private int id;
    private String descr;
    private String ubicacion;
    private String fecha;
    private float presupuesto;
    protected String error;


    public Proyectos(Activity activity) {
        base = new BaseDatos(activity, "buffete", null, 1);
    }

    public Proyectos(int id, String descr, String ubicacion, String fecha,Float pres) {
        this.id = id;
        this.descr=descr;
        this.ubicacion=ubicacion;
        this.fecha=fecha;
        this.presupuesto=pres;

    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean insertar(Proyectos pryecto) {
        try {
            SQLiteDatabase transaccionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION", pryecto.getDescr());
            datos.put("UBICACION", pryecto.getUbicacion());
            datos.put("FECHAR", pryecto.getFecha());
            datos.put("PRESUPUESTO",pryecto.getPresupuesto());

            long resultado = transaccionInsertar.insert("PROYECTOS", "IDPROYECTO", datos);
            transaccionInsertar.close();
            if (resultado == -1) return false;
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }

        return true;
    }

    public Proyectos[] consultar(String clave) {
        Proyectos[] resultado = null;
        try {
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROYECTOS WHERE IDPROYECTO=" + clave;
            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if (c.moveToFirst()) {
                resultado = new Proyectos[c.getCount()];
                int pos = 0;
                do {
                    resultado[pos] = new Proyectos(c.getInt(0), c.getString(1),
                            c.getString(2),c.getString(3), c.getFloat(4));
                    pos++;
                } while (c.moveToNext());
            }else{
                SQL = "SELECT * FROM PROYECTOS WHERE DESCRIPCION =" + clave;
                c = transacciónConsulta.rawQuery(SQL, null);
                if (c.moveToFirst()) {
                    resultado = new Proyectos[c.getCount()];
                    int pos = 0;
                    do {
                        resultado[pos] = new Proyectos(c.getInt(0), c.getString(1),
                                c.getString(2),c.getString(3), c.getFloat(4));
                        pos++;
                    } while (c.moveToNext());
                }

            }
            transacciónConsulta.close();
        } catch (SQLiteException e) {
            return null;
        }
        return resultado;
    }



    //// todos los abogados
    public Proyectos[] consultar() {
        Proyectos[] resultado = null;
        try {
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROYECTOS ";

            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if (c.moveToFirst()) {
                resultado = new Proyectos[c.getCount()];
                int pos = 0;
                do {
                    resultado[pos] = new Proyectos(c.getInt(0), c.getString(1),
                            c.getString(2),c.getString(3), c.getFloat(4));
                    pos++;
                } while (c.moveToNext());
            }
            transacciónConsulta.close();
        } catch (SQLiteException e) {
            return null;
        }
        return resultado;
    }


    public boolean eliminar(String p) {
        int resultado;
        try {
            SQLiteDatabase transaccionEliminar = base.getWritableDatabase();

            resultado = transaccionEliminar.delete("PROYECTOS","IDPROYECTO =? ",new String[]{p});
            transaccionEliminar.close();
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }
        return resultado>0;
    }

    public boolean actualizar(Proyectos p) {
        try {
            SQLiteDatabase transaccionActualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();

            datos.put("DESCRIPCION", p.getDescr());
            datos.put("UBICACION", p.getUbicacion());
            datos.put("FECHAR", p.getFecha());
            datos.put("PRESUPUESTO",p.getPresupuesto());

            long resultado =
                    transaccionActualizar.update("PROYECTOS", datos, "IDPROYECTO=?", new String[]{"" + p.getId()});
            transaccionActualizar.close();
            if (resultado == 0) return false;
        }catch(SQLiteException e){
            setError(e.getMessage());
            return false;
        }
        return true;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getError() {
        return error;
    }
}
