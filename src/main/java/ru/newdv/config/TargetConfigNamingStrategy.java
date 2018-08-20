package ru.newdv.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

public class TargetConfigNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private final Logger logger = LogManager.getLogger(TargetConfigNamingStrategy.class.getName());

    private final String LINKS_TARGET_TABLE_PROPERTY = "LINKS_TARGET_TABLE";
    private final String LINKS_TARGET_LINK_PROPERTY = "LINKS_TARGET_LINK";

    private final String LINKS_TARGET_TABLE_DEFAULT = "LINK";
    private final String LINKS_TARGET_LINK_DEFAULT = "URL";

    private String tableName;
    private String urlColumnName;

    public TargetConfigNamingStrategy() {
        tableName = System.getProperty(LINKS_TARGET_TABLE_PROPERTY, LINKS_TARGET_TABLE_DEFAULT);
        urlColumnName = System.getProperty(LINKS_TARGET_LINK_PROPERTY, LINKS_TARGET_LINK_DEFAULT);

        logger.debug("tableName=" + tableName);
        logger.debug("urlColumnName=" + urlColumnName);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        if (name.getText().equals("TargetTable")) {
            return new Identifier(tableName, name.isQuoted());
        }
        return new Identifier(name.getText(), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        if (name.getText().equals("targetLink")) {
            return new Identifier(urlColumnName, name.isQuoted());
        }
        return new Identifier(name.getText(), name.isQuoted());
    }

}