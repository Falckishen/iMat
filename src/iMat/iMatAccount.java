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
    }

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
        return new iMatAccount(newFirstName, lastName, address, postNumber, telephoneNumber, paymentType);
    }

    public iMatAccount changeLastName(String newLastName) {
        return new iMatAccount(firstName, newLastName, address, postNumber, telephoneNumber, paymentType);
    }

    public iMatAccount changeAddress(String newAddress) {
        return new iMatAccount(firstName, lastName, newAddress, postNumber, telephoneNumber, paymentType);
    }

    public iMatAccount changePostNumber(int newPostNumber) {
        return new iMatAccount(firstName, lastName, address, newPostNumber, telephoneNumber, paymentType);
    }

    public iMatAccount changeTelephoneNumber(int newTelephoneNumber) {
        return new iMatAccount(firstName, lastName, address, postNumber, newTelephoneNumber, paymentType);
    }

    public iMatAccount changePaymentType(PaymentType newPaymentType) {
        return new iMatAccount(firstName, lastName, address, postNumber, telephoneNumber, newPaymentType);
    }
}
