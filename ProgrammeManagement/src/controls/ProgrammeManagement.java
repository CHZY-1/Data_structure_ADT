package controls;

import adt.ConcurrentHashMapWithLinkedLists;
import adt.MapInterface;
import entities.*;

import java.util.Iterator;

/**
 * @author Chan Zhi Yang
 */

public class ProgrammeManagement {

    // There are many programmes
    private MapInterface<String, Programme> programmes;

    public ProgrammeManagement() {

        // Create the data structure with default capacity 31 and load factor of 0.75
        programmes = new ConcurrentHashMapWithLinkedLists<>();
    }

    public void addProgramme(String programmeCode, String programmeName) {
        Programme programme = new Programme(programmeCode, programmeName);
        programmes.put(programmeCode, programme);
    }

    public Programme removeProgramme(String programmeCode) {
        return programmes.remove(programmeCode);
    }

    public Programme findProgramme(String programmeCode) {
        return programmes.getValue(programmeCode);
    }

    public boolean amendProgrammeDetails(String programmeCode, String newProgrammeName) {
        Programme programme = programmes.getValue(programmeCode);

        // check if programme exists
        if (programme != null) {
            programme.setProgrammeName(newProgrammeName);
            return true;
        }else{
            return false;
        }
    }

    public boolean addTutorialGroupToProgramme(String programmeCode, TutorialGroup tutorialGroup) {
        Programme programme = programmes.getValue(programmeCode);

        boolean success = false;

        if (programme != null) {
            success = programme.addTutorialGroup(tutorialGroup);
        }else{
            System.out.println("Programme Not found in the system");
        }

        return success;
    }

    public TutorialGroup removeTutorialGroupFromProgramme(String programmeCode, String tutorialGroupId) {

        Programme programme = programmes.getValue(programmeCode);
        if (programme != null) {
            return programme.removeTutorialGroup(tutorialGroupId);
        }
        return null;
    }

    public Iterator getAllProgrammesIterator(){
        return ((ConcurrentHashMapWithLinkedLists<String, Programme>) programmes).iteratorWithKeys();
    }


}
