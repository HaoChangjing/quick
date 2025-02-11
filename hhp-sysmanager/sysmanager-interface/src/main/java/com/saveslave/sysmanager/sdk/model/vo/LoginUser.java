package com.saveslave.sysmanager.sdk.model.vo;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.*;

/**
 * @author So_Ea
 * @date 2020/7/29
 * @since V1.0
 */
@Data
@Accessors(chain = true)
public class LoginUser implements SocialUserDetails {

    private static final long serialVersionUID = -4549718312483024106L;

    private Long id;

    private String loginPwd;

    private String loginAccount;

    private String isEnable;
    /**
     * company：企业用户 agent：代理用户  supplier：供应商用户
     */
    private String userType;

    /**
     * lzlj 字段：company为组织机构编码，agent为经销商编码，supplier为供应商编码
     */
    private String userTypeCode;

    /**
     * lzlj 字段：company为组织机构名称，agent为经销商名称，supplier为供应商名称
     */
    private String userTypeName;

    /**
     * 用户所属机构   供应商没有机构ID
     */
    private Long orgId;

    /**
     * 用户所属机构编码   供应商没有机构ID
     */
    private String orgCode;

    /**
     * 用户所属机构名称   供应商没有机构ID
     */
    private String orgName;

    private String sotype;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 授权客户端集合
     */
    private List<String> sotypeList;

    private List<RoleInfo> roles;

    /**
     * 密码过期时间
     */
    private Date pwdExpireTime;

    /**
     * 是否允许登录 根据用户登录端来源 判断用户是否有相关系统角色
     */
    private boolean isAllowLogin = false;

    /**
     * 用户拥有的所有菜单code
     */
    private List<String> menuCodes = new ArrayList<>();

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 登录次数
     */
    private Integer loginTimes;

    /**
     * 是否已重置密码 0：是 1：否
     */
    private Integer isReset;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String getUserId() {
        Long id = this.getId();
        return id == null ? null : id.toString();
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (CollectionUtil.isNotEmpty(this.getRoles())) {
            this.getRoles().parallelStream().forEach(role -> collection.add(new SimpleGrantedAuthority(role.getRoleCode())));
        }
        return collection;
    }

    @Override
    public String getPassword() {
        return this.getLoginPwd();
    }

    @Override
    public String getUsername() {
        return this.getLoginAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(this.getIsEnable(),"1");
    }
}
