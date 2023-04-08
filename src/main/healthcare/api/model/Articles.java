package main.healthcare.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ARTICLES")
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ARTICLE_SEQ")
    @SequenceGenerator(name = "ARTICLE_SEQ", sequenceName = "ARTICLE_SEQ", allocationSize = 1)
    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "DOMAIN")
    private String domain;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @ManyToMany(mappedBy = "articles")
    private List<Doctors> authors;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Doctors> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Doctors> authors) {
        this.authors = authors;
    }
}
