package io.pivotal.cf.service.connector;

import feign.Feign;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class HelloRepositoryFactory {

    HelloRepository create(HelloServiceInfo info) {
        log.info("creating helloRepository with info: " + info);

        return Feign.builder()
                .target(HelloRepository.class, info.getUri());
    }
}
