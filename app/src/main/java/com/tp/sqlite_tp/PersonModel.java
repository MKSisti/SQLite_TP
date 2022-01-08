package com.tp.sqlite_tp;

import java.io.Serializable;

//  the person model, is Serializable so I can pass it to the intent putExtra
public class PersonModel implements Serializable {
//  simple and standard fields, then generated getters and setters, two constructors and a toString
    private String id;
    private String prenom;
    private  String nom;
    private int age;

    public PersonModel(String id) {
    }

    public PersonModel(String id ,String prenom, String nom, int age) {
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.id = id;
    }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @Override
    public String toString() {
        return "PersonModel{" +
                "prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", age=" + age +
                '}';
    }

}
