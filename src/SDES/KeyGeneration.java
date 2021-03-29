package SDES;

/** This class Generated two 8-bit subkeys from 10-bit input key **/
class KeyGeneration
{

	private int[] key = new int[10];
	private int[] key1 = new int[8];
	private int[] key2 = new int[8];
	private boolean flag = false;


	public void GenerateKeys(String inputkey){
		int[] key = new int[10];
		char x;
		String str ;

		for(int i=0;i<10;i++){
			x = inputkey.charAt(i);
			str = Character.toString(x);
			key[i] = Integer.parseInt(str);
		}

		this.key = key;

		Display.bits("Input Key : ");
		Display.array(this.key,10);
		Display.bits("\n");

		permutationP10();
		Display.bits("After Permutation(P10) Key : ");
		Display.array(this.key,10);
		Display.bits("\n");

		leftshiftLS1();
		Display.bits("After LeftShift LS-1 Key : ");
		Display.array(this.key,10);
		Display.bits("\n");


		this.key1 = permutationP8();

		Display.bits("Subkey K1 Generated : ");
		Display.array(this.key1,8);
		Display.bits("\n");

		leftshiftLS2();
		Display.bits("After LeftShift LS-2 Key : ");
		Display.array(this.key,10);
		Display.bits("\n");

		this.key2 = permutationP8();
		Display.bits("Subkey K2 Generated : ");
		Display.array(this.key2,8);
		Display.bits("\n"); 

		flag = true;

	}

	/** Perform permutation P10 on 10-bit key 
  P10(k1, k2, k3, k4, k5, k6, k7, k8, k9, k10) = (k3, k5, k2, k7, k4, k10, k1, k9, k8, k6)
	 **/

	public void permutationP10(){
		
		int[] per10 = new int[10];
		per10[0] = key[2];
		per10[1] = key[4];
		per10[2] = key[1];
		per10[3] = key[6];
		per10[4] = key[3];
		per10[5] = key[9];
		per10[6] = key[0];
		per10[7] = key[8];
		per10[8] = key[7];
		per10[9] = key[5];
		key = per10;

	}

	/** Performs a circular left shift (LS-1), or rotation, separately on the first
five bits and the second five bits. **/

	public void leftshiftLS1(){
		
		int[] leftshift1 = new int[10];
		leftshift1[0] = key[1];
		leftshift1[1] = key[2];
		leftshift1[2] = key[3];
		leftshift1[3] = key[4];
		leftshift1[4] = key[0];
		leftshift1[5] = key[6];
		leftshift1[6] = key[7];
		leftshift1[7] = key[8];
		leftshift1[8] = key[9];
		leftshift1[9] = key[5];
		key = leftshift1;

	}

	/** apply Permutaion P8, which picks out and permutes 8 of the 10 bits according to the following 
  rule: P8[ 6 3 7 4 8 5 10 9 ] , 8-bit subkey is returned **/

	public int[] permutationP8(){
		
		int[] per8 = new int[8];
		per8[0] = key[5];
		per8[1] = key[2];
		per8[2] = key[6];
		per8[3] = key[3];
		per8[4] = key[7];
		per8[5] = key[4];
		per8[6] = key[9];
		per8[7] = key[8];

		return per8;

	}


	public void leftshiftLS2(){
		
		int[] leftShift2 = new int[10];

		leftShift2[0] = key[2];
		leftShift2[1] = key[3];
		leftShift2[2] = key[4];
		leftShift2[3] = key[0];
		leftShift2[4] = key[1];
		leftShift2[5] = key[7];
		leftShift2[6] = key[8];
		leftShift2[7] = key[9];
		leftShift2[8] = key[5];
		leftShift2[9] = key[6];

		key = leftShift2;

	}


	public int[] getK1(){
		return key1;
	}

	public int[] getK2(){
		return key2;
	}  

}
