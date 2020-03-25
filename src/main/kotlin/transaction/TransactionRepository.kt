package transaction

import account.Account

interface TransactionRepository {

    fun put(request: TransactionRequest): Transaction
    fun update(transactionId: String, transaction: Transaction): Transaction
    fun getAll(account: Account, status: TransactionStatus): List<Transaction>
}