package ru.newdv.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.util.StringUtils;

public class SourceConfigNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private final Logger logger = LogManager.getLogger(SourceConfigNamingStrategy.class.getName());

    private final String LINKS_SOURCE_TABLE_PROPERTY = "LINKS_SOURCE_TABLE";
    private final String LINKS_SOURCE_ID_PROPERTY = "LINKS_SOURCE_ID";
    private final String LINKS_SOURCE_TEXT_PROPERTY = "LINKS_SOURCE_TEXT";

    private final String LINKS_SOURCE_TABLE_DEFAULT = "ARTICLE";
    private final String LINKS_SOURCE_ID_DEFAULT = "ID";
    private final String LINKS_SOURCE_TEXT_DEFAULT = "TXT";

    private String tableName;
    private String idColumnName;
    private String textColumnName;

    public SourceConfigNamingStrategy() {
        tableName = System.getenv(LINKS_SOURCE_TABLE_PROPERTY);
        if (StringUtils.isEmpty(tableName)) {
            tableName = LINKS_SOURCE_TABLE_DEFAULT;
        }
        idColumnName = System.getenv(LINKS_SOURCE_ID_PROPERTY);
        if (StringUtils.isEmpty(idColumnName)) {
            idColumnName = LINKS_SOURCE_ID_DEFAULT;
        }
        textColumnName = System.getenv(LINKS_SOURCE_TEXT_PROPERTY);
        if (StringUtils.isEmpty(textColumnName)) {
            textColumnName = LINKS_SOURCE_TEXT_DEFAULT;
        }

        logger.debug("tableName=" + tableName);
        logger.debug("idColumnName=" + idColumnName);
        logger.debug("textColumnName=" + textColumnName);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        if (name.getText().equals("SourceTable")) {
            return new Identifier(tableName, name.isQuoted());
        }
        return new Identifier(name.getText(), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        if (name.getText().equals("sourceID")) {
            return new Identifier(idColumnName, name.isQuoted());
        } else if (name.getText().equals("sourceText")) {
            return new Identifier(textColumnName, name.isQuoted());
        }
        return new Identifier(name.getText(), name.isQuoted());
    }

}