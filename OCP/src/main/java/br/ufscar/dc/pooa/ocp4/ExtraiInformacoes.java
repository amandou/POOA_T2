package br.ufscar.dc.pooa.ocp4;

import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ExtraiInformacoes {
    public void procura(PrintWriter pw, Document doc, Elements titles) throws IOException;
}

class TituloPrincipal implements ExtraiInformacoes {

    public void procura(PrintWriter pw, Document doc, Elements titles) throws IOException {

        for (Element t : titles) {
            pw.print(String.format("Principal;%s;", t.text()));
            Element parent = t.parent();
            Elements childs = t.children();

            pw.print(String.format("\"%s\"", childs.attr("href")));

            while (parent != null && !parent.tagName().equals("a")) {
                parent = parent.parent();
            }
            if (parent != null && parent.tagName().equals("a")) {
                pw.print(String.format("\"%s\"", parent.attr("href")));
            }
            pw.print("\n");
        }
    }
}

class TituloSecundario implements ExtraiInformacoes {

    public void procura(PrintWriter pw, Document doc, Elements titles) throws IOException {

        for (Element t : titles) {
            pw.print(String.format("Secundário;%s;", t.text()));
            Element parent = t.parent();
            while (parent != null && !parent.tagName().equals("a")) {
                parent = parent.parent();
            }
            if (parent != null && parent.tagName().equals("a")) {
                pw.print(String.format("\"%s\"", parent.attr("href")));
            }
            pw.print("\n");
        }
    }

}

class HashTags implements ExtraiInformacoes {

    public void procura(PrintWriter pw, Document doc, Elements titles) throws IOException {
        for (Element t : titles) {
            pw.print(String.format("HashTag;%s;", t.text()));
            pw.print("\n");
        }
    }

}
