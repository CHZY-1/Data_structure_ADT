package test;


import entities.*;
import controls.ProgrammeManagement;


public class TestProgrammeManagement {
    public static void main(String[] args) {
        ProgrammeManagement programmeManager = new ProgrammeManagement();

        // programmes
        programmeManager.addProgramme("P001", "Computer Science");
        programmeManager.addProgramme("P002", "Electrical Engineering");

        // List all programmes
        System.out.println(" List of Programmes:");
        programmeManager.listAllProgramme();

        // Create some tutorial groups
        TutorialGroup group1 = new TutorialGroup("T001", "Group 1", "2023");
        TutorialGroup group2 = new TutorialGroup("T002", "Group 2", "2024");

        // Add tutorial groups to programmes
        programmeManager.addTutorialGroupToProgramme("P001", group1);
        programmeManager.addTutorialGroupToProgramme("P002", group2);

        // List all tutorial groups for a programme
        System.out.println("\n Tutorial Groups for Programme P001:");
        programmeManager.listAllTutorialGroupsForProgramme("P001");

        // Remove a tutorial group from a programme
        TutorialGroup removedGroup = programmeManager.removeTutorialGroupFromProgramme("P001", "T001");
        if (removedGroup != null) {
            System.out.println("Removed Tutorial Group:");
            System.out.println("Tutorial Group ID: " + removedGroup.getGroupID());
            System.out.println("Tutorial Group Name: " + removedGroup.getGroupName());
        } else {
            System.out.println("Tutorial Group not found in Programme P001.");
        }

        // List all tutorial groups for a programme after removal
        System.out.println("Tutorial Groups for Programme P001 after Removal:");
        programmeManager.listAllTutorialGroupsForProgramme("P001");
    }
}
