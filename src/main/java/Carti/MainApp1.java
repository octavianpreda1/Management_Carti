package Carti;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MainApp1 {

    public static Map<Integer,Carte> citire(){
        try {
            File file=new File("src/main/java/Carti/carti.json");
            ObjectMapper ob= new ObjectMapper();
            Map<Integer,Carte> carti=ob.readValue(file, new TypeReference<Map<Integer, Carte>>() {});

            return carti;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void salvare(Map<Integer, Carte> colectie){
        try{
            File file= new File("src/main/java/Carti/carti.json");
            ObjectMapper ob= new ObjectMapper();
            ob.writeValue(file, colectie);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Map<Integer,Carte> colectie=citire();
        //afisare
        colectie.forEach((id,carte)-> System.out.println("ID: " + id + " " +carte));

        //stergere
        colectie.remove(1);
        System.out.println();
        colectie.forEach((id,carte)-> System.out.println("ID: " + id + " " +carte));

        //adaugare
        Carte newCarte= new Carte("The bag with two coins","John Branch", 1900);
        colectie.putIfAbsent(1,newCarte);

        System.out.println();
        colectie.forEach((id,carte)-> System.out.println("ID: " + id + " " +carte));

        //salvare
        salvare(colectie);

        //creare colectie set
        Set<Carte> cartiAutor = colectie.values()
                .stream()
                .filter(carte -> carte.autorul().equals("Yuval Noah Harari"))
                .collect(Collectors.toSet());
                //afisare set
             System.out.println();
            cartiAutor.forEach(System.out::println);

            //afisare ordonata
            System.out.println();
            cartiAutor.stream()
                      .sorted(Comparator.comparing(Carte::titlul))
                      .forEach(System.out::println);

        //afisare cea mai veche carte
        System.out.println();
        Optional<Carte> cmvCarte = cartiAutor.stream()
                .min(Comparator.comparing(Carte::anul));
        cmvCarte.ifPresent(System.out::println);



    }


}
