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
}
