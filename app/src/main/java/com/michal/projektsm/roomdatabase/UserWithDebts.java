package com.michal.projektsm.roomdatabase;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithDebts {
    @Embedded public UserEntity user;
    @Relation(
            parentColumn = "id",
            entityColumn = "owner"
    )
    public List<DebtEntity> debts;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<DebtEntity> getDebts() {
        return debts;
    }

    public void setDebts(List<DebtEntity> debts) {
        this.debts = debts;
    }
}
