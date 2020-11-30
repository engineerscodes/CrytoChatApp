package com.example.crytochat.AffineChiper;
import java.util.*;


/**
 *  @author M.NAVEEN
 *  RANDOM CODER'S
 *  Tech/Project Lead Android Club
 */
public class Multiplicative_ciphers
{
	Map <Character,Integer>map=new HashMap();
	Map <Character,Integer>Invermap=new HashMap();
	int key;
	int inverseKey;
	String output="";
	public Multiplicative_ciphers()
	{
		Additve_Cipher ac=new Additve_Cipher();
		this.map=ac.map;
	}

	public String encryption(String input,int key)
	{
		this.key=key;
		char ch[]=input.toCharArray();

		for(int i=0;i<ch.length;i++)
		{

			if(!map.containsKey(ch[i]))
			{
				output+=ch[i];
			}


			else if(map.get(ch[i])==32)
			{     output+=" ";
			}
			else if(map.containsKey(ch[i]))
			{	int temp=map.get(ch[i]);
				//System.out.println(temp+" "+key);
				int sum=((temp*key)%26)+65;
				output+=((char)sum);
			}
		}
		//	System.out.println(output);
		//getkeyInverse();
		return output;
	}

	public String getkeyInverse(String output,int key)
	{
		Invermap=new  Mutiplicative_Inverse ().inverse(26);
		//System.out.print(Invermap);
		if(Invermap.containsKey(key))
		{
			inverseKey=Invermap.get(key);
		}
		return decryption2( output,inverseKey);
	}
	public String decryption2(String output,int Keys)
	{
		char ch[]=output.toCharArray();
		String output2="";
		for(int i=0;i<ch.length;i++)
		{
			if(!map.containsKey(ch[i]))
			{
				output2+=ch[i];
			}

			else if(map.get(ch[i])==32)
			{     output2+=" ";
			}
			else	if(map.containsKey(ch[i]))
			{	int temp=map.get(ch[i]);
				int sum=((temp*Keys)%26);

				if(sum<0)
				{
					//for negative number mod
					// -15 mod26 =15 in java but its 11 to fix it u have to add 25+(-15)=11
					sum=26+sum;
				}
				sum=sum+65;

				output2+=((char)sum);
			}
		}
		//System.out.print(output2);
		return output2;
	}



}