package tk.zlx.exceltest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * 测试事务回滚
 */
@Service
public class C1 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.SERIALIZABLE)
    public void deleteUser(int id){
        String sql = "delete from t_user where id = ?";
        jdbcTemplate.update(sql, id);
        int a = 1/0;
    }

    public void deleteUser2(int id) {
        try {
            String sql = "delete from t_user where id = ?";
            jdbcTemplate.update(sql, id);
            int a = 1 / 0;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println("error  here");
        }
    }
}
