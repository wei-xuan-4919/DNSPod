package cn.dnspod.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author weixuan
 * @date 2023/4/4 10:29
 * @Description: 分页信息
 */
@Data
public class PageInfo<T> implements Serializable {

    /**
     * 当前页
     */
    private long curPage = 1;

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize;

    /**
     * 总数
     */
    private long total;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    public PageInfo() {
    }

    public PageInfo(Page<T> page) {
        this.curPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.records = page.getRecords();
    }

    public PageInfo(long curPage, long pageSize, long total, List<T> list) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.total = total;
        this.records = list;
        this.pages = (total + pageSize - 1) / pageSize;
    }
}
