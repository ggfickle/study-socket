package com.hf.study.socket.dao.entity;

import java.io.Serializable;
import java.sql.*;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

/**
 * 消息表
 * auto-generated by jpa-entity-generator
 */
@Data
@ToString
@Entity(name = "com.hf.study.socket.dao.entity.ImMessage")
@Table(name = "im_message")
public class ImMessage implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"id\"")
  private Long id;
  /**
   * 发送用户id
   */
  @Column(name = "\"from_user_id\"")
  private Long fromUserId;

  /**
   * 接收
   */
  @Column(name = "\"to_user_id\"")
  private Long toUserId;
  /**
   * 消息体
   */
  @Column(name = "\"message\"")
  private String message;
  /**
   * 状态：0：待接收，1：已接收
   */
  @Column(name = "\"receive_status\"")
  private Integer receiveStatus;
  /**
   * 状态：0：未读，1：已读
   */
  @Column(name = "\"read_status\"")
  private Integer readStatus;
  /**
   * 创建时间
   */
  @Column(name = "\"create_time\"")
  private Timestamp createTime;
  /**
   * 创建时间
   */
  @Column(name = "\"update_time\"")
  private Timestamp updateTime;
}