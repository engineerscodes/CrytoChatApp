package com.example.crytochat.AffineChiper;
import java.util.ArrayList;
import java.util.List;


/**
 *  @author M.NAVEEN
 *  RANDOM CODER'S
 *  Tech/Project Lead Android Club
 */
public class FeistelCiphers {


     List<Integer> left = new ArrayList<>();
     List<Integer> right = new ArrayList<>();
     List<Integer> temp = new ArrayList<>();
    //  Dont remove Final
    static final long KEY = 1101110;

    public  void main(String[] args)
    {  //Scanner in = new Scanner(System.in);
        // System.out.println(KEY);

        // String str = in.nextLine();


        String input= encrypt("Hi5585p n4nef");
        System.out.println(decrypt(input));
    }
    public   String  encrypt(String str)
    {


        if (str.length() % 2 != 0)
            str += " ";


        int mid = str.length() / 2;
        for (int i = 0; i < mid; i++)
            left.add((int) str.charAt(i));

        for (int i = mid; i < str.length(); i++)
            right.add((int) str.charAt(i));


        System.out.println("Entered Text: "+ str);



        for (int r = 1; r <= 3; r++)
        {
            for (int i = 0; i < right.size(); i++)
            {
                left.set(i, left.get(i) ^ (keyFunction(right.get(i), KEY, r)));
            }
            temp = left;
            left = right;
            right = temp;
        }
        String EnMSG="";

        for (Integer i : left)
        {
            String result =  Integer.toBinaryString(i);
            while (result.length() % 7 > 0)
                result = "0" + result;
            EnMSG += result;
        }
        for (Integer i : right)
        {
            String result =  Integer.toBinaryString(i);
            while (result.length() % 7 > 0)
                result = "0" + result;
            EnMSG += result;
        }

        //decrypt(EnMSG);
        return EnMSG;
    }

    public  int keyFunction(int cur, long key, int roundNumber)
    {
        return cur * ((int)key + roundNumber)  % 15 ;
    }

    public String decrypt(String EnMSG)
    {
        left.clear();
        right.clear();

        String DeMsg = "";
        for (int i = 0; i < EnMSG.length(); i++)
        {
            if (i != 0 && i % 7 == 0)
                DeMsg += " ";
            DeMsg += EnMSG.charAt(i);
        }

        String[] arr = DeMsg.split(" ");
        // System.out.println(Arrays.toString(arr));

        int mid = arr.length / 2;
        for (int i = 0; i < mid; i++)
        {
            left.add( (Integer.parseInt(arr[i], 2)) );
        }
        for (int i = mid; i < arr.length; i++)
        {
            right.add( (Integer.parseInt(arr[i], 2)) );
        }
        // Below print is for test
        // System.out.println("****** IN DECRPYT ******");
        // System.out.println(left);
        // System.out.println(right);

        for (int r = 3; r >= 1; r--)
        {
            temp = left;
            left = right;
            right = temp;
            for (int i = 0; i < right.size(); i++)
            {
                left.set(i, left.get(i) ^ (keyFunction(right.get(i), KEY, r)));
            }
        }
        String res="";
        for (int i : left)
        {//System.out.print((char) i);
            res+=(char) i;
        }

        for (int i : right)
        { //System.out.print((char) i);
            res+=(char) i;
        }
        // System.out.println("Final text :"+res);
        return res;
    }

}

