package main.healthcare.api.repository;

import main.healthcare.api.model.Articles;
import main.healthcare.api.model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Articles, Long> {
    public List<Articles> findByAuthors(Doctors doctor);
    public List<Articles> findByDomain(String domain);
    public Articles findByArticleId(Long id);

}
