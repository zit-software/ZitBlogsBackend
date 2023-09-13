package com.zit.blog;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class AfterAllIfAllTestsSucceededExtension implements BeforeAllCallback, AfterTestExecutionCallback, AfterAllCallback {
    private static final Namespace NAMESPACE = Namespace.create(AfterAllIfAllTestsSucceededExtension.class);
    private static final String ALL_TESTS_PASSED_KEY = "allTestsPassed";
    private boolean allTestsPassed = true;
    private static int successTestModuleCount = 0;
    private static final int testModuleSize = 2;
    private String testTaker;
    private String judgeServerURL;
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private TestingConfiguration testingConfiguration;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        testingConfiguration = SpringExtension.getApplicationContext(context).getBean(TestingConfiguration.class);
        Store store = getStore(context);
        store.put(ALL_TESTS_PASSED_KEY, allTestsPassed);
        testTaker = testingConfiguration.getTestTaker();
        judgeServerURL = testingConfiguration.getJudgeServerURL();
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isPresent()) {
            allTestsPassed = false;
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Store store = getStore(context);
        Boolean allTestsPassedValue = store.get(ALL_TESTS_PASSED_KEY, Boolean.class);
        successTestModuleCount++;

        if (allTestsPassedValue != null && allTestsPassedValue && successTestModuleCount == testModuleSize) {
            // Execute your code only if all tests have succeeded
            System.out.println("All tests have succeeded. Saving your record...");
            try {
                AddRecordResponse record = restTemplate.postForObject(judgeServerURL + "/record",
                        new Record(null, testTaker, new Date()), AddRecordResponse.class);
                assert record != null;
                System.out.println(record.getName() + " has completed all tasks.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Store getStore(ExtensionContext context) {
        return context.getRoot().getStore(NAMESPACE);
    }
}
