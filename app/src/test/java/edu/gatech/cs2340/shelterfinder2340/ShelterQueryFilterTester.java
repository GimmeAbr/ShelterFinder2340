package edu.gatech.cs2340.shelterfinder2340;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterQuery;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Sylvia Li on 2018/4/5.
 */

public class ShelterQueryFilterTester {
    private Shelter maleShelter;
    private Shelter womenShelter;
    private Shelter childShelter;
    private Shelter familyShelter;
    private Shelter anyoneShelter;
    private Shelter youngAdultShelter;
    private Shelter childrenAndYoungAdult;
    private Shelter menShelter;
    private Shelter femaleChildrenShelter;
    private List<Shelter> testShelters;

    @Before
    public void setUp() {
        testShelters = new ArrayList<>();
        maleShelter = new Shelter("MaleShelter", "Men", "Sunset Boulevard", "123", 1.5, 2.6, "93 Men", "Abhj");
        womenShelter = new Shelter("FemaleShelter", "Women", "Hogwarts believe it or not", "235", 10.8, 6.9, "15 Women", "dhajkdhf");
        childShelter = new Shelter("ChildShelter", "Children", "House on the Hill", "123", 1.5, 2.6, "107 Children", "Abhj");
        familyShelter = new Shelter("FamilyShelter", "Families", "Sky full of Stars", "123", 1.5, 2.6, "93 Men", "Abhj");
        anyoneShelter = new Shelter("AnyoneShelter", "Anyone", "My heart", "123", 1.5, 2.6, "93 Men", "Abhj");
        youngAdultShelter = new Shelter("YoungAdultShelter", "Young Adults", "Bagend", "123", 1.5, 2.6, "93 Men", "Abhj");
        childrenAndYoungAdult = new Shelter("ChildrenAndYoungAdultShelter", "Young Adults/Children", "Bagend", "123", 1.5, 2.6, "93 Men", "Abhj");
        menShelter = new Shelter("MenShelter", "Men", "Ravendell", "123", 1.5, 2.6, "93 Men", "Abhj");
        femaleChildrenShelter = new Shelter("FemaleChildrenShelter", "Women/Children", "Midgard", "123", 1.5, 2.6, "93 Men", "Abhj");
        testShelters.add(maleShelter);
        testShelters.add(womenShelter);
        testShelters.add(childShelter);
        testShelters.add(familyShelter);
        testShelters.add(anyoneShelter);
        testShelters.add(youngAdultShelter);
        testShelters.add(anyoneShelter);
        testShelters.add(childrenAndYoungAdult);
        testShelters.add(menShelter);
        testShelters.add(femaleChildrenShelter);
        Model.getInstance().setSheltersList(testShelters);
    }

    @Test
    public void testMale() {
        ShelterQuery maleQuery = new ShelterQuery(false, false, true, false, false, false, "");
        List<Shelter> maleResults = new ArrayList<>();
        maleResults.add(maleShelter);
        maleResults.add(menShelter);
        assertEquals(maleResults, maleQuery.filterShelter(testShelters));
    }

    @Test
    public void testFemale() {
        ShelterQuery femaleQuery = new ShelterQuery(false, false, false, true, false, false, "");
        List<Shelter> femaleResults = new ArrayList<>();
        femaleResults.add(womenShelter);
        femaleResults.add(femaleChildrenShelter);
        assertEquals(femaleResults, femaleQuery.filterShelter(testShelters));
    }

    @Test
    public void testFamily() {
        ShelterQuery familyQuery = new ShelterQuery(true, false, false, false, false, false, "");
        List<Shelter> familyResults = new ArrayList<>();
        familyResults.add(familyShelter);
        assertEquals(familyResults, familyQuery.filterShelter(testShelters));
    }

    @Test
    public void testChildren() {
        ShelterQuery childrenQuery = new ShelterQuery(false, false, false, false, true, false, "");
        List<Shelter> childrenResults = new ArrayList<>();
        childrenResults.add(childShelter);
        childrenResults.add(childrenAndYoungAdult);
        childrenResults.add(femaleChildrenShelter);
        assertEquals(childrenResults, childrenQuery.filterShelter(testShelters));
    }

    @Test
    public void testYoungAdults() {
        ShelterQuery youngQuery = new ShelterQuery(false, false, false, false, false, true, "");
        List<Shelter> youngResults = new ArrayList<>();
        youngResults.add(youngAdultShelter);
        youngResults.add(childrenAndYoungAdult);
        assertEquals(youngResults, youngQuery.filterShelter(testShelters));
    }

    @Test
    public void testChildrenAndYoungAdults() {
        ShelterQuery childrenYoungQuery = new ShelterQuery(false, false, false, false, true, true, "");
        List<Shelter> childrenYoungResults = new ArrayList<>();
        childrenYoungResults.add(childShelter);
        childrenYoungResults.add(youngAdultShelter);
        childrenYoungResults.add(childrenAndYoungAdult);
        childrenYoungResults.add(femaleChildrenShelter);
        assertEquals(childrenYoungResults, childrenYoungQuery.filterShelter(testShelters));
    }

    @Test
    public void testWomenAndChildren() {
        ShelterQuery womenChildrenQuery = new ShelterQuery(false, false, false, true, true, false, "");
        List<Shelter> womenChildrenResults = new ArrayList<>();
        womenChildrenResults.add(womenShelter);
        womenChildrenResults.add(childShelter);
        womenChildrenResults.add(childrenAndYoungAdult);
        womenChildrenResults.add(femaleChildrenShelter);
        assertEquals(womenChildrenResults, womenChildrenQuery.filterShelter(testShelters));
    }

    @Test
    public void testFilterName() {
        ShelterQuery nameQuery = new ShelterQuery(false, false, false, false, false, false, "Male");
        List<Shelter> nameResults = new ArrayList<>();
        nameResults.add(maleShelter);
        assertEquals(nameResults, nameQuery.filterShelter(testShelters));
    }

    @Test
    public void testAnyone() {
        ShelterQuery anyoneQuery = new ShelterQuery(false, true, false, false, false, false, "");
        assertEquals(testShelters, anyoneQuery.filterShelter(testShelters));
    }
}
