package Instrumente_muzicale;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class MainApp2 {

    public static void salvare(Set<InstrumentMuzical> instrumente){
        try{
            File file=new File("src/main/java/Instrumente_muzicale/instrumente.json");
            ObjectMapper ob=new ObjectMapper();
            ob.activateDefaultTyping(ob.getPolymorphicTypeValidator());
            ob.configure(SerializationFeature.INDENT_OUTPUT, true);
            ob.writeValue(file,instrumente);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Set<InstrumentMuzical> incarcare(){

        try {
            File file=new File("src/main/java/Instrumente_muzicale/instrumente.json");
            ObjectMapper mapper=new ObjectMapper();
            Set<InstrumentMuzical> incarcate = mapper.readValue(file, new TypeReference<Set<InstrumentMuzical>>() {});
            return incarcate;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        Set<InstrumentMuzical> instrumente= new HashSet<>();
        //creare
        instrumente.add(new Chitara("Gibson",2000,TipChitara.ELECTRICA, 6));
        instrumente.add(new Chitara("Fender", 1500, TipChitara.ACUSTICA, 12));
        instrumente.add(new Chitara("Yamaha", 1800, TipChitara.CLASICA, 6));
        instrumente.add(new SetTobe("Roland", 2900, TipTobe.ACUSTICE, 5, 3));
        instrumente.add(new SetTobe("Pearl", 2500, TipTobe.ACUSTICE, 12, 2));
        instrumente.add(new SetTobe("Ludwig", 4000, TipTobe.ELECTRONICE, 6, 4));
        //afisare
        instrumente.forEach((value)-> System.out.println(value));
        //salvare
        salvare(instrumente);
        //incarcare
        Set<InstrumentMuzical> incarcate=new HashSet<>();
                incarcate=incarcare();

        incarcate.forEach((value)-> System.out.println(value));

        //afisare implementare
        System.out.println();
        System.out.println(incarcate.getClass().getName());

        //verificare daca Set perimte duplicate
        InstrumentMuzical duplicat= new Chitara("Gibson", 2000, TipChitara.ELECTRICA, 6);
        boolean dublura=incarcate.add(duplicat);

        if(dublura) System.out.println("Incarcare reusita a dublurii");
        else System.out.println("Incaracare nereusita a dublurii");

        //stergere intrumente peste 300 lei
        incarcate.removeIf(instrument-> instrument.getPret()>3000);
        System.out.println(incarcate);
        System.out.println();
        //afisare date chitari
        incarcate.stream()
                .filter(instrument-> instrument instanceof Chitara)
                .forEach(System.out::println);
        //afisare date tobe
        System.out.println();
        incarcate.stream()
                .filter(instrument-> instrument.getClass() == SetTobe.class)
                .forEach(System.out::println);
        //afis chitara cu cele mai multe corzi
        System.out.println();
        incarcate.stream()
                .filter(instrument-> instrument instanceof Chitara)
                .map(instrument-> (Chitara) instrument)
                .max(Comparator.comparingInt(Chitara::getNr_corzi))
                .ifPresent(System.out::println);
        //afisare tobe dupa nr de tobe
        System.out.println();
        incarcate.stream()
                .filter(instrument->instrument instanceof SetTobe)
                .map(instrument-> (SetTobe) instrument)
                .filter(instrument-> instrument.getTip_tobe()==TipTobe.ACUSTICE)
                .sorted(Comparator.comparingInt(SetTobe::getNr_tobe))
                .forEach(System.out::println);



        }


}
