package fakes

import account.Account
import account.AccountRepository

class FakeAccountRepository : AccountRepository {

    private val accountDatabase = HashMap<String, Account>()

    override fun get(id: String): Account {
        return accountDatabase[id]
            ?: throw IllegalArgumentException("Account not found: Id: $id")
    }

    override fun put(id: String, account: Account): Account {
        accountDatabase[id] = account
        return account
    }
}