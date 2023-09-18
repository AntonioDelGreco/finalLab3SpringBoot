package com.diegoantonio.dalab3.controller;

import com.diegoantonio.dalab3.model.Materia;
import com.diegoantonio.dalab3.model.dto.MateriaDTO;
import com.diegoantonio.dalab3.service.MateriaService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MateriaController.class)
class MateriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MateriaService materiaServiceMock;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addMateriaSuccess() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(2023);
        materiaDTO.setCuatrimestre(1);

        Materia materiaResponse = new Materia();
        materiaResponse.setNombre(materiaDTO.getNombre());
        materiaResponse.setAnio(materiaDTO.getAnio());
        materiaResponse.setCuatrimestre(materiaDTO.getCuatrimestre());

        Mockito.when(materiaServiceMock.addMateria(any(MateriaDTO.class))).thenReturn(materiaResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        Materia actualMateriaResponse = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);
        Assertions.assertEquals(materiaResponse, actualMateriaResponse);
    }
    @Test
    public void addMateriaFailNameNull() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre(null);
        materiaDTO.setAnio(2020);
        materiaDTO.setCuatrimestre(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El nombre de la materia no puede ser un espacio vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addMateriaFailNameLetter() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("1");
        materiaDTO.setAnio(2020);
        materiaDTO.setCuatrimestre(2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El nombre de la materia debe contener al menos una letra.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addMateriaFailAnioNull() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(null);
        materiaDTO.setCuatrimestre(2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El anio no puede estar vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addMateriaFailAnioDigits() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(12833);
        materiaDTO.setCuatrimestre(2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El anio debe ser un numero de 4 digitos.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addMateriaFailAnioMin() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(1990);
        materiaDTO.setCuatrimestre(2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El anio debe ser mayor o igual a 2020.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addMateriaFailCuatrimestreNull() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(2023);
        materiaDTO.setCuatrimestre(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El cuatrimestre no puede estar vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addMateriaFailCuatrimestreMin() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(2023);
        materiaDTO.setCuatrimestre(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El cuatrimestre debe ser 1 o 2.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addMateriaFailCuatrimestreMax() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(2023);
        materiaDTO.setCuatrimestre(5);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El cuatrimestre debe ser 1 o 2.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }

    @Test
    public void updateMateriaSuccess() throws Exception {
        Integer idMateria = 2;
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(2023);
        materiaDTO.setCuatrimestre(1);

        Materia materiaResponse = new Materia();
        materiaResponse.setMateriaId(idMateria);
        materiaResponse.setNombre(materiaDTO.getNombre());
        materiaResponse.setAnio(materiaDTO.getAnio());
        materiaResponse.setCuatrimestre(materiaDTO.getCuatrimestre());

        Mockito.when(materiaServiceMock.updateMateria(eq(idMateria), any(MateriaDTO.class))).thenReturn(materiaResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/{idMateria}", idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Materia actualMateriaResponse = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);
        Assertions.assertEquals(materiaResponse, actualMateriaResponse);
    }
    @Test
    public void updateMateriaFailMin() throws Exception {
        Integer idMateria = 0;
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNombre("Laboratorio 3");
        materiaDTO.setAnio(2023);
        materiaDTO.setCuatrimestre(2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/{idMateria}", idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El valor en la URL debe ser mayor a cero.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }

    @Test
    public void deleteMateriaSuccess() throws Exception {
        Integer idMateria = 2;

        Mockito.doNothing().when(materiaServiceMock).deleteMateria(idMateria);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/materia/{idMateria}", idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "La materia fue borrada satisfactoriamente.";
        Assertions.assertEquals(responseBody, expectedMsg);
    }
    @Test
    public void deleteMateriaFailIdMin() throws Exception {
        Integer idMateria = -1;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/materia/{idMateria}", idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "El valor en la URL debe ser mayor a cero.";
        Assertions.assertTrue(responseBody.contains(expectedMsg));
    }

    @Test
    public void findByNameSuccess() throws Exception {
        String nombre = "Laboratorio 3";

        Materia materiaResponse = new Materia();
        materiaResponse.setNombre(nombre);
        materiaResponse.setAnio(2023);
        materiaResponse.setCuatrimestre(1);

        Mockito.when(materiaServiceMock.findByName(nombre)).thenReturn(materiaResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/materia")
                        .param("nombre", nombre)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia actualMateriaResponse = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);
        Assertions.assertEquals(materiaResponse, actualMateriaResponse);
    }
    @Test
    public void findByNameFailNameNull() throws Exception {
        String nombre = null;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/materia")
                        .param("nombre", nombre)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "No puede faltar el parametro 'nombre'.";
        Assertions.assertTrue(responseBody.contains(expectedMsg));
    }
    @Test
    public void findByNameFailNameNotLetter() throws Exception {
        String nombre = "1";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/materia")
                        .param("nombre", nombre)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "Debe contener al menos una letra.";
        Assertions.assertTrue(responseBody.contains(expectedMsg));
    }

    @Test
    public void orderBySuccess() throws Exception {
        String order = "nombre_asc";

        ArrayList<Materia> materiasResponse = new ArrayList<>();
        Materia materia1 = new Materia();
        materia1.setNombre("Laboratorio 1");
        materia1.setAnio(2023);
        materia1.setCuatrimestre(1);
        materiasResponse.add(materia1);

        Materia materia2 = new Materia();
        materia2.setNombre("Laboratorio 2");
        materia2.setAnio(2024);
        materia2.setCuatrimestre(2);
        materiasResponse.add(materia2);

        Mockito.when(materiaServiceMock.orderBy(order)).thenReturn(materiasResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/materias")
                        .param("order", order)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ArrayList<Materia> actualMateriasResponse = mapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<ArrayList<Materia>>() {});
        Assertions.assertEquals(materiasResponse, actualMateriasResponse);
    }
    @Test
    public void orderByFailOrderParam() throws Exception {
        String order = "ascendente";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/materias")
                        .param("order", order)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "Los unicos valores permitidos son: nombre_asc | nombre_desc | codigo_asc | codigo_desc";
        Assertions.assertTrue(responseBody.contains(expectedMsg));
    }
}