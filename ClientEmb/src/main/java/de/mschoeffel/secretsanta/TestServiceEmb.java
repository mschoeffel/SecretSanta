package de.mschoeffel.secretsanta;

import de.mschoeffel.secretsanta.TestClientService;
import de.mschoeffel.secretsanta.TestDto;
import org.springframework.stereotype.Service;

@Service
public class TestServiceEmb implements TestClientService {
    @Override
    public TestDto findById(Long id) {
        return new TestDto("test");
    }
}
