package main.healthcare.api.service;

import main.healthcare.api.controller.PatientsController;
import main.healthcare.api.dto.ArticleDTO;
import main.healthcare.api.model.Articles;
import main.healthcare.api.model.Doctors;
import main.healthcare.api.repository.ArticleRepository;
import main.healthcare.api.repository.DoctorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    static Logger logger = LoggerFactory.getLogger(PatientsController.class);

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private DoctorsRepository doctorsRepository;

    public Articles publishArticle(ArticleDTO articleDTO) {
        Articles article = new Articles();
        article.setTitle(articleDTO.getTitle());
        article.setDomain(articleDTO.getDomain());
        article.setContent(articleDTO.getContent());
        String[] authors = articleDTO.getDoctorsId().split(",");
        List<Doctors> articleAuthors = new ArrayList<>();
        for (String s : authors) {
            Doctors doctor = doctorsRepository.findByDoctorId(Long.parseLong(s));
            doctor.getArticles().add(article);
            articleAuthors.add(doctor);
        }
        article.setAuthors(articleAuthors);
        return articleRepository.save(article);
    }

    public List<Articles> readArticleByDomain(String domain) {
        List<Articles> articles = articleRepository.findByDomain(domain);
        for (Articles a : articles) {
            for (Doctors d : a.getAuthors()){
                System.out.println("Name = " + d.getName());
            }
        }
        return articles;
    }

    public List<Articles> readArticleByAuthor(Long doctorId) {
        Doctors doctor = doctorsRepository.findByDoctorId(doctorId);
        return articleRepository.findByAuthors(doctor);
    }

    public Articles readArticleById(Long articleId) {
        return articleRepository.findByArticleId(articleId);
    }
}
