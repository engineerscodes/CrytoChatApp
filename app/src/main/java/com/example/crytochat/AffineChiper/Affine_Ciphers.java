package com.example.crytochat.AffineChiper;
import java.util.*;
/**
 *  @author M.NAVEEN
 *  RANDOM CODER'S
 *  Tech/Project Lead Android Club
 */

public class Affine_Ciphers
{   int key1=2;
	int key2=7;
	public String encryption(String input)
	{
		Multiplicative_ciphers ac=new Multiplicative_ciphers ();
		String cpt1=ac.encryption(input, key2);
		Additve_Cipher mc=new Additve_Cipher();
		String output=mc.encryption(cpt1, key1);
		System.out.println(output);
		decode(output);
		return output;
	}
	public String decode(String input)
	{
		Additve_Cipher ac=new Additve_Cipher();
		String cpt1=ac.decryption(input, key1);
		//System.out.println("addtive decode ="+cpt1);
		Multiplicative_ciphers mc=new Multiplicative_ciphers();
		String output=mc.getkeyInverse(cpt1, key2);
		//String output=mc.decryption2(cpt1, key2);
		System.out.println(" Final decode ="+output);
		return output;
	}

	public static void main(String ...strings)
	{ String x=new Affine_Ciphers().encryption("HELLO ");
		System.out.println("\nTHE ENGRYPTED IS:"+x);
	}
}
