/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.sip.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * @author liujialiang
 * @description 账号信息
 * @team wuhan operational dev.
 * @date 2020/5/12 10:49 上午
 **/
@Data
@AllArgsConstructor
public class WsSessionInfo {

    String serviceId;
    String cno;
    String businessId;
    // 每个ws端的唯一id，前端给的，前端会根据此id进行消息展示的唯一标识，只有相同才会展示
    String wsConnectionId;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WsSessionInfo that = (WsSessionInfo) o;
        return Objects.equals(serviceId, that.serviceId)
                && Objects.equals(businessId, that.businessId) && Objects
                .equals(wsConnectionId, that.wsConnectionId) && Objects
                .equals(cno, that.cno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, cno, businessId, wsConnectionId);
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getWsConnectionId() {
        return wsConnectionId;
    }

    public void setWsConnectionId(String wsConnectionId) {
        this.wsConnectionId = wsConnectionId;
    }
}
