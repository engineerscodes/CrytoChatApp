package com.example.crytochat.AffineChiper;

/**
 *  @author M.NAVEEN
 *  RANDOM CODER'S
 *  Tech/Project Lead Android Club
 */
import java.util.*;
public class caesiro
{
    int shift=0;

    public  String encrypt(String text)
    {    shift=8;
        if(shift>26)
        {
            shift%=26;
        }
        else if(shift<0)
        {
            shift=(shift%26)+26;
        }
        String cipher=" ";
        for(int i=0;i<text.length();i++)
        {
            char bal=text.charAt(i);
            if(Character.isLetter(bal))
            {
                if(Character.isLowerCase(bal))
                {
                    char cae=(char)(bal+shift);
                    if(cae>'z')
                    {
                        cipher+=(char)(bal-(26-shift));
                    }
                    else
                    {
                        cipher=cipher+cae;
                    }
                }
                else if(Character.isUpperCase(bal))
                {
                    char cae=(char)(bal+shift);
                    if(cae>'Z')
                    {
                        cipher+=(char)(bal-(26-shift));
                    }
                    else
                    {
                        cipher+=cae;
                    }
                }
            }
            else
            {
                cipher=cipher+bal;
            }
        }

        return cipher;
    }
    public  String decrypt(String text)
    {   shift=8;
        if(shift>26)
        {
            shift%=26;
        }
        else if(shift<0)
        {
            shift=(shift%26)+26;
        }
        String cipher=" ";
        for(int i=0;i<text.length();i++)
        {
            char bal=text.charAt(i);
            if(Character.isLetter(bal))
            {
                if(Character.isLowerCase(bal))
                {
                    char cae=(char)(bal-shift);
                    if(cae<'a')
                    {
                        cipher+=(char)(bal+(26-shift));
                    }
                    else
                    {
                        cipher=cipher+cae;
                    }
                }
                else if(Character.isUpperCase(bal))
                {
                    char cae=(char)(bal-shift);
                    if(cae<'A')
                    {
                        cipher+=(char)(bal+(26-shift));
                    }
                    else
                    {
                        cipher+=cae;
                    }
                }
            }
            else
            {
                cipher=cipher+bal;
            }
        }

        return cipher;
    }

    public static void main(String arg[])
    {
	 /* Scanner sin=new Scanner(System.in);
	  System.out.println("Enter the plain text:\n");
	  String msg=sin.nextLine();
	  System.out.println("Enter the shif:\n");
	 // int key=sin.nextInt();
	  System.out.println("the encrypted Text:\n");
	  String cipher=encrypt(msg);
	  System.out.println(cipher);
	  System.out.println("the decrypted Text:\n");
	  String decode=decrypt(cipher);
	  System.out.println(decode);*/

        String en=new caesiro().encrypt("hello 7299 nave 00%");
        System.out.println(new caesiro().decrypt(en) );

    }
}
