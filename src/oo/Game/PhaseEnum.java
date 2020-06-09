package oo.Game;

public enum PhaseEnum {
    Phase1("Phase 1"),
    Phase2("Phase 2"),
    Phase3("Phase 3"),
    DecideWorstPlayer("Eliminating"),
    End("End");

    private final String meaning;

    PhaseEnum(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return meaning;
    }

}
