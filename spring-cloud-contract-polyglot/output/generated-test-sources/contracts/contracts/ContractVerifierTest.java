package contracts;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import contracts.RestBase;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.RestAssured.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;

public class ContractVerifierTest extends RestBase {

	@Test
	public void validate_contract() throws Exception {
		// given:
			RequestSpecification request = given();

		// when:
			Response response = given().spec(request)
					.get("/hello");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['hello']").isEqualTo("world");
	}

}
