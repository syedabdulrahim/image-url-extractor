package com.example.demo.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ImageCrawlerService {

    public List<String> extractImages(String url) throws IOException, ParseException {
        List<String> imageUrls = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String html = EntityUtils.toString(entity);
                    Document doc = Jsoup.parse(html);
                    Elements images = doc.select("img");
                    for (Element img : images) {
                        String src = img.absUrl("src");

                        if (!src.isEmpty()) {
                            imageUrls.add(src);
                        }
                        else{

                            imageUrls.add(url+( img.attr("src")));
                        }

                    }
                }
            } catch (org.apache.hc.core5.http.ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return imageUrls;
    }
}
