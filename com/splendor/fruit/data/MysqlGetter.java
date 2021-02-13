package com.splendor.fruit.data;

import com.splendor.fruit.FruitMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MysqlGetter {

    private static MysqlGetter instance;
    private MysqlSetup mysql;

    private MysqlGetter() {
        instance = this;
        mysql = FruitMain.getInstance().getMysql();
    }

    public static MysqlGetter getInstance() {
        return instance == null ? new MysqlGetter() : instance;
    }

    public Map<String, String> getTree(String id) {
        try {
            Map<String, String> hashmap = new HashMap<>();
            String sql = "SELECT middle,sign,owner,timer,state,sapling FROM TreeTbl WHERE id=?";
            PreparedStatement s = getStatement(sql);
            s.setString(1, id);
            ResultSet set = s.executeQuery();
            while (set.next()) {
                hashmap.put("id", id);
                hashmap.put("middle", set.getString("middle"));
                hashmap.put("sign", set.getString("sign"));
                hashmap.put("owner", set.getString("owner"));
                hashmap.put("timer", set.getString("timer"));
                hashmap.put("state", set.getString("state"));
                hashmap.put("sapling", set.getString("sapling"));
            }
            if (hashmap.size() < 1)
                return null;
            return hashmap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getSapling(String id) {
        try {
            Map<String, String> hashmap = new HashMap<>();
            String sql = "SELECT item,head,period FROM SpTbl WHERE id=?";
            PreparedStatement s = getStatement(sql);
            s.setString(1, id);
            ResultSet set = s.executeQuery();
            while (set.next()) {
                hashmap.put("id", id);
                hashmap.put("item", set.getString("item"));
                hashmap.put("head", set.getString("head"));
                hashmap.put("period", set.getString("period"));
            }
            if (hashmap.size() < 1)
                return null;
            return hashmap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement getStatement(String sql) throws SQLException {
        mysql.checkConnection();
        return mysql.getConnection().prepareStatement(sql);
    }
}
