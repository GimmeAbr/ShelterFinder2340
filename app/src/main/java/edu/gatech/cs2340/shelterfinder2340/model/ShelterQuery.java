package edu.gatech.cs2340.shelterfinder2340.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This represents the Query object used for Filtering
 * Created by Sylvia Li on 2018/3/22.
 */

public class ShelterQuery {
    private final boolean family;
    private final boolean anyone;
    private final boolean male;
    private final boolean female;
    private final boolean children;
    private final boolean young;
    private final String name;

    private final List<String> keyWord;

    /**
     * The ShelterQuery constructor
     *
     * @param family   whether "family" is a criteria
     * @param anyone   whether "anyone" is a criteria
     * @param male     whether "male" is a criteria
     * @param female   whether "female" is a criteria
     * @param children whether "children" is a criteria
     * @param young    whether "young adults" is a criteria
     * @param name     whether a specific name is searched
     */
    @SuppressWarnings("ConstructorWithTooManyParameters")
    public ShelterQuery(boolean family, boolean anyone, boolean male, boolean female,
                        boolean children, boolean young, String name) {
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

    /**
     * Filter the list of Shelters from Model
     *
     * @param shelterList the list of shelters (orginally)
     * @return the filtered list
     */
    public List<Shelter> filterShelter(List<Shelter> shelterList) {
        Collection<Shelter> backupShelters = new ArrayList<>();
        backupShelters.addAll(shelterList);
        if ((!name.isEmpty()) || male || female || children || young
                || anyone || family) {
            for (Shelter st : backupShelters) {
                String shelterN = st.getShelterName();
                if (!shelterN.contains(name)) {
                    shelterList.remove(st);
                }
                if (family || children || young
                        || female || male) {
                    boolean result = false;
                    for (String kw : keyWord) {
                        String gd = st.getGender();
                        result = result || gd.contains(kw);
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
