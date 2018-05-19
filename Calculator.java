import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Calculator {
    public static void main(String[] args) {
        String fileIn="/home/olga/IdeaProjects/javaRush/JavaRushTasks/2.JavaCore/src/input.txt";
        String fileOut="/home/olga/IdeaProjects/javaRush/JavaRushTasks/2.JavaCore/src/output.txt";

        Calculator calc=new Calculator();

        ArrayList<String> list=calc.toReadInput(fileIn);
        ArrayList<String> outList=new ArrayList<>();

        for(String l:list){
            outList.add(l+"="+calc.parseToCalculate(l));
        }

        calc.toWriteOutput(outList,fileOut);
    }


    ArrayList<String> toReadInput(String s){
        List<String> list=new ArrayList<>();
        try(BufferedReader in=new BufferedReader(new FileReader(s))){
            while(in.ready()){
                list.add(in.readLine().replaceAll(" ",""));
            }
        }catch(IOException e){
            System.out.println("FileNotFound");
        }
        return (ArrayList<String>) list;
    }

    void toWriteOutput(ArrayList<String> outList,String s){
        try(FileWriter output=new FileWriter(s,true)){
            for(String out:outList){
                output.write(out+"\n");
            }
        }catch (IOException e){
                System.out.println("IO out");
        }
    }

    String parseToCalculate(String s){

        StringTokenizer stDig=new StringTokenizer(s,"+-*/");
        StringTokenizer stSign=new StringTokenizer(s,"0123456789");

        ArrayList<Double> digits=new ArrayList<>();
        ArrayList<String> signs=new ArrayList<>();

        while (stSign.hasMoreTokens()){
            signs.add(stSign.nextToken());
        }

        while (stDig.hasMoreTokens()){
            try{
                digits.add(Double.parseDouble(stDig.nextToken()));

            }catch(NumberFormatException e){
                return "can't calculate";
            }
        }

        return calculate(digits,signs);
    }
    String calculate(ArrayList<Double> digits,ArrayList<String> signs){

        for (int i=signs.size()-1; i>=0;i--){
            if ("*".equals(signs.get(i))){
                removeAndAdd(digits,signs,i,digits.get(i)*digits.get(i+1));

            } else if ("/".equals(signs.get(i))){
                removeAndAdd(digits,signs,i,digits.get(i)/digits.get(i+1));
            }
        }

            for (int i=signs.size()-1; i>=0; i--){
                if ("+".equals(signs.get(i))){
                    removeAndAdd(digits,signs,i,digits.get(i)+digits.get(i+1));

                } else if ("-".equals(signs.get(i))){
                    removeAndAdd(digits,signs,i,digits.get(i)-digits.get(i+1));
                }
            }
            Double result=digits.get(0);

            if(result>result.intValue()){
                return result.toString();
            }else return String.valueOf(result.intValue());

            //return digits.get(0).toString();
    }

    void removeAndAdd(ArrayList<Double> digits,ArrayList<String> signs, int i,double temp){
        digits.remove(i+1);
        digits.remove(i);
        digits.add(i,temp);
        signs.remove(i);
    }

}
