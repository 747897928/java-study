package com.aquarius.wizard.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaoyijie
 * @since 2024/5/13 16:27
 */
public class ClickHouseSqlConverter {
    public static void main(String[] args) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS ods_dc.ods_event_pipeline_realtime(\n" +
                "    apexId Nullable(String),\n" +
                "    appId Nullable(String),\n" +
                "    appVersion Nullable(String),\n" +
                "    content Nullable(String),\n" +
                "    createTime DateTime,\n" +
                "    ts DateTime,\n" +
                "    error Nullable(String),\n" +
                "    eventCode Nullable(String),\n" +
                "    eventType Nullable(String),\n" +
                "    projectId Nullable(String),\n" +
                "    sendType Nullable(String),\n" +
                "    wechatOpenId Nullable(String),\n" +
                "    wechatUnionId Nullable(String),\n" +
                "    eventName Nullable(String)\n" +
                ") ENGINE = MergeTree\n" +
                "PARTITION BY ts\n" +
                "ORDER BY (ts,createTime)\n" +
                "COMMENT '事件中心实时查询表';";
        String insertTemplate = convertCreateToInsertPreparedStatement(createTableSql);
        System.out.println(insertTemplate);
    }

    public static String convertCreateToInsertPreparedStatement(String createTableSQL) {
        Pattern pattern = Pattern.compile("CREATE\\s+TABLE\\s+(.+?)\\(");
        Matcher matcher = pattern.matcher(createTableSQL);

        if (matcher.find()) {
            String beforeOpeningBracket = matcher.group(1);
            System.out.println("beforeOpeningBracket = " + beforeOpeningBracket);
            String spaceString = " ";
            int blankLastIndexOf = beforeOpeningBracket.lastIndexOf(spaceString);
            int indexOfSpaceAfter = blankLastIndexOf + spaceString.length();
            String tableName = beforeOpeningBracket.substring(indexOfSpaceAfter);
            System.out.println("tableName = " + tableName);
            int lastIndexBackBracket = createTableSQL.lastIndexOf(") ENGINE");
            //System.out.println("lastIndexBackBracket = " + lastIndexBackBracket);
            int lastIndexTableName = createTableSQL.lastIndexOf(tableName);
            String columnString = createTableSQL.substring(lastIndexTableName + tableName.length() + 1, lastIndexBackBracket);
            //System.out.println("columnString = " + columnString);
            String[] columnArray = columnString.split(",");
            StringBuilder sb = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            StringBuilder sb2 = new StringBuilder();
            for (String columnDefinition : columnArray) {
                //System.out.println(columnDefinition);
                columnDefinition = columnDefinition.trim();
                String[] s = columnDefinition.split(" ");
                String columnName = s[0];
                //System.out.println("columnName = " + columnName);
                sb.append(columnName);
                sb.append(",");
                sb2.append("?,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") VALUES (");
            sb2.deleteCharAt(sb2.length() - 1);
            sb2.append(");");
            return sb.toString() + sb2.toString();
        }
        return null;
    }
}

