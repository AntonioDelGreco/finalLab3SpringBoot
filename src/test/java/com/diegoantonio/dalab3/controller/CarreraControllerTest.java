package com.diegoantonio.dalab3.controller;

import com.diegoantonio.dalab3.model.Carrera;
import com.diegoantonio.dalab3.model.dto.CarreraDTO;
import com.diegoantonio.dalab3.service.CarreraService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CarreraController.class)
class CarreraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarreraService carreraServiceMock;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addCarreraSuccess() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria electrica");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCuatrimestres(6);

        Carrera carreraResponse = new Carrera();
        carreraResponse.setNombre(carreraDTO.getNombre());
        carreraResponse.setDepartamento(carreraDTO.getDepartamento());
        carreraResponse.setCuatrimestres(carreraDTO.getCuatrimestres());

        Mockito.when(carreraServiceMock.addCarrera(any(CarreraDTO.class))).thenReturn(carreraResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Carrera actualCarreraResponse = mapper.readValue(result.getResponse().getContentAsString(), Carrera.class);
        Assertions.assertEquals(carreraResponse, actualCarreraResponse);
    }
    @Test
    public void addCarreraFailNameNull() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre(null);
        carreraDTO.setDepartamento(1);
        carreraDTO.setCuatrimestres(6);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El nombre de la carrera no puede ser un espacio vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addCarreraFailNameLetter() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("1");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCuatrimestres(6);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El nombre de la carrera debe contener al menos una letra.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addCarreraFailDepartamentoNull() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria electrica");
        carreraDTO.setDepartamento(null);
        carreraDTO.setCuatrimestres(6);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El departamento no puede ser un valor vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addCarreraFailDepartamentoMin() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria electrica");
        carreraDTO.setDepartamento(0);
        carreraDTO.setCuatrimestres(6);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El departamento debe ser mayor o igual a 1.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addCarreraFailCuatrimestresNull() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria electrica");
        carreraDTO.setDepartamento(2);
        carreraDTO.setCuatrimestres(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "La cantidad de cuatrimestres que tiene la carrera no puede estar vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addCarreraFailCuatrimestresMin() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria electrica");
        carreraDTO.setDepartamento(2);
        carreraDTO.setCuatrimestres(2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "La cantidad de cuatrimestres que tiene la carrera debe ser de por lo menos 4.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }

    @Test
    public void updateCarreraSuccess() throws Exception {
        Integer idCarrera = 1;
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria electrica");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCuatrimestres(6);

        Carrera carreraResponse = new Carrera();
        carreraResponse.setNombre(carreraDTO.getNombre());
        carreraResponse.setDepartamento(carreraDTO.getDepartamento());
        carreraResponse.setCuatrimestres(carreraDTO.getCuatrimestres());

        Mockito.when(carreraServiceMock.updateCarrera(eq(idCarrera), any(CarreraDTO.class))).thenReturn(carreraResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Carrera actualCarreraResponse = mapper.readValue(result.getResponse().getContentAsString(), Carrera.class);
        Assertions.assertEquals(carreraResponse, actualCarreraResponse);
    }
    @Test
    public void updateCarreraFailIdMin() throws Exception {
        Integer idCarrera = 0;
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria electrica");
        carreraDTO.setDepartamento(1);
        carreraDTO.setCuatrimestres(6);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El valor en la URL debe ser mayor a cero.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }

    @Test
    public void deleteCarreraSuccess() throws Exception {
        Integer idCarrera = 2;

        Mockito.doNothing().when(carreraServiceMock).deleteCarrera(idCarrera);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "La carrera fue borrada satisfactoriamente.";
        Assertions.assertEquals(responseBody, expectedMsg);
    }
    @Test
    public void deleteCarreraFailidMin() throws Exception {
        Integer idCarrera = 0;

        Mockito.doNothing().when(carreraServiceMock).deleteCarrera(idCarrera);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "El valor en la URL debe ser mayor a cero.";
        Assertions.assertTrue(responseBody.contains(expectedMsg));
    }
}