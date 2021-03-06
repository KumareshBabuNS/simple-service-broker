/*
 * Copyright (C) 2016-Present Pivotal Software, Inc. All rights reserved.
 * <p>
 * This program and the accompanying materials are made available under
 * the terms of the under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.ecosystem.servicebroker;

import io.pivotal.ecosystem.servicebroker.model.LastOperation;
import io.pivotal.ecosystem.servicebroker.model.ServiceBinding;
import io.pivotal.ecosystem.servicebroker.model.ServiceInstance;
import io.pivotal.ecosystem.servicebroker.service.ServiceBindingRepository;
import io.pivotal.ecosystem.servicebroker.service.ServiceInstanceRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
class TestConfig {

    private static final String SI_ID = "siId";
    private static final String SB_ID = "sbId";

    static final String SD_ID = "aUniqueId";
    static final String PLAN_ID = "anotherUniqueId";
    private static final String APP_GUID = "anAppGuid";
    private static final String ORG_GUID = "anOrgGuid";
    private static final String SPACE_GUID = "aSpaceGuid";

    @MockBean
    private ServiceBindingRepository serviceBindingRepository;

    @MockBean
    private ServiceInstanceRepository serviceInstanceRepository;

    @MockBean
    HelloBrokerRepository helloBrokerRepository;

    @Bean
    public CreateServiceInstanceRequest createServiceInstanceRequest() {
        CreateServiceInstanceRequest req = new CreateServiceInstanceRequest(SD_ID, PLAN_ID, ORG_GUID, SPACE_GUID, getParameters());
        req.withServiceInstanceId(SI_ID);
        return req;
    }

    @Bean
    public ServiceInstance serviceInstance(CreateServiceInstanceRequest req) {
        ServiceInstance si = new ServiceInstance(req);
        si.setLastOperation(new LastOperation(LastOperation.CREATE, LastOperation.SUCCEEDED, "created."));
        si.addParameter(HelloBroker.USER_NAME_KEY, "world");
        si.addParameter(HelloBroker.PASSWORD_KEY, "guest");
        si.addParameter(HelloBroker.ROLE_KEY, User.Role.User);
        return si;
    }

    private Map<String, Object> getBindResources() {
        Map<String, Object> m = new HashMap<>();
        m.put("app_guid", APP_GUID);
        return m;
    }

    private Map<String, Object> getParameters() {
        Map<String, Object> m = new HashMap<>();
        m.put("foo", "bar");
        m.put("bizz", "bazz");
        return m;
    }

    @Bean
    public CreateServiceInstanceBindingRequest createBindingRequest() {
        CreateServiceInstanceBindingRequest req = new CreateServiceInstanceBindingRequest(SD_ID, PLAN_ID, APP_GUID,
                getBindResources(), getParameters());
        req.withBindingId(SB_ID);
        req.withServiceInstanceId(SI_ID);
        return req;
    }

    @Bean
    public ServiceBinding serviceBinding(CreateServiceInstanceBindingRequest req) {
        return new ServiceBinding(req);
    }

    static CreateServiceInstanceRequest createRequest(String id, boolean async) {
        CreateServiceInstanceRequest req = new CreateServiceInstanceRequest(TestConfig.SD_ID, TestConfig.PLAN_ID, TestConfig.ORG_GUID, TestConfig.SPACE_GUID, new HashMap<>());
        if (async) {
            req.withAsyncAccepted(true);
        }
        req.withServiceInstanceId(id);
        return req;
    }

    static GetLastServiceOperationRequest lastOperationRequest(String id) {
        return new GetLastServiceOperationRequest(id);
    }

    static UpdateServiceInstanceRequest updateRequest(String id, boolean async) {
        UpdateServiceInstanceRequest req = new UpdateServiceInstanceRequest(TestConfig.SD_ID, TestConfig.PLAN_ID);
        if (async) {
            req.withAsyncAccepted(true);
        }
        req.withServiceInstanceId(id);
        return req;
    }

    static DeleteServiceInstanceRequest deleteRequest(String id, boolean async) {
        return new DeleteServiceInstanceRequest(id, TestConfig.SD_ID, TestConfig.PLAN_ID, null, async);
    }
}