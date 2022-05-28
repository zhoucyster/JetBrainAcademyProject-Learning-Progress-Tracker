package tracker;

public class CredentialValidation {
    private final int NAME_MIN_LENGTH = 2;
    private final String VALID_NAME_PATTERN = "^[a-zA-Z ]+([\\- ]?[a-zA-Z]+[ ]?)?([\\' ]?[a-zA-Z]+[ ]?)?[a-zA-Z]?$";
    private final String VALID_EMAIL_PATTERN = "[a-zA-Z|0-9|\\.]+@[a-zA-Z|0-9]+\\.[a-zA-Z|0-9]+";
    private String fName;
    private String lName;
    private String eMail;

    public CredentialValidation(String[] credential) {
        int fieldCount = credential.length;
        this.fName = credential[0];
        this.eMail = credential[fieldCount - 1];
        StringBuilder sb = new StringBuilder();
        //fields between first name and email is last name.
        for ( int i = 1; i < fieldCount - 1; i++) {
            sb.append(credential[i] + " ");
        }
        this.lName = sb.toString().trim();
    }

    private boolean validateName(String name) {
        return (name.replaceAll("\\'", "").replaceAll("\\-", "").length() >= NAME_MIN_LENGTH
                && name.matches(VALID_NAME_PATTERN));

    }

    public boolean validateFirstName(){
        return validateName(this.fName);
    }

    public boolean validateLastName(){
        return validateName(this.lName.trim());
    }

    public boolean validateEmail(){
        return this.eMail.matches(VALID_EMAIL_PATTERN);
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String geteMail() {
        return eMail;
    }
}
