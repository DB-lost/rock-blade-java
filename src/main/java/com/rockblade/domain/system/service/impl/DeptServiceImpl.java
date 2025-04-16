package com.rockblade.domain.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.dto.request.DeptRequest;
import com.rockblade.domain.system.dto.response.DeptResponse;
import com.rockblade.domain.system.entity.Dept;
import com.rockblade.infrastructure.mapper.DeptMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.rockblade.domain.system.entity.table.DeptTableDef.DEPT;

/**
 * 部门表 服务层实现。
 *
 * @author rockblade
 * @since 2025-04-16
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public Page<DeptResponse> listDepts(DeptRequest request) {
        Page<Dept> page = new Page<>(request.getPageNum(), request.getPageSize());

        // 执行分页查询
        Page<Dept> deptPage = QueryChain.of(getMapper())
                .select()
                .from(DEPT)
                .where(DEPT.DELETED.eq(false))
                .and(DEPT.NAME.like(request.getName()).when(StringUtils.hasText(request.getName())))
                .and(DEPT.STATUS.eq(request.getStatus()).when(Objects.nonNull(request.getStatus())))
                .orderBy(DEPT.ORDER.asc())
                .page(page);

        // 转换结果
        List<DeptResponse> records = deptPage.getRecords()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        // 创建新的分页对象并设置值
        Page<DeptResponse> result = new Page<>();
        result.setRecords(records);
        result.setTotalRow(deptPage.getTotalRow());
        result.setPageSize(deptPage.getPageSize());
        result.setPageNumber(deptPage.getPageNumber());

        return result;
    }

    @Override
    public List<DeptResponse> listDeptTree(DeptRequest request) {
        List<Dept> depts = QueryChain.of(getMapper())
                .select()
                .from(DEPT)
                .where(DEPT.DELETED.eq(false))
                .and(DEPT.NAME.like(request.getName()).when(StringUtils.hasText(request.getName())))
                .and(DEPT.STATUS.eq(request.getStatus()).when(Objects.nonNull(request.getStatus())))
                .list();

        return buildDeptTree(depts);
    }

    @Override
    public DeptResponse getDeptById(String deptId) {
        Dept dept = getById(deptId);
        return convertToResponse(dept);
    }

    @Override
    public boolean addDept(DeptRequest request) {
        if (!checkDeptNameUnique(request.getName(), request.getPid(), null)) {
            throw new RuntimeException("部门名称已存在");
        }

        Dept dept = new Dept();
        BeanUtils.copyProperties(request, dept);

        // 处理祖级列表
        if (StringUtils.hasText(request.getPid())) {
            Dept parent = getById(request.getPid());
            dept.setAncestors(parent.getAncestors() + "," + parent.getId());
        } else {
            dept.setAncestors("0");
        }

        return save(dept);
    }

    @Override
    public boolean updateDept(DeptRequest request) {
        if (!checkDeptNameUnique(request.getName(), request.getPid(), request.getId())) {
            throw new RuntimeException("部门名称已存在");
        }

        Dept dept = getById(request.getId());
        String oldAncestors = dept.getAncestors();
        BeanUtils.copyProperties(request, dept);

        // 更新子部门祖级列表
        if (StringUtils.hasText(request.getPid())) {
            Dept parent = getById(request.getPid());
            dept.setAncestors(parent.getAncestors() + "," + parent.getId());
            updateChildrenAncestors(dept.getId(), oldAncestors, dept.getAncestors());
        }

        return updateById(dept);
    }

    @Override
    public boolean deleteDept(String deptId) {
        if (hasChildDept(deptId)) {
            throw new RuntimeException("存在子部门,不允许删除");
        }
        return removeById(deptId);
    }

    @Override
    public boolean checkDeptNameUnique(String deptName, String parentId, String deptId) {
        long count = QueryChain.of(getMapper())
                .select()
                .from(DEPT)
                .where(DEPT.DELETED.eq(false))
                .and(DEPT.NAME.eq(deptName))
                .and(DEPT.PID.eq(parentId))
                .and(DEPT.ID.ne(deptId).when(StringUtils.hasText(deptId)))
                .count();
        return count == 0;
    }

    @Override
    public boolean hasChildDept(String deptId) {
        long count = QueryChain.of(getMapper())
                .select()
                .from(DEPT)
                .where(DEPT.DELETED.eq(false))
                .and(DEPT.PID.eq(deptId))
                .count();
        return count > 0;
    }

    private DeptResponse convertToResponse(Dept dept) {
        if (dept == null) {
            return null;
        }
        DeptResponse response = new DeptResponse();
        BeanUtils.copyProperties(dept, response);
        return response;
    }

    private List<DeptResponse> buildDeptTree(List<Dept> depts) {
        List<DeptResponse> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        for (Dept dept : depts) {
            tempList.add(dept.getId());
        }

        for (Dept dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getPid())) {
                DeptResponse deptResponse = convertToResponse(dept);
                recursionFn(depts, deptResponse);
                returnList.add(deptResponse);
            }
        }

        if (returnList.isEmpty()) {
            returnList = depts.stream().map(this::convertToResponse).collect(Collectors.toList());
        }

        return returnList;
    }

    private void recursionFn(List<Dept> list, DeptResponse dept) {
        // 得到子节点列表
        List<DeptResponse> childList = getChildList(list, dept);
        dept.setChildren(childList);
        for (DeptResponse tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private List<DeptResponse> getChildList(List<Dept> list, DeptResponse dept) {
        return list.stream()
                .filter(d -> dept.getId().equals(d.getPid()))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private boolean hasChild(List<Dept> list, DeptResponse dept) {
        return list.stream().anyMatch(d -> dept.getId().equals(d.getPid()));
    }

    private void updateChildrenAncestors(String deptId, String oldAncestors, String newAncestors) {
        List<Dept> children = QueryChain.of(getMapper())
                .select()
                .from(DEPT)
                .where(DEPT.ANCESTORS.like(oldAncestors + "," + deptId + "%"))
                .list();

        for (Dept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
            updateById(child);
        }
    }
}
