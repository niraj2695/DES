package SDES;
import java.util.*;

/**
 * Created by Ronny on 9/25/2017.
 */

class DES
{
	public static void main(String[] args){

		Scanner scan = new Scanner(System.in);
		String plainText ;
		String key;
		int[] cipherText = new int[8];


		// Input : 10011101
		System.out.print("Enter the Plaintext : ");
		plainText = scan.next();

		//Ex Input : 0110101011  			
		System.out.print("Enter the secret Key : ");
		key = scan.next();

		KeyGeneration keyGen = new KeyGeneration();
		Encryption encrypt  = new Encryption();
		System.out.println(" \n ");

		Display.bits("Operation Key Generation Start  \n");
		keyGen.GenerateKeys(key);
		Display.bits("End of Key Generation Operation  \n");
		

		cipherText = encrypt.encrypt( plainText ,keyGen.getK1(),keyGen.getK2());

		System.out.println(" \n ");
		System.out.println("Decryption ");

		//Ex Input : 10001101
		System.out.print("Enter the Ciphertext : ");
		plainText = scan.next();

		//Ex Input : 1010000010  			
		System.out.print("Enter the Secret Key : ");
		key = scan.next();
		System.out.println(" \n ");
		Display.bits("First - Key Generation \n");
		Display.bits("For decryption Two Sub-keys will be used in reverse order \n");
		keyGen.GenerateKeys(key);
		cipherText = encrypt.encrypt( plainText ,keyGen.getK2(),keyGen.getK1());

	
	}
}


/** print Strings & arrays shortly **/
class Display{
	
	public static void array(int[] a,int len){
			
		System.out.println();

		for(int i=0;i<len;i++){
			System.out.print(a[i] + " ");
		}
	}

	public static void bits(String bits){
		System.out.print(bits);
	}
}

