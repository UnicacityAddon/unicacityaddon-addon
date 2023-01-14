package com.rettichlp.unicacityaddon.base.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Data {

    private int bankBalance;
    private int cashBalance;
    private int jobBalance;
    private int jobExperience;
    private int payDayTime;
    private int serviceCount;
    private long firstAidDate;
    private List<TodolistEntry> todolist;
    private List<CoordlistEntry> coordlist;
    private Map<Integer, HouseData> houseData;
    private List<EquipLogEntry> equipList;
    private String carInfo;
}