package com.ecommerce.microcommerce.model;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@JsonFilter("monFiltreDynamique")
@Entity
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @Length(min=3, max=20, message="Nom trop long ou trop court.")
    private String nom;

    @Min(value=1)
    private int prix;
    private int prixAchat;
    private int marge;


    public Product() {
    }

    public Product(@Length(min = 3, max = 20, message = "Nom trop long ou trop court.") String nom, @Min(value = 1) int prix, int prixAchat, int marge) {
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
        this.marge = marge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    public int getMarge() {
        return marge;
    }
    public void setMarge(int marge) {
        this.marge = marge;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", prixAchat=" + prixAchat +
                ", marge=" + marge +
                '}';
    }
}
