package cleancode.studycafe.mission.pass;

public enum LockerPassSelection {
    SELECTED("선택"),
    NOT_SELECTED("선택하지 않음");

    private final String description;

    LockerPassSelection(String description) {
        this.description = description;
    }
}
