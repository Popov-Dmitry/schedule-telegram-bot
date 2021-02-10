package com.github.PopovDmitry.scheduletelegrambot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class ScheduleParser {

    private String url = "https://nstu.ru/studies/schedule/schedule_classes";
    private List<String> faculties = new ArrayList<>();
    private Map<String, String> groups = new Hashtable<>();

    public void parseGroups() throws IOException {
        Document document = Jsoup.connect(url).get();

        Elements elements = document.getElementsByAttributeValue("class",
                "schedule__faculty-groups__item");
        elements.forEach(element -> groups.put(element.text(), element.attr("href")));

    }

}
