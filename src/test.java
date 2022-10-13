import java.util.Arrays;
import java.util.Scanner;
public class test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("How many numbers(less than 10) are there in the array a ? ");
        int a_len = input.nextInt();

        System.out.println("please input " + a_len + " integers :");
        String s = input.nextLine();
        int[] a = Arrays.asList(s.split(" ")).stream().mapToInt(Integer::parseInt).toArray();


        System.out.print("How many numbers(less than 10) are there in the array b ? ");
        int b_len = input.nextInt();
        System.out.print("please input " + b_len + " integers :");
        String t = input.nextLine();
        int[] b = Arrays.asList(t.split(" ")).stream().mapToInt(Integer::parseInt).toArray();

        if(equals(a, b)){
            System.out.println("Two arrays are equal.");
        }else{
            System.out.println("Two arrays are not equals");
        }
    }
    public static boolean equals(int[] a, int[] b){
        if(a.length!=b.length){
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if(a[i]!=b[i]){
                return false;
            }
        }
        return true;
    }
}


