package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(tags = {"ProductController"})
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @GetMapping(value="/Produits")
    public MappingJacksonValue listeProduits(){
        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique",monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }
    @GetMapping(value="/Produits/{id}")
    @ApiOperation(value="Récupère un produit avec son ID")
    public Product afficherUnProduit(@PathVariable int id){
        Product product = productDao.findById(id);

        if(product==null) throw new ProduitIntrouvableException(
                "Le produit avec l'id " + id + " est INTROUVABLE");

        return product;
    }

    @GetMapping(value = "/test/produits/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }
    @PostMapping(value="/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product){

        if(product.getPrix()==0)throw new ProduitGratuitException("Attention le prix de vente doit être supérieur à 0€");

        Product productAdded=productDao.save(product);

        if(productAdded==null){
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable Integer id) {

        productDao.deleteById(id);
    }

    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {

        productDao.save(product);
    }

    @GetMapping(value="/AdminProduits")
    public MappingJacksonValue calculMargeProduit(){
        Iterable<Product> produits = productDao.findAll();

        for(Product produit : produits){
            produit.setMarge(produit.getPrix()-produit.getPrixAchat());
        }

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept();
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique",monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }

    @GetMapping(value="Produits/Ordered")
    public MappingJacksonValue trierProduitsParOrdreAlphabetique(){

        Iterable<Product> produits = productDao.findAllByOrderByNom();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("marge");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique",monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }
}
