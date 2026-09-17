package models;

public class DebitCard {

    private String cardType;
    private double withdrawLimit;
    private double transferLimit;
    private double ownTransferLimit;
    private double depositLimit;
    private double ownDepositLimit;


    public DebitCard(String cardType){
        this.cardType = cardType;

        switch (cardType){
            case "Platinum":
                withdrawLimit= 20000;
                transferLimit= 40000;
                ownTransferLimit= 80000;
                depositLimit= 100000;
                ownDepositLimit= 200000;
                break;

            case "Titanium":
                withdrawLimit= 10000;
                transferLimit= 20000;
                ownTransferLimit= 40000;
                depositLimit= 100000;
                ownDepositLimit= 200000;
                break;

            case "Mastercard":
                withdrawLimit= 5000;
                transferLimit= 10000;
                ownTransferLimit= 20000;
                depositLimit= 100000;
                ownDepositLimit= 200000;
                break;

            default:
            throw new IllegalArgumentException("Invalid card type.");
        }
    }

    public String getCardType() {
        return cardType;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public double getTransferLimit() {
        return transferLimit;
    }

    public double getOwnTransferLimit() {
        return ownTransferLimit;
    }

    public double getDepositLimit() {
        return depositLimit;
    }

    public double getOwnDepositLimit() {
        return ownDepositLimit;
    }
}
