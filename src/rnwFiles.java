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


import java.io.*;
import java.util.*;

public class rnwFiles{

    public static String dbfold = "../database/";

    public static void write(){
        try{
            FileOutputStream blcFos = new FileOutputStream(dbfold + "BlockChain.ser");
            ObjectOutputStream blcOos = new ObjectOutputStream(blcFos);
            blcOos.writeObject(BlockChain.blockchain);

            FileOutputStream usrFos = new FileOutputStream(dbfold + "Usernames.ser");
            ObjectOutputStream usrOos = new ObjectOutputStream(usrFos);
            usrOos.writeObject(BlockChain.usernames);

            FileOutputStream pswFos = new FileOutputStream(dbfold + "Passwords.ser");
            ObjectOutputStream pswOos = new ObjectOutputStream(pswFos);
            pswOos.writeObject(BlockChain.passwords);

            FileOutputStream wltFos = new FileOutputStream(dbfold + "Wallets.ser");
            ObjectOutputStream wltOos = new ObjectOutputStream(wltFos);
            wltOos.writeObject(BlockChain.wallets);

            FileOutputStream fos4 = new FileOutputStream(dbfold + "ZKPids.ser");
            ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
            oos4.writeObject(BlockChain.zkpIDs);


            blcFos.close();usrFos.close();pswFos.close();wltFos.close();fos4.close();
            blcOos.close();usrOos.close();pswOos.close();wltOos.close();oos4.close();

        }catch(IOException e){
            System.out.println("rnwFiles.write: " + e);
        }
    }

    public static void read(){
        BlockChain.genBlock();
        
        try{
            FileInputStream blcFis = new FileInputStream(dbfold + "BlockChain.ser");
            ObjectInputStream blcOis = new ObjectInputStream(blcFis);

            if(new File(dbfold + "BlockChain.ser").length() != 0)
                BlockChain.blockchain = (ArrayList<Block>)blcOis.readObject();

            FileInputStream usrFis = new FileInputStream(dbfold + "Usernames.ser");
            ObjectInputStream usrOis = new ObjectInputStream(usrFis);
            if(new File(dbfold + "Usernames.ser").length() != 0)
                BlockChain.usernames = (List<String>)usrOis.readObject();

            FileInputStream pswFis = new FileInputStream(dbfold + "Passwords.ser");
            ObjectInputStream pswOis = new ObjectInputStream(pswFis);
            if(new File(dbfold + "Passwords.ser").length() != 0)
                BlockChain.passwords = (List<String>)pswOis.readObject();

            FileInputStream wltFis = new FileInputStream(dbfold + "Wallets.ser");
            ObjectInputStream wltOis = new ObjectInputStream(wltFis);
            if(new File(dbfold + "Wallets.ser").length() != 0)
                BlockChain.wallets = (ArrayList<Wallet>)wltOis.readObject();

            FileInputStream zkpFis = new FileInputStream(dbfold + "ZKPids.ser");
            ObjectInputStream zkpOis = new ObjectInputStream(zkpFis);
            if(new File(dbfold + "ZKPids.ser").length() != 0)
                BlockChain.zkpIDs = (List<Integer>)zkpOis.readObject();

            blcFis.close();usrFis.close();pswFis.close();wltFis.close();zkpFis.close();
            blcOis.close();usrOis.close();pswOis.close();wltOis.close();zkpOis.close();

        }catch(Exception e){
            System.out.println("rnwFiles.read: " + e);
        }
    }

}