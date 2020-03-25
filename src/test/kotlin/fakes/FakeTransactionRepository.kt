package fakes

import account.Account
import transaction.Transaction
import transaction.TransactionRepository
import transaction.TransactionRequest
import transaction.TransactionStatus

class FakeTransactionRepository : TransactionRepository {
    private val transactionTable = HashMap<String, Transaction>()

    private var newId = 0
    override fun getAll(account: Account, status: TransactionStatus): List<Transaction> {
        return transactionTable.values
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
        transactionTable[id] = transaction
        return transaction
    }

    override fun update(transactionId: String, transaction: Transaction): Transaction {
        transactionTable[transactionId] = transaction
        return transaction
    }
}
