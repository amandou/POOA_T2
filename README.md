# Trabalho 2 de Programação Orientada a Objetos Avançada


O objetivo deste trabalho é construir uma ferramenta para encontrar e baixar títulos das notícias do dia, nos principais sites de notícias (G1, UOL, etc) de forma que respeite os princípios da Responsabilidade Única e Aberto Fechado.

* A implementação deste trabalho foi baseado no código deste repositório https://github.com/dlucredio/curso-programacao-oo-avancada/tree/master/openClosedPrinciple4 

---

### Detalhes de implementação

A fim de respeitar o princício Aberto Fechado, foi usado o [Design Pattern Strategy](https://refactoring.guru/design-patterns/strategy), que ao criar uma interface permite que tenha variações de algoritmos independentemente que possam ser utilizados em outras classes. Assim, Foram feitas 2 interfaces:
* ```interface SitesNocicias```
* ```interface ExtraiInformacoes```


A primeira, irá identificar o site que será extraída as informações em que tem métodos ```Get```. A partir desta interface, foram 2 criadas classes que extenderam dela, que resepresentam os sites, tanto a url dele e as tags de extração são instanciadas no construtor.

Caso queira procurar as informações de outros sites, é preciso criar um classe e modificar o construtor para mudar o site e as tags na qual será extraída a informação. Por exemplo, para adicionar o site da BBC ficaria assim:

```
class BCC implements SitesNocicias {
    private Document doc;
    private Elements mainTitle;
    private Elements sndTitle;

    public BBC()) throws IOException {
        doc = Jsoup.connect("https://www.bbc.com").get();
        mainTitle = doc.select("li.media media--overlay block-link");
        sndTitle = doc.select("li.media  block-link");
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

```

A segunda interface chamada de tem ```procura``` que é método abstrato responsável por procurar as informações da página. Então criou-se 3 classes a partir dela, que respectivamente tem as funções: extrair os títulos principais, secundáruios e hashtags.

Para adicionar novos algoritmos na aplicação, basta criar uma nova classe que extende a interface ```ExtraiInformacoes``` e modificar o método ```procura```.

