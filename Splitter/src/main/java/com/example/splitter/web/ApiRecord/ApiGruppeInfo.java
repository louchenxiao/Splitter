package com.example.splitter.web.ApiRecord;

import java.util.List;

public record ApiGruppeInfo(String gruppe, String name, List<String> personen, Boolean geschlossen, List<Auslage> ausgaben) {
}
