package project2.AES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestAES {
	public static void main(String args[]){
		Aes a=new Aes();
		a.star();
		a.result();
	}
}

class Aes{
	Map<String,String> sbox=new HashMap<String,String>();
	String rcon[]={"01","02","04","08","10","20","40","80","1b","36"};
	private String input;
	private String key;
	List<String> list=new ArrayList<String>();
    /**......................................Get key...........................................*/
	public void star(){
		System.out.println("----------------------------------------------------------\nID1 = 104 093 579\nID2 = 104 154 061\nGroup code (A,B) = (0,8)\nAssigned Plaintext and Key:\n	0000 0000 0000 0000 0000 0000 0000 a003 (plaintext)\n	1a0c 24f2 8754 93bc b708 0e43 930f 5303 (key)");
		System.out.println();
		System.out.println("The program is written in Java for operation system Windows\n----------------------------------------------------------\nKey Schedule Results for Each Round with the modified AES:\n----------------------------------------------------------");
		this.input= "00 00 00 00 00 00 00 00 00 00 00 00 00 00 a0 01";
		this.key="1a 0c 24 f2 87 54 93 bc b7 08 0e 43 93 0f 53 01";
		GenerAllKey();
		System.out.println("Round "+0+":\n\tKey:"+"1a 0c 24 f2 87 54 93 bc b7 08 0e 43 93 0f 53 03");
		for(int i=1;i<=10;i++){
			System.out.println("Round "+i+":\n\tKey:"+list.get((i-1)));
		}
		System.out.println("\n----------------------------------------------------------\nData  Results for Each Round with the modified AES:\n----------------------------------------------------------");
	}
	/**......................................Get plain text...........................................*/
	public void result(){
		System.out.println("\nRound 0:\n-----Start: "+input);
		String str=this.ARK(input, key);
		System.out.println("----Output: "+str);
		for(int i=0;i<9;i++){
			System.out.println("Round "+(i+1)+":\n");
			//System.out.println("-----Start:"+str);
			str=this.SubBytes(str);
			//System.out.println("-----S-Box:"+str);
			str=this.ShiftRows(str);
			//System.out.println("--ShiftRow:"+str);
			str=this.MixColumns(str);
			//System.out.println("----MixCol:"+str);
			str=this.ARK(str, list.get(i));
			System.out.println("-------Output:"+str);
		}
		System.out.println("Round "+10+":\n");
		//System.out.println("-----Start:"+str);
		str=this.SubBytes(str);
		//System.out.println("-----S-Box:"+str);
		str=this.ShiftRows(str);
		//System.out.println("--ShiftRow:"+str);
		str=this.ARK(str, list.get(9));
		System.out.println("-------Output:"+str);
		
	}
	/**......................................ShiftRows...........................................*/
	public String ShiftRows(String st){
		st=this.C2R(st);
		String arr[]=st.split(" ");
		String str=arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+
				   arr[5]+" "+arr[6]+" "+arr[7]+" "+arr[4]+" "+
				   arr[10]+" "+arr[11]+" "+arr[8]+" "+arr[9]+" "+
				   arr[15]+" "+arr[12]+" "+arr[13]+" "+arr[14];
		return  this.C2R(str);
	}
	/**......................................SubBytes...........................................*/
	public String SubBytes(String st){
		String arr[]=st.split(" ");
		String str="";
		for(int i=0;i<arr.length;i++){
			str=str+sbox.get(arr[i])+" ";
		}
		return str;
	}
	/**......................................ARK...........................................*/
	public String ARK(String s1,String s2){
		s1=C2R(s1);
		s2=C2R(s2);
		String arr1[]=s1.split(" ");
		String arr2[]=s2.split(" ");
		String str="";
		for(int i=0;i<arr1.length;i++){
			Integer i1=Integer.parseInt(arr1[i],16);
			Integer i2=Integer.parseInt(arr2[i],16);
			Integer i3=i1^i2;
			if(i3<16){
				str=str+"0"+Integer.toHexString(i3)+" ";
			}else{
				str=str+Integer.toHexString(i3)+" ";
			}
		}
		return C2R(str);
	}
	/**......................................MixColumns...........................................*/
	public String MixColumns(String st){
		String arr[]=st.split(" ");
		String str="";
		for(int i=0;i<4;i++){
			Integer in=MCxor(Integer.parseInt(arr[i*4],16),Integer.parseInt(arr[i*4+1],16), Integer.parseInt(arr[i*4+2],16), Integer.parseInt(arr[i*4+3],16), 2, 3, 1, 1);
			if(in<16){
				str=str+"0"+Integer.toHexString(in)+" ";
			}else{
				str=str+Integer.toHexString(in)+" ";
			}
		}
		for(int i=0;i<4;i++){
			Integer in=MCxor(Integer.parseInt(arr[i*4],16),Integer.parseInt(arr[i*4+1],16), Integer.parseInt(arr[i*4+2],16), Integer.parseInt(arr[i*4+3],16), 1, 2, 3, 1);
			if(in<16){
				str=str+"0"+Integer.toHexString(in)+" ";
			}else{
				str=str+Integer.toHexString(in)+" ";
			}
		}
		for(int i=0;i<4;i++){
			Integer in=MCxor(Integer.parseInt(arr[i*4],16),Integer.parseInt(arr[i*4+1],16), Integer.parseInt(arr[i*4+2],16), Integer.parseInt(arr[i*4+3],16), 1, 1, 2, 3);
			if(in<16){
				str=str+"0"+Integer.toHexString(in)+" ";
			}else{
				str=str+Integer.toHexString(in)+" ";
			}
		}
		for(int i=0;i<4;i++){
			Integer in=MCxor(Integer.parseInt(arr[i*4],16),Integer.parseInt(arr[i*4+1],16), Integer.parseInt(arr[i*4+2],16), Integer.parseInt(arr[i*4+3],16), 3, 1, 1,2);
			if(in<16){
				str=str+"0"+Integer.toHexString(in)+" ";
			}else{
				str=str+Integer.toHexString(in)+" ";
			}
		}
		return this.C2R(str);
	}
	
	private Integer MCxor(Integer a1,Integer a2,Integer a3,Integer a4,Integer b1,Integer b2,Integer b3,Integer b4){
		return Mxor(b1,a1)^Mxor(b2,a2)^Mxor(b3,a3)^Mxor(b4,a4);
	}
	
	private Integer Mxor(Integer s1,Integer s2){
		if(s1==1){
			return s2;
		}else if(s1==2){
			if(s2<128){
				return (s2<<1)%256;
			}else{
				return (((s2<<1)%256)^0x1b);
			}
		}else if(s1==3){
			if(s2<128){
				return ((s2<<1)%256) ^ s2;
			}else{
				return  (((s2<<1)%256)^0x1b) ^ s2;
			}
		}else{
			System.out.println("an error has happen!");
			return 0;
		}
	}

		public String xOr(String s1,String s2){
			String arr[]=s1.split(" ");
			String brr[]=s2.split(" ");
			String str="";
			Integer i1=Integer.parseInt(arr[0],16)^Integer.parseInt(brr[0],16);
			Integer i2=Integer.parseInt(arr[1],16)^Integer.parseInt(brr[1],16);
			Integer i3=Integer.parseInt(arr[2],16)^Integer.parseInt(brr[2],16);
			Integer i4=Integer.parseInt(arr[3],16)^Integer.parseInt(brr[3],16);
			if(i1<16){
				str=str+"0"+Integer.toHexString(i1)+" ";
			}else{
				str=str+Integer.toHexString(i1)+" ";
			}
			if(i2<16){
				str=str+"0"+Integer.toHexString(i2)+" ";
			}else{
				str=str+Integer.toHexString(i2)+" ";
			}
			if(i3<16){
				str=str+"0"+Integer.toHexString(i3)+" ";
			}else{
				str=str+Integer.toHexString(i3)+" ";
			}
			if(i4<16){
				str=str+"0"+Integer.toHexString(i4)+" ";
			}else{
				str=str+Integer.toHexString(i4)+" ";
			}
			return str.substring(0,str.length()-1);
		}
		/**......................................ShiftRows...........................................*/
		
		private void GenerAllKey(){
			String st=this.key;
			for(int i=0;i<10;i++){
				st=GenerKey(st, i);
				list.add(st);
			}
		}
	
		private String GenerKey(String st,int i){
			String arr[]=st.split(" ");
			String s1=arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3];
			String s2=arr[4]+" "+arr[5]+" "+arr[6]+" "+arr[7];
			String s3=arr[8]+" "+arr[9]+" "+arr[10]+" "+arr[11];
			String s4=arr[12]+" "+arr[13]+" "+arr[14]+" "+arr[15];
			String s5=arr[13]+" "+arr[14]+" "+arr[15]+" "+arr[12];
			s5=SubBytes(s5);
			//String r=rcon[i];
			String a1=xOr(xOr(s5, s1),rcon[i]+" 00 00 00");
			String a2=xOr(a1, s2);
			String a3=xOr(a2, s3);
			String a4=xOr(a3, s4);
			String str=a1+" "+a2+" "+a3+" "+a4;
			return str;
		}
		private String C2R(String s){
			String arr[]=s.split(" ");
			String str=arr[0]+" "+arr[4]+" "+arr[8]+" "+arr[12]+" "+
					   arr[1]+" "+arr[5]+" "+arr[9]+" "+arr[13]+" "+
					   arr[2]+" "+arr[6]+" "+arr[10]+" "+arr[14]+" "+
					   arr[3]+" "+arr[7]+" "+arr[11]+" "+arr[15];
			return str;
		}
	
	Aes(){
		sbox.put("60","63" );sbox.put("61","7c" );sbox.put("62","77");sbox.put("63","7b" );sbox.put("64","f2" );sbox.put("65","6b" );sbox.put("66","6f");sbox.put("67","c5" );sbox.put("68","30" );sbox.put("69","01" );sbox.put("6a","67");sbox.put("6b","2b" );sbox.put("6c","fe" );sbox.put("6d","d7" );sbox.put("6e","ab");sbox.put("6f","76" );
		sbox.put("10","ca" );sbox.put("11","82" );sbox.put("12","c9");sbox.put("13","7d" );sbox.put("14","fa" );sbox.put("15","59" );sbox.put("16","47");sbox.put("17","f0" );sbox.put("18","ad" );sbox.put("19","d4" );sbox.put("1a","a2");sbox.put("1b","af" );sbox.put("1c","9c" );sbox.put("1d","a4" );sbox.put("1e","72");sbox.put("1f","c0" );
		sbox.put("20","b7" );sbox.put("21","fd" );sbox.put("22","93");sbox.put("23","26" );sbox.put("24","36" );sbox.put("25","3f" );sbox.put("26","f7");sbox.put("27","cc" );sbox.put("28","34" );sbox.put("29","a5" );sbox.put("2a","e5");sbox.put("2b","f1" );sbox.put("2c","71" );sbox.put("2d","d8" );sbox.put("2e","31");sbox.put("2f","15" );
		sbox.put("30","04" );sbox.put("31","c7" );sbox.put("32","23");sbox.put("33","c3" );sbox.put("34","18" );sbox.put("35","96" );sbox.put("36","05");sbox.put("37","9a" );sbox.put("38","07" );sbox.put("39","12" );sbox.put("3a","80");sbox.put("3b","e2" );sbox.put("3c","eb" );sbox.put("3d","27" );sbox.put("3e","b2");sbox.put("3f","75" );
		sbox.put("40","09" );sbox.put("41","83" );sbox.put("42","2c");sbox.put("43","1a" );sbox.put("44","1b" );sbox.put("45","6e" );sbox.put("46","5a");sbox.put("47","a0" );sbox.put("48","52" );sbox.put("49","3b" );sbox.put("4a","d6");sbox.put("4b","b3" );sbox.put("4c","29" );sbox.put("4d","e3" );sbox.put("4e","2f");sbox.put("4f","84" );
		sbox.put("50","53" );sbox.put("51","d1" );sbox.put("52","00");sbox.put("53","ed" );sbox.put("54","20" );sbox.put("55","fc" );sbox.put("56","b1");sbox.put("57","5b" );sbox.put("58","6a" );sbox.put("59","cb" );sbox.put("5a","be");sbox.put("5b","39" );sbox.put("5c","4a" );sbox.put("5d","4c" );sbox.put("5e","58");sbox.put("5f","cf" );
		sbox.put("00","d0" );sbox.put("01","ef" );sbox.put("02","aa");sbox.put("03","fb" );sbox.put("04","43" );sbox.put("05","4d" );sbox.put("06","33");sbox.put("07","85" );sbox.put("08","45" );sbox.put("09","f9" );sbox.put("0a","02");sbox.put("0b","7f" );sbox.put("0c","50" );sbox.put("0d","3c" );sbox.put("0e","9f");sbox.put("0f","a8" );
		sbox.put("70","51" );sbox.put("71","a3" );sbox.put("72","40");sbox.put("73","8f" );sbox.put("74","92" );sbox.put("75","9d" );sbox.put("76","38");sbox.put("77","f5" );sbox.put("78","bc" );sbox.put("79","b6" );sbox.put("7a","da");sbox.put("7b","21" );sbox.put("7c","10" );sbox.put("7d","ff" );sbox.put("7e","f3");sbox.put("7f","d2" );
		sbox.put("80","cd" );sbox.put("81","0c" );sbox.put("82","13");sbox.put("83","ec" );sbox.put("84","5f" );sbox.put("85","97" );sbox.put("86","44");sbox.put("87","17" );sbox.put("88","c4" );sbox.put("89","a7" );sbox.put("8a","7e");sbox.put("8b","3d" );sbox.put("8c","64" );sbox.put("8d","5d" );sbox.put("8e","19");sbox.put("8f","73" );
		sbox.put("90","60" );sbox.put("91","81" );sbox.put("92","4f");sbox.put("93","dc" );sbox.put("94","22" );sbox.put("95","2a" );sbox.put("96","90");sbox.put("97","88" );sbox.put("98","46" );sbox.put("99","ee" );sbox.put("9a","b8");sbox.put("9b","14" );sbox.put("9c","de" );sbox.put("9d","5e" );sbox.put("9e","0b");sbox.put("9f","db" );
		sbox.put("a0","e0" );sbox.put("a1","32" );sbox.put("a2","3a");sbox.put("a3","0a" );sbox.put("a4","49" );sbox.put("a5","06" );sbox.put("a6","24");sbox.put("a7","5c" );sbox.put("a8","c2" );sbox.put("a9","d3" );sbox.put("aa","ac");sbox.put("ab","62" );sbox.put("ac","91" );sbox.put("ad","95" );sbox.put("ae","e4");sbox.put("af","79" );
		sbox.put("b0","e7" );sbox.put("b1","c8" );sbox.put("b2","37");sbox.put("b3","6d" );sbox.put("b4","8d" );sbox.put("b5","d5" );sbox.put("b6","4e");sbox.put("b7","a9" );sbox.put("b8","6c" );sbox.put("b9","56" );sbox.put("ba","f4");sbox.put("bb","ea" );sbox.put("bc","65" );sbox.put("bd","7a" );sbox.put("be","ae");sbox.put("bf","08" );
		sbox.put("c0","ba" );sbox.put("c1","78" );sbox.put("c2","25");sbox.put("c3","2e" );sbox.put("c4","1c" );sbox.put("c5","a6" );sbox.put("c6","b4");sbox.put("c7","c6" );sbox.put("c8","e8" );sbox.put("c9","dd" );sbox.put("ca","74");sbox.put("cb","1f" );sbox.put("cc","4b" );sbox.put("cd","bd" );sbox.put("ce","8b");sbox.put("cf","8a" );
		sbox.put("d0","70" );sbox.put("d1","3e" );sbox.put("d2","b5");sbox.put("d3","66" );sbox.put("d4","48" );sbox.put("d5","03" );sbox.put("d6","f6");sbox.put("d7","0e" );sbox.put("d8","61" );sbox.put("d9","35" );sbox.put("da","57");sbox.put("db","b9" );sbox.put("dc","86" );sbox.put("dd","c1" );sbox.put("de","1d");sbox.put("df","9e" );
		sbox.put("e0","e1" );sbox.put("e1","f8" );sbox.put("e2","98");sbox.put("e3","11" );sbox.put("e4","69" );sbox.put("e5","d9" );sbox.put("e6","8e");sbox.put("e7","94" );sbox.put("e8","9b" );sbox.put("e9","1e" );sbox.put("ea","87");sbox.put("eb","e9" );sbox.put("ec","ce" );sbox.put("ed","55" );sbox.put("ee","28");sbox.put("ef","df" );
		sbox.put("f0","8c" );sbox.put("f1","a1" );sbox.put("f2","89");sbox.put("f3","0d" );sbox.put("f4","bf" );sbox.put("f5","e6" );sbox.put("f6","42");sbox.put("f7","68" );sbox.put("f8","41" );sbox.put("f9","99" );sbox.put("fa","2d");sbox.put("fb","0f" );sbox.put("fc","b0" );sbox.put("fd","54" );sbox.put("fe","bb");sbox.put("ff","16" );
	}
}