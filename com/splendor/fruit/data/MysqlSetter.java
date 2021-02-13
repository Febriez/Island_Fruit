package com.splendor.fruit.data;

import com.splendor.fruit.FruitMain;
import com.splendor.fruit.manager.Sapling;
import com.splendor.fruit.manager.Tree;
import com.splendor.fruit.utils.Converter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlSetter {

    private static MysqlSetter instance;
    private final MysqlSetup mysql;
    private final Converter con = new Converter();

    private MysqlSetter() {
        instance = this;
        mysql = FruitMain.getInstance().getMysql();
    }

    public static MysqlSetter getInstance() {
        return instance == null ? new MysqlSetter() : instance;
    }

    private boolean registerTree(Tree tree) {
        try {
            String sql = "INSERT INTO TreeTbl (id,middle,sign,owner,timer,state,sapling) values (?,?,?,?,?,?,?)";
            PreparedStatement s = mysql.getConnection().prepareStatement(sql);
            s.setString(1, tree.getId().toString());
            s.setString(2, con.getString(tree.getMiddle()));
            s.setString(3, con.getString(tree.getSign()));
            s.setString(4, tree.getOwner().toString());
            s.setString(5, String.valueOf(tree.getTimer()));
            s.setString(6, tree.getState().getName());
            s.setString(7, String.valueOf(tree.getSapling()));
            s.execute();
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("=====================================");
            System.out.println(tree.getId() + " failed!");
            return false;
        }
    }

    public boolean unregisterTree(Tree tree) {
        try {
            String sql = "DELETE TreeTbl WHERE id=?";
            PreparedStatement s = mysql.getConnection().prepareStatement(sql);
            s.setString(1, tree.getId().toString());
            s.execute();
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("=====================================");
            System.out.println(tree.getId() + " failed!");
            return false;
        }
    }

    public boolean saveTree(Tree tree) {
        if (MysqlGetter.getInstance().getTree(tree.getId().toString()) == null)
            return registerTree(tree);
        try {
            String sql = "UPDATE TreeTbl SET middle=?,sign=?,owner=?,timer=?,state=?,sapling=? WHERE id=?";
            PreparedStatement s = mysql.getConnection().prepareStatement(sql);
            s.setString(1, con.getString(tree.getMiddle()));
            s.setString(2, con.getString(tree.getSign()));
            s.setString(3, tree.getOwner().toString());
            s.setString(4, String.valueOf(tree.getTimer()));
            s.setString(5, tree.getState().getName());
            s.setString(6, String.valueOf(tree.getSapling()));
            s.setString(7, tree.getId().toString());
            s.execute();
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean registerSapling(Sapling sp) {
        try {
            String sql = "INSERT INTO TreeTbl (id,item,head,period) values (?,?,?,?,?,?,?)";
            PreparedStatement s = mysql.getConnection().prepareStatement(sql);
            s.setString(1, sp.getId());
            s.setString(2, con.getString(sp.getItem()));
            s.setString(3, String.valueOf(sp.getHead()));
            s.setString(4, String.valueOf(sp.getPeriod()));
            s.execute();
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("=====================================");
            System.out.println(sp.getId() + " failed!");
            return false;
        }
    }

    public boolean unregisterSapling(Sapling sp) {
        try {
            String sql = "DELETE TreeTbl WHERE id=?";
            PreparedStatement s = mysql.getConnection().prepareStatement(sql);
            s.setString(1, sp.getId());
            s.execute();
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("=====================================");
            System.out.println(sp.getId() + " failed!");
            return false;
        }
    }

    public boolean saveSapling(Sapling sp) {
        if (MysqlGetter.getInstance().getTree(sp.getId()) == null)
            return registerSapling(sp);
        try {
            String sql = "UPDATE TreeTbl SET item=?,head=?,period=? WHERE id=?";
            PreparedStatement s = mysql.getConnection().prepareStatement(sql);
            s.setString(1, con.getString(sp.getItem()));
            s.setString(2, String.valueOf(sp.getHead()));
            s.setString(3, String.valueOf(sp.getPeriod()));
            s.setString(4, sp.getId());
            s.execute();
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
