package com.diegoantonio.dalab3.controller;

import com.diegoantonio.dalab3.model.Alumno;
import com.diegoantonio.dalab3.model.dto.AlumnoDTO;
import com.diegoantonio.dalab3.service.AlumnoService;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlumnoController.class)
class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoService alumnoServiceMock;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addAlumnoSuccess() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("jose");
        alumnoDTO.setApellido("enrique");
        alumnoDTO.setDni(12345678L);

        Alumno alumnoResponse = new Alumno();
        alumnoResponse.setNombre(alumnoDTO.getNombre());
        alumnoResponse.setApellido(alumnoDTO.getApellido());
        alumnoResponse.setDni(alumnoDTO.getDni());

        Mockito.when(alumnoServiceMock.addAlumno(any(AlumnoDTO.class))).thenReturn(alumnoResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Alumno actualAlumnoResponse = mapper.readValue(result.getResponse().getContentAsString(), Alumno.class);
        Assertions.assertEquals(alumnoResponse, actualAlumnoResponse);
    }
    @Test
    public void addAlumnoFailNameNull() throws Exception{
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre(null);
        alumnoDTO.setApellido("enrique");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El nombre del alumno no puede estar vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addAlumnoFailNameNotLetter() throws Exception{
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("1");
        alumnoDTO.setApellido("enrique");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El nombre del alumno debe contener al menos una letra.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addAlumnoFailApellidoNull() throws Exception{
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido(null);
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El apellido del alumno no puede estar vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addAlumnoFailApellidoNotLetter() throws Exception{
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("1");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El apellido del alumno debe contener al menos una letra.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addAlumnoFailDniNull() throws Exception{
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El DNI del alumno no puede estar vacio.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addAlumnoFailDniMin() throws Exception{
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(100L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El DNI debe contener 8 digitos solamente.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void addAlumnoFailDniMax() throws Exception{
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(1000000000L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El DNI no puede tener mas de 8 digitos.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }

    @Test
    public void updateAlumnoSuccess() throws Exception {
        Integer idAlumno = 1;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(12345678L);

        Alumno alumnoResponse = new Alumno();
        alumnoResponse.setNombre(alumnoDTO.getNombre());
        alumnoResponse.setApellido(alumnoDTO.getApellido());
        alumnoResponse.setDni(alumnoDTO.getDni());

        Mockito.when(alumnoServiceMock.updateAlumno(eq(idAlumno), any(AlumnoDTO.class))).thenReturn(alumnoResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/alumno/{idAlumno}", idAlumno)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Alumno actualAlumnoResponse = mapper.readValue(result.getResponse().getContentAsString(), Alumno.class);
        Assertions.assertEquals(alumnoResponse, actualAlumnoResponse);
    }
    @Test
    public void updateAlumnoFailIdMin() throws Exception{
        Integer idAlumno = -1;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/alumno/{idAlumno}", idAlumno)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El valor en la URL debe ser mayor a cero.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }

    @Test
    public void deleteAlumnoSuccess() throws Exception {
        Integer idAlumno = 1;

        Mockito.doNothing().when(alumnoServiceMock).deleteAlumno(idAlumno);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/{idAlumno}", idAlumno)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedMsg = "Alumno borrado satisfactoriamente.";
        Assertions.assertEquals(responseBody, expectedMsg);
    }
    @Test
    public void deleteAlumnoFailIdMin() throws Exception{
        Integer idAlumno = -1;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/{idAlumno}", idAlumno)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "El valor en la URL debe ser mayor a cero.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }

    @Test
    public  void updateAsignaturaSuccess() throws Exception {
        Integer idAlumno = 1;
        Integer idAsignatura = 1;
        String estado = "APROBADA";
        Integer nota = 8;

        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(12345678L);

        Alumno alumnoResponse = new Alumno();
        alumnoResponse.setNombre(alumnoDTO.getNombre());
        alumnoResponse.setApellido(alumnoDTO.getApellido());
        alumnoResponse.setDni(alumnoDTO.getDni());

        Mockito.when(alumnoServiceMock.updateAsignatura(eq(idAlumno), eq(idAsignatura), eq(estado), eq(nota)))
                .thenReturn(alumnoResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(
                "/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .param("estado", estado)
                        .param("nota", String.valueOf(nota))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Alumno actualAlumnoResponse = mapper.readValue(result.getResponse().getContentAsString(), Alumno.class);
        Assertions.assertEquals(alumnoResponse, actualAlumnoResponse);
    }
    @Test
    public void updateAsignaturaFailEstado() throws Exception{
        Integer idAlumno = 1;
        Integer idAsignatura = 1;
        String estado = "perdida";
        Integer nota = 8;

        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(
                "/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .param("estado", estado)
                        .param("nota", String.valueOf(nota))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "En el estado solo puede colocar NO_CURSADA | CURSADA | APROBADA";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void updateAsignaturaFailNotaMax() throws Exception{
        Integer idAlumno = 1;
        Integer idAsignatura = 1;
        String estado = "APROBADA";
        Integer nota = 25;

        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(
                                "/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .param("estado", estado)
                        .param("nota", String.valueOf(nota))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "La nota no puede ser mayor a 10.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
    @Test
    public void updateAsignaturaFailNotaMin() throws Exception{
        Integer idAlumno = 1;
        Integer idAsignatura = 1;
        String estado = "APROBADA";
        Integer nota = -1;

        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Jose");
        alumnoDTO.setApellido("Enrique");
        alumnoDTO.setDni(12345678L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(
                                "/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .param("estado", estado)
                        .param("nota", String.valueOf(nota))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String expectedErrMsg = "La nota no puede ser menor que 0.";
        Assertions.assertTrue(responseBody.contains(expectedErrMsg));
    }
}