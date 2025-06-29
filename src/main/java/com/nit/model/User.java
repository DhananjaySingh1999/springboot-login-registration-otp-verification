package com.nit.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "USERID")
	private Integer uid;
	
	@Column(name = "USERNAME")
	private String uname;
	
	@Column(name = "USEREMAIL")
	private String uemail;
	
	@Column(name = "USERPASSWORD")
	private String upwd;
	
	@Column(name = "USEROTP")
	private Integer userotp;
	
	@Column(name = "USERFLAG")
	private Boolean userflag=false;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tbl_role", joinColumns = @JoinColumn(name="USERID"))
	@Column(name = "ROLE")
	private List<String> role;
	
	public User() {
		
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public Integer getUserotp() {
		return userotp;
	}

	public void setUserotp(Integer userotp) {
		this.userotp = userotp;
	}

	public Boolean isUserflag() {
		return userflag;
	}

	public void setUserflag(Boolean userflag) {
		this.userflag = userflag;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + ", uemail=" + uemail + ", upwd=" + upwd + ", userotp="
				+ userotp + ", userflag=" + userflag + ", role=" + role + "]";
	}

	

	

}
