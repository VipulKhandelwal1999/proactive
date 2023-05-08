package com.hackathon.proactivemonitoring.Mapper;

import com.hackathon.proactivemonitoring.models.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setStatus(resultSet.getString("status"));
        transaction.setReceivedTime(resultSet.getObject("received_time", LocalDateTime.class));
        return transaction;
    }
}

