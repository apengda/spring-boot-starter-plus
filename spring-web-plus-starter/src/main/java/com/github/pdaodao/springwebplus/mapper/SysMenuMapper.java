package com.github.pdaodao.springwebplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pdaodao.springwebplus.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> userMenu(@Param("userId") final String userId);
}
