package org.onecell.spring.template.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TestRepository {
    @PersistenceContext
    private Session em;


}
