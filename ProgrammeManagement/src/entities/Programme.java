package entities;

import adt.ConcurrentLinkedHashMap;
/**
 *
 * @author Chan Zhi Yang
 */

public class Programme {
    private String programmeCode;
    private String programmeName;
    private ConcurrentLinkedHashMap<String, TutorialGroup> tutorialGroups;

    public Programme(String programmeCode, String programmeName) {
        this.programmeCode = programmeCode;
        this.programmeName = programmeName;
        this.tutorialGroups = new ConcurrentLinkedHashMap<>();
    }


    public void addTutorialGroup(TutorialGroup tutorialGroup) {
        tutorialGroups.put(tutorialGroup.getGroupID(), tutorialGroup);
    }

    public TutorialGroup removeTutorialGroup(String groupID) {
        return tutorialGroups.remove(groupID);
    }

    public ConcurrentLinkedHashMap<String, TutorialGroup> getTutorialGroups() {
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

    public void setTutorialGroups(ConcurrentLinkedHashMap<String, TutorialGroup> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }
}


