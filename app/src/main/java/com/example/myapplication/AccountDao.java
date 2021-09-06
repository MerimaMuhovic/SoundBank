package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {
    @Query("SELECT * FROM accounts")
    public List<Account> getAllAccounts();
    @Query("SELECT * FROM accounts WHERE id = :id")
    public Account getAccountById(int id);
    @Query("SELECT * FROM accounts WHERE email = :email")
    public Account getAccountByEmail(String email);
    @Query("UPDATE accounts SET username = :username, email = :email,  password = :password  WHERE id = :id")
    public void updateById(String username, String email, String password, int id);
    @Update
    public void updateAccount(Account account);
    @Insert
    public void addAccount(Account newAccount);
}
