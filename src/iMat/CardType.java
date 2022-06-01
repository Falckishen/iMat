package iMat;

public enum CardType {
    MASTER_CARD("MasterCard"),
    VISA("Visa");

    private String displayName;

    CardType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

