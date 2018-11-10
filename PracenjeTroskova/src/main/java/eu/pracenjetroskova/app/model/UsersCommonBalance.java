package eu.pracenjetroskova.app.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "users_common_balance")
public class UsersCommonBalance {
 
    @EmbeddedId
    private UsersCommonBalanceId id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cbId")
    private CommonBalance commonbalance;
 
    @Column(name = "status")
    private String status;
 
}