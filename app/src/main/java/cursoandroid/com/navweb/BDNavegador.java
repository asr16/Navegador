package cursoandroid.com.navweb;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;



public class BDNavegador extends SQLiteOpenHelper {

    private static final int VERSION_BD = 1;
    private static final String NOMBRE_BD = "BDNavegadorWeb.db";
    private static final String NOMBRE_TABLA = "Historial";

    private static final String ins = "CREATE TABLE Historial (id INT PRIMARY KEY, nombre VARCHAR(100), direc VARCHAR(100), visitas INT)";


    public BDNavegador(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ins);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + NOMBRE_TABLA);
        onCreate(db);

    }

    public long insertaPag(Integer id,String nombre, String dir, Integer visitas){
        long resultado = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("id", id);
            valores.put("nombre", nombre);
            valores.put("direc", dir);
            valores.put("visitas", visitas);

            resultado = db.insert("Historial", null, valores);
        }

        db.close();
        return resultado;
    }

    public long modificaPag(Integer id,String nombre, String dir, Integer visitas){
        long resultado = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("id", id);
            valores.put("nombre", nombre);
            valores.put("direc", dir);
            valores.put("visitas", visitas);

            resultado = db.update("Historial", valores, "nombre="+nombre,null);
        }

        db.close();
        return resultado;
    }

    public Integer consultaId(){
        int last=-1;
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            String[] campos = {"id"};
            Cursor cur = db.query("Historial", campos, null, null, null, null, null, null);
            if(cur.getCount() != 0) {
                cur.moveToLast();
                last = cur.getInt(0);
            }
            cur.close();
        }
        db.close();


        return last;
    }

    public String[] consultaUrl(){
        SQLiteDatabase db = getReadableDatabase();
        String[] url = new String[0];
        if (db != null) {
            String[] campos = {"direc"};
            Cursor cur = db.query("Historial", campos, null, null, null, null, null, null);
            url= new String[cur.getCount()];
            cur.moveToFirst();
            if(cur.getCount() != 0) {
                for(int i=0;i<cur.getCount();i++){
                    url[i] = cur.getString(0);
                    cur.moveToNext();
                }

            }
            cur.close();
        }
        db.close();
        return url;
    }

    /*public String consultaTodo(String campo){
        SQLiteDatabase db = getReadableDatabase();
        String pagina ="";
        if (db != null) {
            String[] campos = {campo};
            Cursor cur = db.query("Historial", campos, null, null, null, null, null, null);
            cur.moveToFirst();
            if(cur.getCount() != 0) {
                for(int i=0;i<cur.getCount();i++){
                    pagina[i] = cur.getString(0);
                    cur.moveToNext();
                }

            }
            cur.close();
        }
        db.close();
        return pagina;
    }*/


 /*   public String consultar(String nombre) {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            String[] campos = {"id", "nombre", "telefono", "fecha"};
            Cursor cur = db.query("contactos", campos, "id=" + id, null, null, null, null, null);
            if(cur.getCount() == 0) {
            } else {
                cur.moveToFirst();
                contacto = new Contacto(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3));
            }
            cur.close();
        }
        db.close();
        return contacto;
    }*/
}
