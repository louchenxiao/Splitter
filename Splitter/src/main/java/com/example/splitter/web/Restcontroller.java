package com.example.splitter.web;

import com.example.splitter.domain.Gruppe;

import com.example.splitter.domain.Rechnung;
import com.example.splitter.domain.Result;
import com.example.splitter.service.GroupService;
import com.example.splitter.service.PersonService;
import com.example.splitter.service.SplitterService;
import com.example.splitter.web.ApiRecord.ApiAusgleich;
import com.example.splitter.web.ApiRecord.ApiGruppeInfo;
import com.example.splitter.web.ApiRecord.Auslage;
import com.example.splitter.web.ApiRecord.CreateGruppe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        List<String> personList = new ArrayList<>();
        if (createGruppe.name() == null || createGruppe.personen() == null || createGruppe.personen().length == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for (String name : createGruppe.personen()) {
            personList.add(personService.creatPerson(name).getName());
        }
        groupService.create(createGruppe.name(), personList);
        Integer id = groupService.findAll().stream().max(Comparator.comparing(Gruppe::getId)).get().getId();
        return new ResponseEntity<>(id.toString(), HttpStatus.CREATED);
    }


    @PostMapping("/api/gruppen/{id}/auslagen")
    public ResponseEntity<String> getAuslagen(@RequestBody Auslage auslage, @PathVariable("id") String id) {

        Integer check = groupService.check(id);
        if (check <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (auslage.grund() == null || auslage.glaeubiger() == null || auslage.cent() <= 0 ||
                auslage.schuldner() == null || auslage.schuldner().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (groupService.findByGroupId(check).getGeschlossen()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Rechnung rechnung = new Rechnung(auslage.grund(), new BigDecimal(auslage.cent())
                , auslage.glaeubiger(), auslage.schuldner());
        groupService.addRechnung(check, rechnung);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @GetMapping("/api/gruppen/{id}/ausgleich")
    public ResponseEntity<List<ApiAusgleich>> getAusgleichszahlungen(@PathVariable("id") String id) {

        Integer check = groupService.check(id);
        if (check <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Gruppe byGroupId = groupService.findByGroupId(check);
        List<ApiAusgleich> apiAusgleiches = new ArrayList<>();
        List<Result> result = splitterService.result(personService.createPersonByList(byGroupId.getPersonen()), byGroupId.getRechnungSet().stream().toList());
        for (Result r : result) {
            apiAusgleiches.add(new ApiAusgleich(r.getGiver(), r.getReceiver(), r.getMoney().intValue()));
        }
        return new ResponseEntity<>(apiAusgleiches, HttpStatus.OK);

    }

    @PostMapping("/api/gruppen/{id}/schliessen")
    public ResponseEntity<String> schliessen(@PathVariable("id") String id) {
        Integer check = groupService.check(id);
        if (check <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupService.closeGruppe(check);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/api/gruppen/{id}")
    public ResponseEntity<ApiGruppeInfo> getAllGruppeInfo(@PathVariable("id") String id){
        Integer check = groupService.check(id);
        if (check <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Gruppe byGroupId = groupService.findByGroupId(check);
        List<Auslage> auslagen =new ArrayList<>();
        for (Rechnung r:byGroupId.getRechnungSet()) {
            auslagen.add(new Auslage(r.getName(),r.getPayer(),r.getMoney().intValue(),r.getPersons()));
        }
        return new ResponseEntity<>(new ApiGruppeInfo(byGroupId.getId().toString(),byGroupId.getName()
                ,byGroupId.getPersonen(),byGroupId.getGeschlossen(),auslagen),HttpStatus.OK);
    }
}






