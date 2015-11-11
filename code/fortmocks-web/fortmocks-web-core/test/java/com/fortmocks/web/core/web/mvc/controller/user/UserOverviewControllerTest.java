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

package com.fortmocks.web.core.web.mvc.controller.user;

import com.fortmocks.core.model.user.Role;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindAllUsersInput;
import com.fortmocks.core.model.user.message.FindAllUsersOutput;
import com.fortmocks.web.core.config.TestApplication;
import com.fortmocks.web.core.model.user.dto.UserDtoGenerator;
import com.fortmocks.web.core.processor.ProcessorMainframe;
import com.fortmocks.web.core.web.mvc.controller.AbstractController;
import com.fortmocks.web.core.web.mvc.controller.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class UserOverviewControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/user/";
    private static final String PAGE = "partial/core/user/userOverview.jsp";
    private static final String USERS = "users";
    private static final String ROLES = "roles";
    private static final String COMMAND = "command";
    private static final Integer USER_COUNT = 5;


    @InjectMocks
    private UserOverviewController userOverviewController;

    @Mock
    private ProcessorMainframe processorMainframe;

    @Override
    protected AbstractController getController() {
        return userOverviewController;
    }

    @Test
    public void testGetUsersValid() throws Exception {
        final List<UserDto> userDtos = new ArrayList<UserDto>();
        for(int index = 0; index < USER_COUNT; index++){
            userDtos.add(UserDtoGenerator.generateUserDto());
        }
        final FindAllUsersOutput findAllUsersOutput = new FindAllUsersOutput();
        findAllUsersOutput.setUsers(userDtos);
        when(processorMainframe.process(any(FindAllUsersInput.class))).thenReturn(findAllUsersOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(ROLES, Role.values()))
                .andExpect(MockMvcResultMatchers.model().attribute(COMMAND, new UserDto()))
                .andExpect(MockMvcResultMatchers.model().attribute(USERS, userDtos));
    }

}