package cn.dnspod.controller;



import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.Result;
import cn.dnspod.pojo.constant.SysConstant;
import cn.dnspod.pojo.po.DomainPO;
import cn.dnspod.pojo.po.SubDomainPO;
import cn.dnspod.service.IDomainService;
import cn.dnspod.service.ISubDomainService;
import cn.dnspod.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO
 *
 * @className: SubDomainController
 * @author: weixuan
 * @date: 2023/4/23 19:40
 **/
@Controller
@RequestMapping("/admin/dnspod")
public class SubDomainController {

    @Autowired
    private ISubDomainService subDomainService;

    @Autowired
    private IDomainService domainService;

    @PostMapping("/pageSubDomain")
    @ResponseBody
    public Result pageSubDomain(String domainValue, Integer state, @RequestParam("domainId") Long domainId, @RequestParam("curPage") int curPage, @RequestParam("pageSize") int pageSize) {
        PageInfo<SubDomainPO> subDomainPageInfo = subDomainService.pageSubDomain(domainValue, state, domainId, curPage, pageSize);
        return Result.success(subDomainPageInfo);
    }


    @GetMapping("/deleteSubDomain/{subDomainId}")
    @ResponseBody
    public Result deleteSubDomain(@PathVariable Long subDomainId) {
        SubDomainPO subDomainPO = subDomainService.getById(subDomainId);
        if (subDomainPO == null) {
            return Result.fail("子域名不存在", "子域名不存在Id：" + subDomainId);
        }

        DomainPO domainPO = domainService.getById(subDomainPO.getDomainId());
        String domain;
        if (domainPO != null) {
            domain = subDomainPO.getSubDomainValue() + "." + domainPO.getDomainValue();
        } else {
            domain = subDomainPO.getSubDomainValue();
        }

        boolean flag = subDomainService.removeById(subDomainId);
        if (flag) {
            return Result.success("子域名删除成功", "子域名【" + domain + "】删除成功");
        }
        return Result.fail("子域名删除失败", "子域名【" + domain + "】删除失败");
    }

    @PostMapping("/saveSubDomain")
    @ResponseBody
    public Result saveSubDomain(@RequestBody SubDomainPO subDomainPO) {
        if (Utils.isEmpty(subDomainPO.getSubDomainValue())) {
            return Result.fail("子域名不能为空");
        }

        DomainPO domainPO = domainService.getById(subDomainPO.getDomainId());
        if (domainPO == null) {
            return Result.fail("域名不存在", "域名不存在Id：" + subDomainPO.getDomainId());
        }

        SubDomainPO subDomainByValue = subDomainService.findSubDomainByValue(subDomainPO.getDomainId(), domainPO.getDomainValue());
        String domain = subDomainPO.getSubDomainValue() + "." + domainPO.getDomainValue();
        if (subDomainByValue != null) {
            return Result.fail("子域名已存在", "子域名【" + domain + "】已存在");
        }

        boolean flag = subDomainService.save(subDomainPO);
        if (flag) {
            return Result.success("子添加域名成功", "子添加域名【" + domain + "】成功");
        }
        return Result.fail("子添加域名失败", "子添加域名【" + domain + "】失败");
    }

    @PostMapping("/updateSubDomain")
    @ResponseBody
    public Result updateSubDomain(@RequestBody SubDomainPO subDomainPO) {
        if (Utils.isEmpty(subDomainPO.getSubDomainValue())) {
            return Result.fail("子域名不能为空");
        }
        SubDomainPO byId = subDomainService.getById(subDomainPO.getId());
        if (byId == null) {
            return Result.fail("子域名不存在", "子域名不存在Id：" + subDomainPO.getId());
        }

        DomainPO domainPO = domainService.getById(subDomainPO.getDomainId());
        if (domainPO == null) {
            return Result.fail("域名不存在", "域名不存在Id：" + subDomainPO.getDomainId());
        }

        String domain = subDomainPO.getSubDomainValue() + "." + domainPO.getDomainValue();

        if (!byId.getSubDomainValue().equals(subDomainPO.getSubDomainValue())) {
            SubDomainPO subDomainByValue = subDomainService.findSubDomainByValue(subDomainPO.getDomainId(), subDomainPO.getSubDomainValue());
            if (subDomainByValue != null) {
                return Result.fail("子域名已存在", "子域名【" + domain + "】已存在");
            }
        }

        boolean flag = subDomainService.updateById(subDomainPO);
        if (flag) {
            if (!byId.getSubDomainValue().equals(subDomainPO.getSubDomainValue())) {
                return Result.success("更新子域名成功",
                        "原子域名【" + byId.getSubDomainValue() + "." + domainPO.getDomainValue()  + "】成功修改为【" + domain +"】");
            }
            return Result.success("更新子域名成功", "更新子域名【" + domain + "】成功");
        }
        return Result.fail("更新子域名失败", "更新子域名【" + domain + "】失败");
    }

    @PostMapping("/updateSubDomainState")
    @ResponseBody
    public Result updateSubDomainState(@RequestParam("id") Long id, @RequestParam("state") Integer state) {
        SubDomainPO subDomainPO = subDomainService.getById(id);
        if (subDomainPO == null) {
            return Result.fail("子域名不存在", "子域名不存在Id：" + id);
        }

        DomainPO domainPO = domainService.getById(subDomainPO.getDomainId());
        if (domainPO == null) {
            return Result.fail("域名不存在", "域名不存在Id：" + subDomainPO.getDomainId());
        }

        String domain = subDomainPO.getSubDomainValue() + "." + domainPO.getDomainValue();

        if (state != SysConstant.STATE.DISABLE && state != SysConstant.STATE.ENABLE ) {
            return Result.fail("状态有误，请重新修改", "子域名【"+ domain +"】状态有误：state=" + state);
        }

        subDomainPO.setState(state);

        boolean flag = subDomainService.updateById(subDomainPO);
        if (flag) {
            return Result.success("子域名状态修改成功","子域名【" + domain + "】状态成功修改为：" + (state == SysConstant.STATE.ENABLE ? "启用" : "禁用"));
        }
        return Result.fail("子域名状态修改失败");
    }

}
