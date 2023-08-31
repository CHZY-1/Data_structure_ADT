package entities;

/**
 *
 * @author Chan Zhi Yang
 */

public class TutorialGroup{

    private String groupID;
    private String groupName;
    private String studyYear;

    public TutorialGroup(String groupID, String groupName, String studyYear) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.studyYear = studyYear;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    @Override
    public String toString() {
        return "TutorialGroup{" +
                "groupCode='" + groupID + '\'' +
                ", groupName='" + groupName + '\'' +
                ", studyYear='" + studyYear + '\'' +
                '}';
    }
}