/*
*                                           Group -- 7
*           
*       IREDDY VISHNU VARDHAN REDDY                             2017B3A70842H
*       CHALLA SUMANTH REDDY                                    2017A8PS0706H
*       SUMANTH N                                               2017AAPS0445H
*       NIMMAGADDA BHAGAVATH CHOWDARY                           2017A3PS0532H
*       
*
*       References:
*           
*           [1] https://medium.com/programmers-blockchain/creating-your-first-blockchain-with-java-part-2-transactions-2cdac335e0ce
*           
*           [2] https://github.com/CryptoKass/NoobChain-Tutorial-Part-2 
*
*           [3] https://www.geeksforgeeks.org/data-encryption-standard-des-set-1/
*/

import java.security.*;
import java.util.ArrayList;
import java.io.*;

public class Transaction implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String transactionId; //Contains a hash of transaction*
    public PublicKey sender; //Senders address/public key.
    public PublicKey receiver; //receivers address/public key.
    public float value; //Contains the amount we wish to send to the receiver.
    public String asset;
    private int amount;
    public byte[] signature; //This is to prevent anybody else from spending funds in our wallet.

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; //A rough count of how many transactions have been generated

    // Constructor:
    public Transaction(PublicKey from, PublicKey to, float value, String asset,  ArrayList<TransactionInput> inputs, int amount) {
        this.sender = from;
        this.asset = asset;
        this.receiver = to;
        this.value = value;
        this.inputs = inputs;
        this.amount = amount;
    }

    public boolean processTransaction() {

        if(!verifySignature()) {
            System.out.println("\n\t\t*Transaction Signature failed to verify*");
            return false;
        }
        float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
        transactionId = calulateHash();
        outputs.add(new TransactionOutput( this.receiver, value,transactionId)); //send value to receiver
        outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender

        return true;
    }

    public float getInputsValue() {
        float total = amount + value;
        return total;
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value)	;
        signature = StringUtil.applyECDSASig(privateKey,data);
    }

    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value)	;
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
            System.out.println("\t\t" + o.value);
        }
        return total;
    }

    private String calulateHash() {
        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(receiver) +
                        Float.toString(value) + sequence
        );
    }
}