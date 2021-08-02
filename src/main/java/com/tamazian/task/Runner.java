package com.tamazian.task;

import com.tamazian.task.dao.OrderDao;
import com.tamazian.task.dao.ProductDao;

public class Runner {
    public static void main(String[] args) {
        var productDao = ProductDao.getINSTANCE();
        var orderDao = OrderDao.getINSTANCE();
        //productDao.write();
        orderDao.write();
    }
}
