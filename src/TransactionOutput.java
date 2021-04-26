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


import java.security.PublicKey;
import java.io.*;

public class TransactionOutput implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String id;
    public PublicKey receiver;
    public float value;
    public String parentTransactionId;

    //Constructor
    public TransactionOutput(PublicKey receiver, float value, String parentTransactionId) {
        this.receiver = receiver;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(receiver)+Float.toString(value)+parentTransactionId);
    }

    //Check if asset belongs to you
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == receiver);
    }

}