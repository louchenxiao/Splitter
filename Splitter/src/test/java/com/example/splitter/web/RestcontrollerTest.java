package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.Ueberweisung;
import com.example.splitter.web.ApiRecord.ApiAusgleich;
import com.example.splitter.web.ApiRecord.ApiGruppeInfo;
import com.example.splitter.web.ApiRecord.Auslage;
import com.example.splitter.web.ApiRecord.CreateGruppe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.shouldHaveThrown;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Restcontroller.class)
class RestcontrollerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    Ueberweisung ueberweisung;

    @MockBean
    PersonService personService;

    @MockBean
    GroupService groupService;
    @Autowired
    Restcontroller restcontroller;
    @Test
    @DisplayName("When group is null , Http status is 400")
    void createGruppe() throws Exception {

        String [] a = new String[10];
        CreateGruppe createGruppe = new CreateGruppe(null,a);
        ResponseEntity<String> gruppe = restcontroller.createGruppe(createGruppe);
        assertThat(gruppe.getStatusCode().toString()).isEqualTo("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When personList in Group is null , Http status is 400")
    void createGruppe1() throws Exception {

        String [] a = new String[10];
        CreateGruppe createGruppe = new CreateGruppe("name");
        ResponseEntity<String> gruppe = restcontroller.createGruppe(createGruppe);
        System.out.println(gruppe.getStatusCode().toString());
        assertThat(gruppe.getStatusCode().toString()).isEqualTo("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When name in Group is null , Http status is 400")
    void createGruppe3() throws Exception {

        String [] a = new String[10];
        CreateGruppe createGruppe = new CreateGruppe(null,"a","b");
        ResponseEntity<String> gruppe = restcontroller.createGruppe(createGruppe);
        System.out.println(gruppe.getStatusCode().toString());
        assertThat(gruppe.getStatusCode().toString()).isEqualTo("400 BAD_REQUEST");
    }




    @Test
    @DisplayName("When Auslage is null , Http status is 400")
    void getauslagen() {
        Auslage auslage = new Auslage(null,null,0,Set.of() );
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When grund in Auslage is null , Http status is 400")
    void getauslagen1() {

        Auslage auslage = new Auslage(null,"glaeubiger",66,Set.of("a") );
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When glaeubiger in Auslage is null , Http status is 400")
    void getauslagen2() {
        Auslage auslage = new Auslage("grund",null,66,Set.of("a") );
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }



    @Test
    @DisplayName("When cent in Auslage is null , Http status is 400")
    void getauslagen3() {
        Auslage auslage = new Auslage("grund","ss",0,Set.of("a") );
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When schuldner in Auslage is null , Http status is 400")
    void getauslagen4() {
        Auslage auslage = new Auslage("grund","ss",66,Set.of() );
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }

    @Test
    @DisplayName("When group is closed  , Http status is 409")
    void getauslagen5() {
        Auslage auslage = new Auslage("grund","ss",66,Set.of("s") );
        when(groupService.check("1")).thenReturn(1);
        when(groupService.getStatus(1)).thenReturn(true);
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "1");
        assertThat(getauslagen.getStatusCode().toString()).contains("409");
    }

    @Test
    @DisplayName("All ok , Http status is 201")
    void getauslagen6() {
        Auslage auslage = new Auslage("grund","ss",66,Set.of("s") );
        when(groupService.check("1")).thenReturn(1);
        when(groupService.getStatus(1)).thenReturn(false);
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "1");
        assertThat(getauslagen.getStatusCode().toString()).contains("201");
    }

    @Test
    @DisplayName(" When id is invalid, Http status is 404")
    void getauslagen7() {
        Auslage auslage = new Auslage("grund","ss",66,Set.of("s") );
        when(groupService.check("1")).thenReturn(-1);
        ResponseEntity<String> getauslagen = restcontroller.getauslagen(auslage, "1");
        assertThat(getauslagen.getStatusCode().toString()).contains("404");
    }



    @Test
    @DisplayName("When id is null , Http status is 400")
    void getAusgleichszahlungen() {
        ResponseEntity<List<ApiAusgleich>> ausgleichszahlungen = restcontroller.getAusgleichszahlungen(null);
        assertThat(ausgleichszahlungen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }

    @Test
    @DisplayName("When all ok  , Http status is 200")
    void getAusgleichszahlungen1() {
        when(groupService.check("1")).thenReturn(1);
        when(groupService.findByGroupId(1)).thenReturn(new Gruppe(1,"aa",List.of(new Person("as"))));
        ResponseEntity<List<ApiAusgleich>> ausgleichszahlungen = restcontroller.getAusgleichszahlungen("1");
        assertThat(ausgleichszahlungen.getStatusCode().toString()).contains("200");
    }

    @Test
    @DisplayName("When id  is invalid  , Http status is 404")
    void getAusgleichszahlungen2() {
        when(groupService.check("1")).thenReturn(-1);
       // when(groupService.findByGroupId(1)).thenReturn(new Gruppe(1,"aa",List.of(new Person("as"))));
        ResponseEntity<List<ApiAusgleich>> ausgleichszahlungen = restcontroller.getAusgleichszahlungen("1");
        assertThat(ausgleichszahlungen.getStatusCode().toString()).contains("404");
    }




    @Test
    @DisplayName("When id is null , Http status is 404")
    void schliessen() {
        ResponseEntity<String> schliessen = restcontroller.schliessen(null);
        assertThat(schliessen.getStatusCode().toString()).contains("404 ");
    }


    @Test
    @DisplayName("When id is invalid , Http status is 404")
    void schliessen1() {
        ResponseEntity<String> schliessen = restcontroller.schliessen("asdasdasd");
        assertThat(schliessen.getStatusCode().toString()).contains("404 ");
    }



    @Test
    @DisplayName("When all ok , Http status is 200")
    void schliessen2() {
        when(groupService.check("1")).thenReturn(1);
        when(groupService.findByGroupId(1)).thenReturn(new Gruppe(1,"p",List.of(new Person("aa"))));
        ResponseEntity<String> schliessen = restcontroller.schliessen("1");
        assertThat(schliessen.getStatusCode().toString()).contains("200 ");
    }

    @Test
    @DisplayName("When id is null , Http status is 400")
    void getAllGruppeInfo() {
        ResponseEntity<ApiGruppeInfo> allGruppeInfo = restcontroller.getAllGruppeInfo(null);
        assertThat(allGruppeInfo.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }

    @Test
    @DisplayName("When all ok , Http status is 200")
    void getAllGruppeInfo1() {
        when(groupService.check("1")).thenReturn(1);
        when(groupService.findByGroupId(1)).thenReturn(new Gruppe(1,"as",List.of(new Person("aa"))));
        ResponseEntity<ApiGruppeInfo> allGruppeInfo = restcontroller.getAllGruppeInfo("1");
        assertThat(allGruppeInfo.getStatusCode().toString()).contains("200");
    }


    @Test
    @DisplayName("When id is null , Http status is 400")
    void getAllGruppeInfo2() {
        when(groupService.check("1")).thenReturn(1);
        when(groupService.findByGroupId(1)).thenReturn(new Gruppe(1,"as",List.of(new Person("aa"))));
        ResponseEntity<ApiGruppeInfo> allGruppeInfo = restcontroller.getAllGruppeInfo(null);
        assertThat(allGruppeInfo.getStatusCode().toString()).contains("400");
    }
}