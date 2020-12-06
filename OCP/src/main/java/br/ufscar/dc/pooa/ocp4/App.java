package br.ufscar.dc.pooa.ocp4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

interface SitesNocicias {

    public Document getDoc();

    public Elements getMainTitle();

    public Elements getSndTitle();
}

class Globo implements SitesNocicias {
    private Document doc;
    private Elements mainTitle;
    private Elements sndTitle;

    public Globo() throws IOException {
        doc = Jsoup.connect("https://www.globo.com").get();
        mainTitle = doc.select("p.hui-premium__title");
        sndTitle = doc.select("p.hui-highlight-title");
    }

    public Document getDoc() {
        return doc;
    }

    public Elements getMainTitle() {
        return mainTitle;
    }

    public Elements getSndTitle() {
        return sndTitle;
    }
}

class FreeCodeCamp implements SitesNocicias {
    private Document doc;
    private Elements mainTitle;
    private Elements sndTitle;

    public FreeCodeCamp() throws IOException {
        doc = Jsoup.connect("https://www.freecodecamp.org/news/").get();
        mainTitle = doc.select("h2.post-card-title");
        sndTitle = doc.select("span.post-card-tags");
    }

    public Document getDoc() {
        return doc;
    }

    public Elements getMainTitle() {
        return mainTitle;
    }

    public Elements getSndTitle() {
        return sndTitle;
    }
}

public class App {
    public static void main(String[] args) throws IOException {

        String strNow = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(LocalDateTime.now());
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(String.format("dump-%s.csv", strNow))))) {
            pw.println("Tipo;Notícia;Link");

            Globo globo = new Globo();
            TituloPrincipal globoTitulosPrincipais = new TituloPrincipal();
            globoTitulosPrincipais.procura(pw, globo.getDoc(), globo.getMainTitle());
            TituloSecundario globoTitulosSecundarios = new TituloSecundario();
            globoTitulosSecundarios.procura(pw, globo.getDoc(), globo.getSndTitle());

            FreeCodeCamp free = new FreeCodeCamp(); 
            TituloPrincipal freeCodeCampNews = new TituloPrincipal();
            freeCodeCampNews.procura(pw, free.getDoc(), free.getMainTitle());
            HashTags hash = new HashTags();
            hash.procura(pw, free.getDoc(), free.getSndTitle());
        }

    }
}
