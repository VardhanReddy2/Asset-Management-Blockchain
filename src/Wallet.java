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
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class Wallet implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public ArrayList<String> assetList = new ArrayList<>();
    public ArrayList<Integer> costlist = new ArrayList<>();
    private int bal = 0;

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random); //256
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getBalance() {
        int total = bal;
        return total;
    }
    public void addMoney(int a){
        bal += a;
    }

    public void putMoney(int i){
        bal += costlist.get(i);
    }

    public void cutMoney(int x){
        bal -= x;
    }

    public Transaction sendFunds(PublicKey _receiver,float value, String asset) {
        if(getBalance() < value) {
            System.out.println("\n\t\t*Not Enough funds to send transaction. Transaction Discarded.*");
            return null;
        }
        ArrayList<TransactionInput> inputs = new ArrayList<>();

        Transaction newTransaction = new Transaction(publicKey, _receiver , value, asset, inputs, bal);
        newTransaction.generateSignature(privateKey);

        return newTransaction;
    }

}