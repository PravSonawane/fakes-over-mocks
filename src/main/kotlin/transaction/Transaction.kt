package transaction

import account.Account

data class TransactionRequest(
    val type: TransactionType,
    val account: Account,
    val amount: Int
)

data class Transaction(
    val id: String,
    val type: TransactionType,
    val account: Account,
    val amount: Int,
    val status: TransactionStatus
)

enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL
}

enum class TransactionStatus {
    PENDING,
    COMPLETE
}
