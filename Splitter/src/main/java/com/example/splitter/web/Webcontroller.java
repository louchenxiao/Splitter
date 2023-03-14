package com.example.splitter.web;

import com.example.splitter.domain.*;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.SplitterService;
import org.javamoney.moneta.Money;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Webcontroller {

    private final SplitterService splitterService;
    private final GroupService groupService;
    private final PersonService personService;

    public Webcontroller(SplitterService splitterService, GroupService groupService, PersonService personService) {
        this.splitterService = splitterService;
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
        return "homePage";
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
        if (rechnungName.isEmpty()||rechnungName.isBlank()||personList.isEmpty()){
            return "redirect:/rechnungsDetails/{id}";
        }
        groupService.addRechnung(id,new Rechnung(rechnungName, Money.of(Long.parseLong(money),"EUR"),person,personList));
        return "redirect:/rechnungsDetails/{id}";
    }



    @GetMapping("/createGruppe/{name}")
    public String createGruppe(@PathVariable("name") String name){
        return "createGruppe";
    }

    @PostMapping("/createGruppe/{name}")
    public String createGruppe(@PathVariable String name, String gruppeName, @RequestParam("name[]") List<String> names){
        List<String> strings = names.stream().filter(e -> !e.isEmpty()).filter(e -> !e.isBlank()).toList();
        if (strings.size()==0){
            return "redirect:/createGruppe/{name}";
        }
        if (gruppeName.isEmpty()||gruppeName.isBlank()){
            return "redirect:/createGruppe/{name}";
        }
        Person person = personService.findPerson(name);
        List<Person> personByList = personService.createPersonByList(strings);
        personByList.add(person);
        Gruppe gruppe = groupService.create(gruppeName, personByList);
        for (Person p:personByList) {
            personService.addGruppeId(gruppe.getId(),p);
        }

        return "redirect:/";
    }

    @GetMapping("/result/{id}")
    public   String getResult(@PathVariable Integer id, Model model){
        List<Result> result = splitterService.result(groupService.findByGroupId(id));
        model.addAttribute("result",result);

        return "result" ;
    }

    @GetMapping("/schliessen/{id}")
    public String schliessenPage(@PathVariable Integer id){

        groupService.closeGruppe(id);
        return "schliessen";
    }



































}
