package tracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CredentialValidationTest {

    @Test
    void testValidateFirstName1(){
        String[] credential = {"Jemison Van de Graaff robertvdgraaff", "lname","abc@gmail.com"};
        CredentialValidation cv = new CredentialValidation(credential);
        assertTrue(cv.validateFirstName());
    }
    @Test
    void testValidateFirstName2(){
        String[] credential = {"s-z", "lname","abc@gmail.com"};
        CredentialValidation cv = new CredentialValidation(credential);
        assertTrue(cv.validateFirstName());
    }
    @Test
    void testValidateFirstName3(){
        String[] credential = {"nam-'e", "lname","abc@gmail.com"};
        CredentialValidation cv = new CredentialValidation(credential);
        assertFalse(cv.validateFirstName());
    }

    @Test
    void testValidateFirstName4(){
        String[] credential = {"O'Connor", "lname","abc@gmail.com"};
        CredentialValidation cv = new CredentialValidation(credential);
        assertTrue(cv.validateFirstName());
    }

    @Test
    void testValidateFirstName5(){
        String[] credential = {"me su aa-b'b", "lname","abc@gmail.com"};
        CredentialValidation cv = new CredentialValidation(credential);
        assertTrue(cv.validateFirstName());
    }

    @Test
    void testValidateFirstName6(){
        String[] credential = {"name-", "lname","abc@gmail.com"};
        CredentialValidation cv = new CredentialValidation(credential);
        assertFalse(cv.validateFirstName());
    }
//    @Test
//    void testEmail(){
//        String credential = "Mary Emelianenko 125367at@zzz90.z9";
//        String[] splitCredential = credential.split(" ");
//        CredentialValidation cv = new CredentialValidation(splitCredential);
//        assertTrue(cv.validateEmail());
//    }
}
