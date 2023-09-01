package controls;
import adt.ConcurrentLinkedHashMap;
import entities.*;
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

    public ConcurrentLinkedHashMap<String, Programme> listAllProgrammes() {
        return programmes;
    }

}
