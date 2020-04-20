package org.onecellboy.db.dialect;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.Oracle12cIdentityColumnSupport;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.config.spi.StandardConverters;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.spatial.dialect.oracle.OracleSpatial10gDialect;
import org.hibernate.type.LocalDateTimeType;
import org.hibernate.type.MaterializedBlobType;
import org.hibernate.type.WrappedMaterializedBlobType;

public class OracleSpatialSDO12gExtendDialect extends OracleSpatial10gDialect {

    public static final String PREFER_LONG_RAW = "hibernate.dialect.oracle.prefer_long_raw";

        public OracleSpatialSDO12gExtendDialect() {
            super();
            getDefaultProperties().setProperty( Environment.BATCH_VERSIONED_DATA, "true" );

            /// ADD_HOURS에 대해 추가해 보았다.
            // QueryDSL의 com.querydsl.core.types.Ops 의 operation에 대해 하나씩 추가하는게 좋다. 표준으로 삼기 좋고 어떤 연산이 존재하는지 확인하기 좋다.
            // DB 마다 다 다르다.
            registerFunction("ADD_HOURS", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "(?1 +  numtodsinterval(?2,'HOUR') )"));
        }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes( typeContributions, serviceRegistry );

        // account for Oracle's deprecated support for LONGVARBINARY...
        // 		prefer BLOB, unless the user opts out of it
        boolean preferLong = serviceRegistry.getService( ConfigurationService.class ).getSetting(
                PREFER_LONG_RAW,
                StandardConverters.BOOLEAN,
                false
        );

        if ( !preferLong ) {
            typeContributions.contributeType( MaterializedBlobType.INSTANCE, "byte[]", byte[].class.getName() );
            typeContributions.contributeType( WrappedMaterializedBlobType.INSTANCE, "Byte[]", Byte[].class.getName() );
        }
    }

    @Override
    protected void registerDefaultProperties() {
        super.registerDefaultProperties();
        getDefaultProperties().setProperty( Environment.USE_GET_GENERATED_KEYS, "true" );
    }

    @Override
    public String getNativeIdentifierGeneratorStrategy() {
        return "sequence";
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new Oracle12cIdentityColumnSupport();
    }

}
