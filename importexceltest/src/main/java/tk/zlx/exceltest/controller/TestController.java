package tk.zlx.exceltest.controller;

import org.hibernate.Transaction;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.zlx.exceltest.service.C1;
import tk.zlx.exceltest.service.C2;
import tk.zlx.exceltest.service.C3;

@RestController
public class TestController {

    @Autowired
    private C1 c1;

    @Autowired
    private C2 c2;

    @Autowired
    private C3 c3;

//    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/")
    public void exe() {
        try {
            c1.deleteUser(1);
//            int a = 1/0;
//            c2.exec(2);
        } catch (RuntimeException e) {
//            e.printStackTrace();
//            throw new RuntimeException("sql异常");
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println("controller sql error");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/1")
    public void exe1() {
        c1.deleteUser(1);
        c2.exec(2);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/c3")
    public void merge() {
        try {
            c3.merge();
            int a = 1/0;
        } catch (Exception e) {
            // 异常被抓导致spring事务失效 可手动抛出异常触发事务回滚
//            throw new RuntimeException("controller merge error");
            // 或当异常发生 手动设置事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println("controller merge error");
        }
    }

    @RequestMapping("/c32")
    @Transactional(rollbackFor = Exception.class)
    public void merge2(){
        c3.merge2();
        int a = 1/0;
    }
}


/** 0 事务不出异常 ：如a方法为事务方法 执行时发生异常，如果a方法抓异常且未处理则事务失效 如果a方法不抓异常 b调用a方法且b抓异常则事务回滚
 *              因为a出现异常抛出后 待b抓取到a抛出的异常时 a方法的异常已经结束则事务回滚 和b没啥关系
 *  1 如果事务抓取异常 则事务失效
 *      解决方案：1 在catch里面手动抛出异常 触发事务回滚
 *              2 在catch里面手动设置事务回滚
 *  2 a方法为事务方法，b方法调用a方法 且ab方法均抓异常 则需要在a方法里面处理异常手动回滚事务，此可参照1
 *  3 ab方法均抓异常 且a加事务 b调用a a抛异常 如果b也加事务则异常抛出的内容为a方法中定义的异常 如果b不加事务，则抛出的异常信息为b定义的内容
 *
 **/
