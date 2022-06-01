package com.zlingchun.jpa.repositories;

import com.zlingchun.jpa.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
