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

public class DES{
	
	int[] initPermTab = { 58, 50, 42, 34, 26, 18, 10, 2,
						60, 52, 44, 36, 28, 20, 12, 4,
						62, 54, 46, 38, 30, 22, 14, 6,
						64, 56, 48, 40, 32, 24, 16, 8,
						57, 49, 41, 33, 25, 17, 9, 1,
						59, 51, 43, 35, 27, 19, 11, 3,
						61, 53, 45, 37, 29, 21, 13, 5,
						63, 55, 47, 39, 31, 23, 15, 7 };

	int[][][] SBOX = {
		{ { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
		{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
		{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
		{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },

		{ { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
		{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
		{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
		{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },

		{ { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
		{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
		{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
		{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },

		{ { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
		{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
		{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
		{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },

		{ { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
		{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
		{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
		{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },

		{ { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
		{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
		{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
		{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },

		{ { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
		{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
		{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
		{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },

		{ { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
		{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
		{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
		{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } }
	};

	int[] expDBox = { 32, 1, 2, 3, 4, 5, 4, 5,
					6, 7, 8, 9, 8, 9, 10, 11,
					12, 13, 12, 13, 14, 15, 16, 17,
					16, 17, 18, 19, 20, 21, 20, 21,
					22, 23, 24, 25, 24, 25, 26, 27,
					28, 29, 28, 29, 30, 31, 32, 1 };

	int[] strPermTab = { 16, 7, 20, 21,
						29, 12, 28, 17,
						1, 15, 23, 26,
						5, 18, 31, 10,
						2, 8, 24, 14,
						32, 27, 3, 9,
						19, 13, 30, 6,
						22, 11, 4, 25 };
	  
	int[] PC1Tab = { 57, 49, 41, 33, 25, 17, 9,
					1, 58, 50, 42, 34, 26, 18,
					10, 2, 59, 51, 43, 35, 27,
					19, 11, 3, 60, 52, 44, 36,
					63, 55, 47, 39, 31, 23, 15,
					7, 62, 54, 46, 38, 30, 22,
					14, 6, 61, 53, 45, 37, 29,
					21, 13, 5, 28, 20, 12, 4 };

	int[] PC2Tab = { 14, 17, 11, 24, 1, 5,
					3, 28, 15, 6, 21, 10,
					23, 19, 12, 4, 26, 8,
					16, 7, 27, 20, 13, 2,
					41, 52, 31, 37, 47, 55,
					30, 40, 51, 45, 33, 48,
					44, 49, 39, 56, 34, 53,
					46, 42, 50, 36, 29, 32 };

	int[] shiftTab = { 1, 1, 2, 2,
						2, 2, 2, 2,
						1, 2, 2, 2,
						2, 2, 2, 1 };

	int[] IPInv = { 40, 8, 48, 16, 56, 24, 64,
		32, 39, 7, 47, 15, 55,
		23, 63, 31, 38, 6, 46,
		14, 54, 22, 62, 30, 37,
		5, 45, 13, 53, 21, 61,
		29, 36, 4, 44, 12, 52,
		20, 60, 28, 35, 3, 43,
		11, 51, 19, 59, 27, 34,
		2, 42, 10, 50, 18, 58,
		26, 33, 1, 41, 9, 49,
		17, 57, 25 };

	String hexKey = "133457799BBCDFF1";

	String desEncrypt(String pText)
	{
		String setOfKeys[] = genKeys(hexKey);

		pText = ithPerm(initPermTab, pText);
		int c = 0;
		while(c<16){
			pText = iteration(pText, setOfKeys[c], c);c++;
		}

		pText = pText.substring(8, 16) + pText.substring(0, 8);
		pText = ithPerm(IPInv, pText);
		return pText;
	}

	String[] genKeys(String k)
	{
		String setOfKeys[] = new String[16];
		
		k = ithPerm(PC1Tab, k);
		int c = 0;
		while(c < 16) {
			k = circShiftLeft(k.substring(0, 7), shiftTab[c]) + circShiftLeft(k.substring(7, 14), shiftTab[c]);					
			setOfKeys[c] = ithPerm(PC2Tab, k);
			c++;
		}

		return setOfKeys;
	}

	String ithPerm(int[] seq, String str)
	{
		int slen = seq.length, c = 0;
		str = hex2Bin(str);
		String res = "";
		
		while(c < slen){
			res += str.charAt(seq[c] - 1);
			c++;
		}

		res = bin2Hex(res);
		return res;
	}

	String hex2Bin(String str)
	{
		int n = str.length() * 4;
		str = Long.toBinaryString(Long.parseUnsignedLong(str, 16));
		
		for(;str.length() < n;)
			str = "0" + str;

		return str;
	}

	String bin2Hex(String str)
	{
		int n = (int)str.length() / 4;
		str = Long.toHexString(Long.parseUnsignedLong(str, 2));

		for(;str.length() < n;)
			str = "0" + str;
		return str;
	}

	String circShiftLeft(String str, int nb)
	{
		int n = str.length() * 4;
		int setOfPerm[] = new int[n], c = 0;

		while( c < n - 1){
			setOfPerm[c] = (c + 2);
			c++;
		}
		setOfPerm[n-1] = 1;
		for(;nb-- > 0;)
			str = ithPerm(setOfPerm, str);

		return str;
	}

	String iteration(String str, String key, int n)
	{
		String left = str.substring(0, 8);
		String temp = str.substring(8, 16);
		String right = temp;
		
		temp = ithPerm(expDBox, temp);
		temp = xor(temp, key);
		temp = sBoxFunc(temp);
		temp = ithPerm(strPermTab, temp);
		left = xor(left, temp);
		return (right + left);
	}

	String xor(String a, String b)
	{
		long x = Long.parseUnsignedLong(a, 16), y = Long.parseUnsignedLong(b, 16);
		
		x = x ^ y;
		int bl = b.length();
		a = Long.toHexString(x);
		for(;a.length() < bl;)
			a = "0" + a;
		return a;
	}

	String sBoxFunc(String str){
		String res = "";
		str = hex2Bin(str);
		int c = 0;
		while(c < 48) {
			String temp = str.substring(c, c + 6);
			int num = c / 6;
			int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2), col = Integer.parseInt(temp.substring(1, 5), 2);
			res += Integer.toHexString(SBOX[num][row][col]);

			c += 6;
		}

		return res;
	}
}
	
