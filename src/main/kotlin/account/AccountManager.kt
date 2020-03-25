package account

import transaction.TransactionManager
import transaction.TransactionRequest
import transaction.TransactionType

class AccountManager(
    private val accountRepository: AccountRepository,
    private val transactionManager: TransactionManager
) {

    fun deposit(accountId: String, amount: Int, interest: Int) : Account {
        val account = accountRepository.get(accountId)

        transactionManager.add(TransactionRequest(TransactionType.DEPOSIT, account, amount))
        transactionManager.add(TransactionRequest(TransactionType.DEPOSIT, account, interest))

        return transactionManager.executePendingTransactions(accountId)
    }
}