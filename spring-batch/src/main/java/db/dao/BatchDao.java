package db.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Session;
import org.lib.batch.entity.BatchJobExecutionEntity;
import org.lib.batch.entity.QBatchJobExecutionEntity;
import org.springframework.batch.core.BatchStatus;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

import java.util.LinkedList;
import java.util.List;

public class BatchDao {
    private Session session;

    public BatchDao(Session session){
        this.session=session;
    }

    /**
     *
     * @param batchStatuses null 이거나 없으면 모든.
     * @param page
     * @param limit
     * @return
     */
    public PageImpl<BatchJobExecutionEntity> find(List<BatchStatus> batchStatuses, Pageable pageable)
    {
        PathBuilder builder = new PathBuilderFactory().create(BatchJobExecutionEntity.class);
        Querydsl querydsl = new Querydsl(session, builder);



        QBatchJobExecutionEntity batchJobExecutionEntity = QBatchJobExecutionEntity.batchJobExecutionEntity;
        JPAQueryFactory queryFactory = new JPAQueryFactory(session);
        JPAQuery<?> from = queryFactory.from(batchJobExecutionEntity);
        from.leftJoin(batchJobExecutionEntity.batchJobInstance).fetchJoin().orderBy(batchJobExecutionEntity.job_execution_id.desc());
        JPQLQuery<?> jpqlQuery = querydsl.applyPagination(pageable, from);

        List<String> statues = new LinkedList<>();
        for(BatchStatus status: batchStatuses)
        {
            ((LinkedList<String>) statues).addLast(status.toString());

        }
        if(statues.size()>0)
        {
            BooleanExpression in = batchJobExecutionEntity.status.in(statues);
            jpqlQuery.where(in);
        }


        QueryResults queryResults = jpqlQuery.fetchResults();
        long total = queryResults.getTotal();
        List results = queryResults.getResults();

        return new PageImpl<>(results, pageable, total);
    }

}
