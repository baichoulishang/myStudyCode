package io.github.viscent.mtia.ch2;

import org.springframework.stereotype.Component;

import javax.management.relation.Role;

/**
 * @date 2021/6/22 21:10
 */
@Component
public class RoleServiceImp implements RoleService {
    @Override
    public void printRole(Role role) {
        System.out.println(role.toString());
    }
}
