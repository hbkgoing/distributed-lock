package com.example.distributedlock.pojo;

import lombok.Data;

/**
 * Description TODO
 * Author hebaokai
 * Date 2022/11/5 17:36
 *
 * @Version 1.0
 **/
@Data
public class Stock {

    private Long id;

    private String productCode;

    private String wareHouse;

    private String stock;

}
