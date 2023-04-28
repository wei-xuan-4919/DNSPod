package cn.dnspod.controller;



import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.Result;
import cn.dnspod.pojo.constant.SysConstant;
import cn.dnspod.pojo.po.DomainPO;
import cn.dnspod.service.IDomainService;
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
 * @className: DomainController
 * @author: weixuan
 * @date: 2023/4/23 17:22
 **/
@Controller
@RequestMapping("/admin/dnspod")
public class DomainController {

    @Autowired
    private IDomainService domainService;

    @PostMapping("/pageDomains")
    @ResponseBody
    public Result pageDomains(String domainValue, Integer state, @RequestParam("curPage") int curPage, @RequestParam("pageSize") int pageSize) {
        PageInfo<DomainPO> domainPageInfo = domainService.pageDomain(domainValue, state, curPage, pageSize);
        return Result.success(domainPageInfo);
    }

    @GetMapping("/deleteDomain/{domainId}")
    @ResponseBody
    public Result deleteDomain(@PathVariable Long domainId) {
        DomainPO domainPO = domainService.getById(domainId);
        if (domainPO == null) {
            return Result.fail("域名不存在", "域名不存在Id：" + domainId);
        }

        boolean flag = domainService.deleteDomain(domainId);
        if (flag) {
            return Result.success("域名删除成功", "域名【" + domainPO.getDomainValue() + "】删除成功");
        }
        return Result.fail("域名删除失败", "域名【" + domainPO.getDomainValue() + "】删除失败");
    }

    @PostMapping("/saveDomain")
    @ResponseBody
    public Result saveDomain(@RequestBody DomainPO domainPO) {
        if (Utils.isEmpty(domainPO.getDomainValue())) {
            return Result.fail("域名不能为空");
        }
        DomainPO domainByDomainValue = domainService.findDomainByDomainValue(domainPO.getDomainValue());
        if (domainByDomainValue != null) {
            return Result.fail("域名已存在", "域名【" + domainByDomainValue.getDomainValue() + "】已存在");
        }
        boolean flag = domainService.save(domainPO);
        if (flag) {
            return Result.success("添加域名成功", "添加域名【" + domainPO.getDomainValue() + "】成功");
        }
        return Result.fail("添加域名失败", "添加域名【" + domainPO.getDomainValue() + "】失败");
    }

    @PostMapping("/updateDomain")
    @ResponseBody
    public Result updateDomain(@RequestBody DomainPO domainPO) {
        if (Utils.isEmpty(domainPO.getDomainValue())) {
            return Result.fail("域名不能为空");
        }
        DomainPO byId = domainService.getById(domainPO.getId());
        if (byId == null) {
            return Result.fail("域名不存在", "域名不存在Id：" + domainPO.getId());
        }
        if (!byId.getDomainValue().equals(domainPO.getDomainValue())) {
            DomainPO domainByDomainValue = domainService.findDomainByDomainValue(domainPO.getDomainValue());
            if (domainByDomainValue != null) {
                return Result.fail("域名已存在", "域名【" + domainByDomainValue.getDomainValue() + "】已存在");
            }
        }
        boolean flag = domainService.updateById(domainPO);
        if (flag) {
            if (!byId.getDomainValue().equals(domainPO.getDomainValue())) {
                return Result.success("更新域名成功", "原域名【" + byId.getDomainValue() + "】成功修改为【" + domainPO.getDomainValue() +"】");
            }
            return Result.success("更新域名成功", "更新域名【" + domainPO.getDomainValue() + "】成功");
        }
        return Result.fail("更新域名失败", "更新域名【" + domainPO.getDomainValue() + "】失败");
    }

    @PostMapping("/updateDomainState")
    @ResponseBody
    public Result updateDomainState(@RequestParam("id") Long id, @RequestParam("state") Integer state) {
        DomainPO domainPO = domainService.getById(id);
        if (domainPO == null) {
            return Result.fail("域名不存在", "域名不存在Id：" + id);
        }
        if (state != SysConstant.STATE.DISABLE && state != SysConstant.STATE.ENABLE ) {
            return Result.fail("状态有误，请重新修改", "域名【"+ domainPO.getDomainValue() +"】状态有误：state=" + state);
        }
        boolean flag = domainService.updateById(domainPO);
        if (flag) {
            return Result.success("域名状态修改成功","域名【" + domainPO.getDomainValue() + "】状态成功修改为：" + (state == SysConstant.STATE.ENABLE ? "启用" : "禁用"));
        }
        return Result.fail("域名状态修改失败");
    }

}
