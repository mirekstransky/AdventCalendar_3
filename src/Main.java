import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {

        boolean pravda = false;
        int firstIndex;
        int lastIndex;
        int sum = 0;

        List<Integer> vybranaCisla = new ArrayList<>();
        
        List<String> listInput = readAllLines("input_3.txt");
        //List<String> listInput = readAllLines("input_test.txt");

        for (int i = 0; i < listInput.size(); i++) {

            String radek = listInput.get(i);

            String regex = "\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(radek);

            while (matcher.find()) {
                //System.out.println("Nalezené číslo: " + matcher.group());
                //System.out.println(matcher.group());

                String shodaCislo = matcher.group();

                firstIndex = radek.indexOf(shodaCislo);
                lastIndex = firstIndex + shodaCislo.length()-1;

                firstIndex = matcher.start();
                lastIndex = matcher.end()-1;

               if ((kontrolaBokyZprava(firstIndex,lastIndex,radek)) || (kontrolaBokyZleva(firstIndex,lastIndex,radek))){
                    vybranaCisla.add(Integer.parseInt(shodaCislo));
               } else{
                   //#kontrola zhora + zdola
                   if (i>1) {
                       String horniRadek = listInput.get(i - 1);
                       if (kontrolaZhoraZdola(firstIndex, lastIndex, horniRadek)) {
                           vybranaCisla.add(Integer.parseInt(shodaCislo));
                   }
                   }
                   if (i<listInput.size()-1){
                       String dolniRadek = listInput.get(i+1);
                       if (kontrolaZhoraZdola(firstIndex,lastIndex,dolniRadek)){
                          vybranaCisla.add(Integer.parseInt(shodaCislo));
                       }
                   }
               }
            }

        }
        for (int i = 0; i < vybranaCisla.size() ; i++) {
            sum += vybranaCisla.get(i);
        }
        System.out.println(sum);
    }
    public static List<String> readAllLines(String resource)throws IOException {
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            return reader.lines().collect(Collectors.toList());
        }
    }
    public static boolean kontrolaBokyZleva(int firstIndex,int lastIndex,String radek){
        if (firstIndex == 0){
            return false;
        }
        String znak = String.valueOf(radek.charAt(firstIndex-1));
        if (znak.equals(".")){
            return false;
        }else {
            return true;
        }
    }
    public static boolean kontrolaBokyZprava(int firstIndex,int lastIndex,String radek){


        if (lastIndex == radek.length()-1){
            return false;
        }
        String znak = String.valueOf(radek.charAt(lastIndex+1));
        if (znak.equals(".")){
            return false;
        }else {
            return true;
        }
    }

    public static boolean kontrolaZhoraZdola(int firstIndex, int lastIndex, String radek){
        if (firstIndex!=0){
            firstIndex -= 1;
        }
        if (lastIndex!=radek.length()-1){
            lastIndex += 1;
        }

        String subRetezec = radek.substring(firstIndex,lastIndex+1);

        for (int i = 0; i < subRetezec.length(); i++) {
            String znak = String.valueOf(subRetezec.charAt(i));
            if (!znak.equals(".")){
                return true;
            }
        }
     return false;
    }



    public static boolean isNumeric(String num) {
        if (num == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}