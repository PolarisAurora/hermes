package com.telnet.hermes.sql;

import com.mysql.jdbc.Driver;
import org.springframework.util.NumberUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Ternura
 * @since 2020/9/5 10:58
 */
public class BatchRecordInserter {
    private final static String MYSQL_JDBC_URL = "jdbc:mysql://101.37.174.165:3306/hermes";

    private final static String AUTH_NAME = "root";

    private final static String AUTH_PASSWORD = "Hermes2020@";

    private final static String INSERT_SQL = "insert into t_user values(?, ?, ?, ?, ?, now())";

    private final static String[] NAME_CHAR_POOL = new String[]{
            "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F",
            "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M", "q", "w",
            "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h",
            "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"
    };



    public static void main(String[] args) {
        final int cycleNum = 10000000;
        AtomicLong id = new AtomicLong(0L);
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < processors; i ++) {
            int item = cycleNum/processors;

            Runnable runnable = new InsertRunnable(i * item, (i + 1) * item, id);
            executorService.execute(runnable);
        }
    }


    private static String generateUserId(Random random) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            if (i % 6 == 0) {
                sb.append("-");
                continue;
            }
            int index = random.nextInt(52);
            sb.append(NAME_CHAR_POOL[index]);
        }
        return sb.toString();
    }

    private static String generateUserName(Random random) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(52);
            sb.append(NAME_CHAR_POOL[index]);
        }
        return sb.toString();
    }


    static class InsertRunnable implements Runnable {
        AtomicLong id;

        int start;

        int end;

        public InsertRunnable(int start, int end, AtomicLong id) {
            this.start = start;
            this.end = end;
            this.id = id;
        }

        @Override
        public void run() {
            Random random = new Random();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(MYSQL_JDBC_URL, AUTH_NAME, AUTH_PASSWORD);
                random.setSeed(System.currentTimeMillis());
                for (int i = start; i < end; i ++) {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
                    long insertId = id.incrementAndGet();
                    preparedStatement.setLong(1, insertId);
                    String userId = generateUserId(random);
                    preparedStatement.setString(2, userId);
                    String userName = generateUserName(random);
                    preparedStatement.setString(3, userName);
                    int provinceId = random.nextInt(52);
                    preparedStatement.setInt(4, provinceId);
                    int cityId = random.nextInt(1024);
                    preparedStatement.setInt(5, cityId);
                    boolean result = preparedStatement.execute();

                    System.out.println(insertId + ";" + userId + ";" + userName + ";" + provinceId + ";" + cityId + ";" + result);
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
