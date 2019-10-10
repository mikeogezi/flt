package mikeogezi.daos;

import com.google.gson.Gson;
import mikeogezi.models.Account;
import mikeogezi.utils.DefaultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountDao implements Dao {

    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    private List<Account> accountList = getAccounts();

    @Override
    public Optional get(long id) {
        try {
            return Optional.of(accountList.get((int) id));
        }
        catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public Optional getBy (String bankCode, String accountNumber) {
        for (Account account: accountList) {
            if (account.getBankCode().equals(bankCode) && account.getAccountNumber().equals(accountNumber)) {
                return Optional.of(account);
            }
        }

        return Optional.empty();
    }

    @Override
    public List getAll() {
        return accountList;
    }

    @Override
    public void save(Object o) {
        Account account = (Account) o;
        account.setId(accountList.size());
        accountList.add(account);
    }

    private static List<Account> getAccounts () {

        List<Account> accountList = new ArrayList<>();

        for (int i = 0; i < DefaultUtils.count; ++i) {
            Account account = new Account();

            account.setId(i);
            account.setAccountNumber(DefaultUtils.accountNumbers.get(i));
            account.setBankCode(DefaultUtils.bankCodes.get(i));

            accountList.add(account);
        }

        log.info("Accounts: {}", new Gson().toJson(accountList));
        return accountList;

    }

}
