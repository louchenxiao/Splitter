package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.helper.WithMockOAuth2User;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.Ueberweisung;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Webcontroller.class)
class WebcontrollerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    Ueberweisung ueberweisung;

    @MockBean
    PersonService personService;

    @MockBean
    GroupService groupService;

    @BeforeEach
    void setup() {
        Person person = new Person("Erwin_Lindemann");
        Gruppe gruppe = new Gruppe(33,"Tour",List.of(person));


    }
    @Test
    @DisplayName("Die private Seite ist für nicht-authentifizierte User nicht erreichbar")
    void loginFailed() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(mvcResult.getResponse().getRedirectedUrl())
                .contains("oauth2/authorization/github");

    }



    @Test
    @DisplayName("Status ist 200 and ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void loginSuccess() throws Exception {

        Person mockPerson = mock(Person.class);


        when(personService.creatPerson("Erwin_Lindemann")).thenReturn(mockPerson);
        when(groupService.getAllGruppe(any())).thenReturn(List.of());
        when(mockPerson.getGroupIdList()).thenReturn(List.of());

        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", "Erwin_Lindemann"))
                .andExpect(model().attribute("person", mockPerson))
                .andExpect(model().attribute("groupList", List.of()))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).contains("Erwin_Lindemann");
        assertThat(contentAsString).contains("href=\"/createGruppe/Erwin_Lindemann/\">createGruppe");


    }

    @Test
    @DisplayName("Status ist 200 and html action is ok ")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void getRechnung() throws Exception{
        Person person = new Person("Erwin_Lindemann");
        Gruppe gruppe = new Gruppe(33,"Tour",List.of(person));

        when(groupService.findByGroupId(33)).thenReturn(gruppe);

        MvcResult mvcResult = mvc.perform(get("/rechnungsDetails/{id}", 33))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allRechnung", List.of()))
                .andExpect(model().attribute("group", gruppe))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).contains("Erwin_Lindemann");
        assertThat(contentAsString).contains("action=\"/rechnungsDetails/33/\"");


    }



    @Test
    @DisplayName("Status ist 302, redirectedUrl is ok ")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void addRechnung() throws Exception{
        Person person = new Person("Erwin_Lindemann");
        when(personService.findPerson("Erwin_Lindemann")).thenReturn(person);

        MvcResult mvcResult = mvc.perform(post("/rechnungsDetails/{id}", 33)
                .param("rechnungName", "Tour")
                .param("money", "100")
                .param("payer", "Ellen").param("personList", "Erwin_Lindemann")
                .param("id", "33").with(csrf())).andExpect(status().is(302)).andReturn();

        verify(groupService,times(1))
                .addRechnung(33,new Rechnung("Tour", Money.of(100,"EUR"),any(),any()));

        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        assertThat(redirectedUrl).isEqualTo("/");

    }

    @Test
    @DisplayName("Status ist 200 and ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void createGruppe() throws Exception{
        mvc.perform(get("/createGruppe/{name}","Erwin_Lindemann"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Status ist 302 , html text ok, redirectedUrl ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void testCreateGruppe() throws Exception {
        Person person = new Person("Erwin_Lindemann");
        Gruppe gruppe = new Gruppe(33,"Tour",List.of(person));

        when(personService.createPersonByList(List.of("Erwin_Lindemann"))).thenReturn(List.of(person));
        when(personService.findPerson("Erwin_Lindemann")).thenReturn(person);
        when(groupService.create("Tour",List.of(person))).thenReturn(gruppe);


        MvcResult mvcResult = mvc.perform(post("/createGruppe/{name}", "Erwin_Lindemann")
                        .param("name", "Erwin_Lindemann")
                        .param("gruppeName", "Tour")
                        .param("name[]", "Tour1")
                        .with(csrf()))
                .andExpect(status().is(302))
                .andReturn();

        verify(personService).addGruppeId(33,person);
        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        assertThat(redirectedUrl).isEqualTo("/");
    }

    @Test
    @DisplayName("Status ist 200 and html text is ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void getResult() throws Exception{

        MvcResult result = mvc.perform(get("/result/{id}", 33))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", List.of()))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        assertThat(contentAsString).contains("receiver").contains("Giver").contains("Money");

    }

    @Test
    @DisplayName("Status ist 200 and html text is ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void schliessenPage() throws Exception{
        MvcResult mvcResult = mvc.perform(get("/schliessen/{id}", 22))
                .andExpect(status().isOk())
                .andReturn();
        verify(groupService).closeGruppe(22);;

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).contains("Does it really want to close");
    }

}