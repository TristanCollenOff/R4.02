package com.example.demo.web;

import com.example.demo.data.Voiture;
import com.example.demo.service.Echantillon;
import com.example.demo.service.StatistiqueImpl;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebTests {

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetStatistiquesCode200() throws Exception {
        Echantillon echantillon = new Echantillon(1, 2000);
        when(statistiqueImpl.prixMoyen()).thenReturn(echantillon);

        mockMvc.perform(get("/statistique")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nombreDeVoitures").value(1))
                        .andExpect(jsonPath("$.prixMoyen").value(2000))
                        ;
    }


    @Test
    void testGetStatistiquesCode400() throws Exception {
        when(statistiqueImpl.prixMoyen()).thenThrow(new ArithmeticException());

        mockMvc.perform(get("/statistique")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(400))
                        ;
    }

    @Test
    void testGetStatistiquesCode404() throws Exception {
        mockMvc.perform(get("/statistique-qui-marche-pas")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        ;

    }
    
    @Test 
    void testCreerVoiture() throws Exception {

        String jsonVoiture = """
                {
                    "marque": "Renault",
                    "prix": 1000
                }
                """;

        mockMvc.perform(post("/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVoiture))
                        .andExpect(status().isOk())
                        ;

        verify(statistiqueImpl, times(1)).ajouter(any(Voiture.class));

    }

}
