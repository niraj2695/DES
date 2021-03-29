package SDES;

class Encryption
{
	private int[] key1 = new int[8];
	private int[] key2 = new int[8];
	private int[] ptext = new int[8];

	public void computation(String plaintext , int[] ky1, int[] ky2){
		
		int[] pt = new int[8];

		char c;
		String ts ;


		for(int i=0;i<8;i++){
			c = plaintext.charAt(i);
			ts = Character.toString(c);
			pt[i] = Integer.parseInt(ts);
		}
		this.ptext = pt;

		Display.bits("Plaintext array : ");
		Display.array(this.ptext,8);
		Display.bits("\n");

		this.key1 = ky1;
		this.key2 = ky2;

	}

	/** perform Initial Permutation in following manner [2 6 3 1 4 8 5 7] **/
	public void InitialPermutation(){

		int[] ip = new int[8];

		ip[0] = ptext[1];
		ip[1] = ptext[5];
		ip[2] = ptext[2];
		ip[3] = ptext[0];
		ip[4] = ptext[3];
		ip[5] = ptext[7];
		ip[6] = ptext[4];
		ip[7] = ptext[6];

		ptext = ip;

		Display.bits("Initial Permutaion(IP) : ");
		Display.array(this.ptext,8);
		Display.bits("\n");

	} 

	public void InverseInitialPermutation(){
		int[] invIp = new int[8];

		invIp[0] = ptext[3];
		invIp[1] = ptext[0];
		invIp[2] = ptext[2];
		invIp[3] = ptext[4];
		invIp[4] = ptext[6];
		invIp[5] = ptext[1];
		invIp[6] = ptext[7];
		invIp[7] = ptext[5];

		ptext = invIp;


	}

	/** mappingF . arguments 4-bit right-half of plaintext & 8-bit subkey **/ 
	public int[] mappingF(int[] rh, int[] SK){

		int[] ep = new int[8];

		// EXPANSION/PERMUTATION [4 1 2 3 2 3 4 1] 
		ep[0]  = rh[3];
		ep[1]  = rh[0];
		ep[2]  = rh[1];
		ep[3]  = rh[2];
		ep[4]  = rh[1];
		ep[5]  = rh[2];
		ep[6]  = rh[3];
		ep[7]  = rh[0];

		Display.bits("EXPANSION/PERMUTATION on RH : ");
		Display.array(ep,8);
		Display.bits("\n");

		// Bit by bit XOR with sub-key
		ep[0] = ep[0] ^ SK[0];
		ep[1] = ep[1] ^ SK[1];
		ep[2] = ep[2] ^ SK[2];
		ep[3] = ep[3] ^ SK[3];
		ep[4] = ep[4] ^ SK[4];
		ep[5] = ep[5] ^ SK[5];
		ep[6] = ep[6] ^ SK[6];
		ep[7] = ep[7] ^ SK[7];

		Display.bits("XOR With Key : ");
		Display.array(ep,8);
		Display.bits("\n");

		// S-Box Operation
		final int[][] S0 = { {1,0,3,2} , {3,2,1,0} , {0,2,1,3} , {3,1,3,2} } ;
		final int[][] S1 = { {0,1,2,3},  {2,0,1,3}, {3,0,1,0}, {2,1,0,3}} ;


		int b11 = ep[0]; // first bit of first half 
		int b14 = ep[3]; // fourth bit of first half

		int row1 = BintoDecOperation.BinToDec(b11,b14); // for input in s-box S0


		int b12 = ep[1]; // second bit of first half
		int b13 = ep[2]; // third bit of first half      
		int col1 = BintoDecOperation.BinToDec(b12,b13); // for input in s-box S0

		int o1 = S0[row1][col1]; 

		int[] out1 = BintoDecOperation.DecToBinArr(o1);

		Display.bits("S-Box S0: ");
		Display.array(out1,2);
		Display.bits("\n");

		int d21 = ep[4]; // first bit of second half
		int d24 = ep[7]; // fourth bit of second half
		int row2 = BintoDecOperation.BinToDec(d21,d24);

		int d22 = ep[5]; // second bit of second half
		int d23 = ep[6]; // third bit of second half
		int col2 = BintoDecOperation.BinToDec(d22,d23);

		int o2 = S1[row2][col2];

		int[] out2 = BintoDecOperation.DecToBinArr(o2); 

		Display.bits("S-Box S1: ");
		Display.array(out2,2);
		Display.bits("\n");

		//4 output bits from 2 s-boxes
		int[] out = new int[4];
		out[0] = out1[0];
		out[1] = out1[1];
		out[2] = out2[0];
		out[3] = out2[1];

		//permutation P4 [2 4 3 1]

		int [] O_Per = new int[4];
		O_Per[0] = out[1];
		O_Per[1] = out[3];
		O_Per[2] = out[2];
		O_Per[3] = out[0];

		Display.bits("Output of mapping Function : ");
		Display.array(O_Per,4);
		Display.bits("\n");  

		return O_Per;
	}

	/** fK(L, R, SK) = (L (XOR) mapping Function(R, SK), R) .. returns 8-bit output**/
	public int[] functionFk(int[] L, int[] R,int[] SK){	
		int[] temp = new int[4];
		int[] out = new int[8];


		temp = mappingF(R,SK);


		//OUtput of Mapping Function is XOR with left half with output of mappingF 
		out[0] = L[0] ^ temp[0];
		out[1] = L[1] ^ temp[1];
		out[2] = L[2] ^ temp[2];
		out[3] = L[3] ^ temp[3];

		out[4] = R[0];
		out[5] = R[1];
		out[6] = R[2];
		out[7] = R[3];


		return out;

	}

	/** switch function (SW) interchanges the left and right 4 bits **/
	public int[] switchSW(int[] in){

		int[] temp = new int[8];

		temp[0] = in[4];
		temp[1] = in[5];
		temp[2] = in[6];
		temp[3] = in[7];
		temp[4] = in[0];
		temp[5] = in[1];
		temp[6] = in[2];
		temp[7] = in[3];	

		return temp;
	}

	public int[] encrypt(String plaintext , int[] LK, int[] RK){


		computation(plaintext,LK,RK);

		InitialPermutation();

		//Divide 8 Bit into left half & right half
		int[] LH = new int[4];
		int[] RH = new int[4];
		
		LH[0] = ptext[0];
		LH[1] = ptext[1];
		LH[2] = ptext[2];
		LH[3] = ptext[3];


		RH[0] = ptext[4];
		RH[1] = ptext[5];
		RH[2] = ptext[6];
		RH[3] = ptext[7];


		Display.bits("First Round LH : ");
		Display.array(LH,4);
		Display.bits("\n");

		Display.bits("First Round RH : ");
		Display.array(RH,4);
		Display.bits("\n");

		//first round with sub-key K1
		int[] r1 = new int[8];
		r1 = functionFk(LH,RH,key1);

		Display.bits("After First Round : ");
		Display.array(r1,8);
		Display.bits("\n");
		
		//Switch the left half & right half of about output
		int[] temp = new int[8];
		temp = switchSW(r1);

		Display.bits("After Switch Function : ");
		Display.array(temp,8);
		Display.bits("\n");
		
		// again separate left half & right half for second round
		LH[0] = temp[0];
		LH[1] = temp[1];
		LH[2] = temp[2];
		LH[3] = temp[3];

		RH[0] = temp[4];
		RH[1] = temp[5];
		RH[2] = temp[6];
		RH[3] = temp[7];


		Display.bits("Second Round LH : ");
		Display.array(LH,4);
		Display.bits("\n");

		Display.bits("Second Round RH: ");
		Display.array(RH,4);
		Display.bits("\n");


		//second round with sub-key K2
		int[] r2 = new int[8];
		r2 = functionFk(LH,RH,key2);

		ptext = r2;

		Display.bits("After Second Round : ");
		Display.array(this.ptext,8);
		Display.bits("\n");
		
		InverseInitialPermutation();

		Display.bits("After Inverse IP (Result) : ");
		Display.array(this.ptext,8);
		Display.bits("\n");

		//Encryption done... return 8-bit output .
		return ptext;

	}

}

