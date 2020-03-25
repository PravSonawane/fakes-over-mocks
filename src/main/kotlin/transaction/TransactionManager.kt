package transaction

import account.Account
import account.AccountRepository

class TransactionManager(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {

    fun add(request: TransactionRequest): Transaction {
        return transactionRepository.put(request)
    }

    fun executePendingTransactions(accountId: String): Account {
        val account = accountRepository.get(accountId)
        val transactions = transactionRepository.getAll(account, TransactionStatus.PENDING)

        var amount = 0

        for (t in transactions) {
            when (t.type) {
                TransactionType.DEPOSIT -> {
                    amount += t.amount
                    transactionRepository.update(t.id, t.copy(status = TransactionStatus.COMPLETE))
                }
                TransactionType.WITHDRAWAL -> {
                    amount -= t.amount
                    transactionRepository.update(t.id, t.copy(status = TransactionStatus.COMPLETE))
                }
            }
        }

        val updatedAccount = account.copy(balance = amount)
        accountRepository.put(accountId, updatedAccount)
        return updatedAccount
    }
}