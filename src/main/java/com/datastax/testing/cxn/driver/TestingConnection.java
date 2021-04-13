/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datastax.testing.cxn.driver;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import java.net.InetSocketAddress;
import com.datastax.oss.driver.api.core.cql.BatchStatement;

/**
 *
 * @author stevelacerda
 */
public class TestingConnection {
    public CqlSession session;

    public void connect() {
        session = CqlSession.builder().build();
            
        String keyspace = "keyspace1";
        String table = "numbers";
        String col = "first";
//        this.getData(keyspace, table);
//      this.writeData(keyspace, table);
        this.deleteData(keyspace, table);
    }
    
    public void getData(String keyspace, String table, String col) {
//        ResultSet results = session.execute("select first,last from " + keyspace + "." + table + " where solr_query='{\"q\":\"first:steve\"}'");
        ResultSet results = session.execute("select * from " + keyspace + "." + table); 
        for (Row row : results) {
            String str1 = row.getString(col);
//            String lastName = row.getString("last");
            System.out.printf("String: %s\n", str1);
        }
    }
    
    public void writeData(String keyspace, String table) {
        Integer i = 0;
        
//        BatchStatement batch = new BatchStatement(BatchStatement.Type.UNLOGGED);
//        PreparedStatement preparedInsert = session.prepare("INSERT INTO " + keyspace + "." + table + " (partition_key) " + "VALUES (:partition_key)");
                
        while (i < 5000) {
            try{
                ResultSet results = session.execute("insert into " + keyspace + "." + table + " (partition_key,other_stuff) values ('" + i.toString() + "','" + i.toString() + "');");
//                batch.add(preparedInsert.bind(i.toString()));
//                Integer resultsLength = results.toString().length();
//                String row = results.all().toString();
//                    System.out.printf("Results: %s\n", row);
//                    RetryPolicy.RetryDecision.tryNextHost(ConsistencyLevel.LOCAL_ONE);
            } catch(Exception E) {
//                     do nothing
                System.out.printf("Exception: %s", E.getLocalizedMessage());
            }

            i = i + 1;
        }
        
//        session.execute(batch);
    }    
    
  
    
//    public void writeData(String keyspace, String table) {
//        Integer i = 0;
//        String first = "Steve";
//        
//        PreparedStatement ps = session.prepare("INSERT INTO keyspace1.testnames (first,last,middle) VALUES (:first, :last, :middle)");
//        
//        while (i < 10000000) {
//            try{
//                BoundStatement bs = ps.bind().setString("first", first).setString("last", i.toString() + "54564654165416541654654165465416546541654").setString("middle", i.toString() + "02554641231315454541516541654165");
//                session.execute(bs);
//
////                String query = String.format("insert into %s.%s (first) values (%s)", keyspace, table, first);
////                ResultSet results = session.execute(query);
////                Integer resultsLength = results.toString().length();
////                String row = results.all().toString();
////                System.out.printf("Results: %s\n", row);
////                RetryPolicy.RetryDecision.tryNextHost(ConsistencyLevel.LOCAL_ONE);
//            } catch(Exception E) {
////                     do nothing
//                System.out.printf("Exception: %s", E.getLocalizedMessage());
//            }
//
//            i = i + 1;
//        }
//    }
    
    public void deleteData(String keyspace, String table) {
        Integer i = 1250;
        while (i < 2500) {
            try{
                ResultSet results = session.execute("delete from " + keyspace + "." + table + " where partition_key = '" + i.toString() + "';");
//                batch.add(preparedInsert.bind(i.toString()));
//                Integer resultsLength = results.toString().length();
//                String row = results.all().toString();
//                    System.out.printf("Results: %s\n", row);
//                    RetryPolicy.RetryDecision.tryNextHost(ConsistencyLevel.LOCAL_ONE);
            } catch(Exception E) {
//                     do nothing
                System.out.printf("Exception: %s", E.getLocalizedMessage());
            }

            i = i + 1;
        }
    }

    public void close() {
            session.close();
    }

    public static void main(String[] args) {
        TestingConnection client = new TestingConnection();
        client.connect();
        client.close();			
    }
}
