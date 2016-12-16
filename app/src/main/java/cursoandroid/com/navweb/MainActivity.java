package cursoandroid.com.navweb;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AutoCompleteTextView tNav;
    private String url;
    private BDNavegador bd;
    private ArrayAdapter<String> hist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        webView = (WebView)findViewById(R.id.webView);
        tNav = (AutoCompleteTextView) findViewById(R.id.tNav);

        bd = new BDNavegador(this);
        webView.loadUrl("http://www.google.es");
        webView.setWebViewClient(new WebViewClient());
        rellenaHistorial();
        System.out.print(creaHistorial());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



     void cargarUrl(View view){
        rellenaHistorial();
        url = "";
        if(tNav.getText().toString().contains("http://")){
            url = tNav.getText().toString();
        }else{
            url = "http://"+tNav.getText().toString();
        }
        webView.loadUrl(""+url);
        webView.setWebViewClient(new WebViewClient());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tNav.getWindowToken(), 0);
        guardaUrl();

     }

   /* public void compruebaUrl(){
        String [] historia = creaHistorial();
        for(int i=0;i<historia.length;i++){
            if(!(historia[i].contains(tNav.getText()))){
                guardaUrl();
            }
        }
    }*/

    public void guardaUrl(){
        Integer idnum;
        idnum = bd.consultaId()+1;
        long res;
        res = bd.insertaPag(idnum,"Página"+idnum, tNav.getText().toString(),1);
        Toast.makeText(this,"guardado:"+res ,Toast.LENGTH_SHORT).show();

    }

    public String[] creaHistorial(){return bd.consultaUrl();}

    public void rellenaHistorial(){
        hist = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,creaHistorial());
        tNav.setAdapter(hist);
    }
}
