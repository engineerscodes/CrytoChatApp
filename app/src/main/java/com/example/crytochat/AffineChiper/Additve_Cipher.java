package com.example.crytochat.AffineChiper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *  @author M.NAVEEN
 *  RANDOM CODER'S
 *  Tech/Project Lead Android Club
 */
public class Additve_Cipher
{
	Map <Character,Integer>map;
	String output="";
	int key;
	public Additve_Cipher()
	{
		map=new HashMap<>();
		mapping();
	}

	public void mapping()
	{
		for(int i=65;i<=90;i++)
			map.put((char)i,i-65);

		for(int j=97;j<=122;j++)
		{
			map.put((char)j,j-97);
		}

		map.put((char)32,32);
		map.put((char)46,46);
		//System.out.println(" "+map);
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
				int sum=((temp+key)%26)+65;
				output+=((char)sum);
			}
		}
		//System.out.println(output);
		decryption(output,key);
		return output;
	}

	public String decryption(String output,int key)
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
			{     output2+=" "; }

			else if(map.containsKey(ch[i]))
			{	int temp=map.get(ch[i]);
				int sum=((temp-key)%26);
				if(sum<0)
				{
					//for negative number mod
					// -15 mod26 =15 in java but its 11 to fix it u have to add 25+(-15)=11
					sum=26+sum;
				}
				sum=sum+65;
				//  System.out.println(sum);
				output2+=((char)sum);
			}
		}
		//System.out.println(output2);
		return output2;
	}

}


