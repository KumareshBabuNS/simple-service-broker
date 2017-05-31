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

package io.pivotal.ecosystem.servicebroker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.servicebroker.model.GetLastServiceOperationResponse;
import org.springframework.cloud.servicebroker.model.OperationState;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastOperation implements Serializable {

    public static final long serialVersionUID = 1L;

    private Operation operation;
    private OperationState state;
    private String description;

    public GetLastServiceOperationResponse toResponse() {
        return new GetLastServiceOperationResponse().
                withDescription(getDescription()).
                withOperationState(getState()).
                withDeleteOperation(isDelete());
    }

    private boolean isDelete() {
        return Operation.DELETE.equals(operation);
    }
}
