package SDES;

class BintoDecOperation
{
	/** Gets binary digits as arguments & returns decimal number 
  for example input args [1,0,0] will return 4 **/ 
	public static int BinToDec(int...bits){
		int temp=0;
		int base=1;
		for(int i=bits.length-1 ; i>=0;i--){
			temp = temp + (bits[i]*base);
			base = base * 2 ;
		}

		return temp;
	}

	/** gets decimal number as argument and returns array of binary bits 
  for example input arg [10] will return  [1,0,1,0]**/
	public static int[] DecToBinArr(int number){

		if(number==0){
			int[] zero = new int[2];
			zero[0] = 0;
			zero[1] = 0;
			return zero;	
		}
		
		int[] temp = new int[10] ;

		int count = 0 ;
		for(int i= 0 ; number!= 0 ; i++){
			temp[i] = number % 2;
			number = number/2;
			count++;
		}


		int[] temp2 = new int[count];

		for(int i=count-1, j=0;i>=0 && j<count;i--,j++){
			temp2[j] = temp[i];
		}

		//because we requires 2-bits as output .. so for adding leading 0
		if(count<2){
			temp = new int[2];
			temp[0] = 0;
			temp[1] = temp2[0];
			return temp;
		}

		return temp2;
	}
}

