package com.frc.service


import com.frc.repository.ChoiceGroupRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@CompileStatic
@Slf4j
@Service
@Transactional
class ChoiceGroupService {

    @Autowired
    ChoiceGroupRepository repository
}
