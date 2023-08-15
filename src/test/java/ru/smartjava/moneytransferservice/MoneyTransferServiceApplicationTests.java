package ru.smartjava.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.smartjava.dto.ConfirmMessage;
import ru.smartjava.dto.ErrorMessage;
import ru.smartjava.dto.OperationId;
import ru.smartjava.dto.Transfer;
import ru.smartjava.utils.ObjectsUtils;

import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private final Integer port = 5500;

	private Transfer transfer;

	private Transfer wrongTransfer;

	private ConfirmMessage confirmMessage;

	@Container
	private final GenericContainer<?> myAppContainer = new GenericContainer<>("transfermoney")
			.withExposedPorts(port);
//			private final GenericContainer<?> myAppContainer = new GenericContainer<>(
//					new ImageFromDockerfile()
//							.withDockerfile(Paths.get("./Dockerfile"))
//	)
//			.withExposedPorts(port);

	@BeforeEach
	void initData() {
		transfer = ObjectsUtils.getTransfer();
		wrongTransfer = ObjectsUtils.getWrongTransfer();
		confirmMessage = ObjectsUtils.getConfirmMessage();
	}

	@Test
	void transferSuccessTest() {
		ResponseEntity<OperationId> postResponseEntity = restTemplate.postForEntity("http://localhost:" + myAppContainer.getMappedPort(port) + "/transfer", transfer, OperationId.class);
		System.out.println(postResponseEntity.getBody());
		Assertions.assertEquals(HttpStatus.OK.value(), postResponseEntity.getStatusCodeValue());
		Assertions.assertNotNull(postResponseEntity.getBody());
		Assertions.assertNotNull(postResponseEntity.getBody().getOperationId());
	}

	@Test
	void transferWrongTest() {
		ResponseEntity<ErrorMessage> postResponseEntity = restTemplate.postForEntity("http://localhost:" + myAppContainer.getMappedPort(port) + "/transfer", wrongTransfer, ErrorMessage.class);
		System.out.println(postResponseEntity.getBody());
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), postResponseEntity.getStatusCodeValue());
		Assertions.assertNotNull(postResponseEntity.getBody());
		Assertions.assertNotNull(postResponseEntity.getBody().getId());
	}

	@Test
	void confirmSuccessOperationTest() {
		ResponseEntity<OperationId> addEntity = restTemplate.postForEntity("http://localhost:" + myAppContainer.getMappedPort(port) + "/transfer", transfer, OperationId.class);
		confirmMessage.setOperationId(addEntity.getBody());
		ResponseEntity<OperationId> postResponseEntity = restTemplate.postForEntity("http://localhost:" + myAppContainer.getMappedPort(port) + "/confirmOperation", confirmMessage, OperationId.class);
		System.out.println(postResponseEntity.getBody());
		Assertions.assertEquals(HttpStatus.OK.value(), postResponseEntity.getStatusCodeValue());
		Assertions.assertEquals(Objects.requireNonNull(postResponseEntity.getBody()).toString(), confirmMessage.getOperationId().toString());
	}

	@Test
	void confirmWrongOperationTest() {
		ResponseEntity<ErrorMessage> postResponseEntity = restTemplate.postForEntity("http://localhost:" + myAppContainer.getMappedPort(port) + "/confirmOperation", confirmMessage, ErrorMessage.class);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), postResponseEntity.getStatusCodeValue());
		Assertions.assertEquals(Objects.requireNonNull(postResponseEntity.getBody()).getId(), HttpStatus.BAD_REQUEST.value());
	}

}
