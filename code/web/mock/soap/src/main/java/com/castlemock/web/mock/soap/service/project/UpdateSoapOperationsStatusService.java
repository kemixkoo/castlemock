/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationsStatusInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapOperationsStatusOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateSoapOperationsStatusService extends AbstractSoapProjectService implements Service<UpdateSoapOperationsStatusInput, UpdateSoapOperationsStatusOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateSoapOperationsStatusOutput> process(final ServiceTask<UpdateSoapOperationsStatusInput> serviceTask) {
        final UpdateSoapOperationsStatusInput input = serviceTask.getInput();
        final SoapOperation soapOperation = this.operationRepository.findOne(input.getSoapOperationId());
        soapOperation.setStatus(input.getSoapOperationStatus());
        this.operationRepository.update(input.getSoapOperationId(), soapOperation);
        return createServiceResult(new UpdateSoapOperationsStatusOutput());
    }
}
