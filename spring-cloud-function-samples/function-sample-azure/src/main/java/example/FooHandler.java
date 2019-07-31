/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.AzureSpringBootRequestHandler;

import java.util.Optional;

/**
 * @author Soby Chacko
 */
public class FooHandler extends AzureSpringBootRequestHandler<String, String> {

	private static String myKey = System.getenv("testeKey");

	@FunctionName("uppercase")
	public HttpResponseMessage execute(
		@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS)HttpRequestMessage<Optional<String>> request,
		ExecutionContext context) {

		// Parse query parameter
		String query = request.getQueryParameters().get("name");
		String name = request.getBody().orElse(query);

		HttpResponseMessage responseMessage = null;

		if (name == null) {
			responseMessage = request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Adicione um parametro String para conversão ?name=[STRING]").build();
		} else {
			String uppercase = handleRequest(name, context);
			responseMessage = request.createResponseBuilder(HttpStatus.OK).body("E ai?, " + myKey + " " + uppercase).build();
		}

		return responseMessage;

	}

}
