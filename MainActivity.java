package com.estecode.frutifru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_Personaje;     //aca se declaran las variables de los text que se usan en la activity
    private EditText et_nombre;
    private TextView tv_bestScore;
    private MediaPlayer mp;

    int num_aleatorio = (int) (Math.random() * 10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = (EditText)findViewById(R.id.txt_nombre);       //aca se relacionan las variables que se declararon
        iv_Personaje = (ImageView)findViewById(R.id.iv_Personaje); //para poder hacer una relacion entre la parte grafica
        tv_bestScore = (TextView)findViewById(R.id.txt_score);     //y el codigo sino no se podrian comunicar entre ellas.

        getSupportActionBar().setDisplayShowHomeEnabled(true); //esta linea de codigo es la que inserta el
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);  // icono dentro del actionbar

        int id;
        if (num_aleatorio == 0 || num_aleatorio == 10){                                               //estructura condicional que hace que se cambien las imagenes
            id = getResources().getIdentifier("mango","drawable", getOpPackageName());//aca busca a mango dentro de la carpeta drawable que es donde estan las imag
            iv_Personaje.setImageResource(id);                                                       //hay que guardarlas dentro de un int ya q si la ponemos en un string da error
        }else if (num_aleatorio == 1 || num_aleatorio == 9){
            id = getResources().getIdentifier("fresa","drawable", getOpPackageName());
            iv_Personaje.setImageResource(id);
        }else if (num_aleatorio == 2 || num_aleatorio == 8) {
            id = getResources().getIdentifier("manzana", "drawable", getOpPackageName());
            iv_Personaje.setImageResource(id);
        }else if (num_aleatorio == 3 || num_aleatorio == 7) {
            id = getResources().getIdentifier("uva", "drawable", getOpPackageName());
            iv_Personaje.setImageResource(id);
        }else if (num_aleatorio == 4 || num_aleatorio == 5 || num_aleatorio == 6) {
            id = getResources().getIdentifier("sandia", "drawable", getOpPackageName());
            iv_Personaje.setImageResource(id);
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
              "select * from puntaje where score = (select max (score) from puntaje)", null );
        if (consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);          //se crean dos string para guardar nombre y score y luego poder
            String temp_score = consulta.getString(1);            //llamarlos en la vaiable tv_bestScore y poder imprimir en pantalla
            tv_bestScore.setText("Record" + temp_score + "de" + temp_nombre);  //luego se cierra la base de datos y en el else tambien se cierra pq si no encuentra nada que la cierre como esta
            BD.close();
        }else{
            BD.close();
        }

        mp = MediaPlayer.create(this,R.raw.alphabet_song);         // aca se llama a mp q es igual a media player crear y dentro se pone el contex y la carpeta donde se aloja el audio
        mp.start();                                                        //se hace esto para que en la pantalla principal tenga el audio con star, looping es para q lo vuelva a ejecutar
        mp.setLooping(true);                                               //y luego en el metodo jugar se apaga el audio y se destruye para recuperar recursos en el sistema
    }

    public void Jugar (View view){                          // se crea el metodo o boton jugar
        String nombre = et_nombre.getText().toString();     //

        if (!nombre.equals("")){                            //aca se valida el campo del nombre para que no quede vacio
           mp.stop();                                       //se apaga el audio
           mp.release();                                    //se limpia para que no consuma recursos

            Intent intent = new Intent(this, MainActivity2_Nivel1.class);    // se crea el obj intent y se le indica que pase desde este activity al activity nivel 1
                                                                                            // de esta forma se pasa de un activity a otro activity
            intent.putExtra("jugador", nombre); //obj inten se pone putext y hay que pasarle una key que le llamo "jugador"
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Por favor escribe tu nombre",Toast.LENGTH_LONG).show();

            et_nombre.requestFocus();                                                                       //en estas 3 lines de abajo son para poder abrir el teclado del dispositivo
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);//cuando se cliquea en el edit text
            imm.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override                       // con este metodo lo que se logra es controlar el boton back del movil
    public void onBackPressed() {   // para que no se pueda volver a paginas anteriore por eso esta vacio
                                    // si quisiera hacerlo regresar a algun lado le pones la referencia del activity anterio q estabas viendo
    }
}