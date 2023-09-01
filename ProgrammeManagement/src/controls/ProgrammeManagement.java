package controls;
import adt.ConcurrentLinkedHashMap;
import adt.LinkedList;
import entities.*;

import java.util.Iterator;

/**
 *
 * @author Chan Zhi Yang
 */
public class ProgrammeManagement {
    private ConcurrentLinkedHashMap<String, Programme> programmes;

    public ProgrammeManagement() {
        programmes = new ConcurrentLinkedHashMap<>();
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
        if (programme != null) {
            programme.setProgrammeName(newProgrammeName);
            return true;
        }
        return false;
    }

    public ConcurrentLinkedHashMap<String, Programme> getProgrammes() {
        return programmes;
    }

    public void listAllProgramme(){
        Iterator<String> iterator = programmes.iteratorWithKeys();

        while (iterator.hasNext()) {
            String programKey = iterator.next();
            Programme programme = programmes.getValue(programKey);

            System.out.print("{ Programme Code: " + programme.getProgrammeCode() + " , ");
            System.out.println("Programme Name: " + programme.getProgrammeName() + " } ");

        }
    }

    public void addTutorialGroupToProgramme(String programmeCode, TutorialGroup tutorialGroup) {
        Programme programme = programmes.getValue(programmeCode);
        if (programme != null) {
            programme.addTutorialGroup(tutorialGroup);
        }
    }

    public TutorialGroup removeTutorialGroupFromProgramme(String programmeCode, String tutorialGroupId) {
        Programme programme = programmes.getValue(programmeCode);
        if (programme != null) {
            return programme.removeTutorialGroup(tutorialGroupId);
        }
        return null;
    }

    public void listAllTutorialGroupsForProgramme(String programmeCode) {
        Programme programme = programmes.getValue(programmeCode);

        if (programme != null) {
            LinkedList<TutorialGroup> tutorialGroups = programme.getTutorialGroups();

            if (tutorialGroups != null) {
                Iterator<TutorialGroup> iterator = tutorialGroups.iterator();

                while (iterator.hasNext()) {
                    TutorialGroup group = iterator.next();
                    System.out.println("{");
                    System.out.println("Tutorial Group ID: " + group.getGroupID());
                    System.out.println("Tutorial Group Name: " + group.getGroupName());
                    System.out.println("Tutorial Group Study Year: " + group.getStudyYear());
                    System.out.println("}");
                }
            }
        }
    }

}
