package com.example.demo.service;

import com.example.demo.data.Voiture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StatistiqueTests {

    StatistiqueImpl statistique;

    @BeforeEach
    void setUp() {
        statistique = new StatistiqueImpl();
    }

    @Test
    void testMoyenneAvecPlusieursVoitures() {
        Voiture v1 = new Voiture("Ferrari", 2000);
        Voiture v2 = new Voiture("Porsche", 3000);
        Voiture v3 = new Voiture("Renault", 4000);
        statistique.ajouter(v1);
        statistique.ajouter(v2);
        statistique.ajouter(v3);
        Echantillon echantillon = statistique.prixMoyen();
        assertEquals(3000, echantillon.getPrixMoyen());
        assertEquals(3, echantillon.getNombreDeVoitures());
    }

    @Test
    void testMoyenneAvecUneSeuleVoiture() {
        Voiture v1 = new Voiture("Ferrari", 2000);
        statistique.ajouter(v1);
        Echantillon echantillon = statistique.prixMoyen();
        assertEquals(2000, echantillon.getPrixMoyen());
        assertEquals(1, echantillon.getNombreDeVoitures());
    }

    @Test
    void testMoyenneAvecAucuneVoiture() {
        assertThrows(ArithmeticException.class, () -> {
            statistique.prixMoyen();
        });
    }


}
