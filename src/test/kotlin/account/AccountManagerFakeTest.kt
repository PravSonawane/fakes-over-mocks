package account

import fakes.FakeAccountRepository
import fakes.FakeTransactionRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import transaction.TransactionManager
import transaction.TransactionRepository

class AccountManagerFakeTest {

    private lateinit var accountRepository: AccountRepository
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var transactionManager: TransactionManager

    private lateinit var accountManager: AccountManager

    @Before
    fun before() {
        accountRepository = FakeAccountRepository()
        transactionRepository = FakeTransactionRepository()
        transactionManager = TransactionManager(accountRepository, transactionRepository)
        accountManager = AccountManager(accountRepository, transactionManager)
    }

    @Test
    fun `GIVEN amount and interest to deposit WHEN deposited THEN should update account balance`() {
        // GIVEN
        val originalAccount = Account("account1", 0)
        val amount = 1000
        val interest = 100
        val expectedAccount = originalAccount.copy(balance = 1100)

        accountRepository.put(originalAccount.id, originalAccount)

        // WHEN
        val actualAccount = accountManager.deposit(originalAccount.id, amount, interest)

        // THEN
        assertEquals(expectedAccount, actualAccount)
    }
}