package com.example.aplicativoteste;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //criação de objetos
    private SQLiteDatabase bancoDados;
    public ListView listViewDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listViewDados = (ListView) findViewById(R.id.listViewDados);
        //métodos
        criarBancoDados();
        inserirDadosTemp();
        listarDados();

    }
    //crio o banco de dados
    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("BD_cafeteria", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS produtos("+" id INTEGER PRIMARY KEY AUTOINCREMENT "+" , nome TEXT)");
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //listar dados: faz um select na tabela e mostra os dados na tela
    public void listarDados(){
        try {
            bancoDados = openOrCreateDatabase("BD_cafeteria", MODE_PRIVATE, null);
            //Cursor: usado para manipular o resultado de uma consulta/ A função do Adapter é lidar com o listview, que está na tela do app

            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM produtos", null);
            //lista vazia
            ArrayList<String> linhas = new ArrayList<String>();
            //Adapter
            ArrayAdapter<String> meuAdapter = new ArrayAdapter<String>(
                this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            //liga o adapter ao listView
            listViewDados.setAdapter(meuAdapter);

            if(meuCursor.moveToFirst()){
                do {
                    linhas.add(meuCursor.getString(1));
                } while (meuCursor.moveToNext());
            }
            meuAdapter.notifyDataSetChanged();

            meuCursor.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void inserirDadosTemp(){

        try {
            bancoDados = openOrCreateDatabase("BD_cafeteria", MODE_PRIVATE, null);

            bancoDados.execSQL("DELETE FROM produtos");

            String sql = "INSERT INTO produtos (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,"Café");
            stmt.executeInsert();

            stmt.bindString(1,"Capuccino");
            stmt.executeInsert();

            stmt.bindString(1,"Nutella");
            stmt.executeInsert();

            stmt.bindString(1,"Café coado");
            stmt.executeInsert();

            stmt.bindString(1,"chá");
            stmt.executeInsert();



            bancoDados.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}