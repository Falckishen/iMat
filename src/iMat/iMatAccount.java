package iMat;

/**
 * Account-class with names, address information and payment information.
 */
public class iMatAccount {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final int postNumber;
    private final int telephoneNumber;
    private final PaymentType paymentType;

    public iMatAccount(String firstName, String lastName, String address, int postNumber, int telephoneNumber, PaymentType paymentType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postNumber = postNumber;
        this.telephoneNumber = telephoneNumber;
        this.paymentType = paymentType;
    };

/*-------------------------------------------------------------------------------------------------------------------*/

    private iMatAccount changeFirstName(String newFirstName) {
        return new iMatAccount(newFirstName, this.lastName, this.address, this.postNumber, this.telephoneNumber, this.paymentType);
    }

    private iMatAccount changeLastName(String newLastName) {
        return new iMatAccount(this.firstName, newLastName, this.address, this.postNumber, this.telephoneNumber, this.paymentType);
    }

    private iMatAccount changeAddress(String newAddress) {
        return new iMatAccount(this.firstName, this.lastName, newAddress, this.postNumber, this.telephoneNumber, this.paymentType);
    }

    private iMatAccount changePostNumber(int newPostNumber) {
        return new iMatAccount(this.firstName, this.lastName, this.address, newPostNumber, this.telephoneNumber, this.paymentType);
    }

    private iMatAccount changeTelephoneNumber(int newTelephoneNumber) {
        return new iMatAccount(this.firstName, this.lastName, this.address, this.postNumber, newTelephoneNumber, this.paymentType);
    }

    private iMatAccount changePaymentType(PaymentType newPaymentType) {
        return new iMatAccount(this.firstName, this.lastName, this.address, this.postNumber, this.telephoneNumber, newPaymentType);
    }

    private enum PaymentType {
        CARD,
        INVOICE
    }
}