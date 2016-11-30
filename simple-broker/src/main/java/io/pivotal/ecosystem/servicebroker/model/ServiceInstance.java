/**
 Copyright (C) 2016-Present Pivotal Software, Inc. All rights reserved.

 This program and the accompanying materials are made available under
 the terms of the under the Apache License, Version 2.0 (the "License”);
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package io.pivotal.ecosystem.servicebroker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NonNull;
import org.springframework.cloud.servicebroker.model.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ServiceInstance implements Serializable {

    public static final long serialVersionUID = 1L;

    @JsonSerialize
    @JsonProperty("id")
    private String id;

    @JsonSerialize
    @JsonProperty("organization_guid")
    private String organizationGuid;

    @JsonSerialize
    @JsonProperty("plan_id")
    private String planId;

    @JsonSerialize
    @JsonProperty("service_id")
    private String serviceId;

    @JsonSerialize
    @JsonProperty("space_guid")
    private String spaceGuid;

    @JsonSerialize
    @JsonProperty("parameters")
    private final Map<String, Object> parameters = new HashMap<>();

    @JsonSerialize
    @JsonProperty("accepts_incomplete")
    private boolean acceptsIncomplete;

    //TODO deal with stuff in response bodies
    public ServiceInstance(CreateServiceInstanceRequest request) {
        super();
        this.id = request.getServiceInstanceId();
        this.organizationGuid = request.getOrganizationGuid();
        this.planId = request.getPlanId();
        this.serviceId = request.getServiceDefinitionId();
        this.spaceGuid = request.getSpaceGuid();

        if (request.getParameters() != null) {
            this.parameters.putAll(request.getParameters());
        }
    }

    public ServiceInstance(UpdateServiceInstanceRequest request) {
        super();
        this.id = request.getServiceInstanceId();
        this.planId = request.getPlanId();
        this.serviceId = request.getServiceDefinitionId();
        if (request.getParameters() != null) {
            this.parameters.putAll(request.getParameters());
        }
    }

    public void addParameter(@NonNull String key, @NonNull Object value) {
        this.parameters.put(key, value);
    }

    public Object getParameter(@NonNull String key) {
        return this.parameters.get(key);
    }

    public CreateServiceInstanceResponse getCreateResponse() {
        CreateServiceInstanceResponse resp = new CreateServiceInstanceResponse();
        resp.withAsync(this.acceptsIncomplete);
        return resp;
    }

    public DeleteServiceInstanceResponse getDeleteResponse() {
        DeleteServiceInstanceResponse resp = new DeleteServiceInstanceResponse();
        resp.withAsync(this.acceptsIncomplete);
        return resp;
    }

    public UpdateServiceInstanceResponse getUpdateResponse() {
        UpdateServiceInstanceResponse resp = new UpdateServiceInstanceResponse();
        resp.withAsync(this.acceptsIncomplete);
        return resp;
    }
}