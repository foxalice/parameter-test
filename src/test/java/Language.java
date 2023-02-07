public enum Language {

    EN ("Eng"),
    RU ("Rus");

    private final String describe;

    Language (String describe) {
        this.describe = describe;
    }

    public String getDesc() {
        return describe;
    }
}




