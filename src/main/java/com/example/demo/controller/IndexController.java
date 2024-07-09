package com.example.demo.controller;

import com.example.demo.service.ImageCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class IndexController {


    @Autowired
    ImageCrawlerService imageCrawlerService;
    @PostMapping("/get-image-urls")
    public List<String> index() throws IOException, ParseException {
        return imageCrawlerService.extractImages("https://www.google.co.in/");
//        return "Hello World";

    }

}
