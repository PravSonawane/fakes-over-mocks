package account

interface AccountRepository {
    fun get(id: String): Account
    fun put(id: String, account: Account): Account
}