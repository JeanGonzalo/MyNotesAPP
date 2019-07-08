package com.example.mynotesapp;

import android.app.ProgressDialog;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {

    EditText et_title, et_note;
    ProgressDialog progressDialog;  //esto es la parte del la espera

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        et_title = findViewById(R.id.idTitle);
        et_note = findViewById(R.id.idNote);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");


    }

    //Estamos que inflamos el metode el menu_editor en editor_activity.xml y asi lo podramos
    //manipular
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:

                String title = et_title.getText().toString().trim();
                String note = et_note.getText().toString().trim();
                int color = -2184710;


                // esta parte me gusta es la seguridad al no ingresas un campo
                // el truco es el tring y el is empty
                if(title.isEmpty()){
                    et_title.setError("Please enter a title");
                }else if (note.isEmpty()){
                    et_note.setError("Please enter a note");
                }else{
                    saveNote(title, note, color);
                }


                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote(final String title, final String note, final int color) {

        progressDialog.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title,note,color);

        //esto no esta en la libreria de github, pero enqueue sinifica encolar
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {

                progressDialog.dismiss();


                Log.e("Response",""+response.body().isSuccess());


                if(response.isSuccessful() && response.body() != null){

                    Log.e("Response2",""+response.body().isSuccess());

                    Boolean success = response.body().isSuccess();
                    if (success){
                        Toast.makeText(EditorActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        finish();  //back to main activity //volver a la actividad main o principal
                    }

                    else {
                        Toast.makeText(EditorActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } //if error, still in this activity //si hay error te quedas en esta actividad
                }



            }

            @Override
            public void onFailure(Call<Note> call,Throwable t) {

                progressDialog.dismiss();

                Log.e("error",""+t.toString());

                Toast.makeText(EditorActivity.this,
                        t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
