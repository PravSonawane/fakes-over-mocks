package account

import database.RealDatabase

class SimpleAccountRepository(
    private val database: RealDatabase
) : AccountRepository {

    override fun get(id: String): Account {
        return database.accountTable[id]
            ?: throw IllegalArgumentException("Account not found: Id: $id")
    }

    override fun put(id: String, account: Account): Account {
        database.accountTable[id] = account
        return account
    }
}