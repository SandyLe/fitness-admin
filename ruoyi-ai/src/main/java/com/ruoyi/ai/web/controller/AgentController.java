package com.ruoyi.ai.web.controller;

import java.util.List;

import com.ruoyi.common.constant.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.ai.domain.Agent;
import com.ruoyi.ai.service.IAgentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 智能体Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/ai/agent")
public class AgentController extends BaseController
{
    @Autowired
    private IAgentService agentService;

    /**
     * 查询智能体列表
     */
    @PreAuthorize("@ss.hasPermi('ai:agent:list')")
    @GetMapping("/list")
    public TableDataInfo list(Agent agent)
    {
        //列表查询设置用户
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            agent.setUserId(getUserId());
        }
        startPage();
        List<Agent> list = agentService.selectAgentList(agent);
        return getDataTable(list);
    }

    /**
     * 导出智能体列表
     */
    @PreAuthorize("@ss.hasPermi('ai:agent:export')")
    @Log(title = "智能体", code = "log.ai.agent", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Agent agent)
    {
        //列表查询设置用户
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            agent.setUserId(getUserId());
        }
        List<Agent> list = agentService.selectAgentList(agent);
        ExcelUtil<Agent> util = new ExcelUtil<Agent>(Agent.class);
        util.exportExcel(response, list, "智能体数据");
    }

    /**
     * 获取智能体详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:agent:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(agentService.selectAgentById(id));
    }

    /**
     * 新增智能体
     */
    @PreAuthorize("@ss.hasPermi('ai:agent:add')")
    @Log(title = "智能体", code = "log.ai.agent", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Agent agent)
    {
        return toAjax(agentService.insertAgent(agent));
    }

    /**
     * 修改智能体
     */
    @PreAuthorize("@ss.hasPermi('ai:agent:edit')")
    @Log(title = "智能体", code = "log.ai.agent", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Agent agent)
    {
        return toAjax(agentService.updateAgent(agent));
    }

    /**
     * 删除智能体
     */
    @PreAuthorize("@ss.hasPermi('ai:agent:remove')")
    @Log(title = "智能体", code = "log.ai.agent", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(agentService.deleteAgentByIds(ids));
        return toAjax(agentService.deleteAgent(ids));
    }
}
