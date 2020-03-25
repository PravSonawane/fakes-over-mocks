package account

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import transaction.Transaction
import transaction.TransactionManager
import transaction.TransactionRequest
import transaction.TransactionStatus
import transaction.TransactionType

class AccountManagerMockTest {

    private lateinit var accountRepository : AccountRepository
    private lateinit var transactionManager : TransactionManager

    private lateinit var accountManager: AccountManager

    @Before
    fun before() {
        accountRepository = mockk()
        transactionManager = mockk()
        accountManager = AccountManager(accountRepository, transactionManager)
    }

    @Test
    fun `GIVEN amount and interest to deposit WHEN deposited THEN should create deposit transactions and execute them`() {
        // GIVEN
        val originalAccount = Account("account1", 0)
        val amount = 1000
        val interest = 100
        val expectedAccount = originalAccount.copy(balance = 1100)
        val amountRequest = TransactionRequest(TransactionType.DEPOSIT, originalAccount, amount)
        val interestRequest = TransactionRequest(TransactionType.DEPOSIT, originalAccount, interest)
        val amountTransaction = Transaction("t1", TransactionType.DEPOSIT, originalAccount, amount, TransactionStatus.PENDING)
        val interestTransaction = Transaction("t2", TransactionType.DEPOSIT, originalAccount, interest, TransactionStatus.PENDING)

        every { accountRepository.get(originalAccount.id) } returns originalAccount
        every { transactionManager.add(amountRequest) } returns amountTransaction
        every { transactionManager.add(interestRequest) } returns interestTransaction
        every { transactionManager.executePendingTransactions(originalAccount.id) } returns expectedAccount

        // WHEN
        val actualAccount = accountManager.deposit(originalAccount.id, amount, interest)

        // THEN
        assertEquals(expectedAccount, actualAccount)
        verify {
            accountRepository.get(originalAccount.id)
            transactionManager.add(amountRequest)
            transactionManager.add(interestRequest)
            transactionManager.executePendingTransactions(originalAccount.id)
        }
    }
}