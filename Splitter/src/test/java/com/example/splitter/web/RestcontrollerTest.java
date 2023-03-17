package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.SplitterService;
import com.example.splitter.web.ApiRecord.ApiAusgleich;
import com.example.splitter.web.ApiRecord.ApiGruppeInfo;
import com.example.splitter.web.ApiRecord.Auslage;
import com.example.splitter.web.ApiRecord.CreateGruppe;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@WebMvcTest(Restcontroller.class)
class RestcontrollerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    SplitterService splitterService;

    @MockBean
    PersonService personService;

    @MockBean
    GroupService groupService;
    @Autowired
    Restcontroller restcontroller;
    @Test
    @DisplayName("When group is null , Http status is 400")
    void createGruppe() {
        String [] a = new String[10];
        CreateGruppe createGruppe = new CreateGruppe(null,a);
        ResponseEntity<String> gruppe = restcontroller.createGruppe(createGruppe);
        assertThat(gruppe.getStatusCode().toString()).isEqualTo("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When personList in Group is null , Http status is 400")
    void createGruppe1() {
        CreateGruppe createGruppe = new CreateGruppe("name");
        ResponseEntity<String> gruppe = restcontroller.createGruppe(createGruppe);
        assertThat(gruppe.getStatusCode().toString()).isEqualTo("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When Group name is null , Http status is 400")
    void createGruppe3() {
        CreateGruppe createGruppe = new CreateGruppe(null,"a","b");
        ResponseEntity<String> gruppe = restcontroller.createGruppe(createGruppe);
        assertThat(gruppe.getStatusCode().toString()).isEqualTo("400 BAD_REQUEST");
    }




    @Test
    @DisplayName("When Auslage missing content , Http status is 400")
    void getauslagen() {
        when(groupService.check("12")).thenReturn(12);
        Auslage auslage = new Auslage(null,null,0,Set.of() );
        ResponseEntity<String> getAuslagen = restcontroller.getAuslagen(auslage, "12");
        assertThat(getAuslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When grund in Auslage is null , Http status is 404")
    void getauslagen1() {
        when(groupService.check("1")).thenReturn(1);
        Auslage auslage = new Auslage(null,"glaeubiger",66,Set.of("a") );
        ResponseEntity<String> getAuslagen = restcontroller.getAuslagen(auslage, "1");
        System.out.println(getAuslagen);
        assertThat(getAuslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When glaeubiger in Auslage is null , Http status is 400")
    void getauslagen2() {
        when(groupService.check("12")).thenReturn(12);
        Auslage auslage = new Auslage("grund",null,66,Set.of("a") );
        ResponseEntity<String> getAuslagen = restcontroller.getAuslagen(auslage, "12");
        assertThat(getAuslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }



    @Test
    @DisplayName("When cent in Auslage is 0 , Http status is 400")
    void getauslagen3() {
        when(groupService.check("12")).thenReturn(12);
        Auslage auslage = new Auslage("grund","ss",0,Set.of("a") );
        ResponseEntity<String> getAuslagen = restcontroller.getAuslagen(auslage, "12");
        assertThat(getAuslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }


    @Test
    @DisplayName("When schuldner in Auslage is null , Http status is 400")
    void getauslagen4() {
        when(groupService.check("12")).thenReturn(12);
        Auslage auslage = new Auslage("grund","ss",66,Set.of() );
        ResponseEntity<String> getauslagen = restcontroller.getAuslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("400 BAD_REQUEST");

    }

    @Test
    @DisplayName("When group is closed  , Http status is 409")
    void getauslagen5() {
        Auslage auslage = new Auslage("grund","ss",66,Set.of("s") );
        Gruppe gruppe = new Gruppe(12,"a",List.of("a"),Set.of(),true);
        when(groupService.check("12")).thenReturn(12);

        when(groupService.findByGroupId(12)).thenReturn(gruppe);
        //when(groupService.getStatus(1)).thenReturn(true);
        ResponseEntity<String> getauslagen = restcontroller.getAuslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("409");
    }

    @Test
    @DisplayName("All ok , Http status is 201")
    void getauslagen6() {
        Auslage auslage = new Auslage("grund","ss",66,Set.of("s") );
        Gruppe gruppe = new Gruppe(12,"a",List.of("a"),new HashSet<>(),false);
        when(groupService.findByGroupId(12)).thenReturn(gruppe);
        when(groupService.check("12")).thenReturn(12);
        ResponseEntity<String> getauslagen = restcontroller.getAuslagen(auslage, "12");
        assertThat(getauslagen.getStatusCode().toString()).contains("201");
    }

    @Test
    @DisplayName(" When id is invalid, Http status is 404")
    void getauslagen7() {
        Auslage auslage = new Auslage("grund","ss",66,Set.of("s") );
        when(groupService.check("1")).thenReturn(-1);
        ResponseEntity<String> getauslagen = restcontroller.getAuslagen(auslage, "1");
        assertThat(getauslagen.getStatusCode().toString()).contains("404");
    }



    @Test
    @DisplayName("When id is null , Http status is 404")
    void getAusgleichszahlungen() {
        when(groupService.check(null)).thenReturn(-1);
        ResponseEntity<List<ApiAusgleich>> ausgleichszahlungen = restcontroller.getAusgleichszahlungen(null);
        assertThat(ausgleichszahlungen.getStatusCode().toString()).contains("404");

    }

    @Test
    @DisplayName("When all ok  , Http status is 200")
    void getAusgleichszahlungen1() {
        when(groupService.check("1")).thenReturn(1);
        when(groupService.findByGroupId(1)).thenReturn(new Gruppe("aa",List.of("as")));
        ResponseEntity<List<ApiAusgleich>> ausgleichszahlungen = restcontroller.getAusgleichszahlungen("1");
        assertThat(ausgleichszahlungen.getStatusCode().toString()).contains("200");
    }

    @Test
    @DisplayName("When id is not exist  , Http status is 404")
    void getAusgleichszahlungen2() {
        when(groupService.check("1")).thenReturn(-1);
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
    @DisplayName("When id not exist , Http status is 404")
    void schliessen1() {
        ResponseEntity<String> schliessen = restcontroller.schliessen("asdasdasd");
        assertThat(schliessen.getStatusCode().toString()).contains("404");
    }



    @Test
    @DisplayName("When all ok , Http status is 200")
    void schliessen2() {
        when(groupService.check("1")).thenReturn(1);
        when(groupService.findByGroupId(1)).thenReturn(new Gruppe("p",List.of("aa")));
        ResponseEntity<String> schliessen = restcontroller.schliessen("1");
        assertThat(schliessen.getStatusCode().toString()).contains("200");
    }

    @Test
    @DisplayName("When id is null , Http status is 404")
    void getAllGruppeInfo() {
        when(groupService.check(null)).thenReturn(-1);
        ResponseEntity<ApiGruppeInfo> allGruppeInfo = restcontroller.getAllGruppeInfo(null);
        assertThat(allGruppeInfo.getStatusCode().toString()).contains("404");

    }

    @Test

    @DisplayName("When all ok , Http status is 200")
    void getAllGruppeInfo1() {

        Gruppe gruppe = new Gruppe(1,"ss",List.of("44"),Set.of(),false);
        when(groupService.check("1")).thenReturn(1);
        when(groupService.findByGroupId(1)).thenReturn(gruppe);
        //when(Gruppe.createGruppe("a",List.of())).thenReturn(new Gruppe(1,"a",List.of("a"),Set.of(),false));
        ResponseEntity<ApiGruppeInfo> allGruppeInfo = restcontroller.getAllGruppeInfo("1");
        assertThat(allGruppeInfo.getStatusCode().toString()).contains("200");
    }


    @Test
    @DisplayName("When id not exist , Http status is 404")
    void getAllGruppeInfo2() {
        when(groupService.check("9")).thenReturn(-1);
        ResponseEntity<ApiGruppeInfo> allGruppeInfo = restcontroller.getAllGruppeInfo("9");
        assertThat(allGruppeInfo.getStatusCode().toString()).contains("404");
    }
}

