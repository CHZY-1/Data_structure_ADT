package controls;

import adt.ConcurrentHashMapWithLinkedLists;
import adt.LinkedList;
import entities.*;

import java.util.Iterator;

/**
 * @author Chan Zhi Yang
 */

public class ProgrammeManagement {

    // There are many programmes
    private ConcurrentHashMapWithLinkedLists<String, Programme> programmes;

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

    public void listAllProgramme(){

        Iterator<String> iterator = programmes.iteratorWithKeys(); // Concurrent HashMap Iterator

        if(!iterator.hasNext()){
            System.out.println("No programme existing in the system yet");
        }else{
            System.out.println("  All Programmes ");
            System.out.println("==========================================================");
            while (iterator.hasNext()) {
                String programKey = iterator.next();
                Programme programme = programmes.getValue(programKey);

                System.out.print("{ Programme Code: " + programme.getProgrammeCode() + " , ");
                System.out.println("Programme Name: " + programme.getProgrammeName() + " } ");
            }
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

    public void listAllTutorialGroupsForProgramme(String programmeCode) {
//        Programme programme = programmes.getValue(programmeCode);
//
//        if (programme != null) {
//            LinkedList<TutorialGroup> tutorialGroups = programme.getTutorialGroups();
//
//            if (tutorialGroups != null) {
//                Iterator<TutorialGroup> iterator = tutorialGroups.iterator();
//
//                if(iterator.hasNext()){
//
//                    System.out.println("   All Tutorial Groups for " + programmeCode);
//                    System.out.println("===============================================");
//
//                    while (iterator.hasNext()) {
//                        TutorialGroup group = iterator.next();
//                        System.out.print("{ ");
//                        System.out.print("Tutorial Group ID: " + group.getGroupID() + " , ");
//                        System.out.print("Tutorial Group Name: " + group.getGroupName() + " , ");
//                        System.out.print("Tutorial Group Study Year: " + group.getStudyYear());
//                        System.out.println(" } ");
//                    }
//
//                }else{
//                    System.out.println("No Tutorial Group exists in this Programme, Programme Code : " + programmeCode);
//                }
//            }
//        }
    }

    public void generateReport() {
//        Iterator<String> programmeIterator = programmes.iteratorWithKeys();
//
//        if (!programmeIterator.hasNext()) {
//            System.out.println("No programmes exist in the system yet");
//            return;
//        }
//
//        if (programmeIterator.hasNext()) {
//            System.out.println("\n\n               Programme Management Report");
//            System.out.println("===============================================================================");
//
//            int programmeCounter = 1;
//
//            while (programmeIterator.hasNext()) {
//                String programCode = programmeIterator.next();
//                Programme programme = programmes.getValue(programCode);
//
//                // Print headings
//                System.out.println("\nProgramme " + programmeCounter);
//                System.out.println("-----------------------------------------------");
//                System.out.println("Programme Code: " + programme.getProgrammeCode());
//                System.out.println("Programme Name: " + programme.getProgrammeName());
//                System.out.println("Total Tutorial Groups: " + programme.getTutorialGroups().getNumberOfEntries());
//
//                LinkedList<TutorialGroup> tutorialGroups = programme.getTutorialGroups();
//
//                if (tutorialGroups != null && !tutorialGroups.isEmpty()) {
//                    System.out.println("\nTutorial Groups for " + programme.getProgrammeCode() + " :");
//                    System.out.println("=======================================");
//                    System.out.printf("%-5s %-20s %-30s %-15s%n", "No.", "Group ID", "Group Name", "Study Year");
//                    System.out.println("---------------------------------------------------------------");
//
//                    Iterator<TutorialGroup> tutorialGroupIterator = tutorialGroups.iterator();
//
//                    int tutorialGroupCounter = 1;
//
//                    while (tutorialGroupIterator.hasNext()) {
//                        TutorialGroup tutorialGroup = tutorialGroupIterator.next();
//
//                        System.out.printf("%-5d %-20s %-30s %-15s%n", tutorialGroupCounter, tutorialGroup.getGroupID(),
//                                tutorialGroup.getGroupName(), tutorialGroup.getStudyYear());
//
//                        tutorialGroupCounter++;
//                    }
//                } else {
//                    System.out.println("No Tutorial Groups for this programme.");
//                }
//
//                programmeCounter++;
//            }
//        } else {
//            System.out.println("No programmes exist in the system yet");
//        }
    }

    public Iterator getAllProgrammesIterator(){
        return programmes.iteratorWithKeys();
    }

}
