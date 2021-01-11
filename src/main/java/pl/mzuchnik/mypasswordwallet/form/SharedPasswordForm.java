package pl.mzuchnik.mypasswordwallet.form;

import pl.mzuchnik.mypasswordwallet.form.validator.PasswordCorrectForAuthenticatedUser;
import pl.mzuchnik.mypasswordwallet.form.validator.PasswordExits;
import pl.mzuchnik.mypasswordwallet.form.validator.UserExists;

import javax.validation.constraints.NotBlank;

public class SharedPasswordForm {

    @PasswordExits
    private long passwordId;

    @UserExists
    @NotBlank(message = "User's login cannot be empty")
    private String consumerLogin;

    private boolean editable;

    private String ownerNote;

    @PasswordCorrectForAuthenticatedUser
    @NotBlank(message = "Password cannot be empty")
    private String confirmPassword;

    public long getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(long passwordId) {
        this.passwordId = passwordId;
    }

    public String getConsumerLogin() {
        return consumerLogin;
    }

    public void setConsumerLogin(String consumerLogin) {
        this.consumerLogin = consumerLogin;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getOwnerNote() {
        return ownerNote;
    }

    public void setOwnerNote(String ownerNote) {
        this.ownerNote = ownerNote;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
