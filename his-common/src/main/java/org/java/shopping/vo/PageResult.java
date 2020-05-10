package org.java.shopping.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用于封装分页的查询结果，方便显示在layui的table组件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private Integer code;//状态码
    private String msg;//消息
    private Long count;//数据总数
    private List<T> data; //用于保存查询到的结果

    private Integer pageNum;;//当前页
    private Integer maxPage;//总页数

}
