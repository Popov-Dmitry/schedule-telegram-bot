package com.github.PopovDmitry.scheduletelegrambot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    public String parseSchedule(String groupName, Day day) throws Exception {
        if (!groups.containsKey(groupName)) {
            throw new Exception("Group not found");
        }
        Document document = Jsoup.connect(groups.get(groupName)).get();
        //System.out.println(groups.get(groupName));
        Elements fullTable = document.getElementsByAttributeValue("class", "schedule__table-body");
        Elements subjectsElements = fullTable.get(0).child(day.ordinal()).child(1).children();

        StringBuilder schedule = new StringBuilder();
        for(int i = 0; i < subjectsElements.size(); i++) {
            if(subjectsElements.get(i).child(1).child(0).child(0).child(0).text().isEmpty()) { //проверка поля названия предмета на пустоту
                continue;
            }
            Elements innerSubjects = subjectsElements.get(i).child(1).children(); //несколько предметов в одно время
            Element currentSubject = null; //предмет на текущей недели
            String dataWeek;
            String subjectTeacherRoom;
            String teacher = "";
            String room = "";
            String subject = "";
            for(int j = 0; j < innerSubjects.size(); j ++) {
                if(!innerSubjects.get(j).child(0).child(0).child(0).hasAttr("data-week")) {
                    currentSubject = innerSubjects.get(j);

                    subjectTeacherRoom = currentSubject.child(0).child(0).text();
                    int childrenSize = currentSubject.child(0).child(0).childrenSize();
                    if(childrenSize == 2) {
                        teacher = currentSubject.child(0).child(0).child(0).text().trim();
                        room = currentSubject.child(0).child(0).child(1).text().trim();
                    }
                    if(childrenSize == 1) {
                        if(currentSubject.child(0).child(0).child(0).hasClass("schedule__table-class")) {
                            room = currentSubject.child(0).child(0).child(0).text().trim();
                        }
                        else {
                            teacher = currentSubject.child(0).child(0).child(0).text().trim();
                        }
                    }
                    if(childrenSize > 2) {
                        if(currentSubject.child(0).child(0).child(childrenSize - 1).hasClass("schedule__table-class")) {
                            room = currentSubject.child(0).child(0).child(childrenSize - 1).text().trim();
                            teacher = currentSubject.child(0).child(0).child(0).text().trim();
                            for (int t = 1; t < childrenSize - 1; t++) {
                                teacher += ", ";
                                teacher += currentSubject.child(0).child(0).child(t).text().trim();
                            }
                        }
                        else {
                            teacher = currentSubject.child(0).child(0).child(0).text().trim() + " ";
                            for (int t = 1; t < childrenSize - 1; t++) {
                                teacher += currentSubject.child(0).child(0).child(t).text().trim() + " ";
                            }
                        }
                    }

                    subject = subjectTeacherRoom.replace( " · ", "")
                                                .replace(teacher, "")
                                                .replace(room, "")
                                                .trim();
                }
                if(innerSubjects.get(j).child(0).child(0).child(0).attr("data-week").equalsIgnoreCase("current")) {
                    currentSubject = innerSubjects.get(j);

                    subjectTeacherRoom = currentSubject.child(0).child(0).text();
                    teacher = currentSubject.child(0).child(0).child(1).text();
                    room = currentSubject.child(0).child(0).child(2).text();
                    dataWeek = innerSubjects.get(j).child(0).child(0).child(0).text();

                    subject = subjectTeacherRoom.replace( " · " + teacher + " " + room, "")
                                                .replace(teacher, "")
                                                .replace(room, "")
                                                .replace(dataWeek + " ", "")
                                                .trim();
                }

                if(currentSubject != null) {
                    schedule.append(subjectsElements.get(i).child(0).text())
                            .append(" | ")
                            .append(subject)
                            .append(" | ")
                            .append(teacher)
                            .append(" | ")
                            .append(room)
                            .append("\n");
                    break;
                }
            }

        }

        return schedule.toString();
    }

    public String weekSchedule(String groupName) throws Exception {
        StringBuilder schedule = new StringBuilder();
        for(int i = 0; i < Day.values().length; i++) {
            schedule.append("------")
                    .append(Day.Ru.values()[i].toString())
                    .append("------")
                    .append("\n")
                    .append(parseSchedule(groupName, Day.values()[i]))
                    .append("\n");
        }

        return schedule.toString();
    }

}
