package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.service.Ueberweisung;
import org.javamoney.moneta.Money;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class Webcontroller {

    private Ueberweisung ueberweisung;

    public Webcontroller(Ueberweisung ueberweisung){
        this.ueberweisung = ueberweisung;
    }

    @GetMapping("/")
    public  String login(OAuth2AuthenticationToken auth, Model m){
        String login = auth.getPrincipal().getAttribute("login");
        m.addAttribute("name", login);
        Person p = ueberweisung.creatPerson(login);
        m.addAttribute("person",p);
        m.addAttribute("groupList",ueberweisung.personGroupeList(p));


       // System.out.println(m);
        return "homePage";
    }

    @GetMapping("/rechnungsDetails/{id}")
    public String getRechung(@PathVariable("id") Integer id, Model model){
        Gruppe gruppe = ueberweisung.findByGroupId(id);
        model.addAttribute("group",gruppe);
        //System.out.println(model);
        return "rechnungsDetails" ;
    }

    @PostMapping("/rechnungsDetails/{id}")
    public String AddRechnung(  String rechnungName, String money,Person payer,Model model, @PathVariable("id") Integer gruppenId){

        model.addAttribute("rechnungName",rechnungName);
        model.addAttribute("money",money );
        model.addAttribute("payer",payer);
        Gruppe byGroupId = ueberweisung.findByGroupId(gruppenId);
        List<Person> personList = byGroupId.getPersonList();
        long l = Long.parseLong(money);
        Rechnung rechnung1 = ueberweisung.createRechnung(rechnungName, Money.of(l ,"EUR"), payer, personList);

        //model.addAttribute("person",rechnung.getPersons());
        System.out.println(model);
        ueberweisung.addRechnung(gruppenId,rechnung1,LocalDateTime.now());
        //System.out.println(model);
        return "redirect:/";
    }



    @GetMapping("/createGruppe/{name}")
    public String createGruppe(Model model,@PathVariable("name") String name){
        return "createGruppe";
    }

    @PostMapping("/createGruppe/{name}")
    public String createGruppe(@PathVariable String name,String gruppeName,@RequestParam("name[]") List<String> names){
        List<Person> personByList = ueberweisung.createPersonByList(names);
        personByList.add(ueberweisung.creatPerson(name));
        Gruppe group = ueberweisung.createGroup(gruppeName, personByList);
        for (Person p:personByList) {
            ueberweisung.addGruppeId(group.getId(), p);
        }
        System.out.println(group);
        return "redirect:/";
    }

































}
