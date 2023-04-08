package main.healthcare.api.controller;

import main.healthcare.api.dto.ArticleDTO;
import main.healthcare.api.dto.DiagnoseDTO;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Articles;
import main.healthcare.api.model.Diagnose;
import main.healthcare.api.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    static Logger logger = LoggerFactory.getLogger(PatientsController.class);

    @Autowired
    private ArticleService articleService;


    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/publishArticle")
    public ResponseEntity<Articles> publicArticle(@RequestBody ArticleDTO articleDTO) throws ValidateDataException {
        logger.info("Inserting new article in database...");
        return new ResponseEntity<>(articleService.publishArticle(articleDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT', 'PHARMACIST')")
    @GetMapping("/readArticle/domain={domain}")
    public List<Articles> readArticleByDomain(@PathVariable String domain) throws ValidateDataException {
        logger.info("Getting article from database...");
        return articleService.readArticleByDomain(domain);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT', 'PHARMACIST')")
    @GetMapping("/readArticle/doctorId={doctorId}")
    public List<Articles> readArticleByAuthor(@PathVariable Long doctorId) throws ValidateDataException {
        logger.info("Getting article from database...");
        return articleService.readArticleByAuthor(doctorId);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT', 'PHARMACIST')")
    @GetMapping("/readArticle/articleId={articleId}")
    public Articles readArticleById(@PathVariable Long articleId) throws ValidateDataException {
        logger.info("Getting article from database...");
        return articleService.readArticleById(articleId);
    }
}
