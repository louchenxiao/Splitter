package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.helper.WithMockOAuth2User;
import com.example.splitter.service.GroupRepo;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.SplitterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    SplitterService splitterService;

    @MockBean
    PersonService personService;

    @MockBean
    GroupService groupService;


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
    void loginSuccess () throws Exception {
        Person mockPerson = mock(Person.class);

        when(personService.creatPerson("Erwin_Lindemann")).thenReturn(mockPerson);
        when(groupService.getAllGruppe(any())).thenReturn(List.of());

        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", "Erwin_Lindemann"))
                .andExpect(model().attribute("person", mockPerson))
                .andExpect(model().attribute("groupList", List.of()))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).contains("Erwin_Lindemann");
        assertThat(contentAsString).contains("/createGruppe/Erwin_Lindemann");


    }

    @Test
    @DisplayName("Status ist 200 and html action is ok ")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void getRechnung () throws Exception {
        Person person = new Person(1,"Erwin_Lindemann",List.of());
        Gruppe gruppe = new Gruppe(33, "Tour", List.of("Erwin_Lindemann"), Set.of(),false);

        when(groupService.findByGroupId(33)).thenReturn(gruppe);

        MvcResult mvcResult = mvc.perform(get("/rechnungsDetails/{id}", 33))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allRechnung", Set.of()))
                .andExpect(model().attribute("group", gruppe))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).contains("Erwin_Lindemann");
        assertThat(contentAsString).contains("action=\"/rechnungsDetails/33/\"");


    }


    @Test
    @DisplayName("Status ist 302, redirectedUrl is ok ")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void addRechnung () throws Exception {
        Person person = new Person(33,"Erwin_Lindemann",List.of());
        Gruppe gruppe = new Gruppe(33,"name",List.of("a"),Set.of(),true);
        //when(personService.findPerson("Erwin_Lindemann")).thenReturn(person);
        when(groupService.findByGroupId(33)).thenReturn(gruppe);


        MvcResult mvcResult = mvc.perform(post("/rechnungsDetails/{id}", 33)
                .param("rechnungName", "Tour")
                .param("money", "100")
                .param("payer", "Ellen").param("personList", "Erwin_Lindemann")
                .param("id", "33").with(csrf())).andExpect(status().is(302)).andReturn();


        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        assertThat(redirectedUrl).isEqualTo("/rechnungsDetails/33");

    }

    @Test
    @DisplayName("Status ist 200 and ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void createGruppe () throws Exception {
        mvc.perform(get("/createGruppe/{name}", "Erwin_Lindemann"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Status ist 302 , html text ok, redirectedUrl ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void test_CreateGruppe () throws Exception {

        Gruppe gruppe = new Gruppe(33, "Tour", List.of("Erwin_Lindemann"),Set.of(),false);


        when(groupService.findAll()).thenReturn(List.of(gruppe));
        MvcResult mvcResult = mvc.perform(post("/createGruppe/{name}", "Erwin_Lindemann")
                        .param("name", "Erwin_Lindemann")
                        .param("gruppeName", "Tour")
                        .param("name[]", "Tour1")
                        .with(csrf()))
                .andExpect(status().is(302))
                .andReturn();
        Person person =new Person(1,"Erwin_Lindemann",List.of());
        when(personService.createPersonByList(any())).thenReturn(List.of(person));
        //verify(personService).addGruppeId(33,person );
        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        assertThat(redirectedUrl).isEqualTo("/");

    }

    @Test
    @DisplayName("Status ist 200 and html text is ok")
    @WithMockOAuth2User(login = "Erwin_Lindemann")
    void getResult () throws Exception {
        Gruppe gruppe= new Gruppe("a",List.of());
        GroupRepo g = mock(GroupRepo.class);
        when(groupService.findByGroupId(33)).thenReturn(gruppe);
        when(g.findByID(33)).thenReturn(gruppe);
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
    void schliessenPage () throws Exception {
        MvcResult mvcResult = mvc.perform(get("/schliessen/{id}", 22))
                .andExpect(status().isOk())
                .andReturn();
        verify(groupService).closeGruppe(22);

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).contains("Do you really want to close this group?");
    }

}


