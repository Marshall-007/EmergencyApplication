package com.example.towtruck;

public class RegisterdUsers {

    public String Rname, Rsurname, Remail, Rmobile, Rpassword;
    // Method and class must have the same Name with the constructor.
    public  RegisterdUsers(){

    }

    public RegisterdUsers(String Name, String Surname, String Email, String Mobile, String Password){
        this.Rname=Name;
        this.Rsurname=Surname;
        this.Remail=Email;
        this.Rmobile=Mobile;
        this.Rpassword=Password;

    }
}
