package edu.gatech.cs2340.shelterfinder2340.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sylvia Li on 2018/3/22.
 */

public class ShelterQuery {
    private boolean family;
    private boolean anyone;
    private boolean male;
    private boolean female;
    private boolean children;
    private boolean young;
    private String name;

    private ArrayList<String> keyWord;

    public ShelterQuery (boolean family, boolean anyone, boolean male, boolean female, boolean children, boolean young, String name){
        this.family = family;
        this.anyone = anyone;
        this.male = male;
        this.female = female;
        this.children = children;
        this.young = young;
        this.name = name;
        keyWord = new ArrayList<>();
        loadKey();
    }

    private void loadKey() {
        if (!anyone) {
            if (male) {
                keyWord.add("Men");
            }
            if (female) {
                keyWord.add("Women");
            }
            if (family) {
                keyWord.add("Families");
            }
            if (children) {
                keyWord.add("Children");
            }
            if (young) {
                keyWord.add("Young");
            }
        }
    }

    public List<Shelter> filterShelter(List<Shelter> shelterList) {
        List<Shelter> backupShelters = new ArrayList<Shelter>();
        backupShelters.addAll(shelterList);
        if (name.length() > 0 || male || female || children || young
                || anyone || family) {
            for (Shelter st : backupShelters) {
                if (!st.getShelterName().contains(name)) {
                    shelterList.remove(st);
                }
                if (family || children || young
                        || female || male) {
                    boolean result = false;
                    for (String kw : keyWord) {
                        result = result || st.getGender().contains(kw);
                    }
                    if (!result) {
                        shelterList.remove(st);
                    }
                }
            }
        }
        return shelterList;
    }
}
