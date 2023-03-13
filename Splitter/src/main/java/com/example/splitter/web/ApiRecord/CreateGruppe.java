package com.example.splitter.web.ApiRecord;


import java.util.Arrays;

public record CreateGruppe(String name, String... personen) {

    @Override
    public String toString() {
        return "CreateGruppe{" +
                "name='" + name + '\'' +
                ", personen=" + Arrays.toString(personen) +
                '}';
    }
}






