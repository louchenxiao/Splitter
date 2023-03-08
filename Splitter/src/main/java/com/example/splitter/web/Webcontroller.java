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
       Person p = new Person(login);
        Person p1 = new Person("wwww");
       ueberweisung.createGroup(1, LocalDateTime.now(),p);
       ueberweisung.createGroup(2, LocalDateTime.now(),p);
        Rechnung rechnung = ueberweisung.createRechnung("a", Money.of(111, "EUR"), p1, List.of(p));
        ueberweisung.addRechnung(1,rechnung,LocalDateTime.now());
        ueberweisung.addPerson(1,p1,LocalDateTime.now());
       m.addAttribute("person",p);
       m.addAttribute("groupList",ueberweisung.personGroupeList(p));


       // System.out.println(m);
        return "homepage";
    }

    @GetMapping("/rechnungsDetails/{id}")
    public String getRechung(@PathVariable("id") Integer id, Model model){
        Gruppe gruppe = ueberweisung.findByGroupId(id);
        model.addAttribute("group",gruppe);
        //System.out.println(model);
        return "rechnungsDetails" ;
    }

    @GetMapping("/addPerson/{id}")
    public String getPerson(@PathVariable("id") Integer id, Model model){
        return "addPerson" ;
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


    @PostMapping("/addPerson/{id}")
    public String addPerson(String name, Model model, @PathVariable("id") Integer gruppenId){
        model.addAttribute("name",name);
        Person person = ueberweisung.createPerson(name);
        System.out.println(gruppenId);
        ueberweisung.addPerson(gruppenId,person, LocalDateTime.now());
        //System.out.println(model);
        return "redirect:/";
    }

































}
