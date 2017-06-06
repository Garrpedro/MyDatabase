package com.example.pedrogarrido.mydatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView lv;

    TextView txt;

    //Botões
    Button btnSubmeter, btnRegistos;

    //EditText
    EditText username, password, confPassword, email, telefone;

    //Adapter
    DBAdapter db;

    //Utilizador
    Utilizador user;

    final List<String> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);

        btnSubmeter = (Button) findViewById(R.id.btnSubmeter);
        btnRegistos = (Button) findViewById(R.id.btnRegistos);

        username = (EditText) findViewById(R.id.editTxtUsername);
        password = (EditText) findViewById(R.id.editTxtPass);
        confPassword = (EditText) findViewById(R.id.editTxtConPass);
        email = (EditText) findViewById(R.id.editTxtEmail);
        telefone = (EditText) findViewById(R.id.editTxtTelefone);

        lv = (ListView) findViewById(R.id.lv);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, users);

        lv.setAdapter(arrayAdapter);

        btnSubmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new Utilizador(username.getText().toString(), password.getText().toString(),
                        confPassword.getText().toString(), email.getText().toString(), telefone.getText().toString() );

                AddUser(user);
            }
        });

        btnRegistos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetUsers();


                arrayAdapter.notifyDataSetChanged();

            }
        });

    }

    public void AddUser(Utilizador user)
    {
        db.open();

        if (db.insertUser(user.getUsername(), user.getPassword(), user.getConfPassword(), user.getEmail(), user.getTelefone()) >= 0)
            Toast.makeText(this, "Boa idiota", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Deu Merda", Toast.LENGTH_LONG).show();

        db.close();
    }

    public void GetUsers()
    {
        db.open();
        Cursor c = db.getAllUsers();

        if (c.moveToFirst())
        {
            do {
                DisplayUser(c);
            }while (c.moveToNext());
        }
        db.close();

    }

    public void DisplayUser(Cursor c)
    {
         users.add("ID: " + c.getString(0) + "\n" + "Username: " + c.getString(1) + "\n" + "Password: " + c.getString(2) + "\n" +
                 "ConfPassword: " + c.getString(3) + "\n" + "Email: " + c.getString(4)+ "\n" + "Telefone: " + c.getString(5));
    }

    public void DisplayUsers(Cursor c)
    {
    }
}
