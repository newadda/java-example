package org.onecellboy.querydsl;

import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QSort;

import javax.persistence.EntityManager;
import java.util.List;

public class QueryDSLPaging {

    private EntityManager entityManager;
    private Class fromClass;

    public QueryDSLPaging(EntityManager entityManager,Class fromClass) {
        this.entityManager = entityManager;
        this.fromClass = fromClass;
    }

    public  Querydsl getQuerydsl()
    {
        PathBuilder builder = new PathBuilderFactory().create(fromClass);
        Querydsl querydsl = new Querydsl(entityManager, builder);
        return  querydsl;
    }

    public  void applyOrderBy(JPAQuery query, QSort sort)
    {

        for( OrderSpecifier<?> orderSpecifier :sort.getOrderSpecifiers() )
        {
            QueryBase queryBase = query.orderBy(orderSpecifier);

        }
    }

    public JPQLQuery applyPagination(Pageable pageable, JPAQuery query)
    {
        Querydsl querydsl = getQuerydsl();
        JPQLQuery jpqlQuery = querydsl.applyPagination(pageable, query);
        return  jpqlQuery;
    }

    public PageImpl createPage(List content, Pageable pageable, long total)
    {
        return new PageImpl<>(content, pageable, total);
    }

    public JPAQueryFactory getJPAQueryFactory()
    {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        return  jpaQueryFactory;
    }


}
