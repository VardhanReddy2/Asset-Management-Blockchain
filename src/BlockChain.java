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


import java.util.*;
import java.security.Security;


public class BlockChain {
    static Scanner scan;

    public static ArrayList<Block> blockchain;
    static List<Integer> zkpIDs;
    static ArrayList<Wallet> wallets;
    static List<String> usernames;
    static List<String> passwords;
    
    
    static Block prevBlock;
    static int k;
    public static int p, g;
    public static Transaction genT;
    static Wallet bank;
    static Block genesis;
    
    public static void genBlock(){
        try{
            System.out.println("\n\n\t\t*****The BlackChain Begins!!*****");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
            usernames = new ArrayList<>();
            passwords = new ArrayList<>();
            zkpIDs = new ArrayList<>();
            wallets = new ArrayList<>();
            scan = new Scanner(System.in);
            k = 0;
            bank = new Wallet();
            p=11;
            g=2;
            genesis = new Block("0","satoshi");
            blockchain = new ArrayList<>();
            prevBlock = genesis;
            usernames.add("satoshi");
            passwords.add("nakamoto");
            Wallet wallet = new Wallet();
            wallets.add(wallet);
            wallet.addMoney(1020275);
            wallet.assetList.add("BTC");
            wallet.assetList.add("ETH");
            wallet.assetList.add("BNB");
            wallet.assetList.add("DOGE");
            wallet.costlist.add(60);
            wallet.costlist.add(22);
            wallet.costlist.add(63);
            wallet.costlist.add(79);
            blockchain.add(genesis);
            }catch(Exception e){
                System.out.println("Chain.genBlock: " + e);
            }
    }
    public static void main(String[] args){
        rnwFiles.read();
        init();
    }

    public static void init() {
        System.out.print("\n\t\tPlease select an option:\n\t\t\t1. Login\n\t\t\t2. Register\n\t\t\t3. Exit\n\n\t\t");
        int nn = scan.nextInt();
        if (nn == 1)
            login();
        else if (nn == 2)
            register();
        else if(nn == 3)
            System.exit(0);
        else {
            System.out.println("\n\t\t*Bruhhh!! Please enter a valid number*");
            init();
        }
    }

    public static void register(){
        System.out.print("\n\t\tPlease Enter your z-value(Captcha kinda thing (give only int value) ):\t");
        int zkpid = scan.nextInt(); 
        boolean flag0 = false;
        for (int zid: zkpIDs) {
            if (zid == zkpid) {
                flag0 = true;
                break;
            }
        }
        if(flag0){
            System.out.println("\n\t\t*ZKP-ID already exists. Try again!*");
            register();
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("\n\t\tPlease Enter Your username:\t");
        
        String username;
        username = scan.next();
        boolean flag = false;
        for (String user: usernames) {
            if (user.equals(username)) {
                flag = true;
                break;
            }
        }
        if(flag){
            System.out.println("\n\t\t*Someone already took that Username. Try something different!*");
            register();
        }
        else{
            System.out.print("\n\t\tPlease Enter Your Password:\t");
            String password = scan.next();
            
            zkpIDs.add(zkpid);
            usernames.add(username);
            passwords.add(password);
            Wallet wallet = new Wallet();
            wallets.add(wallet);
            System.out.println("\n\n\t\t*****\tWelcome\t"+username+"\t*****\n");
            loggedIn(username);
        }

    }

    public static void login(){
        String username;
        System.out.print("\n\t\tPlease enter your username:\t");
        username = scan.next();

        String password;
        System.out.print("\n\t\tPlease enter your password:\t");
        password = scan.next();

        int i = 0;
        boolean flag = false;
        while( i < usernames.size()) {

            if(usernames.get(i).equals(username)){
                if(passwords.get(i).equals(password)){
                    flag = true;
                    break;
                }
            }
            
            i++;
        }
        
        if("satoshi".equals(username)){
            chainManager();
            return;
        }

        if(flag){
            System.out.println("\n\n\t\t*****\tWelcome\t"+username+"\t*****\n");
            loggedIn(username);
        }
        else{
            System.out.println("\n\t\t*Shit! you missed something. Try again.*");
            init();
        }
    }

    public static boolean zkpVerify(String username){
        int i = 0, present = 0;
        while(i<usernames.size()){
            if(usernames.get(i).equals(username)){
                present = i - 1;
                break;
            }
            i++;
        }
        int y = calcExpo(g, zkpIDs.get(present), p);
        boolean check = zkpDetailed(y);
        if(!check){
            System.out.println("\n\t\t*ZKP failed. Alien entered. Try again to prove yourself HUMAN!*");
            check = zkpVerify(username);
        }
        return check;
    }

    public static Boolean zkpDetailed(int y){

        
        Scanner sc=new Scanner(System.in);
        System.out.println("\n\n\t\t***\tZero Knowledge Proof - ZKP\t***");
        System.out.println("\n\t\tLet's check if you are a user of our blockchain. Choose a random number(r) between 0 and 9");
        System.out.print("\n\t\tCompute h=(2^r)(mod 11) and Enter h:\t");
        int h = sc.nextInt();
        
        Random rr = new Random();
        int b = rr.nextInt(2);
        System.out.println("\n\n\t\tRandom bit b is "+b);
        System.out.print("\n\t\tNow compute s = (r + b*x)mod(10).Here x is the number you are proving you know..your SECRET z-value:\t");
        int s = sc.nextInt();
        int av = calcExpo(2,s,11);
        int bv = (h*calcExpo(y,b,11))%11;
        

        if(av == bv){
             System.out.println("\n\t\t*ZKP SUCCESS! Your transaction is verified!*");
             return true;
        }
        else{
            System.out.println("\n\t\t*ZKP Failed! Try again.");
            return false;
        }
    }

    public static void loggedIn(String username){
        rnwFiles.write();

        System.out.print("\n\t\tPlease select an option:\n\t\t\t1. Buy Assets\n\t\t\t2. Show My Transactions\n\t\t\t3. Validate\n\t\t\t4. My Assets\n\t\t\t5. Add Money\n\t\t\t6. Sell Assets\n\t\t\t7. Balance\n\t\t\t8. Logout\n\n\t\t");
        
        int usrSlc = scan.nextInt(), usrIndx = 0;

        for (int i = 0; i < usernames.size(); i++) {
            if(usernames.get(i).equals(username)){
                usrIndx = i;
            }
        }


        if(usrSlc == 1){
            int i = 0;
            System.out.println("\n\t\tWhat asset would you like to buy:\t");
            
            for (String asset:wallets.get(0).assetList) {
                i++;
                System.out.println("\t\t\t"+i+". "+asset+"\t-\t$"+wallets.get(0).costlist.get(i-1));
            }

            System.out.print("\t\t");
            int n = scan.nextInt() - 1;

            if(n>i-1 || n < 0){
                System.out.println("\n\t\t*Enter a valid number*");
                loggedIn(username);
                return;
            }

            i = 0;
            boolean flag = false;
            for (Block bc: blockchain) {
                if(bc.user.equals(username)) {
                    flag = true;
                    break;
                }
                i++;
            }

            if(!flag) {
                System.out.println("\n\t\t*No money in your wallet. Get money and comeback!*");
                loggedIn(username);
                return;
            }


            Block block = blockchain.get(i);
            if(!zkpVerify(username)){
                loggedIn(username);
                return;
            }

            boolean check = block.addTransaction(wallets.get(usrIndx).sendFunds(wallets.get(0).publicKey, wallets.get(0).costlist.get(n), wallets.get(0).assetList.get(n)));
            if(check){
                System.out.println("\n\t\tTransaction Completed! Thanks for buying " + wallets.get(0).assetList.get(n));

                wallets.get(usrIndx).assetList.add(wallets.get(0).assetList.get(n));
                wallets.get(usrIndx).costlist.add(wallets.get(0).costlist.get(n));
                wallets.get(usrIndx).cutMoney(wallets.get(0).costlist.get(n));
                wallets.get(0).assetList.remove(n);
                wallets.get(0).costlist.remove(n);
            }
            else
                System.out.println("\n\t\t*Try Try Try Try again!*");
            loggedIn(username);
        }
        else if(usrSlc == 2){
            boolean flag = false;
            int i = 0;
            
            for (Block bc: blockchain) {
                if(bc.user.equals(username)) {
                    flag = true;
                    break;
                }
                i++;
            }

            
            if(!flag){
                System.out.println("\n\t\t*Sorry you have nothing... I mean transactions. xD*");
            }
            else {
                System.out.println("\n\t\tYour Transactions:\n");
                for (Transaction transaction: blockchain.get(i).transactions){
                    System.out.println("\n\t\t\tTransaction id " + transaction.transactionId);
                    System.out.println("\t\t\tSender " + transaction.sender);
                    System.out.println("\t\t\treceiver " + transaction.receiver);
                    System.out.println("\t\t\tAsset transferred to sender " + transaction.asset);
                    System.out.println("\t\t\tValue of Transaction " + transaction.value);
                    System.out.println("\t\t\tSignature " + Arrays.toString(transaction.signature)+"\n");
                }
            }
            loggedIn(username);
        }
        else if(usrSlc == 3){
            isChainValid();
            loggedIn(username);
        }
        else if(usrSlc == 8){
            rnwFiles.write();
            init();
        }
        else if(usrSlc == 5){
            int amount;
            System.out.print("\n\t\tBrrr... I am the BANK manager.. Tell me how much you need:\t");
            amount = scan.nextInt();
            
            if(!zkpVerify(username)){
                loggedIn(username);
                return;
            }

            addMoney(wallets.get(usrIndx), amount, username);
            System.out.println("\n\t\t\tHere you go... Enjoy your $" + amount);
            loggedIn(username);
        }
        else  if(usrSlc == 6){
            int i = 0;
            for (Block bc: blockchain) {
                if(bc.user.equals(username)) {
                    break;
                }
                i++;
            }


            if(wallets.get(i).assetList.isEmpty()){
                System.out.println("\n\t\tNothing Here! Hurry up! The market is bullish. 3:(:)");
                loggedIn(username);
            }
            else {
                int uswlt = 0;
                System.out.println("\n\t\tIt's finally payday. What do you want to sell:\t");
                
                for (String asset: wallets.get(i).assetList) {
                    uswlt++;
                    System.out.println("\t\t\t"+uswlt+". "+asset);
                }

                System.out.print("\t\t");
                int nu = scan.nextInt() - 1;
                
                Block block = blockchain.get(i);
                if(nu>uswlt-1 || nu<0)
                {
                    System.out.println("\n\t\t*Please enter a valid number*");
                    loggedIn(username);
                }
                else {

                    if(!zkpVerify(username)){
                        loggedIn(username);
                        return;
                    }

                    boolean check = block.addTransaction(wallets.get(0).sendFunds(wallets.get(i).publicKey, wallets.get(i).costlist.get(nu), wallets.get(i).assetList.get(nu)));
                    if(check){
                        System.out.println("\n\t\tTransaction Completed. " + wallets.get(i).assetList.get(nu));
                        
                        wallets.get(0).assetList.add(wallets.get(i).assetList.get(nu));
                        wallets.get(0).costlist.add(wallets.get(i).costlist.get(nu));
                        wallets.get(i).putMoney(nu);
                        wallets.get(i).assetList.remove(nu);
                        wallets.get(i).costlist.remove(nu);
                    }
                    else
                        System.out.println("\n\t\tPlease try again");
                    
                    
                    loggedIn(username);
                }
            }
        }
        else if(usrSlc == 7){
            System.out.println("\n\t\tYour Balance:\n\t\t\t$"+wallets.get(usrIndx).getBalance());
            loggedIn(username);
        }
        else if (usrSlc == 4){
            int j = 0;
            for (Block bc: blockchain) {
                if(bc.user.equals(username))
                    break;
                j++;
            }

            System.out.println("\n\n\t\tYour Assets:");
            for (String ass : wallets.get(j).assetList)
                System.out.println("\t\t\t" + ass);

            
            if(wallets.get(j).assetList.isEmpty())
                System.out.println("\n\t\t*You don't own any assets*");

            loggedIn(username);
        }
        else{
            System.out.println("\n\t\t*Please enter a valid number*");
            loggedIn(username);
        }
    }


    public static void isChainValid() {
        Block currentBlock, previousBlock;
        int difficulty = 5;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        validate: {
            //loop through blockchain to check hashes:
            int i = 1;
            while(i < blockchain.size()) {
                currentBlock = blockchain.get(i);
                previousBlock = blockchain.get(i-1);
                //compare registered hash and calculated hash:
                if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                    break validate;
                    // System.out.println("#Current Hashes not equal");
                }

                //compare previous hash and registered previous hash
                if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                    break validate;
                    // System.out.println("#Previous Hashes not equal");
                }

                //check if hash is solved
                if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                    break validate;
                    // System.out.println("#This block hasn't been mined");
                }

                //loop thru blockchains transactions:
                int t = 0;
                while( t <currentBlock.transactions.size()) {
                    Transaction currentTransaction = currentBlock.transactions.get(t);

                    if(!currentTransaction.verifySignature()) {
                        System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                        return;
                    }

                    if( currentTransaction.outputs.get(0).receiver != currentTransaction.receiver) {
                        System.out.println("#Transaction(" + t + ") output receiver is not who it should be");
                        return;
                    }
                    t++;

                }
                i++;
            }
        }
        System.out.println("\n\t\tBlockchain is valid");
    }

    public static void addBlock(Block newBlock) {
        int difficulty = 3;
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

    public static int calcExpo(int a,int b,int c){
    	int res = 1, i = 0;
    	while(i<b){
    		res = ((res%c)*(a%c))%c;
            i++;
    	}

    	return res;
    }

    public static void addMoney(Wallet wallet, int amount, String username){
        Transaction genT;
        int i = 0;
        boolean flag = false;

        genT = new Transaction(bank.publicKey, wallet.publicKey, amount, null, null, 0);
        genT.generateSignature(bank.privateKey);	 //manually sign the genesis transaction
        genT.transactionId = Integer.toString(k++); //manually set the transaction id
        genT.outputs.add(new TransactionOutput(genT.receiver, genT.value, genT.transactionId)); //manually add the Transactions Output
        
        for (Block blo: blockchain) {
            if(blo.user.equals(username)) {
                flag = true;
                break;
            }
            i++;
        }

        Block bc;
        if(!flag){
            bc = new Block(prevBlock.hash, username);
            addBlock(bc);
            prevBlock = bc;
        }
        else
            bc = blockchain.get(i);
        
        i = 0;    
        bc.addTransaction(genT);
        for (Block bb: blockchain) {
            if(bb.user.equals(username)) {
                break;
            }
            i++;
        }

        wallets.get(i).addMoney(amount);
    }

    public static void chainManager(){
        int num;
        System.out.println("\n\n\t\t*****\tWelcome\tMr. Satoshi Nakamoto A.K.A God\t*****");
        System.out.print("\n\t\tPlease select an option:\n\t\t\t1. View Assets in the pool\n\t\t\t2. Add Assets\n\t\t\t3. View Users\n\t\t\t4. Logout\n\n\t\t");
        num = scan.nextInt();

        if(num == 1){
            System.out.println("\n\n\t\tAssets in the pool:");
            for (String ass : wallets.get(0).assetList)
                System.out.println("\t\t\t" + ass);
        
            if(wallets.get(0).assetList.isEmpty())
                System.out.println("\n\t\t*POOL is empty! Introduce few new projects*");
            
            chainManager();
        }
        else if(num == 2    ){
            System.out.print("\n\t\tEnter the Name and cost separated by a space:\t");
            String ass = scan.next();
            int assc = scan.nextInt();
            wallets.get(0).assetList.add(ass);
            wallets.get(0).costlist.add(assc);
            rnwFiles.write();
            chainManager();      
        }
        else if(num == 3){
            System.out.println("\n\t\tUsers of our BlockChain:");
            int t = 0;
            for(String tuser: usernames){
                t++;
                if(tuser.equals("satoshi")){
                    t--;
                    continue;
                }
                System.out.println("\t\t\t"+t+". "+tuser);
            }
            System.out.print("\n\t\tSelect a user to view their transactions or 0 to exit:\t");
            int nus = scan.nextInt();
            if(nus == 0)
                chainManager();
            else if(nus <= t){
                int i = 0;
                String ussrn = usernames.get(nus);
                boolean flag = false;
                for (Block bc: blockchain) {
                    if(bc.user.equals(ussrn)) {
                        flag = true;
                        break;
                    }
                    i++;
                }
                if(!flag){
                    System.out.println("\n\t\t*"+ussrn+" did not use our blockchain for any transactions yet."+"*");
                }
                else {
                    System.out.println("\n\t\t"+ussrn+"'s Transactions:\n");
                    for (Transaction transaction: blockchain.get(i).transactions){
                        System.out.println("\n\t\t\tTransaction id " + transaction.transactionId);
                        System.out.println("\t\t\tSender " + transaction.sender);
                        System.out.println("\t\t\treceiver " + transaction.receiver);
                        System.out.println("\t\t\tAsset transferred to sender " + transaction.asset);
                        System.out.println("\t\t\tValue of Transaction " + transaction.value);
                        System.out.println("\t\t\tSignature " + Arrays.toString(transaction.signature)+"\n");
                    }
                }
                chainManager();
            }
            else{
                System.out.println("\n\t\t*Enter a valid number*");
                chainManager();
            }
        }
        else if(num == 4){
            rnwFiles.write();
            init();
        }
        else {
            System.out.println("\n\t\t*Enter a valid number*");
            chainManager();
        }
    }
    
}
