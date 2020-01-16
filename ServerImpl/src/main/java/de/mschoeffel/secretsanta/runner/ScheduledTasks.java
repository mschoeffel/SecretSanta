package de.mschoeffel.secretsanta.runner;

import de.mschoeffel.secretsanta.interaction.AcceptOverduePartner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private AcceptOverduePartner acceptOverduePartner;

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron = "*/60 * * * * *")
    public void acceptOverduePartner(){
        LOG.info("Accepting overdue Partner...");
        acceptOverduePartner.execute();
    }
}
