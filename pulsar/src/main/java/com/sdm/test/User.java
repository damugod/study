package com.sdm.test;

import lombok.*;

import java.math.BigDecimal;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test
 * @Description：
 * @author: sdm
 * @date: 2022/11/21 3:13 下午
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private String name ;

    private Integer age ;

    private BigDecimal price;

    private String name2;
}
