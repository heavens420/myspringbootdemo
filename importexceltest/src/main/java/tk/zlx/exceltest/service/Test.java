//package tk.zlx.exceltest.service;
//
//import org.springframework.transaction.annotation.Transactional;
//
//public class Test {
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int doSaveUser() throws Exception {
//        int result = 0;
//        UserEntity u = new UserEntity();
//        u.setSex(1);
//        u.setName("AAA");
//        try {
//            result = userMapper.create(u);
//            int i = 1 / 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//}
//
//
