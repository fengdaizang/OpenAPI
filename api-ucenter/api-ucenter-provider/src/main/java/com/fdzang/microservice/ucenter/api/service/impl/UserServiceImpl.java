package com.fdzang.microservice.ucenter.api.service.impl;

import com.fdzang.microservice.ucenter.api.service.UserService;
import com.fdzang.microservice.ucenter.common.dto.UserDTO;
import com.fdzang.microservice.ucenter.dao.domain.UserDO;
import com.fdzang.microservice.ucenter.dao.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghu
 * @Date: 2019/11/21 10:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO getById(Integer id) {
        UserDO userDO = userMapper.selectByPrimaryKey(id);

        if(userDO != null){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userDO,userDTO);

            return userDTO;
        }

        return null;
    }
}
