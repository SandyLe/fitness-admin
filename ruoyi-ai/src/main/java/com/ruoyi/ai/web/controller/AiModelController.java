package com.ruoyi.ai.web.controller;

import java.util.List;

import com.ruoyi.ai.domain.Agent;
import com.ruoyi.ai.service.IAgentService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.i18n.MessageUtils;
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
import com.ruoyi.ai.domain.AiModel;
import com.ruoyi.ai.service.IAiModelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * AI模型配置Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/ai/model")
public class AiModelController extends BaseController
{
    @Autowired
    private IAiModelService aiModelService;

    @Autowired
    private IAgentService agentService;

    /**
     * 查询AI模型配置列表
     */
    @PreAuthorize("@ss.hasPermi('ai:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiModel aiModel)
    {
        //列表查询设置用户
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            aiModel.setCreateBy(getUsername());
        }

        startPage();
        List<AiModel> list = aiModelService.selectAiModelList(aiModel);
        return getDataTable(list);
    }

    /**
     * 导出AI模型配置列表
     */
    @PreAuthorize("@ss.hasPermi('ai:model:export')")
    @Log(title = "AI模型配置", code = "log.ai.aiModel", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiModel aiModel)
    {
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            aiModel.setCreateBy(getUsername());
        }
        List<AiModel> list = aiModelService.selectAiModelList(aiModel);
        ExcelUtil<AiModel> util = new ExcelUtil<AiModel>(AiModel.class);
        util.exportExcel(response, list, "AI模型配置数据");
    }

    /**
     * 获取AI模型配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aiModelService.selectAiModelById(id));
    }

    /**
     * 新增AI模型配置
     */
    @PreAuthorize("@ss.hasPermi('ai:model:add')")
    @Log(title = "AI模型配置", code = "log.ai.aiModel", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiModel aiModel)
    {
        return toAjax(aiModelService.insertAiModel(aiModel));
    }

    /**
     * 修改AI模型配置
     */
    @PreAuthorize("@ss.hasPermi('ai:model:edit')")
    @Log(title = "AI模型配置", code = "log.ai.aiModel", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiModel aiModel)
    {
        return toAjax(aiModelService.updateAiModel(aiModel));
    }

    /**
     * 删除AI模型配置 这里的珊瑚需要增加判断，如果有智能体引用了这个模型，则不允许删除
     */
    @PreAuthorize("@ss.hasPermi('ai:model:remove')")
    @Log(title = "AI模型配置", code = "log.ai.aiModel", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        //删除之前，先验证是否有智能体引用，如果有，提示用户先删除智能体
        if(null != ids && ids.length>0){
            for(Long modelId:ids){
                Agent agent = new Agent();
                agent.setModelId(modelId);
                if(!Constants.SUPER_ADMIN.equals(getUsername())){
                    agent.setUserId(getUserId());
                }
                List<Agent> list = agentService.selectAgentList(agent);
                if(null != list && !list.isEmpty()){
                    return error(MessageUtils.message("bus.ai.model.no.delete",list.get(0).getAgentName()));
                }
            }
        }
        return toAjax(aiModelService.deleteAiModelByIds(ids));
    }


}
