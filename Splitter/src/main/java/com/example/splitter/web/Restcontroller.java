package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;
import com.example.splitter.domain.Person;
import com.example.splitter.domain.Rechnung;
import com.example.splitter.domain.Result;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.SplitterService;
import com.example.splitter.web.ApiRecord.ApiAusgleich;
import com.example.splitter.web.ApiRecord.ApiGruppeInfo;
import com.example.splitter.web.ApiRecord.Auslage;
import com.example.splitter.web.ApiRecord.CreateGruppe;
import org.javamoney.moneta.Money;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RestController
public class Restcontroller {
    private final GroupService groupService;
    private final PersonService personService;
    private final SplitterService splitterService;

    public Restcontroller(GroupService groupService, PersonService personService, SplitterService splitterService) {
        this.groupService = groupService;
        this.personService = personService;
        this.splitterService = splitterService;
    }

    @PostMapping("/api/gruppen")
    public ResponseEntity<String> createGruppe(@RequestBody CreateGruppe createGruppe) {
        List<Person> personList = new ArrayList<>();

        if (createGruppe.name()==null||createGruppe.personen()==null||createGruppe.personen().length==0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (String name : createGruppe.personen()) {
            personList.add(personService.creatPerson(name));
        }


        Gruppe gruppe = groupService.create(createGruppe.name(), personList);

        return new ResponseEntity<>(gruppe.getId().toString(), HttpStatus.CREATED);

    }

    @PostMapping("/api/gruppen/{id}/auslagen")
    public ResponseEntity<String> getAuslagen(@RequestBody Auslage auslage, @PathVariable("id") String id){
        Integer check = groupService.check(id);
        if (check==-1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (auslage.grund()==null||auslage.glaeubiger()==null||auslage.cent()<=0||
                auslage.schuldner()==null||auslage.schuldner().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (groupService.getStatus(check)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        List<Person> personList = new ArrayList<>();
        for (String name : auslage.schuldner()) {
            personList.add(personService.creatPerson(name));
        }
        Rechnung rechnung = new Rechnung(auslage.grund(), Money.of(auslage.cent(),"EUR")
                ,personService.findPerson(auslage.glaeubiger()),personList);
        groupService.addRechnung(check,rechnung);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/api/gruppen/{id}/ausgleich")
    public ResponseEntity<List<ApiAusgleich>> getAusgleichszahlungen(@PathVariable("id")String id){
        Integer check = groupService.check(id);
        if (check==-1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Gruppe byGroupId = groupService.findByGroupId(check);

        List<ApiAusgleich> apiAusgleiches =new ArrayList<>();
        List<Result> result = splitterService.result(byGroupId);
        for (Result r:result) {
            apiAusgleiches.add(new ApiAusgleich(r.getGiver().getName(),r.getReceiver().getName(), r.getMoney().getNumber().intValue()));
        }
        return new ResponseEntity<>(apiAusgleiches,HttpStatus.OK);


    }

    @PostMapping("/api/gruppen/{id}/schliessen")
    public ResponseEntity<String>  schliessen (@PathVariable("id") String id){

        Integer check = groupService.check(id);
        if (check<=0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       Gruppe byGroupId = groupService.findByGroupId(check);

        byGroupId.setGeschlossen();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/gruppen/{id}")
    public ResponseEntity<ApiGruppeInfo> getAllGruppeInfo(@PathVariable("id") String id){
        Integer check = groupService.check(id);
        if (check==-1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Gruppe byGroupId = groupService.findByGroupId(check);

        List<String> personList = new ArrayList<>();
        for (Person p : byGroupId.getPersonen()) {
            personList.add(p.getName());
        }

        List<Auslage> auslagen =new ArrayList<>();

        for (Rechnung r:byGroupId.getRechnungList()) {
            auslagen.add(new Auslage(r.rechnungName(),r.payer().getName(),r.geld().getNumber().intValue(),r.persons().stream()
                    .map(Person::getName).collect(Collectors.toSet())));
        }


        return new ResponseEntity<>(new ApiGruppeInfo(byGroupId.getId().toString(),byGroupId.getName()
                ,personList,byGroupId.getGeschlossen(),auslagen),HttpStatus.OK);
    }
}
