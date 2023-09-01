package entities;

import adt.LinkedList;

import java.util.Iterator;

/**
 *
 * @author Chan Zhi Yang
 */

public class Programme {
    private String programmeCode;
    private String programmeName;
    private LinkedList<TutorialGroup> tutorialGroups;

    public Programme(String programmeCode, String programmeName) {
        this.programmeCode = programmeCode;
        this.programmeName = programmeName;
        this.tutorialGroups = new LinkedList<>();
    }

    public void addTutorialGroup(TutorialGroup tutorialGroup) {
        tutorialGroups.add(tutorialGroup);
    }

    public TutorialGroup removeTutorialGroup(String groupID) {
        TutorialGroup removedGroup = null;

        Iterator iterator = tutorialGroups.iterator();

        while (iterator.hasNext()) {
            TutorialGroup currentGroup = (TutorialGroup) iterator.next();
            if (currentGroup.getGroupID().equals(groupID)) {
                removedGroup = currentGroup;
                iterator.remove();
                break;
            }
        }

        return removedGroup;
    }

    public LinkedList<TutorialGroup> getTutorialGroups() {
        return tutorialGroups;
    }


    //Getters and Setters
    public String getProgrammeCode() {
        return programmeCode;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeCode(String programmeCode) {
        this.programmeCode = programmeCode;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public void setTutorialGroups(LinkedList<TutorialGroup> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    @Override
    public String toString() {
        return "Programme{" +
                "programmeCode='" + programmeCode + '\'' +
                ", programmeName='" + programmeName + '\'' +
                ", tutorialGroupsNumOfEntries=" + tutorialGroups.getNumberOfEntries() +
                '}';
    }
}


