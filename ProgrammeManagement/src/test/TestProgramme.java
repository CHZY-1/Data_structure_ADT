package test;

import  adt.LinkedList;
import entities.*;
import java.util.Iterator;

//Author: Chan Zhi Yang

public class TestProgramme {

    public static void main(String[] args) {

        Programme program1 = new Programme("P001", "Computer Science");
        Programme program2 = new Programme("P002", "Electrical Engineering");

        // Create tutorial groups
        TutorialGroup group1 = new TutorialGroup("T001", "CS101", "1");
        TutorialGroup group2 = new TutorialGroup("T002", "EE201", "2");

        // Add tutorial groups to programs
        program1.addTutorialGroup(group1);
        program1.addTutorialGroup(group2);
        program2.addTutorialGroup(group2);

        // List all programs
        System.out.println("List of Programs:");
        System.out.println(program1.getProgrammeCode() + " - " + program1.getProgrammeName());
        System.out.println(program2.getProgrammeCode() + " - " + program2.getProgrammeName());

        // List all tutorial groups for a program
        System.out.println("\nTutorial Groups for " + program1.getProgrammeName() + ":");
        LinkedList<TutorialGroup> groupsForProgram1 = (LinkedList<TutorialGroup>)program1.getTutorialGroups();

        Iterator iteratorForProgram1 = groupsForProgram1.iterator();

        while (iteratorForProgram1.hasNext()){
            TutorialGroup group = (TutorialGroup) iteratorForProgram1.next();
            System.out.println(group.getGroupID() + " - " + group.getGroupName());
        }

        // Remove a tutorial group
        TutorialGroup removedGroup = program2.removeTutorialGroup("T002");
        System.out.println("\nRemoved Tutorial Group:");
        if (removedGroup != null) {
            System.out.println("Group ID: " + removedGroup.getGroupID());
            System.out.println("Group Name: " + removedGroup.getGroupName());
            System.out.println("Study Year " + removedGroup.getStudyYear());
        } else {
            System.out.println("Group not found.");
        }
    }
}
