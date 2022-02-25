package tk.zlx.exceltest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 测试事务回滚
 */
@Service
public class C2 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Transactional(rollbackFor = Exception.class)
    public void exec(int id){
        String sql = "update t_user set name = 'hhhhhhhh' where id = ?";
        jdbcTemplate.update(sql,id);
    }

}
