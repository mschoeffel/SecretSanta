package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.runner.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AcceptOverduePartner {

    private GroupMemberRepository groupMemberRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    public AcceptOverduePartner(GroupMemberRepository groupMemberRepository){
        this.groupMemberRepository = groupMemberRepository;
    }

    public void execute(){
        List<GroupMember> groupMember = groupMemberRepository.findAll();
        LocalDateTime time = LocalDateTime.now().minusMinutes(5);
        for(GroupMember member : groupMember){
            if(!(member.getDrawAccepted() != null && member.getDrawAccepted())) {
                if (member.getLastDrawTime() != null) {
                    if (member.getLastDrawTime().isBefore(time)) {
                        LOG.info("Auto accepted: " + member.getGroup().getName() + " " + member.getName() + " Time: " + member.getLastDrawTime());
                        member.setDrawAccepted(true);
                        groupMemberRepository.save(member);
                    }
                }
            }
        }
    }
}
