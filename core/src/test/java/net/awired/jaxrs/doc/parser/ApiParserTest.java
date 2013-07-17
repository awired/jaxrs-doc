/**
 *
 *     Copyright (C) Awired.net
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package net.awired.jaxrs.doc.parser;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import net.awired.jaxrs.doc.DocConfig;
import net.awired.jaxrs.doc.domain.ApiDefinition;
import net.awired.jaxrs.doc.domain.ProjectDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApiParserTest {

    private ProjectDefinition p = new ProjectDefinition();

    @Mock
    private OperationParser operationParser;

    @InjectMocks
    private DocConfig config = new DocConfig(Arrays.asList("net.awired"));

    private ApiParser parser = new ApiParser(config);

    @Path("there")
    class TestResource {

    }

    @Path("mypath")
    interface TestInterace {

    }

    class TestInterface implements TestInterace {

    }

    class TestExtends extends TestResource {

    }

    class TestOperation extends TestResource {

        @GET
        public void operation() {

        }

        public void notOperation() {

        }

    }

    ArgumentCaptor<ApiDefinition> captor = ArgumentCaptor.forClass(ApiDefinition.class);

    @Test
    public void should_call_operation_parser() throws Exception {
        Method method = TestOperation.class.getMethod("operation");
        when(operationParser.isOperation(method)).thenReturn(true);
        when(operationParser.isOperation(TestOperation.class.getMethod("notOperation"))).thenReturn(false);

        parser.parse(p, TestOperation.class);
        verify(operationParser).parse(eq(p), captor.capture(), eq(method));
        assertThat(captor.getValue().getPath()).isEqualTo("there");
        assertThat((Object) captor.getValue().getResourceClass()).isEqualTo(TestOperation.class);
        verify(operationParser, never()).parse(eq(p), captor.capture(),
                eq(TestOperation.class.getMethod("notOperation")));
    }
}
