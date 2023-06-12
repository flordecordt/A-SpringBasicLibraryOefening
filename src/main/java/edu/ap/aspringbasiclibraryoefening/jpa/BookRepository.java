package edu.ap.aspringbasiclibraryoefening.jpa;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long>{
    List<Book> findByTitleContainsIgnoreCase(@Param("title") String title);
}
