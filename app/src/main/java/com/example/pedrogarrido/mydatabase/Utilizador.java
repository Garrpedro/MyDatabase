package com.example.pedrogarrido.mydatabase;

/**
 * Created by Pedro Garrido on 06/06/2017.
 */

public class Utilizador {

    private String Username;
    private String Password;
    private String ConfPassword;
    private String Email;
    private String Telefone;

    public Utilizador(String username, String password, String confPassword, String email, String telefone) {
        Username = username;
        Password = password;
        ConfPassword = confPassword;
        Email = email;
        Telefone = telefone;
    }

    public Utilizador()
    {

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfPassword() {
        return ConfPassword;
    }

    public void setConfPassword(String confPassword) {
        ConfPassword = confPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }
}
