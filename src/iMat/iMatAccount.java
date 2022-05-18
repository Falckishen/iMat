package iMat;

/**
 * Account-class with names, address information and payment information. Object should be immutable.
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

    /**
     * Public get-ers for the attributes.
     * @return the chosen attribute value.
     */
    public int getPostNumber() {
        return postNumber;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Public set-ers. Safe-sets the attributes - for immutability.
     * @return new account with changed attribute.
     */
    public iMatAccount changeFirstName(String newFirstName) {
        return new iMatAccount(newFirstName, this.lastName, this.address, this.postNumber, this.telephoneNumber, this.paymentType);
    }

    public iMatAccount changeLastName(String newLastName) {
        return new iMatAccount(this.firstName, newLastName, this.address, this.postNumber, this.telephoneNumber, this.paymentType);
    }

    public iMatAccount changeAddress(String newAddress) {
        return new iMatAccount(this.firstName, this.lastName, newAddress, this.postNumber, this.telephoneNumber, this.paymentType);
    }

    public iMatAccount changePostNumber(int newPostNumber) {
        return new iMatAccount(this.firstName, this.lastName, this.address, newPostNumber, this.telephoneNumber, this.paymentType);
    }

    public iMatAccount changeTelephoneNumber(int newTelephoneNumber) {
        return new iMatAccount(this.firstName, this.lastName, this.address, this.postNumber, newTelephoneNumber, this.paymentType);
    }

    public iMatAccount changePaymentType(PaymentType newPaymentType) {
        return new iMatAccount(this.firstName, this.lastName, this.address, this.postNumber, this.telephoneNumber, newPaymentType);
    }
}
