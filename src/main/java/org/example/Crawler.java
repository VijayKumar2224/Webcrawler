package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    HashSet<String> urlSet;

    int MAX_LENGTH = 2;

    Crawler(){
        urlSet = new HashSet<String>();
    }
    public void getPageTextsAndLink(String url, int depth){
        if(urlSet.contains(url)){
            return;
        }
        if(depth > MAX_LENGTH){
            return;
        }
        depth++;
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();

            Indexer indexer = new Indexer(document, url);
            System.out.println(document.title());
            Elements availablelinksOnPage = document.select("a[href]");
            for (Element currentlink : availablelinksOnPage) {
                getPageTextsAndLink(currentlink.attr("abs:href"), depth);
            }
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextsAndLink("https://www.javatpoint.com", 1);

    }
}