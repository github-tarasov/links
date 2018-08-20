package ru.newdv.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SourceConfigNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private final Logger logger = LogManager.getLogger(SourceConfigNamingStrategy.class.getName());

    private final String LINKS_SOURCE_TABLE_PROPERTY = "LINKS_SOURCE_TABLE";
    private final String LINKS_SOURCE_ID_PROPERTY = "LINKS_SOURCE_ID";
    private final String LINKS_SOURCE_TEXT_PROPERTY = "LINKS_SOURCE_TEXT";

    private final String LINKS_SOURCE_TABLE_DEFAULT = "ARTICLE";
    private final String LINKS_SOURCE_ID_DEFAULT = "TXT";
    private final String LINKS_SOURCE_TEXT_DEFAULT = "ID";

    private String tableName;
    private String idColumnName;
    private String textColumnName;

    public SourceConfigNamingStrategy() {
        tableName = System.getProperty(LINKS_SOURCE_TABLE_PROPERTY, LINKS_SOURCE_TABLE_DEFAULT);
        idColumnName = System.getProperty(LINKS_SOURCE_ID_PROPERTY, LINKS_SOURCE_ID_DEFAULT);
        textColumnName = System.getProperty(LINKS_SOURCE_TEXT_PROPERTY, LINKS_SOURCE_TEXT_DEFAULT);

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