package org.onecell.spring.jta.lib.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource {
    Map<Object,Object> dataSourceMap =null;

    @Override
    protected Object determineCurrentLookupKey() {
        boolean currentTransactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if(currentTransactionReadOnly==true)
        {
            return dataSourceMap.keySet().stream().collect(Collectors.toList()).get((int)
                    Math.floorMod(Thread.currentThread().getId(),this.dataSourceMap.size())
            );

        }
        return null;
    }

    public void setMaster(DataSource dataSource)
    {
        this.setDefaultTargetDataSource(dataSource);
    }

    public void setSlave(List<DataSource> dataSourceList)
    {

        int i=0;
        Map<Object,Object> map = new HashMap<>();
        for(DataSource dataSource:dataSourceList)
        {
            map.put("slave-"+i,dataSource);
            i++;
        }
        this.dataSourceMap = map;
        this.setTargetDataSources(map);
    }
}
