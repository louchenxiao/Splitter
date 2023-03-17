package com.example.splitter.web;

import com.example.splitter.domain.*;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.SplitterService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
    public String login(OAuth2AuthenticationToken auth, Model m) {
        String login = auth.getPrincipal().getAttribute("login");
        m.addAttribute("name", login);
        Person person = personService.creatPerson(login);
        List<Gruppe> allGruppe = groupService.getAllGruppe(person.getList());
        m.addAttribute("person", person);
        m.addAttribute("groupList", allGruppe);
        return "homePage";

    }

    @GetMapping("/rechnungsDetails/{id}")
    public String getRehung(@PathVariable("id") Integer id, Model model) {
        Gruppe group = groupService.findByGroupId(id);
        Set<Rechnung> allRechnung = group.getRechnungSet();
        model.addAttribute("allRechnung", allRechnung);
        model.addAttribute("group", group);
        return "rechnungsDetails";
    }

    @PostMapping("/rechnungsDetails/{id}")
    public String AddRechnung(String rechnungName,
                              String money, String payer,
                              @RequestParam(value = "personList", required = false) Set<String> personList,
                              @PathVariable("id") Integer id) {
        if (rechnungName.isEmpty() || rechnungName.isBlank() || personList.isEmpty()) {
            return "redirect:/rechnungsDetails/{id}";
        }
        Gruppe byGroupId = groupService.findByGroupId(id);
        if (!byGroupId.getGeschlossen()){
            byGroupId.addRechnung(rechnungName, new BigDecimal(money), payer, personList);
        }
        groupService.save(byGroupId);


        return "redirect:/rechnungsDetails/{id}";
    }


    @GetMapping("/createGruppe/{name}")
    public String createGruppe(@PathVariable("name") String name) {
        return "createGruppe";
    }

    @PostMapping("/createGruppe/{name}")
    public String createGruppe(@PathVariable String name, String gruppeName, @RequestParam("name[]") List<String> names) {
        names.add(name);
        List<String> strings = names.stream().filter(e -> !e.isEmpty()).filter(e -> !e.isBlank()).distinct().toList();
        if (strings.size() == 0) {
            return "redirect:/createGruppe/{name}";
        }
        if (gruppeName.isEmpty() || gruppeName.isBlank()) {
            return "redirect:/createGruppe/{name}";
        }
        List<Person> personByList = personService.createPersonByList(strings);
        groupService.create(gruppeName, strings);
        Integer id = groupService.findAll().stream().max(Comparator.comparing(Gruppe::getId)).get().getId();
        for (Person p : personByList) {
            personService.addGruppeId(id, p);
        }
        return "redirect:/";
    }

    @GetMapping("/result/{id}")
    public String getResult(@PathVariable Integer id, Model model) {
        List<String> personen = groupService.findByGroupId(id).getPersonen();
        List<Person> personByList = personService.createPersonByList(personen);
        Set<Rechnung> rechnungSet = groupService.findByGroupId(id).getRechnungSet();
        List<Result> result = splitterService.result(personByList,rechnungSet.stream().toList());
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/schliessen/{id}")
    public String schliessenPage(@PathVariable Integer id) {
        groupService.closeGruppe(id);
        return "schliessen";
    }
}



































