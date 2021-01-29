package ba.unsa.etf.rpr.tutorijal8;

import com.github.tsijercic1.InvalidXMLException;
import com.github.tsijercic1.Node;
import com.github.tsijercic1.XMLParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {
    public static void main(String[] args) {
    }

    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> ret = new ArrayList<>();
        try{
            Scanner ulaz = new Scanner(new FileReader("mjerenja.txt"));
            while(ulaz.hasNextLine()){
                String trenutnaLinija = ulaz.nextLine();
                String[] val = trenutnaLinija.split(",");
                String ime = val[0];
                double[] niz = new double[1000];
                for(int i = 0; i < val.length - 1 && i < 1000; i++)
                    niz[i] = Double.parseDouble(val[i+1]);
                ret.add(new Grad(ime, niz));
            }
            ulaz.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Datoteka ulaz.txt ne postoji ili se ne može otvoriti");
        }
        return ret;
    }

    public static UN ucitajXml(ArrayList<Grad> objects) {
        UN un = new UN();
        ArrayList<Drzava> listaDrzava = new ArrayList<>();
        try{
            Node node = new XMLParser("drzave.xml").getDocumentRootNode();
            ArrayList<Node> listNode = node.getChildNodes();
            for(Node drzavaNode : listNode){
                Drzava drzava = new Drzava();
                drzava.setBrojStanovnika(Integer.parseInt(drzavaNode.getAttributes().get("stanovnika")));
                drzava.setNaziv(drzavaNode.getChildNode("naziv").getContent());

                Grad glavniGrad = new Grad();
                Node glavnigradNode = drzavaNode.getChildNode("glavnigrad");
                glavniGrad.setBrojStanovnika(Integer.parseInt(glavnigradNode.getAttributes().get("stanovnika")));
                glavniGrad.setNaziv(glavnigradNode.getChildNode("naziv").getContent());

                drzava.setGlavniGrad(glavniGrad);
                drzava.setPovrsina(Double.parseDouble(drzavaNode.getChildNode("povrsina").getContent()));
                drzava.setJedinica(drzavaNode.getChildNode("povrsina").getAttributes().get("jedinica"));

                listaDrzava.add(drzava);
            }
        }
        catch(InvalidXMLException | IOException e){
            e.printStackTrace();
        }
        un.setDrzave(listaDrzava);
        return un;
    }
}
