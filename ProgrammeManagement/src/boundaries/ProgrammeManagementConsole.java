package boundaries;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import adt.LinkedList;
import controls.ProgrammeManagement;
import entities.Programme;
import entities.TutorialGroup;

/**
 *
 * @author Chan Zhi Yang
 *
 */

public class ProgrammeManagementConsole{

    private final ProgrammeManagement programmeManagement;
    private final Scanner scanner;

    public ProgrammeManagementConsole() {
        programmeManagement = new ProgrammeManagement();
        scanner = new Scanner(System.in);
    }

    // Start UI
    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nProgramme Management System");
            System.out.println("1. Manage Programmes");
            System.out.println("2. Manage Tutorial Groups in Programme");
            System.out.println("0. Exit");
            System.out.print("Enter your choice : ");

            int choice = getUserChoice(this.scanner);

            switch (choice) {
                case 1 -> manageProgrammes();
                case 2 -> manageTutorialGroups();
                case 0 -> isRunning = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Programme Management System has been terminated.");
    }

    // Sub-menu - Programme Management
    private void manageProgrammes() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nProgrammes Menu");
            System.out.println("1. Add a New Programme");
            System.out.println("2. Remove a Programme");
            System.out.println("3. Find Programme");
            System.out.println("4. Amend Programme Details");
            System.out.println("5. List All Programmes");
            System.out.println("6. Generate Report");
            System.out.println("7. Add Sample Programme");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(this.scanner);

            switch (choice) {
                case 1 -> addProgramme();
                case 2 -> removeProgramme();
                case 3 -> findProgramme();
                case 4 -> amendProgrammeDetails();
                case 5 -> listAllProgrammes();
                case 6 -> generateReports();
                case 7 -> {
                    addSampleProgramme();
                    System.out.println("Sample programme added");
                }
                case 0 -> isRunning = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Sub-menu - Manage Tutorial Group in Programme
    private void manageTutorialGroups() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nTutorial Groups Menu");
            System.out.println("1. Add a Tutorial Group to a Programme");
            System.out.println("2. Remove a Tutorial Group from a Programme");
            System.out.println("3. List All Tutorial Groups for a Programme");
            System.out.println("4. Add Sample Tutorial Group to Programmes");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(this.scanner);

            switch (choice) {
                case 1 -> addTutorialGroupToProgramme();
                case 2 -> removeTutorialGroupFromProgramme();
                case 3 -> listAllTutorialGroupsForProgramme();
                case 4 -> {
                    addSampleTutorialGroupToProgramme();
                    System.out.println("Sample Tutorial Group added");
                }
                case 0 -> isRunning = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Read User choice for menu
    private static int getUserChoice(Scanner scanner) {
        int choice = -1;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                choice = scanner.nextInt();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }

        scanner.nextLine();
        return choice;
    }

    // Sample data to test
    private void addSampleProgramme(){
        programmeManagement.addProgramme("RCS", "Computer Science");
        programmeManagement.addProgramme("RDS", "Data Science");
        programmeManagement.addProgramme("RIT", "Information Technology");
    }

    public void addSampleTutorialGroupToProgramme(){
        programmeManagement.addTutorialGroupToProgramme("RCS", new TutorialGroup("CS-G001", "Computer Science Group 1", "2"));
        programmeManagement.addTutorialGroupToProgramme("RCS", new TutorialGroup("CS-G002", "Computer Science Group 2", "2"));

        programmeManagement.addTutorialGroupToProgramme("RDS", new TutorialGroup("DS-G001", "Data Science Group 1", "2"));
        programmeManagement.addTutorialGroupToProgramme("RDS", new TutorialGroup("DS-G002", "Data Science Group 2", "2"));
    }

    // Add programme
    private void addProgramme() {
        System.out.print("Enter Programme Code: ");
        String programmeCode = scanner.nextLine().trim();
        System.out.print("Enter Programme Name: ");
        String programmeName = scanner.nextLine().trim();

        programmeManagement.addProgramme(programmeCode, programmeName);
        System.out.println("\n Programme added successfully.");

    }

    // Remove programme
    private void removeProgramme() {
        System.out.print("Enter Programme Code to remove: ");
        String programmeCode = scanner.nextLine().trim();

        Programme removedProgramme = programmeManagement.removeProgramme(programmeCode);
        if (removedProgramme != null) {
            System.out.println("Programme Removed : " + removedProgramme);
        } else {
            System.out.println("Programme not exists in the system.");
        }
    }

    // Retrieve programme
    private void findProgramme() {
        System.out.print("Enter Programme Code to find: ");
        String programmeCode = scanner.nextLine().trim();

        Programme foundProgramme = programmeManagement.findProgramme(programmeCode);
        if (foundProgramme != null) {
            System.out.println("Programme found: " + foundProgramme);
        } else {
            System.out.println("Programme not exists in the system.");
        }
    }

    // Modify programme, modify programme name
    private void amendProgrammeDetails() {
        System.out.print("Enter Programme Code to amend details: ");
        String programmeCode = scanner.nextLine().trim();

        Programme foundProgramme = programmeManagement.findProgramme(programmeCode);

        if (foundProgramme != null) {
            System.out.print("Enter New Programme Name: ");
            String newProgrammeName = scanner.nextLine().trim();
            boolean success = programmeManagement.amendProgrammeDetails(programmeCode, newProgrammeName);

            if(success){
                System.out.println("Programme details amended successfully.");
            }else{
                System.out.println("Programme details amended unsuccessful.");
            }

        }else{
            System.out.println("Programme not found.");
        }
    }

    // List all programme in the system
    private void listAllProgrammes() {

        Iterator<String> iterator = programmeManagement.getAllProgrammesIterator();

        if(!iterator.hasNext()){
            System.out.println("No programme existing in the system yet");
        }else{
            System.out.println("  All Programmes ");
            System.out.println("==========================================================");
            while (iterator.hasNext()) {
                String programKey = iterator.next();
                Programme programme = programmeManagement.findProgramme(programKey);

                System.out.print("{ Programme Code: " + programme.getProgrammeCode() + " , ");
                System.out.println("Programme Name: " + programme.getProgrammeName() + " } ");
            }
        }

    }

    // Add tutorial group to a particular programme
    private void addTutorialGroupToProgramme() {
        System.out.print("Enter Programme Code that you want to add the Tutorial Group to: ");
        String programmeCode = scanner.nextLine().trim();

        boolean success;

        if(programmeManagement.findProgramme(programmeCode) != null){

            System.out.print("Enter Tutorial Group ID: ");
            String tutorialGroupID = scanner.nextLine().trim();
            System.out.print("Enter Tutorial Group Name: ");
            String tutorialGroupName = scanner.nextLine().trim();
            System.out.print("Enter Study Year: ");
            String studyYear = scanner.nextLine().trim();

            TutorialGroup tutorialGroup = new TutorialGroup(tutorialGroupID,tutorialGroupName, studyYear);

            success = programmeManagement.addTutorialGroupToProgramme(programmeCode, tutorialGroup);

            if(success){
                System.out.println("Tutorial Group added to Programme successfully");
            }else{
                System.out.println("Unsuccessful to add Tutorial Group into Programme");
            }
        }else{

            System.out.println("This Programme is not exists in the system, Programme Code : " + programmeCode);
        }

    }

    // Remove tutorial group from a particular programme
    private void removeTutorialGroupFromProgramme() {

        TutorialGroup removedGroup;

        System.out.print("Enter Programme Code that you want to remove the Tutorial Group: ");
        String programmeCode = scanner.nextLine().trim();

        if(programmeManagement.findProgramme(programmeCode) != null){
            System.out.print("Enter Tutorial Group ID to remove: ");
            String tutorialGroupID = scanner.nextLine().trim();

            removedGroup = programmeManagement.removeTutorialGroupFromProgramme(programmeCode, tutorialGroupID);

            if(removedGroup != null){
                System.out.println("Successfully removed the Tutorial Group : " + tutorialGroupID);
            }else{
                System.out.println("Remove operation not successful. The Tutorial Group : " + tutorialGroupID + " is not exists in the Programme : " + programmeCode);
            }

        }else{

            System.out.println("This Programme is not exists in the system, Programme Code : " + programmeCode);
        }

    }

    // List all tutorial group from a particular programme
    private void listAllTutorialGroupsForProgramme() {

        System.out.print("Enter Programme Code that you want to list all Tutorial Group: ");
        String programmeCode = scanner.nextLine().trim();

        if(programmeManagement.findProgramme(programmeCode) != null){

            Programme programme = programmeManagement.findProgramme(programmeCode);

            if (programme != null) {
                LinkedList<TutorialGroup> tutorialGroups = programme.getTutorialGroups();

                if (tutorialGroups != null) {
                    Iterator<TutorialGroup> iterator = tutorialGroups.iterator();

                    if(iterator.hasNext()){

                        System.out.println("   All Tutorial Groups for " + programmeCode);
                        System.out.println("===============================================");

                        while (iterator.hasNext()) {
                            TutorialGroup group = iterator.next();
                            System.out.print("{ ");
                            System.out.print("Tutorial Group ID: " + group.getGroupID() + " , ");
                            System.out.print("Tutorial Group Name: " + group.getGroupName() + " , ");
                            System.out.print("Tutorial Group Study Year: " + group.getStudyYear());
                            System.out.println(" } ");
                        }

                    }else{
                        System.out.println("No Tutorial Group exists in this Programme, Programme Code : " + programmeCode);
                    }
                }
            }

        }else{
            System.out.println("This Programme is not exists in the system, Programme Code : " + programmeCode);
        }
    }

    // Report to show all programme with tutorial group
    private void generateReports() {
        Iterator<String> programmeIterator = programmeManagement.getAllProgrammesIterator();

        if (!programmeIterator.hasNext()) {
            System.out.println("No programmes exist in the system yet");
            return;
        }

        if (programmeIterator.hasNext()) {
            System.out.println("\n\n               Programme Management Report");
            System.out.println("===============================================================================");

            int programmeCounter = 1;

            while (programmeIterator.hasNext()) {
                String programCode = programmeIterator.next();
                Programme programme = programmeManagement.findProgramme(programCode);

                // Print headings
                System.out.println("\nProgramme " + programmeCounter);
                System.out.println("-----------------------------------------------");
                System.out.println("Programme Code: " + programme.getProgrammeCode());
                System.out.println("Programme Name: " + programme.getProgrammeName());
                System.out.println("Total Tutorial Groups: " + programme.getTutorialGroups().getNumberOfEntries());

                LinkedList<TutorialGroup> tutorialGroups = programme.getTutorialGroups();

                if (tutorialGroups != null && !tutorialGroups.isEmpty()) {
                    System.out.println("\nTutorial Groups for " + programme.getProgrammeCode() + " :");
                    System.out.println("=======================================");
                    System.out.printf("%-5s %-20s %-30s %-15s%n", "No.", "Group ID", "Group Name", "Study Year");
                    System.out.println("---------------------------------------------------------------");

                    Iterator<TutorialGroup> tutorialGroupIterator = tutorialGroups.iterator();

                    int tutorialGroupCounter = 1;

                    while (tutorialGroupIterator.hasNext()) {
                        TutorialGroup tutorialGroup = tutorialGroupIterator.next();

                        System.out.printf("%-5d %-20s %-30s %-15s%n", tutorialGroupCounter, tutorialGroup.getGroupID(),
                                tutorialGroup.getGroupName(), tutorialGroup.getStudyYear());

                        tutorialGroupCounter++;
                    }
                } else {
                    System.out.println("No Tutorial Groups for this programme.");
                }

                programmeCounter++;
            }
        } else {
            System.out.println("No programmes exist in the system yet");
        }
    }


    public static void main(String[] args) {
        ProgrammeManagementConsole ui = new ProgrammeManagementConsole();
        ui.start();
    }

}
