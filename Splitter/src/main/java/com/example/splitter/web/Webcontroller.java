package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.domain.Result;
import com.example.splitter.service.Ueberweisung;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "homePage";
    }

    @GetMapping("/rechnungsDetails/{id}")
    public String getRehung(@PathVariable("id") Integer id, Model model){
        List<Rechnung> allRechnung = ueberweisung.getAllRechnung(id);
        Gruppe group = ueberweisung.findByGroupId(id);
        model.addAttribute("allRechnung",allRechnung);
        model.addAttribute("group",group);
        return "rechnungsDetails" ;
    }

    @PostMapping("/rechnungsDetails/{id}")
    public String AddRechnung(String rechnungName,
                              String money,String payer,
                              @RequestParam(value = "personList", required = false) List<Person> personList,
                              @PathVariable("id") Integer id){
        Person person = ueberweisung.findPerson(payer);
        Rechnung rechnung = ueberweisung.addRechnung(id, rechnungName, money, person, personList);
        System.out.println(rechnung);
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

    @GetMapping("/result/{id}")
    public  @ResponseBody List<Result> getResult(@PathVariable Integer id, Model model){
        List<Result> result = ueberweisung.result(id);
        List<Person> personList = ueberweisung.findByGroupId(id).getPersonList();
        List<Rechnung> rechnungList = ueberweisung.getAllRechnung(id);
        List<Result> rrr =ueberweisung.second(ueberweisung.rechnen(personList,rechnungList)).stream().filter(e->!e.getMoney().isZero()).toList();
        System.out.println(personList);
        System.out.println(rechnungList);
        System.out.println(ueberweisung.rechnen(personList,rechnungList));
        System.out.println(rrr);
        //model.addAttribute("result",result);
        return result ;
    }



































}
