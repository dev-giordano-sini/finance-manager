package it.financemanager.infrastructure.persistence.entity;import jakarta.persistence.*;
@Entity @Table(name="roles") public class RoleEntity extends JpaBaseEntity {@Column(nullable=false,unique=true,length=50) public String code;public String description;protected RoleEntity(){}public RoleEntity(String c){code=c;}}
