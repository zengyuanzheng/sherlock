package china.z.admin.entity;

import javax.persistence.*;

@Entity
@Table(name = "role_permission", schema = "starter")
public class TRolePermissionEntity {
    private String uuid;
    private String roleUuid;
    private String permissionUuid;
    private String createTime;
    private String createUserUuid;

    @Id
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "role_uuid")
    public String getRoleUuid() {
        return roleUuid;
    }

    public void setRoleUuid(String roleUuid) {
        this.roleUuid = roleUuid;
    }

    @Basic
    @Column(name = "permission_uuid")
    public String getPermissionUuid() {
        return permissionUuid;
    }

    public void setPermissionUuid(String permissionUuid) {
        this.permissionUuid = permissionUuid;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_user_uuid")
    public String getCreateUserUuid() {
        return createUserUuid;
    }

    public void setCreateUserUuid(String createUserUuid) {
        this.createUserUuid = createUserUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TRolePermissionEntity that = (TRolePermissionEntity) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (roleUuid != null ? !roleUuid.equals(that.roleUuid) : that.roleUuid != null) return false;
        if (permissionUuid != null ? !permissionUuid.equals(that.permissionUuid) : that.permissionUuid != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUserUuid != null ? !createUserUuid.equals(that.createUserUuid) : that.createUserUuid != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (roleUuid != null ? roleUuid.hashCode() : 0);
        result = 31 * result + (permissionUuid != null ? permissionUuid.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUserUuid != null ? createUserUuid.hashCode() : 0);
        return result;
    }
}
