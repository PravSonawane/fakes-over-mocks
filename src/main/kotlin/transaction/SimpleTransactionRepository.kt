package transaction

import account.Account
import database.RealDatabase

class SimpleTransactionRepository(
    private val database: RealDatabase
) : TransactionRepository {

    private var newId = 0

    override fun getAll(account: Account, status: TransactionStatus): List<Transaction> {
        return database.transactionTable.values
            .filter { it.status == status }
            .filter { it.account == account }
    }

    override fun put(request: TransactionRequest): Transaction {
        val id = (++newId).toString()
        val transaction = Transaction(
            id,
            request.type,
            request.account,
            request.amount,
            TransactionStatus.PENDING
        )
        database.transactionTable[id] = transaction
        return transaction
    }

    override fun update(transactionId: String, transaction: Transaction): Transaction {
        database.transactionTable[transactionId] = transaction
        return transaction
    }
}