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

import java.util.ArrayList;
import java.util.Date;
import java.io.*;

public class Block implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String hash;
    public String previousHash;
    public String gensRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>(); //our data will be a simple message.
    public long timeStamp; //as number of milliseconds since 1/1/1970.
    public int nonce;
    public String user;

    //Block Constructor.
    public Block(String previousHash, String user) {
        this.user = user;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    //Calculate new hash based on blocks contents
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        gensRoot
        );
        DES desobj = new DES();
        String desHash = "";
        
        for(int i=0;i<64;i+=16)
            desHash += desobj.desEncrypt(calculatedhash.substring(i, i+16));

        return desHash;
    }

    //Increases nonce value until hash target is reached.
    public void mineBlock(int difficulty) {
        gensRoot = StringUtil.getGensRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("\n\t\tBlock Mined!!! : " + hash);
    }

    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((!"0".equals(previousHash))) {
            if((!transaction.processTransaction())) {
                System.out.println("\n\t\tTransaction failed to process. Discarded.");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("\t\tTransaction Successfully added to Block");
        return true;
    }
}