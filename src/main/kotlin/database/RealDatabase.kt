package database

import account.Account
import transaction.Transaction

class RealDatabase {

    val accountTable = HashMap<String, Account>()
    val transactionTable = HashMap<String, Transaction>()
}