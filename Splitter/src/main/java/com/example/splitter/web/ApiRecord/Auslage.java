package com.example.splitter.web.ApiRecord;

import java.util.Set;

public record Auslage(String grund, String glaeubiger, int cent, Set<String> schuldner) {
}
