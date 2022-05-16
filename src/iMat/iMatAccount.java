package iMat;

/**
 * Account-class with names, address information and payment information.
 */
public class iMatAccount {
    private String firstName;
    private String lastName;
    private String address;
    private int postNumber;
    private int telephoneNumber;
    private enum paymentType {
        CARD,
        INVOICE
    }

    public iMatAccount(String firstName, String lastName, String address, int postNumber, int telephoneNumber){

    };


}
