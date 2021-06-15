package org.onecell.spring.template.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.istech.db.entity.test.TB_COU_DNGR_RVR_DAY;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TestRepository {
    @PersistenceContext
    private Session em;

    public List<TB_COU_DNGR_RVR_DAY> findAll()
    {
        Query select_c_from_tb_cou_dngr_rvr_day_c = em.createQuery("SELECT c FROM TB_COU_DNGR_RVR_DAY c");

        List resultList = select_c_from_tb_cou_dngr_rvr_day_c.getResultList();
        return resultList;
    }

}
