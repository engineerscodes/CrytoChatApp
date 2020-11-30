/*
Author: Jayan Praveen
Title: Feistel Cipher
*/
import java.util.*;

class encrypt
{
    encrypt(List<Integer> right, List<Integer> left, List<Integer> temp, long KEY)
    {
        for (int r = 1; r <= 3; r++) 
        {
            for (int i = 0; i < right.size(); i++) 
            {
                left.set(i, left.get(i) ^ (Function.function(right.get(i), KEY, r)));
            }
            temp = left;
            left = right;
            right = temp;
        }

        new decrypt(right,left,temp,KEY);
    }
}

class decrypt
{
    decrypt(List<Integer> right, List<Integer> left, List<Integer> temp, long KEY)
    {
        for (int r = 3; r >= 1; r--) 
        {
            temp = left;
            left = right;
            right = temp;
            for (int i = 0; i < right.size(); i++)
            {
                left.set(i, left.get(i) ^ (Function.function(right.get(i), KEY, r)));
            }
        }
        System.out.print("Ouput: ");
        for (int i : left) 
            System.out.print((char) i);

        for (int i : right) 
            System.out.print((char) i);
    }
}

class Function
{
    public static int function(int cur, long key, int roundNumber)
    {
        // Change the function
        return (int) 2 * (int) key ;
    }
}

public class FeistelCipher
{
    public static void main(String args[])
    {
        Scanner in = new Scanner(System.in);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        // Change the KEY value
        final long KEY = 12;
        System.out.println(KEY);

        String str = in.nextLine();

        if (str.length() % 2 != 0)
            str += " ";

        System.out.println("Entered Text: "+ str);

        int mid = str.length() / 2;
        for (int i = 0; i < mid; i++)
            left.add((int) str.charAt(i));

        for (int i = mid; i < str.length(); i++)
            right.add((int) str.charAt(i));

        new encrypt(right,left,temp,KEY);

    }
}
