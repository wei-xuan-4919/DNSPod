package cn.dnspod.controller;



import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.Result;
import cn.dnspod.pojo.po.DnsPodLogPO;
import cn.dnspod.service.IDnsPodLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weixuan
 * @date 2023/4/24 0:38
 * @Description:
 */
@Controller
@RequestMapping("/admin/dnspod")
public class DnsPodLogController {

    @Autowired
    private IDnsPodLogService dnsPodLogService;

    @PostMapping("/pageDnsPodLog")
    @ResponseBody
    public Result pageDnsPodLog(String domainValue, Integer state, @RequestParam("curPage") int curPage, @RequestParam("pageSize") int pageSize) {
        PageInfo<DnsPodLogPO> dnsPodLogPageInfo = dnsPodLogService.pageDnsPodLog(domainValue, state, curPage, pageSize);
        return Result.success(dnsPodLogPageInfo);
    }

}
