package tk.zlx.exceltest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 测试事务回滚
 */
@Component
public class C3 {

    @Autowired private C1 c1;
    @Autowired private C2 c2;

    @Transactional(rollbackFor = Exception.class)
    public void merge(){
        try {
            c1.deleteUser(1);
            c2.exec(2);
        } catch (RuntimeException e) {
//            throw new RuntimeException("merge error");
            System.out.println("service merge error");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void merge2(){
        c1.deleteUser(1);
        c2.exec(2);
    }
}
