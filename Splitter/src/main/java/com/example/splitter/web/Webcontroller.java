package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.domain.Result;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.Ueberweisung;
import org.javamoney.moneta.Money;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Webcontroller {

    private final Ueberweisung ueberweisung;
    private final GroupService groupService;
    private final PersonService personService;

    public Webcontroller(Ueberweisung ueberweisung, GroupService groupService, PersonService personService) {
        this.ueberweisung = ueberweisung;
        this.groupService = groupService;
        this.personService = personService;
    }

    @GetMapping("/")
    public  String login(OAuth2AuthenticationToken auth, Model m){
        String login = auth.getPrincipal().getAttribute("login");
        m.addAttribute("name", login);
        Person person = personService.creatPerson(login);
        List<Gruppe> allGruppe = groupService.getAllGruppe(person.getGroupIdList());
        m.addAttribute("person",person);
        m.addAttribute("groupList",allGruppe);
        return "homepage";
    }

    @GetMapping("/rechnungsDetails/{id}")
    public String getRehung(@PathVariable("id") Integer id, Model model){
        Gruppe group = groupService.findByGroupId(id);
        List<Rechnung> allRechnung = group.getRechnungList();
        model.addAttribute("allRechnung",allRechnung);
        model.addAttribute("group",group);
        return "rechnungsDetails" ;
    }

    @PostMapping("/rechnungsDetails/{id}")
    public String AddRechnung(String rechnungName,
                              String money,String payer,
                              @RequestParam(value = "personList", required = false) List<Person> personList,
                              @PathVariable("id") Integer id){
        Person person = personService.findPerson(payer);
        groupService.addRechnung(id,new Rechnung(rechnungName, Money.of(Long.parseLong(money),"EUR"),person,personList));
        return "redirect:/";
    }



    @GetMapping("/createGruppe/{name}")
    public String createGruppe(Model model,@PathVariable("name") String name){
        return "createGruppe";
    }

    @PostMapping("/createGruppe/{name}")
    public String createGruppe(@PathVariable String name,String gruppeName,@RequestParam("name[]") List<String> names){
        Person person = personService.findPerson(name);
        List<Person> personByList = personService.createPersonByList(names);
        personByList.add(person);
        Gruppe gruppe = groupService.create(gruppeName, personByList);
        for (Person p:personByList) {
            personService.addGruppeId(gruppe.getId(),p);
        }

        return "redirect:/";
    }

    @GetMapping("/result/{id}")
    public   String getResult(@PathVariable Integer id, Model model){
        List<Result> result = ueberweisung.result(groupService.findByGroupId(id));
        model.addAttribute("result",result);

        return "result" ;
    }

    @GetMapping("/schliessen/{id}")
    public String schliessenPage(@PathVariable Integer id){

        groupService.closeGruppe(id);
        return "schliessen";
    }



































}
