package org.onecellboy.querydsl.util;

import com.querydsl.core.QueryResults;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QSort;

import javax.persistence.EntityManager;
import java.util.List;

public class SpringQueryDSLUtil {


    public   static void applyOrderBy(JPAQuery query, QSort sort)
    {

        for( OrderSpecifier<?> orderSpecifier :sort.getOrderSpecifiers() )
        {
            QueryBase queryBase = query.orderBy(orderSpecifier);

        }
    }

    /*
     * clazz : Entity 클래스
     * */
    public  static JPQLQuery applyPagination(EntityManager entityManager,Class clazz, Pageable pageable, JPAQuery query)
    {
        Querydsl querydsl = getQuerydsl(entityManager, clazz);
        JPQLQuery jpqlQuery = querydsl.applyPagination(pageable, query);
        return  jpqlQuery;
    }

    public static PageImpl createPage(List content, Pageable pageable, long total)
    {
        return new PageImpl<>(content, pageable, total);
    }

    public static PageImpl createPage(QueryResults queryResults, Pageable pageable )
    {
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }



    protected static  Querydsl getQuerydsl(EntityManager entityManager,Class clazz)
    {

        PathBuilder builder = new PathBuilderFactory().create(clazz);
        Querydsl querydsl = new Querydsl(entityManager, builder);
        return  querydsl;
    }





}
